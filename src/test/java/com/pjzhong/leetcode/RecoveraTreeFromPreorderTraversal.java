package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * We run a preorder depth first search on the root of a binary tree.
 *
 * At each node in this traversal, we output D dashes (where D is the depth of this node), then we
 * output the value of this node.  (If the depth of a node is D, the depth of its immediate child is
 * D+1.  The depth of the root node is 0.)
 *
 * If a node has only one child, that child is guaranteed to be the left child.
 *
 * Given the output S of this traversal, recover the tree and return its root.
 *
 * @author ZJP
 * @link https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/
 * @since 2019年11月30日 15:57:20
 **/
public class RecoveraTreeFromPreorderTraversal {

  @Test
  public void test() {
    String[] strs = {"1-2--3--4-5--6--7", "1-2--3---4-5--6---7", "1-401--349---90--88"};

    for (String s : strs) {
      TreeNode n = recoverFromPreorder(s);
      System.out.println(n);
    }
  }

  private int idx;
  private int deep;

  public TreeNode recoverFromPreorder(String S) {
    idx = 0;
    deep = 0;
    TreeNode node = new TreeNode(extractNum(S));
    return recover(node, S, 1);
  }

  private TreeNode recover(TreeNode node, String s, int dp) {
    searchDeep(s);
    if (deep == dp) {
      int num = extractNum(s);
      if (0 < num) {
        node.left = new TreeNode(num);
        recover(node.left, s, dp + 1);
      }
    }

    searchDeep(s);
    if (deep == dp) {
      int num = extractNum(s);
      if (0 < num) {
        node.right = new TreeNode(num);
        recover(node.right, s, dp + 1);
      }
    }
    return node;
  }

  private void searchDeep(String s) {
    int res = -1;
    while (idx < s.length() && s.charAt(idx) == '-') {
      res++;
      idx++;
    }

    if (0 <= res) {
      deep = res + 1;
    }
  }

  private int extractNum(String s) {
    int res = 0;
    while (idx < s.length() && Character.isDigit(s.charAt(idx))) {
      res = res * 10 + s.charAt(idx) - '0';
      idx++;
    }
    return res;
  }


}
