package concurrency.example;

/**
 * You can fixed this by using explicitly lock(ReentrantLock.tryLock())
 * */
public class LeftRIghtDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                doSomething();
            }
        }
    }

    private void doSomething() {}
}
