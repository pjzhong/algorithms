package dsa.treetest;

import dsa.tree.SplayBinarySearchTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

public class SplayTreeTest {

    SplayBinarySearchTree<Integer, String> tree = new SplayBinarySearchTree<>();

    private int loops = 1000;

    @Before
    public void before() {
        Random random = new Random();

        for(int i = 0, next; i < loops; i++) {
            next = random.nextInt(loops);
            tree.put(next, String.valueOf(next));
        }
     }

    @Test
    public void inOrderTraversalGet() {
        Set<String> stringSet = new HashSet<>();
        List<String>  stringList = new ArrayList<>();
        tree.inOrderTraversal(stringSet::add);
        tree.inOrderTraversal(stringList::add);

        for(String str : stringList) {
            Assert.assertNotNull(tree.get(Integer.valueOf(str)));
        }

        for(String str : stringSet) {
            Assert.assertNotNull(tree.get(Integer.valueOf(str)));
        }

        for(String str : stringList) {
            Assert.assertTrue(stringSet.contains(str));
        }

        Assert.assertEquals(stringSet.size(), tree.size());
        Assert.assertEquals(stringList.size(), tree.size());
        Assert.assertEquals(stringSet.size(), stringList.size());
    }

    @Test
    public void randomGet() {
        Set<String> strings = new HashSet<>();
        tree.inOrderTraversal(strings::add);

        Random random = new Random();
        for(int i = 0, next; i < loops; i++) {
            next = random.nextInt(loops);
            String nextStr= String.valueOf(next), treeGet = tree.get(next);
            if(treeGet == null) {
                Assert.assertFalse(strings.contains(nextStr));
            }
        }

        Assert.assertEquals(strings.size(), tree.size());
    }
}