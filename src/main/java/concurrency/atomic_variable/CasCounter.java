package concurrency.atomic_variable;

import concurrency.anno.ThreadSafe;

@ThreadSafe
/**
 * In practice, if all you need is a counter or sequence generator, just use the AtmoicInteger or AtmoicLong)
 * */
public class CasCounter {
    private SimulatedCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        }while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }
}
