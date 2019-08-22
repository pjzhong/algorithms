package com.pjzhong.dsa.treetest;

import com.pjzhong.dsa.tree.BTree;
import org.junit.Test;

import java.util.Arrays;

public class BTreeTest {

    @Test
    public void test() {
        BTree bTree = new BTree(8);
        for (int i = 0; i <= 100000; i++) {
            bTree.insert(i);
        }

        for(int i = 0; i <= 99999; i++) {
            bTree.remove(i);
        }

        bTree.traverse(System.out::println);
    }

    @Test
    public void removeCopy() {
        int[] ints = {1, 2, 3, 4, 5, 0};
        int n = ints.length;
        int delIdx = 0;

        if (delIdx + 1 < n) {
            System.arraycopy(ints, delIdx + 1, ints, delIdx, n - (delIdx + 1));

        }
        ints[--n] = 0;
        System.out.println(Arrays.toString(ints));
        System.out.println(n);
    }

    @Test
    public void stepAhead() {
        int[] keys = {1, 2, 3, 4, 5, 0};
        int[] child = {1, 2, 3, 4, 5, 6, 0};
        int n = 6;
        int idx = 2;

        System.arraycopy(keys, 0, keys, 1, n - 1);
        System.arraycopy(child, 0, child, 1, n);
        System.out.println(Arrays.toString(keys));
        System.out.println(Arrays.toString(child));
    }

    @Test
    public void stepBehind() {
        int[] keys = {0, 2, 3, 4, 5, 6};
        int[] child = {0, 2, 3, 4, 5, 6,7};
        int n = 6;
        int idx = 2;

        System.arraycopy(keys, 1, keys, 0, n - 1);
        System.arraycopy(child, 1, child, 0, n);
        keys[n - 1] = 0;
        child[n] = 0;
        System.out.println(Arrays.toString(keys));
        System.out.println(Arrays.toString(child));
    }
}
