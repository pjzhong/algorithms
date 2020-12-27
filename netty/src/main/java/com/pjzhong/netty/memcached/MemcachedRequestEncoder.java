package com.pjzhong.netty.memcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.StandardCharsets;

public class MemcachedRequestEncoder extends MessageToByteEncoder<MemcachedRequest> {

  @Override
  protected void encode(ChannelHandlerContext ctx, MemcachedRequest msg, ByteBuf out)
      throws Exception {
    //convert key and boyd to bytes array;
    byte[] key = msg.getKey().getBytes(StandardCharsets.UTF_8);
    byte[] body = msg.getBody().getBytes(StandardCharsets.UTF_8);
    // total size of boyd = key size + content size + extras size
    int bodySize = key.length + body.length + (msg.isHasExtras() ? 8 : 0);

    // write magic byte
    out.writeByte(msg.getMagic());
    // write opcode byte
    out.writeByte(msg.getOpCode());
    // write key length (2 byte) i.e Java short
    out.writeShort(key.length);
    // write extras length(1 byte)
    int extraSize = msg.isHasExtras() ? 0x08 : 0x0;
    out.writeByte(extraSize);
    // byte is the data type. not currently implemented in
    out.writeByte(0);
    //next two byte are reserved, not currently implemented
    out.writeShort(0);

    // write total body length (4 bytes - 32 bit int)
    out.writeInt(bodySize);
    //write opaque(4 byte) - a 32 bit int that is returned
    // in the response
    out.writeInt(msg.getId());

    // write CAS(8 bytes)
    //24 byte header finished with the CAS
    out.writeLong(msg.getCas());
    if(msg.isHasExtras()) {
      out.writeInt(msg.getFlags());
      out.writeInt(msg.getExpires());
    }

    //write key
    out.writeBytes(key);
    //write value
    out.writeBytes(body);
  }
}
