package concurrency.example.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class OddEven {

  private ReentrantLock lock = new ReentrantLock();
  private Condition evenGo = lock.newCondition();
  private Condition oddGo = lock.newCondition();
  private volatile String working = "even";
  private volatile boolean go = true;

  private class Even implements Runnable {

    @Override
    public void run() {
      int start = 2, grow = 2;
      try {
        lock.lock();
        while (go) {
          while (!working.equals(Thread.currentThread().getName())) {
            evenGo.await();
          }

          for (int i = 0; i < 10; i++) {
            System.out.format("%d ", start);
            start += grow;
          }
          System.out.printf("--------------%s--------%n", working);
          working = "odd";
          oddGo.signal();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
        stop();
      } finally {
        oddGo.signalAll();
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
        while (go) {
          while (!working.equals(Thread.currentThread().getName())) {
            oddGo.await();
          }

          for (int i = 0; i < 10; i++) {
            System.out.format("%d ", start);
            start += grow;
          }
          System.out.printf("-------------%s----------------%n", working);
          working = "even";
          evenGo.signal();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        evenGo.signalAll();
        lock.unlock();
      }
    }
  }

  public void start() {
    new Thread(new Even(), "even").start();
    new Thread(new Odd(), "odd").start();
  }

  private void stop() {
    go = false;
  }

  public static void main(String[] args) throws InterruptedException {
    OddEven oddEven = new OddEven();
    oddEven.start();
    TimeUnit.MILLISECONDS.sleep(100L);
    oddEven.stop();
  }
}
