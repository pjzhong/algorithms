package com.pjzhong.leetcode.contest100;

import com.pjzhong.leetcode.TreeNode;

/**
 * @link https://com.pjzhong.leetcode.com/contest/weekly-contest-100/problems/increasing-order-search-tree/
 */
public class IncreasingOrderSearchTree {

  TreeNode result;

  public TreeNode increasingBST(TreeNode root) {
    result = new TreeNode(-1);
    TreeNode p = result;
    recursive(root);
    return p.right;
  }

  private void recursive(TreeNode node) {
    if (node == null) {
      return;
    }
    recursive(node.left);
    add(node);
    recursive(node.right);
  }

  public void add(TreeNode n) {
    TreeNode node = new TreeNode(n.val);
    result.right = node;
    result = node;
  }
}
