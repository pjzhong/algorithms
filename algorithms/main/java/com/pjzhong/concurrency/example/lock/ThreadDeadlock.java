package com.pjzhong.concurrency.example.lock;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadlock {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    public class LoadFileTask implements Callable<String> {
        private String name;

        public LoadFileTask(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            return name;
        }
    }

    public class RenderPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = executor.submit(new LoadFileTask("header.html"));
            footer = executor.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            return "<body></body>";
        }
    }

    public void execute() {
        //If tasks execute in thread pool is not independent and relate other tasks in the same pool,
        // be aware of the pool size. It may deadlock like this one.
        //You can fixed it using a unbounded ThreadPool, like Executors.newCachedThreadPool.
        executor.submit(new RenderPageTask());
    }

    public static void main(String[] args) {
        ThreadDeadlock deadlock = new ThreadDeadlock();
        deadlock.execute();
    }
}
