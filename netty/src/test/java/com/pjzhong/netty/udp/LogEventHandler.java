package com.pjzhong.netty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
    StringBuilder builder = new StringBuilder();
    builder.append(msg.getReceived())
        .append(" [")
        .append(msg.getSource().toString())
        .append("] [")
        .append(msg.getLogfile())
        .append("] : ")
        .append(msg.getMsg());
    System.out.println(builder.toString());
  }
}
