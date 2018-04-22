package nio;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ConnectAsync {

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 80;

        InetSocketAddress addr = new InetSocketAddress(host, port);
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(addr);

        while(!sc.finishConnect()) {}

        System.out.println("connection established");

        //Do something with the connected socket
        //The  SocketChannel is still nonblocking

        Selector selector = Selector.open();
        sc.register(selector, SelectionKey.OP_ACCEPT);

        sc.close();
    }
}
