package concurrency.example;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Executor {

    /**
     * 核心线程数(corePoolSize)：当有任务提交至线程池时，线程池会检查当前线程数是否少于corePoolSize。如果少于，则会创建
     * 一条新的线程，即使当前有空闲的线程也会创建， 否则复用当前空闲的线程。
     *
     * 最大线程数(maximumPoolSize)：既任务数量过多，当前corePoolSize条线程不足以应付，导致任务队列已经满了。
     * 就会创建新的线程(当新任务到来的时候创建)，最多maximumPoolSize条。
     *
     * 存活时间(keepAliveTime)：当线程数已经大于corePoolSize的时候， 线程会存活keepAliveTime继续等到新任务。如果没有则
     * 结束。直到剩下corePoolSize条线程，这几条线程持续存活。
     *
     * 时间单位(TimeUnit):KeepAliveTime的时间单位
     *
     * 任务队列(WorkQueue):只要实现了BlockingQueue, 都可以作为线程池的任务队列。目前JAVA提供了:
     *   1.SynchronousQueue
     *   2.ArrayBlockingQueue
     *   3.LinkedBlockingQueue
     *   4.PriorityBlockingQueue
     *   5.DelayQueue
     *   6.DelayedWOrkQueue - Only available in ScheduledThreadPoolExecutor
     *
     * 线程工厂(ThreadFactory):简单的工厂模式。 线程池的线程都交由它来创建， 可以根据你自己来设置线程的各种属性：如名字....
     *
     * 饱和策略(RejectedExecutionHandler):任务队列已满并且线程数已经达到maximumPoolSize，采取哪种策略来应对。目前线程池
     * 提供的有4种：
     *
     *    1.DiscardOldestPolicy - 放弃任务队列的头部的第一个任务。并且重新尝试重新提交任务
     *    2.AbortPolicy - 直接抛出RejectedExecutionException
     *    3.CallerRunsPolicy - 在提交者的线程里执行提交的任务
     *    4.DiscardPolicy - 静默抛弃当前提交的任务
     *
     * for more info, {@link ThreadPoolExecutor}'s constructors
     * */
    public void ThradPoolParameterExplain() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,//corePoolSize
                10,//maximumPoolSize,
                1000L,//keepAliveTime,
                TimeUnit.MILLISECONDS,//unit
                new LinkedBlockingQueue<>(100),//workQueue
                Thread::new,//ThreadFactory
                new ThreadPoolExecutor.CallerRunsPolicy()//RejectedExecution policy
        );
    }
}
