package com.pjzhong.disruptor.log;

import com.lmax.disruptor.EventTranslator;
import org.apache.logging.log4j.message.Message;

public class RingBufferLogEventTranslator implements EventTranslator<RingBufferLogEvent> {

  private AsyncLogger asyncLogger;
  protected Message message;

  @Override
  public void translateTo(RingBufferLogEvent event, long sequence) {
    event.setValues(asyncLogger, message);
    new RingBufferLogEventHandler().onEvent(event, 0, false);
    clear();
  }

  void clear() {
    setBasicValues(null, null);
  }

  public void setBasicValues(final AsyncLogger logger, final Message message) {
    this.asyncLogger = logger;
    this.message = message;
  }
}
