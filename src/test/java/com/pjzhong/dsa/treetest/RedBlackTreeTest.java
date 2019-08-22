package com.pjzhong.dsa.treetest;

import com.pjzhong.dsa.tree.RedBlackTree;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

public class RedBlackTreeTest {

    private static RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

    @BeforeClass
    public static void before() {
        int size = 1000;
        for(int i = 1; i <= size; i++) {
            tree.put(i, i);
        }
    }

    @Test
    public void inOrderTraversalGet() {
        Set<Integer> stringSet = new HashSet<>();
        List<Integer>  stringList = new ArrayList<>();
        tree.inOrderTraversal(stringSet::add);
        tree.postOrderTraversal(stringList::add);


        for(Integer str : stringSet) {
            Assert.assertNotNull(tree.get(Integer.valueOf(str)));
        }

        //todo 改写成迭代式旋转。如果数据量大，会出现stackOverFlowException
        for(Integer str : stringList) {
            Assert.assertNotNull(tree.get(Integer.valueOf(str)));
        }


        for(Integer str : stringList) {
            Assert.assertTrue(stringSet.contains(str));
        }

        Assert.assertEquals(stringSet.size(), tree.size());
        Assert.assertEquals(stringList.size(), tree.size());
        Assert.assertEquals(stringSet.size(), stringList.size());
    }


    @Test
    public void traversalTest() {
        Consumer<Integer> consumer =  System.out::println;

        tree.preOrderTraversal(consumer);
        tree.inOrderTraversal(consumer);
        tree.levelTraversal(consumer);
        tree.postOrderTraversal(consumer);
    }
}
