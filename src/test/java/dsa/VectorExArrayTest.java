package dsa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;


public class VectorExArrayTest {

    int[] nums = new int[100];

    @Before
    public void before() {
        Arrays.fill(nums, Integer.MIN_VALUE);
    }

    @Test
    public void test () {
        Vector_OrderExArray<Integer> integers = new Vector_OrderExArray<>();
        Random random = new Random(System.currentTimeMillis());
        for(int i = 20; i >= 1; i--) {
            integers.insert(i);
        }
        integers.insert(3);

      /*  integers.uniquify();*/
        integers.sort();
        for(Integer i : integers) {
            System.out.println(i);
        }
      /*  int index = integers.search(3);
        Assert.assertNotEquals("Not found", -1, index);
        Assert.assertEquals(3, integers.get(index).intValue());*/
    }

    private int removeRange(int lo, int hi) {
        if(lo == hi) { return 0; }
        while(hi < 99) { nums[lo++] = nums[hi++];}

        return hi - lo;
    }
}
