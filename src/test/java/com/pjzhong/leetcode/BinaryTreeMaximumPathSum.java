package com.pjzhong.leetcode;

/**
 Given a non-empty binary tree, find the maximum path sum.

 For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along
 the parent-child connections. The path must contain at least one node and does not need to go through the root.

 Example 1:

 Input: [1,2,3]

 1
 / \
 2   3

 Output: 6
 Example 2:

 Input: [-10,9,20,null,null,15,7]

 -10
 / \
 9  20
 /  \
 15   7

 Output: 42

 https://leetcode.com/problems/binary-tree-maximum-path-sum/description/

 the solution inspired by this question: https://leetcode.com/problems/binary-tree-pruning/description/
 * */
public class BinaryTreeMaximumPathSum {

    private int max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        max = Integer.MIN_VALUE;
        maxSum(root);
        return max;
    }

    private int maxSum(TreeNode node) {
        if(node == null) { return 0;}
        int left = maxSum(node.left);
        int right = maxSum(node.right);

        int plusLeft = 0, plusRight = 0;
        //如果左或右加上当前节点的值小于等于0， 那么我就没必要加上它了
        //因为此节点就是一个分割点，它与最终答案没有任何关系
        if(0 < left + node.val) {
            plusLeft = left + node.val;
        }

        if(0 < right + node.val) {
            plusRight = right + node.val;
        }

        max = Math.max(max , node.val + right + left);
        return Math.max(plusLeft, plusRight);
    }
}
