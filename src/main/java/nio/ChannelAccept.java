package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Test nonblocking accept() using ServerSocketChannel.
 * Start this program, then "telnet localhost 1234" to connect to it
 * */
public class ChannelAccept {
    public static final String GREETING = "Hello, But I should leave.\r\n";

    public static void main(String[] args) throws Exception {
        int port = 1234;
        if(args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes("UTF-8"));
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);

        while (true) {
            System.out.println("Waiting for connections");

            SocketChannel sc = ssc.accept();
            if(sc == null) {
                //no connections, snooze a while
                TimeUnit.SECONDS.sleep(2);
            } else {
                System.out.format("Incoming connection from:%s\n", sc.getRemoteAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
