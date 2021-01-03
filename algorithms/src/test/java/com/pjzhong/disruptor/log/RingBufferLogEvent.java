package com.pjzhong.disruptor.log;


import org.apache.logging.log4j.message.Message;

public class RingBufferLogEvent {

  private AsyncLogger asyncLogger;
  private boolean endOfBatch;
  protected Message message;


  public Message getMessage() {
    return message;
  }

  public void setValues(AsyncLogger logger, Message message) {
    this.asyncLogger = logger;
    this.message = message;
  }

  public void clear() {
    this.asyncLogger = null;
    this.message = null;
  }

  public void exec(boolean endOfBatch) {
    this.endOfBatch = endOfBatch;
    asyncLogger.actualAsyncLog(this);
  }
}
