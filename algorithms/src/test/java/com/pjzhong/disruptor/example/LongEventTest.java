package com.pjzhong.disruptor.example;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.nio.ByteBuffer;
import org.junit.Test;

public class LongEventTest {

  @Test
  public void test() {
    int buffSize = 16;

    Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, buffSize,
        DaemonThreadFactory.INSTANCE);

    disruptor.handleEventsWith(new LongEventHandler());

    disruptor.start();

    RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

    LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);

    ByteBuffer bb = ByteBuffer.allocate(8);
    for (long l = 0; l < 10000; l++) {
      bb.putLong(0, l);
      producer.onData(bb);
    }
  }

}
