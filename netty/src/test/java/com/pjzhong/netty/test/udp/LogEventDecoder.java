package com.pjzhong.netty.test.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {

  @Override
  protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out)
      throws Exception {
    ByteBuf data = msg.content();
    int idx = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);
    String fileName = data.slice(0, idx).toString(StandardCharsets.UTF_8);
    String logsMsg = data.slice(idx + 1, data.readableBytes()).toString(StandardCharsets.UTF_8);

    LogEvent event = new LogEvent(msg.sender(), System.currentTimeMillis(), fileName, logsMsg);
    out.add(event);
  }
}
