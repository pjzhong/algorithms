package com.pjzhong.concurrency.example.managestate.impl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock can have as many condition objects as you want.
 * The equivalents of wait, notify, and notifyAll for Condition objects are await, signal
 * and signalAll. However, Condition extends Object, which means that it also has wait and notify methods. Be sure
 * to use the proper version - await and signal, signalAll - instead!
 * */
public class ConditionQueueBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private int tail, head, count;
    private final T[] items;

    public ConditionQueueBoundedBuffer(int capacity) {
        this.items = (T[]) new Object[capacity];
    }

    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            if(++tail == items.length) { tail = 0;}
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if(++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
