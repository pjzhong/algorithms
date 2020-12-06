package com.pjzhong.netty.test.unit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

/**
 * @author ZJP
 * @since 2020年11月22日 20:22:35
 **/
public class AbsInterEncoder extends MessageToMessageEncoder<ByteBuf> {

  @Override
  protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    while (msg.readableBytes() >= 4) {
      int value = Math.abs(msg.readInt());
      out.add(value);
    }
  }
}
