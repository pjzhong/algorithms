package concurrency.example.lock;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Locks {

  public static <L extends Lock> void lockAll(Iterable<L> locks) {
    Deque<L> stack = new LinkedList<>();
    try {
      for (L lock : locks) {
        lock.lock();
        stack.push(lock);
      }
    } catch (RuntimeException e) {
      unlockAll(stack);
      throw e;
    }
  }

  public static <L extends Lock> void lockInterruptiblyAll(Iterable<L> locks)
      throws InterruptedException {
    Deque<L> stack = new LinkedList<>();
    try {
      for (L lock : locks) {
        lock.lockInterruptibly();
        stack.push(lock);
      }
    } catch (Exception e) {
      unlockAll(stack);
      throw e;
    }
  }

  public static <L extends Lock> boolean tryLockAll(Iterable<L> locks) {
    Deque<L> stack = new LinkedList<>();
    boolean success = false;
    try {
      for (L lock : locks) {
        success = lock.tryLock();
        if (success) {
          stack.push(lock);
        } else {
          break;
        }
      }
    } catch (Exception e) {
      unlockAll(stack);
      throw e;
    }
    return success;
  }

  public static <L extends Lock> boolean tryLockAll(long time, TimeUnit unit, Iterable<L> locks)
      throws InterruptedException {
    Deque<L> stack = new LinkedList<>();
    boolean success = false;
    try {
      long limitNanos = unit.toNanos(time);
      long startNanos = System.nanoTime();
      for (L lock : locks) {
        long remainingNanos = !success ? limitNanos : limitNanos - (System.nanoTime() - startNanos);

        success = lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS);
        if (success) {
          stack.push(lock);
        } else {
          break;
        }
      }
    } catch (Exception e) {
      unlockAll(stack);
      throw e;
    }

    if (!success) {
      unlockAll(stack);
    }
    return success;
  }

  public static <L extends Lock> void unlockAll(Iterable<L> locks) {
    for (L lock : locks) {
      lock.unlock();
    }
  }

  public static <L extends Lock> void lockAll(L... locks) {
    lockAll(Arrays.asList(locks));
  }

  public static <L extends Lock> void lockInterruptiblyAll(L... locks)
      throws InterruptedException {
    lockInterruptiblyAll(Arrays.asList(locks));
  }

  public static <L extends Lock> boolean tryLockAll(L... locks) {
    return tryLockAll(Arrays.asList(locks));
  }

  public static <L extends Lock> boolean tryLockAll(long time, TimeUnit unit, L... locks)
      throws InterruptedException {
    return tryLockAll(time, unit, Arrays.asList(locks));
  }

  public static <L extends Lock> void unlockAll(L... locks) {
    unlockAll(Arrays.asList(locks));
  }

}
