package com.pjzhong.disruptor.log;

import org.junit.Test;

public class AsyncLoggerTest {

  @Test
  public void tryOut() {
    AsyncLogger logger = new AsyncLogger();
    logger.info("try out");
  }

}
