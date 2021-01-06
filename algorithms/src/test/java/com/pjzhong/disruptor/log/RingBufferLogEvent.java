package com.pjzhong.disruptor.log;

import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.util.StringBuilders;

public class RingBufferLogEvent {

  private AsyncLogger asyncLogger;
  private boolean endOfBatch;
  private StringBuilder messageText;
  private long threadId;
  private String threadName;
  private int threadPriority;
  private String fqcn;
  private StackTraceElement location;

  public void setValues(AsyncLogger logger, Message message, String fqcn, long threadId,
      String threadName, int threadPriority, StackTraceElement location) {
    this.asyncLogger = logger;
    setMessage(message);
    this.threadId = threadId;
    this.threadName = threadName;
    this.threadPriority = threadPriority;
    this.location = location;
    this.fqcn = fqcn;
  }

  private void setMessage(final Message msg) {
    if (msg instanceof ReusableMessage) {
      final ReusableMessage reusable = (ReusableMessage) msg;
      reusable.formatTo(getMessageTextForWriting());
    }
  }

  private StringBuilder getMessageTextForWriting() {
    if (messageText == null) {
      // Happens the first time messageText is requested or if a user logs
      // a custom reused message when Constants.ENABLE_THREADLOCALS is false
      messageText = new StringBuilder(Constants.INITIAL_REUSABLE_MESSAGE_SIZE);
    }
    messageText.setLength(0);
    return messageText;
  }

  public void clear() {
    this.asyncLogger = null;
    this.fqcn = null;
    this.location = null;
    StringBuilders.trimToMaxSize(this.messageText, 1024);
  }

  public void exec(boolean endOfBatch) {
    this.endOfBatch = endOfBatch;
    asyncLogger.actualAsyncLog(this);
  }

  public boolean isEndOfBatch() {
    return endOfBatch;
  }

  public long getThreadId() {
    return threadId;
  }

  public String getThreadName() {
    return threadName;
  }

  public int getThreadPriority() {
    return threadPriority;
  }

  public String getFqcn() {
    return fqcn;
  }

  public StackTraceElement getLocation() {
    return location;
  }

  public String getFormattedMessage() {
    return messageText != null ? messageText.toString() : "";
  }
}
