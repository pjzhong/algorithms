package dsa.treetest;

import dsa.tree.RedBlackTree;
import org.junit.Before;
import org.junit.Test;

public class RedBlackTreeTest {

    RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

    @Before
    public void before() {
        int[] datas= {10, 20, 30, 15};
        
        for(int i : datas) {
            tree.put(i, i);
        }
    }

    @Test
    public void test() {

    }

}
