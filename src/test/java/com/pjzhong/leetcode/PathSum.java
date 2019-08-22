package com.pjzhong.leetcode;

/**
 Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along
 the path equals the given sum.

 Note: A leaf is a node with no children.

 Example:

 Given the below binary tree and sum = 22,

       5
     / \
   4    8
 /     / \
 11   13  4
 /  \      \
 7    2      1
 return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.

 https://leetcode.com/problems/path-sum/description/
 * */
public class PathSum {


    public boolean hasPathSum(TreeNode root, int sum) {
        return hasPathSum(root, 0, sum);
    }

    //try to use subtraction, is more concise
    public boolean hasPathSum(TreeNode root, int count, int sum) {
        if(root == null) { return false; }
        if(isLeaf(root)) {
            if(count + root.val == sum) { return true;}
        }
        return hasPathSum(root.left, count + root.val, sum) || hasPathSum(root.right, count + root.val, sum);
    }


    private boolean isLeaf(TreeNode node) {
        return node != null && node.left == null &&node.right == null;
    }
}
