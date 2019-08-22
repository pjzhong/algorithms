package com.pjzhong.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Test behavior of Memory mapped buffer types. Create a file ,write
 * some data to it, then create three different types of mappings
 * to it. Observe the effects of changes through the buffer APIs and updating the file directly.
 * The data spans page boundaries  to illustrate to page-oriented nature of Copy-On-Write mappings.
 * */
public class MapFile {

    public static void main(String[] args) throws Exception {
        //Create a temp file and get a channel connected to it
        File tempFile = File.createTempFile("mmaptest", null);
        RandomAccessFile file = new RandomAccessFile(tempFile, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer temp = ByteBuffer.allocate(100);

        //Put something in the file, starting at location 0
        temp.put(bytes("This is the file content"));
        temp.flip();
        channel.write(temp, 0);

        //Put something else in the file, starting at location 8192.
        //8192 is 8 KB, almost certainly a different memory/FS page.
        //This may cause a file hole, depending on the filesystem page size
        temp.clear();
        temp.put(bytes("This is more file content"));
        temp.flip();
        channel.write(temp, 8192);

        //Create three type of mappings to the same file
        MappedByteBuffer ro = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        MappedByteBuffer rw = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        MappedByteBuffer cow = channel.map(FileChannel.MapMode.PRIVATE, 0, channel.size());

        //the Buffer states before any modifications
        System.out.println("Begin");
        showBuffers(ro, rw, cow);

        //Modify the copy-on-write buffer
        cow.position(8);
        cow.put(bytes("COW"));

        System.out.println("Change to COW buffer");
        showBuffers(ro, rw, cow);

        //Modify the read/write buffer
        rw.position(9);
        rw.put(bytes(" R/W "));
        rw.position(8194);
        rw.put(bytes(" R/W "));
        rw.force();

        System.out.println("Change to R/W buffer");
        showBuffers(ro, rw, cow);

        //Write to the file through the channel; hit both pages
        temp.clear();
        temp.put(bytes("Channel write"));
        temp.flip();
        channel.write(temp, 0);
        temp.rewind();
        channel.write(temp, 8202);

        System.out.println("Write on channel");
        showBuffers(ro, rw, cow);

        //Modify the copy-on-write buffer again
        cow.position(8207);
        cow.put(bytes(" COW2 "));

        System.out.println("Second change to COW buffer");
        showBuffers(ro, rw, cow);

        //Modify the read/write buffer
        rw.position(0);
        rw.put(bytes(" R/W2 "));
        rw.position(8210);
        rw.put(bytes(" R/W2 "));
        rw.force();

        System.out.println("Second change to R/W buffer");
        showBuffers(ro, rw, cow);

        // cleanup
        channel.close();
        file.close();
        tempFile.delete();
    }

    private static void showBuffers(ByteBuffer ro, ByteBuffer rw, ByteBuffer row) {
        dumpBuffer("R/O", ro);
        dumpBuffer("R/W", rw);
        dumpBuffer("COW", row);
        System.out.println();
    }

    private static void dumpBuffer(String prefix, ByteBuffer buffer) {
        System.out.print(prefix = ": '");

        int nulls = 0;
        int limit = buffer.limit();

        for(int i = 0; i < limit; i++) {
            char c= (char) buffer.get(i);
            if(c == '\u0000') {
                nulls++;
            } else {
                if(nulls != 0) {
                    System.out.format("|[%d nulls]|", nulls);
                    nulls = 0;
                }
                System.out.print(c);
            }
        }
        System.out.println("'");
    }

    private static byte[] bytes(String str) throws Exception {
        return str.getBytes("UTF-8");
    }
}
