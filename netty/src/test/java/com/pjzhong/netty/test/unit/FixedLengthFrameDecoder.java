package com.pjzhong.netty.test.unit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * 固定帧解析
 *
 * @author ZJP
 * @since 2020年11月22日 19:59:05
 **/
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

  private final int frameLength;

  public FixedLengthFrameDecoder(int frameLength) {
    this.frameLength = frameLength;
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    while (in.readableBytes() >= frameLength) {
      ByteBuf buf = in.readBytes(frameLength);
      out.add(buf);
    }
  }


}
