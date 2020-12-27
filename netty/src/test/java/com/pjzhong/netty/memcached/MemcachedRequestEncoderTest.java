package com.pjzhong.netty.memcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.Test;

public class MemcachedRequestEncoderTest {

  @Test
  public void testMemcachedRequestENcoder() {
    MemcachedRequest request = new MemcachedRequest(Opcode.SET, "key1", "value1");

    EmbeddedChannel channel = new EmbeddedChannel(new MemcachedRequestEncoder());
    Assert.assertTrue(channel.writeOutbound(request));

    ByteBuf encoded = channel.readOutbound();

    Assert.assertNotNull(encoded);
    Assert.assertEquals(request.getMagic(), encoded.readByte());
    Assert.assertEquals(request.getOpCode(), encoded.readByte());
    Assert.assertEquals(4, encoded.readShort());
    Assert.assertEquals(0x08, encoded.readByte());
    Assert.assertEquals(0, encoded.readByte());
    Assert.assertEquals(0, encoded.readShort());
    Assert.assertEquals(18, encoded.readInt());
    Assert.assertEquals(request.getId(), encoded.readInt());
    Assert.assertEquals(request.getCas(), encoded.readLong());
    Assert.assertEquals(request.getFlags(), encoded.readInt());
    Assert.assertEquals(request.getExpires(), encoded.readInt());
    Assert.assertEquals(request.getKey(), encoded.readBytes(4).toString(StandardCharsets.UTF_8));
    Assert.assertEquals(request.getBody(), encoded.readBytes(6).toString(StandardCharsets.UTF_8));

    Assert.assertFalse(channel.finish());
    Assert.assertNull(channel.readInbound());
  }

}
