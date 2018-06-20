package concurrency;

import concurrency.atomic_variable.LinkedQueue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedQueueTest {
    private int grow = 1000,  multi = 10, limit = grow * multi;

    public class adder implements Runnable {
        private int start, end;
        private LinkedQueue<Integer> queue;

        public adder(int start, int end, LinkedQueue<Integer> queue) {
            this.start = start;
            this.end = end;
            this.queue = queue;
        }

        @Override
        public void run() {
            for(int i = start; i < end; i++) {
                queue.enqueue(i);
            }
        }
    }
    public class taker implements Runnable {
        private LinkedQueue<Integer> queue;
        private Integer[] result;

        public taker(LinkedQueue<Integer> queue, Integer[] result) {
            this.queue = queue;
            this.result = result;
        }

        @Override
        public void run() {
            int take = 0;
            while (take < grow) {
                Integer i = queue.dequeue();
                if(i != null) {
                    result[i] = i;
                    take++;
                }
            }
        }
    }


    @Test
    public void test() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        Integer[] result = new Integer[limit];

        for(int lo = 0, hi = grow; hi <= limit;) {
            executor.execute(new adder(lo, hi, queue));
            executor.execute(new taker(queue, result));
            lo = hi;
            hi += grow;
        }

        executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        for(int i = 0; i < limit; i++) {
            Assert.assertNotNull(result[i]);
        }
    }
}
