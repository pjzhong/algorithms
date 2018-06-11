package concurrency.example;


import java.util.concurrent.TimeUnit;

public class VolatileVariable implements Runnable {

    private volatile boolean asleep = false;

    @Override
    public void run() {
        int i = 0;
        while (!this.asleep) {
            System.out.println(i++);
        }
        System.out.format("%s I am done\n", Thread.currentThread());
    }


    public static void main(String[] args) throws InterruptedException {
        VolatileVariable variable = new VolatileVariable();
        Thread thread = new Thread(variable);
        thread.start();

        TimeUnit.SECONDS.sleep(3);
        variable.asleep = true;
        System.out.format("%s is going to sleep\n", thread.toString());
    }
}
