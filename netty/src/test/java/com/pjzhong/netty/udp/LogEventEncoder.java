package com.pjzhong.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

  private final InetSocketAddress address;

  public LogEventEncoder(InetSocketAddress address) {
    this.address = address;
  }

  @Override
  protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out) {
    ByteBuf buf = ctx.alloc().buffer();
    buf.writeBytes(msg.getLogfile().getBytes(StandardCharsets.UTF_8));
    buf.writeByte(LogEvent.SEPARATOR);
    buf.writeBytes(msg.getMsg().getBytes(StandardCharsets.UTF_8));
    out.add(new DatagramPacket(buf, address));
  }
}
