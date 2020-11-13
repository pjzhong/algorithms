package com.pjzhong.concurrency.example.managestate.impl;

import com.pjzhong.concurrency.anno.ThreadSafe;
import com.pjzhong.concurrency.example.managestate.BaseBoundedBuffer;

/**
 * A more efficient way, but using notifyAll would another thread wake up in the wrong moment.
 * For example, thread A is waiting for empty buffer, but A has the chance when he wake seeing the buffer is fulled;
 * */
@ThreadSafe
public class IntrinsicBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    protected IntrinsicBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) { wait();}//I want to go to sleep, but wake me up when something interesting happens
        doPut(v);
        notifyAll();//Something interesting happened
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) { wait(); }//I want to go to sleep, but wake me up when something interesting happens
        V v = doTake();
        notifyAll();//Something interesting happened
        return v;
    }
 }
