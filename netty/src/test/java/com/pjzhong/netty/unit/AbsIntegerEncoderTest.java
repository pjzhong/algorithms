package com.pjzhong.netty.unit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

public class AbsIntegerEncoderTest {

  @Test
  public void testEncoded() {
    ByteBuf buf = Unpooled.buffer();
    for (int i = 1; i < 10; i++) {
      buf.writeInt(i * -1);
    }

    EmbeddedChannel channel = new EmbeddedChannel(new AbsInterEncoder());
    Assert.assertTrue(channel.writeOutbound(buf));


    for(int i = 1; i < 10; i++) {
      Assert.assertEquals(Integer.valueOf(i), channel.readOutbound());
    }
    Assert.assertNull(channel.readOutbound());
  }

}
