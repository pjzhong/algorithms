package com.pjzhong.concurrency.example.managestate.impl;

import com.pjzhong.concurrency.anno.ThreadSafe;
import com.pjzhong.concurrency.example.managestate.BaseBoundedBuffer;
import com.pjzhong.concurrency.example.managestate.BufferEmptyException;
import com.pjzhong.concurrency.example.managestate.BufferFullException;

@ThreadSafe
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

  public GrumpyBoundedBuffer(int capacity) {
    super(capacity);
  }

  public synchronized void put(V v) throws BufferFullException {
    if (isFull()) {
      throw new BufferFullException();
    }
    doPut(v);
  }

  public synchronized V take() throws BufferEmptyException {
    if (isFull()) {
      throw new BufferEmptyException();
    }
    return doTake();
  }
}
