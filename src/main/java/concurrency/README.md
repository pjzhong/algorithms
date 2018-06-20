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
     - [MonitorPattern](example/lock/MonitorPattern.java)
     - [ReadWriteLock](example/lock/ReadWriteMap.java)
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
  - ConcurrencyCollection
      - [ProducerConsumer - Using BlockQueue](example/ProducerConsumer.java)
      - [Cache Example - ConcurrentHashMap](example/MomorizerExample.java)

## Race Conditions
A race condition occurs when the correctness of a computation depends on the relative timing or interleaving of multiple
threads by the runtime.for example, more than one threads read or write on the same shared variable without proper
synchronization
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

## Compare and Swap(CAS)
CAS has three operands - a memory location V on which to operate,, the expected old value A, and the new value B.
CAS atomically updates V to the new value B, but only if the value in V matches the expected old value A; otherWise it
does nothing.In either case, it return the value currently in V.(The variant called compare-and-set instead return
whether the operation succeeded.)
 - [SimulatedCAS](atomic_variable/SimulatedCAS.java)
 - [A threadSafe Counter Using SimulatedCAS](atomic_variable/CasCounter.java)
 - Non-blocking Algorithms base on CAS
     - [ConcurrentStack](atomic_variable/ConcurrentStask.java)
     - [LinkedQueue](atomic_variable/LinkedQueue.java) and it's [unit-test](/src/test/java/concurrency/LinkedQueueTest.java)

