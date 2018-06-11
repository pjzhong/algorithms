package concurrency.example;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * A simple Object pool using semaphore
 * */
public class ObjectPool {
    private Semaphore semaphore;
    private LinkedList<Object> pool;

    public ObjectPool(int n) {
        semaphore = new Semaphore(n);
        pool = new LinkedList<>();
    }

    public Object getObject() {
        try {
            semaphore.acquire();
            return pool.removeFirst();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void release(Object object) {
        if(object != null) {
            pool.push(object);
            semaphore.release();
        }
    }
}
