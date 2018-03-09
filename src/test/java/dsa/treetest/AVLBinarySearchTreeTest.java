package dsa.treetest;

import dsa.tree.AVLBinarySearchTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

public class AVLBinarySearchTreeTest {

    AVLBinarySearchTree<Integer, String> tree = new AVLBinarySearchTree<>();
    private int randomLimit = 100000000;


    @Before
    public void before() {
        Random random = new Random();
        int loops = 10000;
        for(int i = 1, next = -1; i <= loops; i++) {
            next = random.nextInt(randomLimit);
            tree.put(i, String.valueOf(i));
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
    public void randomDeleteTest() {
        String testTarget = null;
        Random random = new Random();
        int loops = 100000;
        for(int i = 0; i <= loops && testTarget == null; i++) {
            testTarget = tree.get(random.nextInt(randomLimit));
        }

        Assert.assertNotNull("testTarget is null", testTarget);

        int expectedSize = tree.size() - 1;
        String afterDelete = tree.delete(Integer.valueOf(testTarget));
        Assert.assertEquals(testTarget, afterDelete);
        Assert.assertEquals(expectedSize, tree.size());
        Assert.assertNull(tree.get(Integer.valueOf(testTarget)));
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
