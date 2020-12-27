package com.pjzhong.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.net.InetSocketAddress;

public class LogEventMonitor {

  private final EventLoopGroup group;
  private final Bootstrap bootstrap;

  public LogEventMonitor(InetSocketAddress address) {
    group = new NioEventLoopGroup();
    bootstrap = new Bootstrap()
        .group(group)
        .channel(NioDatagramChannel.class)
        .option(ChannelOption.SO_BROADCAST, true)
        .handler(new ChannelInitializer<Channel>() {
          @Override
          protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new LogEventDecoder())
                .addLast(new LogEventHandler());
          }
        }).localAddress(address);
  }


  public Channel bind() throws InterruptedException {
    return bootstrap.bind().sync().channel();
  }

  public void stop() {
    group.shutdownGracefully();
  }

  public static void main(String[] args) throws InterruptedException {
    LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(8888));
    try {
      Channel channel = monitor.bind();
      System.out.println("LogEventMonitor running");
      channel.closeFuture().sync();
    } finally {
      monitor.stop();
    }
  }

}
