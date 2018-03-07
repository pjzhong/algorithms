package dsa.treetest;

import dsa.tree.AVLBinarySearchTree;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.function.Consumer;

public class AVLBinarySearchTreeTest {

    AVLBinarySearchTree<Integer, String> tree = new AVLBinarySearchTree<>();
    private int randomLimit = 100000000;


    @Before
    public void before() {
        Random random = new Random();
        int loops = 1000000;
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
}
