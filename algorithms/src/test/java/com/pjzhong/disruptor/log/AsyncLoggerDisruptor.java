package com.pjzhong.disruptor.log;

import com.lmax.disruptor.TimeoutBlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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
    disruptor = new Disruptor<>(RingBufferLogEvent::new, 4096,
        factory, ProducerType.MULTI,
        new TimeoutBlockingWaitStrategy(10, TimeUnit.MILLISECONDS));

    disruptor.handleEventsWith(new RingBufferLogEventHandler());
    disruptor.start();
  }

  boolean tryPublish(RingBufferLogEventTranslator translator) {
    return disruptor.getRingBuffer().tryPublishEvent(translator);
  }
}
