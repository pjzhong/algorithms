package concurrency.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class GetOne {

    private final FutureTask<Integer> getOne = new FutureTask<>(() -> {
        TimeUnit.SECONDS.sleep(2);//Act as a long-run task
        return 1;
    });
    private final Thread thread = new Thread(getOne);//The thread in which the computation will be performed.

    public void start() {
        thread.start();
    }

    public Integer get() throws ExecutionException, InterruptedException {
        return getOne.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        GetOne getOne = new GetOne();
        getOne.start();
        System.out.println(getOne.get());
    }
}
