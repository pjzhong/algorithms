package dsa.treetest;

import dsa.tree.AVLBinarySearchTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

public class AVLBinarySearchTreeTest {

    private static AVLBinarySearchTree<Integer, String> tree = new AVLBinarySearchTree<>();
    private static int randomLimit = 100000000;


    @BeforeClass
    public static void before() {
        Random random = new Random();
        int loops = 10000;
        for(int i = 1, next = -1; i <= loops; i++) {
            next = random.nextInt(randomLimit);
            tree.put(next, String.valueOf(next));
        }
    }

    @Test
    public void inOrderTraversalTest() {
        Consumer<String> consumer = (s) -> System.out.format("%s ", s);
        tree.inOrderTraversal(consumer);
    }

    @Test
    public void deleteMaxMin() {
        int expectedSize = tree.size() - 2;
        String maximum = tree.max() ,minimum = tree.min();
        String AfterDeleteMaximum = tree.deleteMax() ,AfterDeleteMinimum = tree.deleteMin();

        Assert.assertEquals(expectedSize, tree.size());
        Assert.assertEquals(maximum, AfterDeleteMaximum);
        Assert.assertEquals(minimum, AfterDeleteMinimum);
        Assert.assertNull(tree.get(Integer.valueOf(maximum)));
        Assert.assertNull(tree.get(Integer.valueOf(minimum)));
    }

    @Test
    public void deleteTest() {
        List<String> strs = new LinkedList<>();
        tree.inOrderTraversal(strs::add);

        for(String str : strs) {
            tree.remove(Integer.valueOf(str));
        }

        Assert.assertEquals(0, tree.size());
        Assert.assertTrue(tree.isEmpty());
    }


    @Test
    public void traversalTest() {
        Consumer<String> consumer =  System.out::println;

        tree.preOrderTraversal(consumer);
        tree.inOrderTraversal(consumer);
        tree.levelTraversal(consumer);
        tree.postOrderTraversal(consumer);
    }
}

