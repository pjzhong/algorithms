package dsa.listtest;

import org.junit.Test;

/**
 * Created by Administrator on 2/9/2018.
 */
public class FibonacciTest {

    @Test
    public void test() {
        int a = 4181, b = 2584;

        b = a - b;
        a = a - b;

        System.out.println(a);
        System.out.println(b);
    }

}
