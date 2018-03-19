package dsa.sortest;

import dsa.sort.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SortTest {

    @Test
    public void HeapSortTest() {
        Sort<Integer> sort = new HeapSort<>();
        Sort(sort);
    }

    @Test
    public void InsertionSortTest() {
        Sort<Integer> sort = new InsertionSort<>();
        Sort(sort);
    }

    @Test
    public void MergeSortTest() {
        Sort<Integer> sort = new MergeSort<>();
        Sort(sort);
    }

    @Test
    public void BubbleSortTest() {
        Sort<Integer> sort = new BubbleSort<>();
        Sort(sort);
    }

    @Test
    public void SelectionSort() {
        Sort<Integer> sort = new SelectionSort<>();
        Sort(sort);
    }

    private static <E extends Comparable<E>>void Sort(Sort<E> sort) {
        int size = 100000;
        Random random = new Random();
        Integer[] data = new Integer[size];
        for(int i = 0; i < size; i++) {
            data[i] = random.nextInt(size);
        }

        sort.sort((E[]) data, 0, data.length);
        for(int i = 1; i < size; i++) {
            Assert.assertTrue((data[i - 1].compareTo(data[i])) <= 0);
        }
    }
}
