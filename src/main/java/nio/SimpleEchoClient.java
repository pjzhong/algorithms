package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Simple echo-back server which listens for incoming stream  connections and echos back whatever it  reads. A Single
 * Selector object is used to listen to the server socket (to accept new Connections) and all the active socket channels.
 *
 * */
public class SimpleEchoClient {

    private static int PORT_NUMBER = 1234;

    public void go(String[] args) throws Exception {
        int port = PORT_NUMBER;

        if(0 < args.length) {
            port = Integer.parseInt(args[0]);
        }

        System.out.println("Listening on port " + port);

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            int n = selector.select();
            if(n != 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                while(it.hasNext()) {
                    SelectionKey key = it.next();

                    if(key.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel channel = serverSocketChannel.accept();
                        SelectionKey client = registerChannel(selector, channel);
                        ((Handler)client.attachment()).sayHello();
                    } else if(key.isReadable()) {
                        ((Handler)key.attachment()).read();
                    } else if(key.isWritable()) {
                        ((Handler)key.attachment()).write();
                    }

                    it.remove();
                }
            }
        }
    }

    protected SelectionKey registerChannel(Selector selector, SocketChannel channel) throws Exception {
        if(channel != null) {
            channel.configureBlocking(false);
            SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
            Handler handler = new Handler(channel, key);
            key.attach(handler);
            return key;
        }

        throw new IllegalArgumentException("Channel can't be null");
    }

    private static class Handler {
        private SocketChannel socketChannel;
        private SelectionKey selectionKey;
        private ByteBuffer prefix = ByteBuffer.wrap("\r\n\nREPLY:".getBytes());
        private ByteBuffer separateLine = ByteBuffer.wrap("\r\n-----------------------------------\r\n".getBytes());
        private ByteBuffer buffer = ByteBuffer.allocate(32);

        public Handler(SocketChannel socketChannel, SelectionKey selectionKey) {
            this.socketChannel = socketChannel;
            this.selectionKey = selectionKey;
        }

        /**
         * Sample data handler method for a channel with data ready to read.
         * key A SelectionKey object associated with a channel
         * determined by the selector be be read for reading. If the channel
         * return an EOF condition, it is closed here, which automatically invalidates the associated
         * key. The selector will then de-register the channel on the next select call.
         * */
        public void read() throws IOException {
            socketChannel.read(buffer);
            if(!buffer.hasRemaining()) {
                String input = new String((buffer.array())).trim();
                if(input.isEmpty()) {
                    socketChannel.close();
                } else {
                    System.out.println(input);
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                }
            }
        }

        public void write() throws IOException {
            prefix.flip();
            socketChannel.write(prefix);

            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();

            separateLine.flip();
            socketChannel.write(separateLine);

            selectionKey.interestOps(SelectionKey.OP_READ);
        }

        public void sayHello() throws Exception {
            socketChannel.write(prefix);
            String hi = String.format("Hi there! I would repeat after you each  %d characters", buffer.capacity());
            socketChannel.write(ByteBuffer.wrap(hi.getBytes("UTF-8")));
            socketChannel.write(separateLine);
            buffer.clear();
        }

    }

    public static void main(String[] args) throws Exception {
        SimpleEchoClient sockets = new SimpleEchoClient();
        sockets.go(args);
    }
}
