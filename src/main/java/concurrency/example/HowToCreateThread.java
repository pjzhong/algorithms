package concurrency.example;

class UsingRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello Word!");
    }


    public static void main(String[] args) {
        //Runnable就像其它接口一样是独立的，可以单独使用。不一定要与线程绑定
        //所以创建Runnable的实例不算创建线程，Thread只是用Runnable来表示它要执行什么。
        UsingRunnable runnable = new UsingRunnable();
        runnable.run();

        //Using Thread
        new Thread(runnable).start();
    }
}

class ExtendingThread extends Thread {

    //Thread实现了Runnable接口， 所以我们需要重写run()方法， 来执行我们需要的动作
    @Override
    public void run() {
        System.out.println("Hello World!");
    }

    public static void main(String[] args) {
        ExtendingThread thread = new ExtendingThread();

        //把线程单做一个普通Runnable实现类来用，不启动它
        thread.run();

        System.out.println("启动线程");
        thread.start();
    }

}

public class HowToCreateThread {
}
