package dsa.listtest;

import dsa.list.PriorityQueue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class PriorityQueueTest {

    @Test
    public void addAndRemoveTest() {
        PriorityQueue<Integer> integers = new PriorityQueue<>();
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
}
