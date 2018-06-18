package concurrency;

import concurrency.atomic_variable.LinkedQueue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LinkedQueueTest {
    LinkedQueue<Integer> queue;
    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    public class adder implements Runnable {
        private int start, end;
        private CountDownLatch putEnd;
        private LinkedQueue<Integer> queue;

        public adder(int start, int end, LinkedQueue<Integer> queue, CountDownLatch putEnd) {
            this.start = start;
            this.end = end;
            this.queue = queue;
            this.putEnd = putEnd;
        }

        @Override
        public void run() {
            try {
                for(int i = start; i < end; i++) {
                    queue.enqueue(i);
                }
            }  finally {
                putEnd.countDown();
            }
        }
    }
    public class taker implements Runnable {
        private LinkedQueue<Integer> queue;
        private CountDownLatch latch;
        private Integer[] result;

        public taker(LinkedQueue<Integer> queue, CountDownLatch latch, Integer[] result) {
            this.latch = latch;
            this.queue = queue;
            this.result = result;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Integer i = queue.dequeue();
                    if(i == null) { break;}
                    result[i] = i;
                }
            } finally {
                latch.countDown();
            }
        }
    }

    private int loop = 1000, grow = 1000, workers = 10;

    @Before
    public void before() throws InterruptedException {
        CountDownLatch putEnd = new CountDownLatch(workers);
        queue = new LinkedQueue<>();

        for(int i = 0; i < workers; i++) {
            executor.execute(new adder(loop - grow, loop, queue, putEnd));
            loop += grow;
        }
        putEnd.await();
    }

    @Test
    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(workers);
        Integer[] result = new Integer[grow * workers];
        for(int i = 0; i < workers; i++) {
            executor.execute(new taker(queue, latch, result));
        }
        latch.await();
        for(int i = 0; i < result.length; i++) {
            Assert.assertNotNull(result[i]);
        }
    }
}
