package concurrency.pattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ProducerConsumer {

    private final int BOUND = 10000;

    private volatile boolean stop = false;
    BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(BOUND);

    public class Producer implements Runnable {
        @Override
        public void run() {
            try {
                for(int i = 0; i < Integer.MAX_VALUE && !stop;i++) {
                    blockingQueue.offer(i, 1000, TimeUnit.MILLISECONDS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class Consumer implements Runnable {
        @Override
        public void run() {
            while (!stop) {
                System.out.println(blockingQueue.poll());
            }
        }
    }


    public void start() {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }

    public void stop() {
        stop = true;
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer producerConsumer = new ProducerConsumer();

        producerConsumer.start();
        TimeUnit.SECONDS.sleep(3);
        producerConsumer.stop();
    }
}
