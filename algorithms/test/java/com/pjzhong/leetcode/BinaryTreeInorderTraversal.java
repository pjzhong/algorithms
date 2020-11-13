package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, return the inorder traversal of its nodes' values. For example: Given binary
 * tree [1,null,2,3], 1 \ 2 / 3 return [1,3,2].
 *
 * Note: Recursive solution is trivial, could you do it iteratively?
 *
 *
 * https://leetcode.com/problems/binary-tree-inorder-traversal/description/
 */
public class BinaryTreeInorderTraversal {

  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    LinkedList<TreeNode> stack = new LinkedList<>();

    TreeNode n;
    goAloneLeft(root, stack);
    while (!stack.isEmpty()) {
      n = stack.pop();
      result.add(n.val);
      goAloneLeft(n.right, stack);
    }

    return result;
  }

  private void goAloneLeft(TreeNode node, LinkedList<TreeNode> stack) {
    while (node != null) {
      stack.push(node);
      node = node.left;
    }
  }
}

