package com.pjzhong.disruptor.log;

import com.lmax.disruptor.EventHandler;

public class RingBufferLogEventHandler implements EventHandler<RingBufferLogEvent> {

  @Override
  public void onEvent(RingBufferLogEvent event, long sequence, boolean endOfBatch) {
    try {
      event.exec(endOfBatch);
    } finally {
      event.clear();
    }
  }
}
