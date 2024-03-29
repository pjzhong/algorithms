package com.pjzhong.netty.chat.client;

import com.pjzhong.netty.chat.util.NettyEventLoopUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @sine 2018/7/6
 * @auth zhongjp
 */
public class SimpleChatClient {

  private final String host;
  private final int port;

  public SimpleChatClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void run() throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap()
          .group(group)
          .channel(NettyEventLoopUtil.getClientSocketChannelClass())
          .handler(new SimpleChatClientInitializer());
      Channel channel = bootstrap.connect(host, port).sync().channel();
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      while (true) {
        String input = in.readLine();
        if ("exit".equals(input)) {
          channel.close();
          break;
        }
        channel.writeAndFlush(input + "\r\n");
      }
    } finally {
      group.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    new SimpleChatClient("localhost", 8888).run();
  }
}
