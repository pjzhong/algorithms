package dsa.listtest;

import dsa.list.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkedListTest {

    LinkedList<Integer> list = new LinkedList<>();

    @Before
    public void before() {
        int sizes = 100;
        for(int i = 0; i < 100; i++) {
            list.add(i);
        }
        Assert.assertEquals(sizes, list.size());
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void clearTest() {
        List<Integer> test = new LinkedList<>();
        for(int i : list) {
            test.add(i);
        }

        for(Integer i : list) {
            test.remove(0);
        }

        Assert.assertEquals(0, test.size());
        Assert.assertTrue(test.isEmpty());
    }

    @Test
    public void iterativeTest() {
        int expectedLoops = list.size(), count = 0;
        for(int i : list) { count++; }

        Assert.assertEquals(expectedLoops, count);
    }

    @Test
    public void addFirstAndLastTest() {
        int expectedSize = list.size() + 2;
        int first = Integer.MIN_VALUE, last = Integer.MAX_VALUE;

        list.addFirst(first);
        list.addLast(last);

        Assert.assertEquals(first, list.getFirst().intValue());
        Assert.assertEquals(last, list.getLast().intValue());
        Assert.assertEquals(expectedSize, list.size());
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void removeFirstAndLastTest() {
        int expectedSize = list.size() - 2;
        int first = list.getFirst(), last = list.getLast();

        int removeFirst = list.removeFirst(), removeLast = list.removeLast() ;

        Assert.assertEquals(first, removeFirst);
        Assert.assertEquals(last, removeLast);
        Assert.assertEquals(expectedSize, list.size());
    }

    @Test
    public void equalsAndHastTest() {
        List<Integer> otherMe = new LinkedList<>();
        for(int i : list) { otherMe.add(i); }

        Assert.assertEquals(list, otherMe);
        Assert.assertEquals(list.hashCode(), otherMe.hashCode());
    }
}
