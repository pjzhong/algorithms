package com.pjzhong.concurrency.example.managestate.impl;

import com.pjzhong.concurrency.anno.ThreadSafe;
import com.pjzhong.concurrency.example.managestate.BaseBoundedBuffer;

import java.util.concurrent.TimeUnit;

@ThreadSafe
/**
 * But would oversleep(missing the right chance to do the right thing)
 * */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    private final long SLEEP_GRANULARITY = 100;

    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if(!isFull()) {
                    doPut(v);
                    return;
                }
            }
            TimeUnit.MILLISECONDS.sleep(SLEEP_GRANULARITY);
        }
    }

    public void take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if(!isEmpty()) {
                    doTake();
                    return;
                }
            }
            TimeUnit.MILLISECONDS.sleep(SLEEP_GRANULARITY);
        }
    }



}
