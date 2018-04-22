package nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

/**
 * Provide RFC 868 time service (http://www.ietf.org/rfc/rfc0868.txt).
 * This code implements an RFC 868 listener to provide time
 * service. The defined port for time service is 37. On most
 * unix systems, root privilege is required to bind to ports
 * below 1024. You can either run this code as root or
 * provide another port number on the command line. Use
 * "-p port#" with TimeClient if you choose an alternate port.
 *
 * Note: The familiar rdate command on unix will probably not work
 * with this server. Most versions of rdate use TCP rather than UDP
 * to request the time.
 *
 */
public class TimeServer {

    private static final int DEFAULT_TIME_PORT = 37;
    private static final long DIFF_1900 = 2208988800L;

    protected DatagramChannel channel;

    public TimeServer(int port) throws Exception {
        this.channel = DatagramChannel.open();
        this.channel.bind(new InetSocketAddress(port));

        System.out.format("Listening on port %d from time requests\n", port);
    }

    public void listen() throws Exception {
        ByteBuffer longBuffer = ByteBuffer.allocate(8);

        longBuffer.order(ByteOrder.BIG_ENDIAN);
        longBuffer.putLong(0, 0);
        longBuffer.position(4);

        ByteBuffer buffer = longBuffer.slice();

        while(true) {
            buffer.clear();

            SocketAddress sa = this.channel.receive(buffer);

            if(sa != null) {
                System.out.println("Time request from " + sa);
                buffer.clear();

                longBuffer.putLong(0, (System.currentTimeMillis() / 1000) + DIFF_1900);
                this.channel.send(buffer, sa);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int port = DEFAULT_TIME_PORT;

        if(args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        try {
            TimeServer server = new TimeServer(port);
            server.listen();
        } catch (SocketException e) {
            System.out.format("Can't bind to port %d , try a different one\n", port);
        }
    }
}
