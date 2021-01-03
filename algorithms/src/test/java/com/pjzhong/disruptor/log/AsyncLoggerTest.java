package com.pjzhong.disruptor.log;

import org.junit.Test;

public class AsyncLoggerTest {

  @Test
  public void tryOut() {
    AsyncLogger logger = new AsyncLogger();

    for (int i = 0; i < 100000; i++) {
      logger.info("try out " + i);
    }
  }

}
