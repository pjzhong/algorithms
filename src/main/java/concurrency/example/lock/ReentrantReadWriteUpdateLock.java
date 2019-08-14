package concurrency.example.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * For more info, please checkout the author github
 *
 * @link https://github.com/npgall/concurrent-locks
 * @since 2019年08月11日 23:24:56
 **/
public class ReentrantReadWriteUpdateLock {

  private ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
  private Lock updateMutex = new ReentrantLock();

  private final ReadLock readLock = new ReadLock();
  private final UpdateLock updateLock = new UpdateLock();
  private final WriteLock writeLock = new WriteLock();

  public Lock updateLock() {
    return updateLock;
  }

  public Lock readLock() {
    return readLock;
  }

  public WriteLock writeLock() {
    return writeLock;
  }

  static abstract class HoldCountLock implements Lock {

    static class HoldCount {

      int value;
    }

    final ThreadLocal<HoldCount> threadCount = ThreadLocal.withInitial(HoldCount::new);

    final Lock backingLock;

    HoldCountLock(Lock backingLock) {
      this.backingLock = backingLock;
    }

    HoldCount holdCount() {
      return threadCount.get();
    }

    @Override
    public void lock() {
      backingLock.lock();
      holdCount().value++;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
      validatePreconditions();
      backingLock.lockInterruptibly();
      holdCount().value++;
    }

    @Override
    public boolean tryLock() {
      validatePreconditions();
      boolean suc = backingLock.tryLock();
      if (suc) {
        holdCount().value++;
      }
      return suc;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
      validatePreconditions();
      boolean suc = backingLock.tryLock(time, unit);
      if (suc) {
        holdCount().value++;
      }
      return suc;
    }

    @Override
    public void unlock() {
      backingLock.unlock();
      holdCount().value--;
    }

    @Override
    public Condition newCondition() {
      throw new UnsupportedOperationException("This lock does not support conditions");
    }

    abstract void validatePreconditions();
  }

  class ReadLock extends HoldCountLock {

    public ReadLock() {
      super(readWriteLock.readLock());
    }

    @Override
    void validatePreconditions() {
      if (updateLock.holdCount().value > 0) {
        throw new IllegalStateException(
            "Cannot acquire read lock, as this thread previously acquired and must first release the update lock");
      }
    }
  }

  class UpdateLock extends HoldCountLock {

    public UpdateLock() {
      super(updateMutex);
    }

    @Override
    void validatePreconditions() {
      if (readLock.holdCount().value > 0) {
        throw new IllegalStateException(
            "Cannot acquire update lock , as this thread previously acquired and must first release the read lock");
      }
    }
  }

  class WriteLock implements Lock {

    @Override
    public void lock() {
      validatePreconditions();
      Locks.lockAll(updateLock, readWriteLock.writeLock());
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
      validatePreconditions();
      Locks.lockInterruptiblyAll(updateLock, readWriteLock.writeLock());
    }

    @Override
    public boolean tryLock() {
      validatePreconditions();
      return Locks.tryLockAll(updateLock, readWriteLock.writeLock());
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
      validatePreconditions();
      return Locks.tryLockAll(time, unit, updateLock, readWriteLock.writeLock());
    }

    @Override
    public void unlock() {
      Locks.unlockAll(readWriteLock.writeLock(), updateLock);
    }

    @Override
    public Condition newCondition() {
      throw new UnsupportedOperationException("This lock does not support conditions");
    }

    void validatePreconditions() {
      if (readLock.holdCount().value > 0) {
        throw new IllegalStateException(
            "Cannot acquire write lock, as this thread previous acquired and must first release the read lock");
      }
    }
  }

}
