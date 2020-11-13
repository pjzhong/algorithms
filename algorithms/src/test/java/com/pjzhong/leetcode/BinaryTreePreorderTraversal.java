package com.pjzhong.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, Given a binary tree, return the preorder traversal of its nodes' values.
 * For example:
 * Given binary tree [1,null,2,3],
 * 1
 * \
 *  2
 *  /
 *  3
 *  return [1,2,3].
 *
 *  Note: Recursive solution is trivial, could you do it iteratively?
 *
 *
 *  https://leetcode.com/problems/binary-tree-preorder-traversal/description/
 * */
public class BinaryTreePreorderTraversal {

    public List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();

        if(root != null) {
            stack.push(root);
        }
        TreeNode n;
        while(!stack.isEmpty()) {

            n = stack.pop();
            result.add(n.val);
            if(n.right != null) { stack.push(n.right); }
            if(n.left != null) { stack.push(n.left); }
        }

        return result;
    }
}

