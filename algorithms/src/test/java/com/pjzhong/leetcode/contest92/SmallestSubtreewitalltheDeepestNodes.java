package com.pjzhong.leetcode.contest92;

import com.pjzhong.leetcode.TreeNode;


import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/description/
 */
public class SmallestSubtreewitalltheDeepestNodes {

  private LinkedHashSet<TreeNode> treeNodes;

  //todo optimize it
  public TreeNode subtreeWithAllDeepest(TreeNode root) {
    LinkedList<TreeNode> nodes = new LinkedList<>();
    if (root != null) {
      nodes.add(root);
    }

    TreeNode node = null, result = null;
    int deepth = 0;
    while (!nodes.isEmpty()) {
      int size = nodes.size();
      for (int i = 0; i < size; i++) {
        node = nodes.removeFirst();
        if (node.left != null) {
          nodes.add(node.left);
        }
        if (node.right != null) {
          nodes.add(node.right);
        }
      }
      if (!nodes.isEmpty()) {
        ++deepth;
      }
    }

    if (deepth == 0) {
      return root;
    }

    treeNodes = null;
    dfs(root, deepth, new LinkedHashSet<>());
    return (TreeNode) treeNodes.toArray()[treeNodes.size() - 1];
  }

  public void dfs(TreeNode node, int deepth, LinkedHashSet<TreeNode> path) {
    if (node == null) {
      return;
    }

    path.add(node);
    if (deepth == 0) {
      if (treeNodes == null) {
        treeNodes = new LinkedHashSet<>(path);
      } else {
        treeNodes.retainAll(path);
      }
    } else {
      dfs(node.left, deepth - 1, path);
      dfs(node.right, deepth - 1, path);
    }
    path.remove(node);
  }

}
