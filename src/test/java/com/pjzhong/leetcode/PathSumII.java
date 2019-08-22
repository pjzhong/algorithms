package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.

 Note: A leaf is a node with no children.

 Example:

 Given the below binary tree and sum = 22,

   5
  / \
 4   8
 /   / \
 11  13  4
 /  \    / \
 7    2  5   1
 Return:

 [
 [5,4,11,2],
 [5,8,4,5]
 ]
 https://leetcode.com/problems/path-sum-ii/description/
 * */
public class PathSumII {

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        return pathSum(root, sum, new LinkedList<>(), new ArrayList<>());
    }

    private List<List<Integer>> pathSum(TreeNode root, int sum, LinkedList<Integer> count, List<List<Integer>> result) {
        if(root == null) { return result;}

        if(isLeaf(root) && (sum - root.val == 0)) {
            count.add(root.val);
            result.add(new ArrayList<>(count));
            count.removeLast();
        } else {
            count.add(root.val);
            pathSum(root.left, sum - root.val, count, result);
            pathSum(root.right, sum - root.val, count, result);
            count.removeLast();
        }

        return result;
    }

    private boolean isLeaf(TreeNode node) {
        return node != null && node.left == null &&node.right == null;
    }
}
