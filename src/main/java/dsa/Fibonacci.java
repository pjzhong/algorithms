package dsa;

/**
 * Created by Administrator on 2/9/2018.
 */
public class Fibonacci {

    private int current = 1;
    private int previous = 0;
    private int index = 1;

    public Fibonacci(int index) {
        if(index > 1) {
            this.index = index;
            init(index);
        }
    }

    private void init(int index) {
        for(int i = 2; i <= index; i++) {
            current = previous + current;
            previous = current - previous;
        }
    }

    public int get() {
        return current;
    }

    public int prev() {
        if(index >= 2 ) {
            previous = current - previous;
            current = current - previous;
        }

        return current;
    }
}
