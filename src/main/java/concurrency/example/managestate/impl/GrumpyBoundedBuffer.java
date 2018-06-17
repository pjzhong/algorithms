package concurrency.example.managestate.impl;

import concurrency.anno.ThreadSafe;
import concurrency.example.managestate.BaseBoundedBuffer;
import concurrency.example.managestate.BufferEmptyException;
import concurrency.example.managestate.BufferFullException;

@ThreadSafe
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws BufferFullException {
        if(isFull()) {
            throw new BufferFullException();
        }
        doPut(v);
    }

    public synchronized V take() throws BufferEmptyException {
        if(isFull()) {
            throw new BufferEmptyException();
        }
        return doTake();
    }
}
