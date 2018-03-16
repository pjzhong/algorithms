package dsa.listtest;

import dsa.list.PriorityQueueLeftHeap;
import dsa.list.Queue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class PriorityQueueTestLeftHeap {

    @Test
    public void addAndRemoveTest() {
        Queue<Integer> integers = new PriorityQueueLeftHeap<>();
        Random random = new Random();
        int loops = 100;
        for(int i = 0; i < loops; i++) {
            integers.enqueue(random.nextInt(loops));
        }
        Assert.assertEquals(loops, integers.size());
        Assert.assertFalse(integers.isEmpty());

        int lastRemove = Integer.MAX_VALUE;
        for(int i = 0, size = integers.size(); i < size; i++) {
            int remove = integers.dequeue();
            Assert.assertTrue(lastRemove >= remove);
            lastRemove = remove;
        }

        Assert.assertEquals(0, integers.size());
        Assert.assertTrue(integers.isEmpty());
    }

    @Test
    public void heapifyTest() {
        int size = 10000;
        Random random = new Random();
        Integer[] ints = new Integer[size];
        for(int i = 0; i < size; i++) {
           ints[i] = random.nextInt(size);
        }

        Queue<Integer> queue = new PriorityQueueLeftHeap<>(ints);
        int lastRemove = Integer.MAX_VALUE;
        for(int i = 0; i < size; i++) {
            int remove = queue.dequeue();
            Assert.assertTrue(lastRemove >= remove);
            lastRemove = remove;
        }

        Assert.assertEquals(0, queue.size());
        Assert.assertTrue(queue.isEmpty());
    }
}
