package com.pjzhong.nio;

import java.io.FileInputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Test channel transfer. This is a very simplistic concatenation program. It takes a list of file
 * names as arguments, opens each in turn and transfers (copies) their content to the given
 * WritableByteChannel
 */
public class ChannelTransfer {

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.err.println("Usage: filename ...");
      return;
    }

    catFiles(Channels.newChannel(System.out), args);
  }


  private static void catFiles(WritableByteChannel target, String[] files) throws Exception {
    for (int i = 0; i < files.length; ++i) {
      FileInputStream fis = new FileInputStream(files[i]);
      FileChannel channel = fis.getChannel();

      System.out.format("transforming  %s\n", files[i]);
      channel.transferTo(0, channel.size(), target);
      System.out.format("transforming  %s done\n\n", files[i]);

      channel.close();
      fis.close();
    }
  }
}
