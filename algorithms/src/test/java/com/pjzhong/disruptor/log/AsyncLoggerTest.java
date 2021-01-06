package com.pjzhong.disruptor.log;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AsyncLoggerTest {


  @Test
  public void log4jLogger() {
    Logger sjLog = LoggerFactory.getLogger(this.getClass());

    for (int i = 0; i < 100000; i++) {
      sjLog.info(String.valueOf(i));
    }
  }

  @Test
  public void tryOut() throws InterruptedException {
    AsyncLogger logger = new AsyncLogger(this.getClass());

    for (int i = 0; i < 100000; i++) {
      logger.info(String.valueOf(i));
    }
  }
}
