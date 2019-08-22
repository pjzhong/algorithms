package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between
 * two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a
 * node to be a descendant of itself).” Given the following binary tree:  root =
 * [3,5,1,6,2,0,8,null,null,7,4]
 *
 * _______3______ /              \ ___5__          ___1__ /      \        /      \ 6      _2_     0
 * 8 /  \ 7   4 Example 1:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1 Output: 3 Explanation: The LCA of of
 * nodes 5 and 1 is 3. Example 2:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4 Output: 5 Explanation: The LCA of nodes
 * 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition. Note:
 *
 * All of the nodes' values will be unique. p and q are different and both values will exist in the
 * binary tree.
 *
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/
 */
public class LowestCommonAncestorOfaBinaryTree {

  private List<TreeNode> pPath, qPath;

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    search(root, p, q, new LinkedList<>());

    TreeNode result = null;
    int size = Math.min(pPath.size(), qPath.size());
    for (int i = size - 1; 0 <= i; i--) {
      if (pPath.get(i).val == qPath.get(i).val) {
        result = pPath.get(i);
        break;
      }
    }
    return result;
  }

  public void search(TreeNode root, TreeNode p, TreeNode q, LinkedList<TreeNode> path) {
    if (root == null || (pPath != null && qPath != null)) {
      return;
    }

    path.add(root);
    if (root.val == p.val) {
      pPath = new ArrayList<>(path);
    } else if (root.val == q.val) {
      qPath = new ArrayList<>(path);
    }

    search(root.left, p, q, path);
    search(root.right, p, q, path);
    path.removeLast();
  }


}
