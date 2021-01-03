package com.pjzhong.disruptor.example;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler {

  @Override
  public void onEvent(Object event, long sequence, boolean endOfBatch) {
    System.out.println("Event :" + event);
  }
}
