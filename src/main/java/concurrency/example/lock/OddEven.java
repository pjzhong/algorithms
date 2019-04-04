package concurrency.example.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基偶轮流输出
 */
public class OddEven {

  private ReentrantLock lock = new ReentrantLock();
  private volatile String working = "even";
  private volatile boolean go = true;

  private class Count implements Runnable {

    private int start;
    private String self, other;
    private Condition selfGo, otherGo;

    Count(int start, String self, String other, Condition selfWait,
        Condition otherGo) {
      this.start = start;
      this.self = self;
      this.other = other;
      this.selfGo = selfWait;
      this.otherGo = otherGo;
    }

    @Override
    public void run() {
      lock.lock();
      try {
        do {
          //检查运行条件
          while (!working.equals(self)) {
            if (go) {
              selfGo.await(1000L, TimeUnit.MILLISECONDS);
            } else {
              return;
            }
          }

          //干活
          for (int i = 0; i < 10; i++) {
            System.out.printf("%d ", start);
            start += 2;
          }
          System.out.printf("--------%4s--------%n", self);

          //切换
          working = other;
          otherGo.signal();
        } while (go);
      } catch (InterruptedException e) {
        e.printStackTrace();
        stop();
      } finally {
        otherGo.signal();
        lock.unlock();
      }
    }
  }

  public void start() {
    Condition evenGo = lock.newCondition(), oddGo = lock.newCondition();
    new Thread(new Count(1, "odd", "even", oddGo, evenGo), "1").start();
    new Thread(new Count(2, "even", "odd", evenGo, oddGo), "3").start();
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
