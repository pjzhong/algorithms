package netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author zhongjp
 * @since 2018/7/6
 */
public class SimpleChatServer {

  private int port = 8888;
  private static ChannelGroup channels;

  public SimpleChatServer() {
  }

  public SimpleChatServer(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new SimpleChatServerInitializer(this))
          .option(ChannelOption.SO_BACKLOG, 128)
          .childOption(ChannelOption.SO_KEEPALIVE, true);
      System.out.println("SimpleChatServer has started");

      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
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
  }
}
