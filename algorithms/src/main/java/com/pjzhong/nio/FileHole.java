package com.pjzhong.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Create a file with holes in it
 */
public class FileHole {

  public static void main(String[] args) throws Exception {
    File temp = File.createTempFile("holy", null);
    RandomAccessFile file = new RandomAccessFile(temp, "rw");
    FileChannel channel = file.getChannel();
    //Create a working buffer
    ByteBuffer byteBuffer = ByteBuffer.allocate(100);

    putData(0, byteBuffer, channel);
    putData(5000000, byteBuffer, channel);
    putData(5000, byteBuffer, channel);

    //Size will report the largest position written, but there are two holes in this file.
    //This file will not consume 5MB on disk(unless the filesystem is extremely brain-damaged)
    System.out.format("Wrote temp file:'%s', size=%d \n", temp.getPath(), channel.size());

    channel.close();
    file.close();
  }

  private static void putData(int position, ByteBuffer buffer, FileChannel channel)
      throws Exception {
    String string = "*<-- location " + position;

    buffer.clear();
    buffer.put(string.getBytes("US-ASCII"));
    buffer.flip();

    channel.position(position);
    channel.write(buffer);
  }
}
