package com.pjzhong.netty.memcached;

import java.util.Random;

public class MemcachedRequest {

  private static final Random rand = new Random();
  private byte magic = (byte) 0x80;//fixed so hard coded
  private byte opCode;// the operation e.g. set or get
  private String key;// the key to delete, get or set
  private int flags = 0xdeadbeef;// random;
  private int expires; //0 = item never expires;
  private String body;//if opCode is set, the value
  private int id = rand.nextInt();//Opaque
  private long cas; //data version check...not used
  private boolean hasExtras; //not all ops have extras

  public MemcachedRequest(byte opCode, String key, String  value) {
    this.opCode = opCode;
    this.key = key;
    this.body = value == null ? "" : value;
    this.hasExtras = opCode == Opcode.SET;
  }

  public MemcachedRequest(byte opCode, String key) {
    this(opCode, key, null);
  }

  public int getMagic() {
    return magic;
  }

  public byte getOpCode() {
    return opCode;
  }

  public String getKey() {
    return key;
  }

  public int getFlags() {
    return flags;
  }

  public int getExpires() {
    return expires;
  }

  public String getBody() {
    return body;
  }

  public int getId() {
    return id;
  }

  public long getCas() {
    return cas;
  }

  public boolean isHasExtras() {
    return hasExtras;
  }
}

