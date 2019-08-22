package com.pjzhong.leetcode;

/**
 * Invert a binary tree.
 *
 * 4 /   \ 2     7 / \   / \ 1   3 6   9 to
 *
 * 4 /   \ 7     2 / \   / \ 9   6 3   1
 *
 * https://leetcode.com/problems/invert-binary-tree/description/
 */
public class InvertBinaryTree {

  public TreeNode invertTree(TreeNode root) {
    invert(root);
    return root;
  }

  public void invert(TreeNode root) {
    if (root == null) {
      return;
    }
    TreeNode temp = root.left;
    root.left = root.right;
    root.right = temp;
    invertTree(root.left);
    invertTree(root.right);
  }
}
