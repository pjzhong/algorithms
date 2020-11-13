package com.pjzhong.leetcode;

import java.util.LinkedList;

/**
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *
 * 1 / \ 2   2 / \ / \ 3  4 4  3 But the following [1,2,2,null,3,null,3] is not: 1 / \ 2   2 \   \ 3
 * 3 Note: Bonus points if you could solve it both recursively and iteratively.
 *
 * https://leetcode.com/problems/symmetric-tree/description/
 */
public class SymmetricTree {

  public boolean isSymmetric(TreeNode node) {
    return isSymmetric(node, node);
  }

  public boolean isSymmetric(TreeNode left, TreeNode right) {
    if (left == null || right == null) {
      return left == right;
    }
    return left.val == right.val && isSymmetric(left.left, right.right) && isSymmetric(left.right,
        right.left);
  }

  public boolean isSymmerticIterative(TreeNode node) {
    LinkedList<TreeNode> stack = new LinkedList<>();
    stack.push(node);
    stack.push(node);

    TreeNode left, right;
    while (!stack.isEmpty()) {
      left = stack.pop();
      right = stack.pop();
      if (left == null || right == null) {
        if (left != right) {
          return false;
        }
      } else {
        if (left.val != right.val) {
          return false;
        }
        stack.push(left.right);
        stack.push(right.left);
        stack.push(right.right);
        stack.push(left.left);
      }
    }
    return true;
  }
}
