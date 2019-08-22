package com.pjzhong.chat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.flush.FlushConsolidationHandler;

/**
 * @author zhongjp
 * @since 2018/7/6
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {

  private SimpleChatServer server;

  public SimpleChatServerInitializer(SimpleChatServer server) {
    this.server = server;
  }

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addFirst("autoflush", new FlushConsolidationHandler());//Add it consolidate flush
    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
    pipeline.addLast("decoder", new StringDecoder());
    pipeline.addLast("encoder", new StringEncoder());
    pipeline.addLast("auth", new AuthHandle(server));
    pipeline.addLast("handler", new SimpleChatServerHandler(server));
  }
}
