package com.pjzhong.netty.chat.server;

import io.netty.util.AttributeKey;

public final class ServerConst {

  public static AttributeKey<String> NAME = AttributeKey.valueOf("name");

  private ServerConst() {
  }

}
