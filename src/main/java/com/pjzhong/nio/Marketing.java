package com.pjzhong.nio;


import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Demonstrate gathering write using many  buffers.
 */
public class Marketing {

  private static final String DEMOGRAPHIC = "blablabla.txt";

  public static void main(String[] args) throws Exception {
    int reps = 10;
    if (args.length > 0) {
      reps = Integer.parseInt(args[0]);
    }

    FileOutputStream fos = new FileOutputStream(DEMOGRAPHIC);
    FileChannel gatherChannel = fos.getChannel();
    ByteBuffer[] bs = utterBS(reps);

    while (gatherChannel.write(bs) > 0) {
      //Empty body
      //Loop until write() returns zero
    }

    fos.close();
  }

  //-----------------------------------------
  // These are just representative; add your own
  private static String newLine = System.getProperty("line.separator");
  private static String[] col1 = {
      "Aggregate", "Enable", "Leverage",
      "Facilitate", "Synergize", "Repurpose",
      "Strategize", "Reinvent", "Harness"
  };
  private static String[] col2 = {
      "cross-platform", "best-of-breed", "frictionless",
      "ubiquitous", "extensible", "compelling",
      "mission-critical", "collaborative", "integrated"
  };
  private static String[] col3 = {
      "methodologies", "infomediaries", "platforms",
      "schemas", "mindshare", "paradigms",
      "functionalities", "web services", "infrastructures"
  };

  private static Random random = new Random();

  private static ByteBuffer[] utterBS(int howMany) throws Exception {
    List<ByteBuffer> list = new LinkedList<>();

    for (int i = 0; i < howMany; ++i) {
      list.add(pickRandom(col1, " "));
      list.add(pickRandom(col2, " "));
      list.add(pickRandom(col3, newLine));
    }

    ByteBuffer[] buffers = new ByteBuffer[list.size()];
    list.toArray(buffers);
    return buffers;
  }

  private static ByteBuffer pickRandom(String[] strings, String suffix) throws Exception {
    String string = strings[random.nextInt(strings.length)];
    int total = string.length() + suffix.length();
    ByteBuffer buffer = ByteBuffer.allocate(total);
    buffer.put(string.getBytes("US-ASCII"));
    buffer.put(suffix.getBytes("US-ASCII"));
    buffer.flip();

    return buffer;
  }


}
