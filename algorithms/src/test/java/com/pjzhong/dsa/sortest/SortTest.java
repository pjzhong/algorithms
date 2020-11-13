package com.pjzhong.dsa.sortest;

import com.pjzhong.dsa.sort.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
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
    public void ShellSortTest() {
        Sort<Integer> sort = new ShellSort<>();
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
    public void SelectionSortTest() {
        Sort<Integer> sort = new SelectionSort<>();
        Sort(sort);
    }

    @Test
    public void QuickSortTest() {
        Sort<Integer> sort = new QuickSort<>();
        Sort(sort);
    }

    private void Sort(Sort sort) {
        Integer[] thiData = Arrays.copyOf(data, data.length);

        sort.sort(thiData, 0, thiData.length);
        for(int i = 1, size = thiData.length; i < size; i++) {
            Assert.assertTrue((thiData[i - 1].compareTo(thiData[i])) <= 0);
        }
    }

    @BeforeClass
    public static void beforeClass() {
        int size = 1000;
        Random random = new Random();
        data = new Integer[size];
        for(int i = 0; i < size; i++) {
            data[i] = random.nextInt(size);
        }
    }

    private static Integer[] data;

}
