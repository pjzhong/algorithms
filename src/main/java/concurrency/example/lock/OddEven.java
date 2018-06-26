package concurrency.example.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class OddEven {

    private ReentrantLock lock = new ReentrantLock();
    private Condition workCondition = lock.newCondition();
    private volatile boolean first = false;//true:odd, false:even
    private volatile boolean stop = false;

    private class Even implements Runnable {

        @Override
        public void run() {
            int start = 2, grow = 2;
            try {
                lock.lock();
                while(!stop) {
                    while (first) {
                        if(stop) { return;}
                        workCondition.await(1000L, TimeUnit.MILLISECONDS);
                    }

                    for(int i = 0; i < 10; i++) {
                        System.out.format("%d ", start);
                        start+=grow;
                    }
                    System.out.println("----------------------------Even--------------------------");
                    first = !first;
                    workCondition.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                stop();
            } finally {
                workCondition.signalAll();
                lock.unlock();
            }
        }
    }

    private class Odd implements Runnable {

        @Override
        public void run() {
            int start = 1, grow = 2;
            try {
                lock.lock();
                while(!stop) {
                    while (!first) {
                        if(stop) { return;}
                        workCondition.await(1000L, TimeUnit.MILLISECONDS);
                    }
                    
                    for(int i = 0; i < 10; i++) {
                        System.out.format("%d ", start);
                        start+=grow;
                    }
                    System.out.println("----------------------------Odd--------------------------");
                    first = !first;
                    workCondition.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                workCondition.signalAll();
                lock.unlock();
            }
        }
    }

    public void start() {
        new Thread(new Even()).start();
        new Thread(new Odd()).start();
    }

    public void stop() {
        stop = true;
    }

    public static void main(String[] args) throws InterruptedException {
        OddEven oddEven = new OddEven();

        oddEven.start();
        TimeUnit.MILLISECONDS.sleep(100L);
        oddEven.stop();
    }
}
