package com.pjzhong.disruptor.log;

import com.lmax.disruptor.EventTranslator;
import org.apache.logging.log4j.message.Message;

public class RingBufferLogEventTranslator implements EventTranslator<RingBufferLogEvent> {

  private AsyncLogger asyncLogger;
  protected String fqcn;
  protected Message message;
  private StackTraceElement location;
  private long threadId;
  private String threadName;
  private int threadPriority;

  @Override
  public void translateTo(RingBufferLogEvent event, long sequence) {
    event.setValues(asyncLogger,
        message,
        fqcn,
        threadId,
        threadName,
        threadPriority,
        location);
    clear();
  }

  void clear() {
    setBasicValues(null,
        null,
        null,
        null);
  }

  public void setBasicValues(final AsyncLogger logger, final String fqcn,
      final StackTraceElement location, final Message message) {
    this.asyncLogger = logger;
    this.fqcn = fqcn;
    this.location = location;
    this.message = message;
  }
·
  public void updateThreadValues() {
    final Thread currentThread = Thread.currentThread();
    this.threadId = currentThread.getId();
    this.threadName = currentThread.getName();
    this.threadPriority = currentThread.getPriority();
  }
}
