package com.pjzhong.disruptor.log;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ReusableSimpleMessage;

public class AsyncLogger {

  private RingBufferLogEventTranslator translator;
  private AsyncLoggerDisruptor loggerDisruptor;

  public AsyncLogger() {
    translator = new RingBufferLogEventTranslator();
    loggerDisruptor = new AsyncLoggerDisruptor();
    loggerDisruptor.start();
  }

  public void info(String info) {
    ReusableSimpleMessage message = new ReusableSimpleMessage();
    message.set(info);
    log(message);
  }

  public void log(Message message) {
    logWithTranslator(message);
  }

  private void logWithTranslator(final Message message) {
    translator.setBasicValues(this, message);
    loggerDisruptor.tryPublish(translator);
  }

  public void actualAsyncLog(final RingBufferLogEvent event) {
    System.out.println(Thread.currentThread().getId() + " " + event + "  " + event.getMessage()
        .getFormattedMessage());
  }
}
