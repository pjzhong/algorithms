package com.pjzhong.netty.chat.server;

import com.pjzhong.netty.chat.util.NettyEventLoopUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author zhongjp
 * @since 2018/7/6
 */
public class SimpleChatServer {

  private int port = 8888;
  private static ChannelGroup channels;
  private ServerBootstrap bootstrap;

  public SimpleChatServer() {
  }

  public void run() throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    bootstrap = new ServerBootstrap();
    bootstrap.group(bossGroup, workerGroup)
        .channel(NettyEventLoopUtil.getServerSocketChannelClass())
        .childHandler(new SimpleChatServerInitializer(this))
        .option(ChannelOption.SO_BACKLOG, 128)
        .childOption(ChannelOption.SO_KEEPALIVE, true);
    System.out.println("SimpleChatServer has started");

    bootstrap.bind(port).sync();
  }

  public void close() {
    if (bootstrap != null) {
      channels.newCloseFuture().awaitUninterruptibly();
      bootstrap.config().group().shutdownGracefully().awaitUninterruptibly();
      bootstrap.config().childGroup().shutdownGracefully().awaitUninterruptibly();
    }
  }

  public void boardCast(String s) {
    if (channels != null) {
      channels.writeAndFlush(s);
    }
  }

  public long onLine() {
    return channels == null ? 0 : channels.size();
  }

  public void addChannel(Channel channel) {
    channels.add(channel);
  }

  public static void main(String[] args) throws Exception {
    SimpleChatServer chatServer = new SimpleChatServer();
    chatServer.run();

    Runtime.getRuntime().addShutdownHook(new Thread(chatServer::close));
  }
}
