package com.pjzhong.nio;

import com.pjzhong.dsa.list.LinkedList;
import com.pjzhong.dsa.list.List;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Date;

/**
 * Request com.pjzhong.time server, per RFC 868. RFC 868(http://www.ietf.org/rfc/rfc0868.txt) is a
 * very simple com.pjzhong.time protocol whereby one system can request the current com.pjzhong.time
 * from another system Most Linux, BSD and Solaris systems provide RFC 868 com.pjzhong.time server
 * on port 37. This simple program will inter-operate with those.The National Institute of Standards
 * and Technology(NIST) operates a public com.pjzhong.time server at com.pjzhong.time.nist.gov.
 *
 * The RFC 868 protocol specifies a 32 bit unsigned value be sent, representing the number of
 * seconds since Jan 1, 1900. The Java epoch begins on Jan 1, 1970(same as unix)so an adjustment is
 * made by adding or subtracting 2,208,988,800 as appropriate. To avoid shifting and masking, a
 * four-bytes lice of an eight-byte buffer is used to send/recieve. But getLong() is done on the
 * full eight bytes to get a long value.
 *
 * When run, this program will issue com.pjzhong.time requests to each hostname given on the command
 * line, then enter a loop to receive packets. Note that some requests or replies may be lost, which
 * means this could block forever.
 */
public class TimeClient {

  private static final int DEFAULT_TIME_PORT = 37;
  private static final long DIFF_1990 = 2208988800L;

  protected int port = DEFAULT_TIME_PORT;
  protected List<InetSocketAddress> remoteHosts;
  protected DatagramChannel channel;

  public TimeClient(String[] args) throws Exception {
    if (args.length == 0) {
      throw new Exception("Usage:[-p port] host ...");
    }

    parseAtg(args);
    this.channel = DatagramChannel.open();
  }

  protected void sendRequests() throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(1);

    for (InetSocketAddress sa : remoteHosts) {
      System.out.format("Requesting time from %s:%d\n", sa.getHostName(), sa.getPort());

      buffer.clear().flip();
      channel.send(buffer, sa);
    }
  }

  protected InetSocketAddress receivePacket(DatagramChannel channel, ByteBuffer buffer)
      throws Exception {
    buffer.clear();
    return (InetSocketAddress) channel.receive(buffer);
  }

  public void getReplies() throws Exception {
    ByteBuffer longBuffer = ByteBuffer.allocate(8);

    longBuffer.order(ByteOrder.BIG_ENDIAN);
    longBuffer.putLong(0, 0);
    longBuffer.position(4);

    ByteBuffer buffer = longBuffer.slice();
    int expect = remoteHosts.size();
    int replies = 0;
    System.out.println("\nWaiting for replies...");

    while (true) {
      InetSocketAddress sa;
      sa = receivePacket(channel, buffer);

      buffer.flip();
      replies++;

      printTime(longBuffer.getLong(0), sa);

      if (replies == expect) {
        System.out.println("All packets answered");
        break;
      }

      System.out.format("Received %d of %d replies", replies, expect);
    }
  }

  protected void printTime(long remote1900, InetSocketAddress sa) {
    long local = System.currentTimeMillis() / 1000;
    long remote = remote1900 - DIFF_1990;
    Date remoteDate = new Date(remote * 1000);
    Date localDate = new Date(local * 1000);
    long skew = remote - local;

    System.out.format("Replay from %s:%d\n", sa.getHostName(), sa.getPort());
    System.out.format("there: %s\n", remoteDate);
    System.out.format("here: %s\n", localDate);
    System.out.print(" skew: ");

    if (skew == 0) {
      System.out.println("none");
    } else if (0 < skew) {
      System.out.println(skew + " seconds ahead");
    } else {
      System.out.println((-skew) + " seconds behind");
    }
  }

  public void parseAtg(String[] args) {
    remoteHosts = new LinkedList<>();
    for (int i = 0; i < args.length; i++) {
      String arg = args[i];

      if (arg.equals("-p")) {
        this.port = Integer.parseInt(args[++i]);
        InetSocketAddress sa = new InetSocketAddress("localhost", port);

        //Validate that is has add address
        if (sa.getAddress() == null) {
          System.out.format("Cannot resolve address: %s", arg);
        } else {
          remoteHosts.add(sa);
        }
      }
    }
  }

  public static void main(String[] args) throws Exception {
    TimeClient client = new TimeClient(args);

    client.sendRequests();
    client.getReplies();
  }
}
