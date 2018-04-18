package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopy {

    public static void main(String[] args) throws Exception {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel target = Channels.newChannel(System.out);

        channelCopyOne(source, target);

        source.close();
        target.close();
    }

    /**
     * This method copies data from the src channel and writes it to the dest channel until EOF on src.
     * This implementation makes use of compact() on the temp buffer to pack down the data if the buffer
     * wasn't fully drained. This may result in data copying, but minimizes system calls. It also requires a
     * cleanup loop to make sure all the data gets sent.
     * */
    private static void channelCopyOne(ReadableByteChannel source, WritableByteChannel target) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);

        while(source.read(buffer) != -1) {
            buffer.flip();
            target.write(buffer);
            buffer.compact();
        }

        buffer.flip();
        while(buffer.hasRemaining()) {
            target.write(buffer);
        }
    }

    /**
     * This method performs the same copy, but assures the temp buffer is empty before reading more data.This never
     * requires data copying but may result in more systems calls.No post-loop cleanup is needed because the buffer
     * will be empty when the loop is exited
     * */
    private static void channelCopyTwo(ReadableByteChannel source, WritableByteChannel target) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);

        while(source.read(buffer) != -1) {
            buffer.flip();
            while(buffer.hasRemaining()) {
                target.write(buffer);
            }
            buffer.clear();
        }
    }
}
