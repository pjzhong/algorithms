package com.pjzhong.disruptor.log;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class AsyncLoggerDisruptor {

  private Disruptor<RingBufferLogEvent> disruptor;

  public Disruptor<RingBufferLogEvent> getDisruptor() {
    return disruptor;
  }

  public synchronized void start() {
    if (disruptor != null) {
      return;
    }

    disruptor = new Disruptor<>(RingBufferLogEvent::new, 1024,
        DaemonThreadFactory.INSTANCE, ProducerType.MULTI,
        new BlockingWaitStrategy());

    disruptor.handleEventsWith(new RingBufferLogEventHandler());
  }

  boolean tryPublish(RingBufferLogEventTranslator translator) {
    return disruptor.getRingBuffer().tryPublishEvent(translator);
  }
}
