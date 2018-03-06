package dsa.treetest;

import dsa.tree.BinarySearchTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTest {

    BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();

    @Before
    public void before() {
        int[] values = {16, 10, 25, 5, 11, 19, 28, 2, 8, 15, 17, 22, 27, 37, 4, 13, 33};
        for(int value : values) {
            tree.put(value, value);
        }
    }

    @Test
    public void insertTest() {
        int[] values = {34};
        for(int value : values) {
            tree.put(value, value);
        }

        Assert.assertTrue(tree.get(34).equals(34));
        Assert.assertEquals(18, tree.size());
        Assert.assertEquals(5, tree.height());
    }

    @Test
    public void deleteTest() {
        int four = tree.delete(4);
        int twentyFive = tree.delete(25);
        
        Assert.assertEquals(4, four);
        Assert.assertEquals(25, twentyFive);
    }

    @Test
    public void deleteMaxMin() {
        int maximum = tree.deleteMax();
        int minimum = tree.deleteMin();
        Assert.assertEquals(2, minimum);
        Assert.assertEquals(37, maximum);
    }

}
