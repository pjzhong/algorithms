package concurrency.example;

import concurrency.anno.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class HitCounter {

    private AtomicInteger counter = new AtomicInteger(0);

    public int hit() {
        return counter.incrementAndGet();
    }
}
