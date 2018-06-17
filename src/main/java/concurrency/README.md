# Examples
  - Synchronizers
     - [A simple ObjectPool with Semaphore](example/ObjectPool.java)
     - [Latch Demo](example/TestHarness.java)
     - [HorseRace using CyclicBarrier](example/HorseRace.java)
  - Executor
     - [Something I know about Executor](example/Executor.java)
  - Lock - Locking is not just about mutual exclusion; it is also about memory visibility
     - [LockSplitting](exmaple/locl/LockSplitting.java)
     - [IntrinsicLocks](example/lock/IntrinsicLocks.java)
     - ConditionQueue
         - [GrumpyBoundedBuffer - just throw exception](example/managestate/impl/GrumpyBoundedBuffer.java)
         - [SleepyBoundedBuffer - sleep a while](example/managestate/impl/SleepyBoundedBuffer.java)
         - [IntrinsicBoundedBuffer - multi predicates on the same queue](example/managestate/impl/IntrinsicBoundedBuffer.java)
         - [ConditionQueueBoundedBuffer - multi predicates on the different queues](example/managestate/impl/ConditionQueueBoundedBuffer.java)
  - Deadlock
     - [dependent Tasks Deadlock](example/lock/ThreadDeadlock.java)
     - [LockOrdering](example/lock/LeftRightDeadLock.java)
  - AQS - AbstractQueuedSynchronizer
     - [OneShotLatch](example/aqs/OneShotLatch.java)

# Pattern
- [ProducerConsumer - Using BlockQueue](pattern/ProducerConsumer.java)
- [Cache Example](pattern/MomorizerExample.java)
- [MonitorPattern](pattern/MonitorPattern.java)


## Race Conditions
A race condition occurs when the correctness of a computation depends on the relative timing or interleaving of multiple
threads by the runtime.
 - using a potentially stale observation to make a decision or perform a computation.This type of race condition is
 called [check-then-act](example/LazyInitRace.java) or [read-modify-write](example/HitCounter.java).

## Intrinsic Locks
Every Java object can act as a lock for purposes of synchronization(using the Keyword - synchronized) as long it is use
consistently. These build-in locks are called intrinsic locks or monitor locks, these lock are
mutexes(Only One thread can held the lock at a time).Intrinsic lock is reentrancy, acquiring the same lock would't
cause deadlock.
 - [IntrinsicLocks](lock/IntrinsicLocks.java)

## Volatile Variables
A weaker form of synchronization, Volatile. Using volatile can ensure memory visibility and prevent reordered with other
operations. volatile variable mostly use as completion, interruption, or status flag.
 - [UsingVolatileVariable](example/VolatileVariable.java)

## Immutable Object
Immutable object are always thread-safe, you are free to share it to multi threads without any protection.Like the object
you most use in java:String, Integer....etc



