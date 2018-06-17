package concurrency.example.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    public class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int ignored) {
            //Succeed if latch is open(state == 1), else fail
            return getState() == 1 ? 1 : -1;
        }

        @Override
        public boolean tryReleaseShared(int ignored) {
            setState(1);//Latch is now open
            return true;
        }
    }
}
