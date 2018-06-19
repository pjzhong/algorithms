package concurrency.example.lock;

import java.util.ArrayList;
import java.util.List;

/**
 Every Java can act as a Lock, using the keyword - synchronized, as long it is use consistently

 The only way to acquire a IntrinsicLock is entrance a synchronized block or method.

 Intrinsic lock is Reentrancy, acquiring the same lock would't cause deadlock. Reentrancy is implemented by
 associating with each lock an acquisition count and an owing thread. When the count is zero, the lock consider unheld.
 So a lock much must be release after you done with it.

 Intrinsic lock releasing is done by jvm, so you are free of it

 * */
public class MonitorPattern {

    private final Object lock = new Object();
    private List<Object> list = new ArrayList<>();

    public void add(Object object) {
        synchronized (lock) {
            list.add(object);
        }
    }

    public void get(int i) {
        synchronized (lock) {
            list.get(i);
        }
    }
}
