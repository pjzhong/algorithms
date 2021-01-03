package com.pjzhong.disruptor.log;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ThreadFactory;

public class AsyncLoggerDisruptor {

  private Disruptor<RingBufferLogEvent> disruptor;

  public Disruptor<RingBufferLogEvent> getDisruptor() {
    return disruptor;
  }

  public synchronized void start() {
    if (disruptor != null) {
      return;
    }

    ThreadFactory factory = Thread::new;
    disruptor = new Disruptor<>(RingBufferLogEvent::new, 1024,
        factory, ProducerType.MULTI,
        new BlockingWaitStrategy());

    disruptor.handleEventsWith(new RingBufferLogEventHandler());
    disruptor.start();
  }

  boolean tryPublish(RingBufferLogEventTranslator translator) {
    return disruptor.getRingBuffer().tryPublishEvent(translator);
  }
}
