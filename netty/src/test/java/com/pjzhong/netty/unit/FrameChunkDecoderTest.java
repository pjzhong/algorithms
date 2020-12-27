package com.pjzhong.netty.unit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Assert;
import org.junit.Test;

public class FrameChunkDecoderTest {

  @Test
  public void testFramesDecoded() {
    ByteBuf buf = Unpooled.buffer();
    for (int i = 0; i < 9; i++) {
      buf.writeByte(i);
    }

    ByteBuf input = buf.duplicate();

    EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));

    Assert.assertTrue(channel.writeInbound(input.readBytes(2)));
    try {
      channel.writeInbound(input.readBytes(4));
      Assert.fail();
    } catch (TooLongFrameException e) {
      // expected
    }
    Assert.assertTrue(channel.writeInbound(input.readBytes(3)));
    Assert.assertTrue(channel.finish());

    Assert.assertEquals(buf.readBytes(2), channel.readInbound());
    Assert.assertEquals(buf.skipBytes(4).readBytes(3), channel.readInbound());
  }


  @Test
  public void test() {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(1,1);
    Entry one = null;
    for(Entry<Integer, Integer> e : map.entrySet()) {
      one = e;
    }

    Entry two = null;
    map.put(1,2);
    for(Entry<Integer, Integer> e : map.entrySet()) {
      two = e;
    }

    System.out.println(one == two);
  }

}
