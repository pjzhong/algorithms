package dsa.treetest;

import dsa.tree.RedBlackTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class RedBlackTreeTest {

    RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

    @Before
    public void before() {
       for(int i = 1; i <= 22; i++) {
           tree.put(i, i);
       }
    }

    @Test
    public void deleteTest() {
        List<Integer> strs = new LinkedList<>();
        tree.inOrderTraversal(strs::add);

        for(Integer i : strs) {
            tree.remove(Integer.valueOf(i));
        }

        Assert.assertEquals(0, tree.size());
        Assert.assertTrue(tree.isEmpty());
    }

    @Test
    public void traversalTest() {
        Consumer<Integer> consumer =  System.out::println;

        tree.preOrderTraversal(consumer);
        tree.inOrderTraversal(consumer);
        tree.levelTraversal(consumer);
        tree.postOrderTraversal(consumer);
    }

    @Test
    public void test() {
        tree.remove(17);
    }

}
