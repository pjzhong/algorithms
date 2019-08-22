package com.pjzhong.concurrency;

import com.pjzhong.concurrency.example.aqs.OneShotLatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class OneShotLatchTest {

  private class Wait implements Runnable {

    private OneShotLatch latch;

    public Wait(OneShotLatch latch) {
      this.latch = latch;
    }

    @Override
    public void run() {
      System.out.format("%s is Waiting\n", Thread.currentThread());
      try {
        latch.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.format("%s is Done waiting\n", Thread.currentThread());
    }
  }

  @Test
  public void test() throws InterruptedException {
    OneShotLatch latch = new OneShotLatch();
    int threads = 10;
    for (int i = 0; i < threads; i++) {
      new Thread(new Wait(latch)).start();
    }
    TimeUnit.SECONDS.sleep(3);
    latch.signal();
  }
}
