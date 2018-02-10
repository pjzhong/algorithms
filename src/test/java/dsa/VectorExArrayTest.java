package dsa;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 2/4/2018.
 */
public class VectorExArrayTest {

    int[] nums = new int[100];

    @Before
    public void before() {
        Arrays.fill(nums, Integer.MIN_VALUE);
    }

    @Test
    public void test () {
        Vector_OrderExArray<Integer> integers = new Vector_OrderExArray<>();
        for(int i = 1; i <= 7; i++) {
            integers.insert(i % 50);
        }

        integers.uniquify();
        System.out.println(integers.search(1));
    }

    private int removeRange(int lo, int hi) {
        if(lo == hi) { return 0; }
        while(hi < 99) { nums[lo++] = nums[hi++];}

        return hi - lo;
    }
}
