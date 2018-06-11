package concurrency.example;

import java.util.concurrent.CountDownLatch;

/**
 Latches can be used to ensure that certain activities do not proceed until other one-time
 activities complete, such as:
     - Ensuring that a computation does not proceed until resources it needs have been initialized.
     - Ensuring that a service does not start until other services on which it depends have started.
     - Waiting until all the parties involved in an activity, for instance the players in a
     multi-player game.

 —— Java Concurrency in Practice
 * */
public class TestHarness {

    public long timeTasks(int nThreads, final Runnable task) {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for(int i = 0; i < nThreads; i++) {
            new Thread(() -> {
                try {
                    startGate.await();
                    task.run();
                } catch (Exception ignored){

                } finally {
                    endGate.countDown();
                }
            }).start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) {
        TestHarness harness = new TestHarness();
        harness.timeTasks(5,() -> System.out.println("Hello World!"));
    }
}
