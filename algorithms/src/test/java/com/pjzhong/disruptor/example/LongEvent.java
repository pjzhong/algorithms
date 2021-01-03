package com.pjzhong.disruptor.example;

public class LongEvent {

  private long value;

  public LongEvent setValue(long value) {
    this.value = value;
    return this;
  }
}
