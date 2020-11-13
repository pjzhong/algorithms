package com.pjzhong.leetcode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class H2O {

  private Semaphore H = new Semaphore(0);
  private Semaphore O = new Semaphore(1);
  private AtomicInteger h = new AtomicInteger();

  public H2O() {

  }

  public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
    H.acquire();
    // releaseHydrogen.run() outputs "H". Do not change or remove this line.
    releaseHydrogen.run();
    if (h.decrementAndGet() == 0) {
      O.release();
    }
  }

  public void oxygen(Runnable releaseOxygen) throws InterruptedException {
    O.acquire();
    // releaseOxygen.run() outputs "H". Do not change or remove this line.
    releaseOxygen.run();
    h.set(2);
    H.release(2);
  }

  public static void main(String[] args) {
    H2O h2O = new H2O();

    ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        0, TimeUnit.SECONDS,
        new SynchronousQueue<>());

    int o = 3;
    for (int i = 0; i < o; i++) {
      executorService.execute(() -> {
        try {
          h2O.oxygen(() -> System.out.print("O"));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    for (int i = 0; i < o * 2; i++) {
      executorService.execute(() -> {
        try {
          h2O.hydrogen(() -> System.out.print("H"));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }
  }

}
