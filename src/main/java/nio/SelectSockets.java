package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Simple echo-back server which listens for incoming stream  connections and echos back whatever it  reads. A Single
 * Selector object is used to listen to the server socket (to accept new Connections) and all the active socket channels.
 *
 * */
public class SelectSockets {

    private static int PORT_NUMBER = 1234;

    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

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
                        registerChannel(selector, channel, SelectionKey.OP_READ);
                        sayHello(channel);
                    } else if(key.isReadable()) {
                        readDataFromSocket(key);
                    }

                    it.remove();
                }
            }
        }
    }

    protected void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception {
        if(channel != null) {
            channel.configureBlocking(false);
            channel.register(selector, ops);
        }
    }

    /**
     * Sample data handler method for a channel with data ready to read.
     * @param key A SelectionKey object associated with a channel
     * determined by the selector be be read for reading. If the channel
     * return an EOF condition, it is closed here, which automatically invalidates the associated
     * key. The selector will then de-register the channel on the next select call.
     * */
    protected void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;

        buffer.clear();

        while(0 < (count = socketChannel.read(buffer))) {
            buffer.flip();

            while(buffer.hasRemaining()) { socketChannel.write(buffer); }
            //WARNING: the above loop is evil. Because it's writing back to
            //the same nonblocking channel it read the data from, this code
            //can potentially spin in a busy loop(maybe the channel is not writable at this moment). In read life
            //you'd do something more useful than this

            buffer.clear();
        }

        if(count < 0) {
            socketChannel.close();
        }
    }

    private void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hi there!\r\n".getBytes("UTF-8"));
        buffer.flip();

        channel.write(buffer);
    }

    public static void main(String[] args) throws Exception {
        SelectSockets sockets = new SelectSockets();
        sockets.go(args);
    }
}
