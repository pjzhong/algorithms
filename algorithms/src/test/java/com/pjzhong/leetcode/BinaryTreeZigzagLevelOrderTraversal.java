package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 Given a binary tree, return the zigzag level order traversal of its nodes' values.
 (ie, from left to right, then right to left for the next level and alternate between).

 For example:
 Given binary tree [3,9,20,null,null,15,7],
 3
 / \
 9  20
 /  \
 15   7
 return its zigzag level order traversal as:
 [
 [3],
 [20,9],
 [15,7]
 ]

 https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/description/
 * */
public class BinaryTreeZigzagLevelOrderTraversal {

    public List<TreeNode> buildTest() {
        List<TreeNode> testCases = new ArrayList<>();
        TreeNode caseOne = new TreeNode(3);
        caseOne.left = new TreeNode(9);
        caseOne.right = new TreeNode(20);
        caseOne.right.left = new TreeNode(15);
        caseOne.right.right = new TreeNode(7);
        testCases.add(caseOne);

        TreeNode caseTwo = new TreeNode(1);
        caseTwo.left = new TreeNode(2);
        caseTwo.right = new TreeNode(3);
        caseTwo.left.left = new TreeNode(4);
        caseTwo.right.right = new TreeNode(5);
        testCases.add(caseTwo);


        return testCases;
    }

    @Test
    public void test() {
        for(TreeNode node : buildTest()) {
            zigzagLevelOrder(node).forEach(System.out::println);
            System.out.println();
        }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<TreeNode> parent = new LinkedList<>();
        if(root != null) {  parent.add(root); }

        TreeNode node;
        boolean right = false;
        for(;!parent.isEmpty();) {
            LinkedList<Integer> integers = new LinkedList<>();
            int size = parent.size();//A more elegance way to travel better then use to list(parent, child)
            for(int i = 0; i < size; i++) {
                node = parent.poll();
                if(right) { integers.addFirst(node.val); }
                else { integers.addLast(node.val);}

                if(node.left != null) { parent.add(node.left); }
                if(node.right != null) { parent.add(node.right); }
            }

            right = !right;
            if(!integers.isEmpty()) { result.add(integers); }
        }

        return result;
    }
}
