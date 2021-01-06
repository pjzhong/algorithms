package com.pjzhong.disruptor.log;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ReusableMessageFactory;
import org.apache.logging.log4j.message.ReusableSimpleMessage;


public class AsyncLogger {

  public static final String FQCN = AsyncLogger.class.getName();

  private AsyncLoggerDisruptor loggerDisruptor;
  private String name;

  private ThreadLocal<StringBuilder> LOCAL_STRING_BUILDER = new ThreadLocal<>();
  private ThreadLocal<RingBufferLogEventTranslator> TRANSLATOR = new ThreadLocal<>();
  private ThreadLocal<ReusableSimpleMessage> SIMPLE_REUSE_MESSAGE = new ThreadLocal<>();

  public AsyncLogger(Class clazz) {
    name = clazz.getName();
    loggerDisruptor = new AsyncLoggerDisruptor();
    loggerDisruptor.start();
  }

  public void info(String info) {
    ReusableSimpleMessage message = getSimpleMessage();
    try {
      message.set(info);
      logWithTranslator(FQCN, getLocation(FQCN), message);
    } finally {
      ReusableMessageFactory.release(message);
    }
  }

  public ReusableSimpleMessage getSimpleMessage() {
    ReusableSimpleMessage message = SIMPLE_REUSE_MESSAGE.get();
    if (message == null) {
      SIMPLE_REUSE_MESSAGE.set(message = new ReusableSimpleMessage());
    }
    return message;
  }

  private StackTraceElement getLocation(String fqcn) {
    if (fqcn == null) {
      return null;
    }
    // LOG4J2-1029 new Throwable().getStackTrace is faster than Thread.currentThread().getStackTrace().
    final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
    boolean found = false;
    for (int i = 0; i < stackTrace.length; i++) {
      final String className = stackTrace[i].getClassName();
      if (fqcn.equals(className)) {
        found = true;
        continue;
      }
      if (found) {
        return stackTrace[i];
      }
    }

    return null;
  }


  private void logWithTranslator(final String fqcn, final StackTraceElement location,
      final Message message) {
    RingBufferLogEventTranslator translator = getTranslator();
    translator.setBasicValues(this,
        fqcn,
        location,
        message);
    translator.updateThreadValues();
    loggerDisruptor.tryPublish(translator);
  }

  private RingBufferLogEventTranslator getTranslator() {
    RingBufferLogEventTranslator translator = TRANSLATOR.get();
    if (translator == null) {
      TRANSLATOR.set(translator = new RingBufferLogEventTranslator());
    }
    return translator;
  }

  public void actualAsyncLog(final RingBufferLogEvent event) {
    StringBuilder builder = getBuilder();
    builder
        .append("[").append(event.getThreadName()).append("] ")
        .append(name, name.lastIndexOf(".") + 1, name.length()).append(".")
        .append(event.getLocation().getMethodName())
        .append(" - ").append(event.getFormattedMessage()).append("\n");
    System.out.print(builder.toString());
    trimToMaxSize(builder, 1024);
  }

  private StringBuilder getBuilder() {
    StringBuilder stringBuilder = LOCAL_STRING_BUILDER.get();
    if (stringBuilder == null) {
      LOCAL_STRING_BUILDER.set(stringBuilder = new StringBuilder());
    }
    stringBuilder.setLength(0);
    return stringBuilder;
  }

  private void trimToMaxSize(StringBuilder stringBuilder, int maxSize) {
    if (stringBuilder != null && stringBuilder.capacity() > maxSize) {
      stringBuilder.setLength(maxSize);
      stringBuilder.trimToSize();
    }
  }

}
