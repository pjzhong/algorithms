package dsa.sortest;

import dsa.sort.HeapSort;
import dsa.sort.Sort;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class HeapSortTest {

    @Test
    public void test() {
        Sort<Integer> sort = new HeapSort<>();
        int size = 10000000;
        Random random = new Random();
        Integer[] data = new Integer[size];
        for(int i = 0; i < size; i++) {
            data[i] = random.nextInt(size);
        }

        data = sort.sort(data, 0, data.length);
        for(int i = 1; i < size; i++) {
            Assert.assertTrue((data[i - 1].compareTo(data[i])) <= 0);
        }
    }
}
