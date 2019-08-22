package com.pjzhong.dsa.list;

import java.util.Random;

/**
 * Created by PJ_Z on 2/18/2018.
 */
public class Searchs {

    private static Random random = new Random(System.currentTimeMillis());

    public static int search(Comparable[] elements, Comparable value) {
        int result = -1;

        switch (random.nextInt(2)) {
            case 0: result = binarySearch(elements, value, 0, elements.length);break;
            case 1: result = fibonacciSearch(elements, value, 0, elements.length);break;
        }

        return result;
    }

    private static int binarySearch(Comparable[] elements, Comparable value, int lo, int hi) {
        while (lo < hi) {
            int mid = (lo + hi) >>> 1, compareResult = value.compareTo(elements[mid]);
            if(compareResult < 0 ) {
                hi = mid;
            } else {
                lo = mid + 1;
            }//[lo, mi)或(mi, hi)
        }//出口时 A[lo = hi] 为大于e的最小元素

        return --lo;//lo - 1既不大于value的元素的最大index
    }

    private static int fibonacciSearch(Comparable[] elements, Comparable value, int lo, int hi) {
        Fibonacci fib = new Fibonacci(hi - lo);
        while(lo < hi) {
            while(hi - lo < fib.get()) {
                fib.prev();
            }

            int mid = lo + fib.get() - 1, compare = value.compareTo(elements[mid]);
            if(compare < 0) {
                hi = mid;
            } else if(0 < compare) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}
