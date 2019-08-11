package concurrency.example.lock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReentrantReadWriteUpdateLockTest {

  ExecutorService one, two;
  ReentrantReadWriteUpdateLock readWriteLock;

  @Before
  public void setUp() throws Exception {
    one = Executors.newFixedThreadPool(1);
    two = Executors.newFixedThreadPool(1);
    readWriteLock = new ReentrantReadWriteUpdateLock();
  }

  @After
  public void tearDown() {
    one.shutdown();
    two.shutdown();
  }

  @Test
  public void testUpdateLockIsExclusive() throws Exception {
    assertTrue(one.submit(new TryLockTask(readWriteLock.updateLock())).get());
    assertFalse(two.submit(new TryLockTask(readWriteLock.updateLock())).get());
    assertTrue(one.submit(new UnlockTask(readWriteLock.updateLock())).get());

    assertTrue(two.submit(new TryLockTask(readWriteLock.updateLock())).get());
    assertTrue(two.submit(new UnlockTask(readWriteLock.updateLock())).get());
  }

  @Test
  public void testUpdateLockAllowsOtherReaders() throws Exception {
    assertTrue(one.submit(new TryLockTask(readWriteLock.updateLock())).get());
    assertTrue(two.submit(new TryLockTask(readWriteLock.readLock())).get());
    assertTrue(one.submit(new UnlockTask(readWriteLock.updateLock())).get());
    assertTrue(two.submit(new UnlockTask(readWriteLock.readLock())).get());
  }

  @Test
  public void testUpdateLockBlocksOtherWrites() throws Exception {
    assertTrue(one.submit(new TryLockTask(readWriteLock.updateLock())).get());
    assertFalse(two.submit(new TryLockTask(readWriteLock.writeLock())).get());
    assertTrue(one.submit(new UnlockTask(readWriteLock.updateLock())).get());
    assertTrue(two.submit(new TryLockTask(readWriteLock.writeLock())).get());
    assertTrue(two.submit(new UnlockTask(readWriteLock.writeLock())).get());
  }

  @Test
  public void testWriteLockBlocksOtherReader() throws ExecutionException, InterruptedException {
    assertTrue(one.submit(new TryLockTask(readWriteLock.writeLock())).get());
    assertFalse(two.submit(new TryLockTask(readWriteLock.readLock())).get());
    assertTrue(one.submit(new UnlockTask(readWriteLock.writeLock())).get());

    assertTrue(two.submit(new TryLockTask(readWriteLock.readLock())).get());
    assertTrue(two.submit(new UnlockTask(readWriteLock.readLock())).get());
  }

  @Test
  public void testUpdateLockUpgradeToWriteLock() throws Exception {
    assertTrue(one.submit(new TryLockTask(readWriteLock.updateLock())).get());
    assertTrue(one.submit(new TryLockTask(readWriteLock.writeLock())).get());
    assertFalse(readWriteLock.readLock().tryLock());
    assertTrue(one.submit(new UnlockTask(readWriteLock.updateLock())).get());
    assertTrue(one.submit(new UnlockTask(readWriteLock.writeLock())).get());

    assertTrue(one.submit(new TryLockTask(readWriteLock.readLock())).get());
    assertTrue(one.submit(new UnlockTask(readWriteLock.readLock())).get());
  }

  @Test
  public void testPreventedFromAcquiringReadLockIfHoldingUpdateLock()
      throws ExecutionException, InterruptedException {
    assertTrue(one.submit(new TryLockTask(readWriteLock.updateLock())).get());

    Exception expected = null;
    try {
      one.submit(new TryLockTask(readWriteLock.readLock())).get();
    } catch (ExecutionException e) {
      if (e.getCause() instanceof IllegalStateException) {
        expected = e;
      }
    }

    assertNotNull(expected);
    assertTrue(one.submit(new UnlockTask(readWriteLock.updateLock())).get());

    assertTrue(one.submit(new TryLockTask(readWriteLock.readLock())).get());
    assertTrue(one.submit(new UnlockTask(readWriteLock.readLock())).get());
  }

  @Test
  public void testPreventedFromAcquiringUpdateLockIfHoldingReadLock()
      throws ExecutionException, InterruptedException {
    assertTrue(one.submit(new TryLockTask(readWriteLock.readLock())).get());

    Exception expected = null;
    try {
      one.submit(new TryLockTask(readWriteLock.updateLock())).get();
    } catch (ExecutionException e) {
      if (e.getCause() instanceof IllegalStateException) {
        expected = e;
      }
    }

    assertNotNull(expected);
    assertTrue(one.submit(new UnlockTask(readWriteLock.readLock())).get());

    assertTrue(one.submit(new TryLockTask(readWriteLock.updateLock())).get());
    assertTrue(one.submit(new UnlockTask(readWriteLock.updateLock())).get());
  }


  @Test
  public void testPreventedFromAcquiringWriteLockIfHoldingReadLock()
      throws ExecutionException, InterruptedException {
    assertTrue(one.submit(new TryLockTask(readWriteLock.readLock())).get());

    Exception expected = null;
    try {
      one.submit(new TryLockTask(readWriteLock.writeLock())).get();
    } catch (ExecutionException e) {
      if (e.getCause() instanceof IllegalStateException) {
        expected = e;
      }
    }

    assertNotNull(expected);
    assertTrue(one.submit(new UnlockTask(readWriteLock.readLock())).get());

    assertTrue(one.submit(new TryLockTask(readWriteLock.writeLock())).get());
    assertTrue(one.submit(new UnlockTask(readWriteLock.writeLock())).get());
  }


  @Test
  public void testReadLockHoldCount() {
    readWriteLock.readLock().lock();
    int hc1 = ((ReentrantReadWriteUpdateLock.ReadLock) readWriteLock.readLock()).holdCount().value;
    readWriteLock.readLock().unlock();
    int hc2 = ((ReentrantReadWriteUpdateLock.ReadLock) readWriteLock.readLock()).holdCount().value;

    assertEquals(1, hc1);
    assertEquals(0, hc2);
  }

  @Test
  public void testReadLockHoldCount_Interruptibly() throws InterruptedException {
    readWriteLock.readLock().lockInterruptibly();
    int hc1 = ((ReentrantReadWriteUpdateLock.ReadLock) readWriteLock.readLock()).holdCount().value;
    readWriteLock.readLock().unlock();
    int hc2 = ((ReentrantReadWriteUpdateLock.ReadLock) readWriteLock.readLock()).holdCount().value;

    assertEquals(1, hc1);
    assertEquals(0, hc2);
  }

  @Test
  public void testReadLockHoldCount_WithTimeout() throws ExecutionException, InterruptedException {
    assertTrue(one.submit(new TryLockTask(readWriteLock.writeLock())).get());

    assertFalse(readWriteLock.readLock().tryLock(1, TimeUnit.SECONDS));
    int hc1 = ((ReentrantReadWriteUpdateLock.ReadLock) readWriteLock.readLock()).holdCount().value;
    assertEquals(0, hc1);

    assertTrue(one.submit(new UnlockTask(readWriteLock.writeLock())).get());

    assertTrue(readWriteLock.readLock().tryLock(1, TimeUnit.SECONDS));
    int hc2 = ((ReentrantReadWriteUpdateLock.ReadLock) readWriteLock.readLock()).holdCount().value;
    assertEquals(1, hc2);

    readWriteLock.readLock().unlock();
    int hc3 = ((ReentrantReadWriteUpdateLock.ReadLock) readWriteLock.readLock()).holdCount().value;
    assertEquals(0, hc3);
  }


  @Test
  public void testUpdateLockHoldCount() {
    readWriteLock.updateLock().lock();
    int hc1 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;
    readWriteLock.updateLock().unlock();
    int hc2 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;

    assertEquals(1, hc1);
    assertEquals(0, hc2);
  }

  @Test
  public void testUpdateLockHoldCount_Interruptibly() throws InterruptedException {
    readWriteLock.updateLock().lockInterruptibly();
    int hc1 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;
    readWriteLock.updateLock().unlock();
    int hc2 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;

    assertEquals(1, hc1);
    assertEquals(0, hc2);
  }

  @Test
  public void testUpdateLockHoldCount_WithTimeout()
      throws ExecutionException, InterruptedException {
    assertTrue(one.submit(new TryLockTask(readWriteLock.writeLock())).get());

    assertFalse(readWriteLock.updateLock().tryLock(1, TimeUnit.SECONDS));
    int hc1 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;
    assertEquals(0, hc1);

    assertTrue(one.submit(new UnlockTask(readWriteLock.writeLock())).get());

    assertTrue(readWriteLock.updateLock().tryLock(1, TimeUnit.SECONDS));
    int hc2 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;
    assertEquals(1, hc2);

    readWriteLock.updateLock().unlock();
    int hc3 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;
    assertEquals(0, hc3);
  }

  @Test
  public void testWriteLockAcquiresUpdateLock() throws Exception {
    readWriteLock.updateLock().lock();
    int hc1 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;
    assertEquals(1, hc1);

    readWriteLock.writeLock().lock();
    int hc2 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;
    assertEquals(2, hc2);

    readWriteLock.writeLock().unlock();
    int hc3 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;
    assertEquals(1, hc3);

    readWriteLock.updateLock().unlock();
    int hc4 = ((ReentrantReadWriteUpdateLock.UpdateLock) readWriteLock.updateLock())
        .holdCount().value;
    assertEquals(0, hc4);
  }

  @Test
  public void testWriteLock_Lock() throws ExecutionException, InterruptedException {
    readWriteLock.writeLock().lock();

    assertFalse(two.submit(new TryLockTask(readWriteLock.readLock())).get());

    readWriteLock.writeLock().unlock();
  }

  @Test
  public void testWriteLock_LockInterruptibly() throws ExecutionException, InterruptedException {
    readWriteLock.writeLock().lockInterruptibly();

    assertFalse(two.submit(new TryLockTask(readWriteLock.readLock())).get());

    readWriteLock.writeLock().unlock();
  }

  @Test
  public void testWriteLock_LockTimeout() throws ExecutionException, InterruptedException {
    readWriteLock.writeLock().tryLock(1, TimeUnit.SECONDS);

    assertFalse(two.submit(new TryLockTask(readWriteLock.readLock())).get());

    readWriteLock.writeLock().unlock();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testReadLockNewCondition() throws Exception {
    readWriteLock.readLock().newCondition();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testUpdateLockNewCondition() throws Exception {
    readWriteLock.updateLock().newCondition();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testWriteLockNewCondition() throws Exception {
    readWriteLock.writeLock().newCondition();
  }


  static class TryLockTask implements Callable<Boolean> {

    final Lock lock;

    public TryLockTask(Lock lock) {
      this.lock = lock;
    }

    @Override
    public Boolean call() {
      return lock.tryLock();
    }
  }

  static class UnlockTask implements Callable<Boolean> {

    final Lock lock;

    public UnlockTask(Lock lock) {
      this.lock = lock;
    }

    @Override
    public Boolean call() {
      lock.unlock();
      return true;
    }
  }
}
