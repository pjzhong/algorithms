package leetcode;

/**
 Given a binary tree, determine if it is a valid binary search tree (BST).

 Assume a BST is defined as follows:

 The left subtree of a node contains only nodes with keys less than the node's key.
 The right subtree of a node contains only nodes with keys greater than the node's key.
 Both the left and right subtrees must also be binary search trees.
 Example 1:

 Input:
   2
  / \
 1   3
 Output: true
 Example 2:

     5
    / \
   1   4
  / \
 3   6
 Output: false
 Explanation: The input is: [5,1,4,null,null,3,6]. The root node's value
 is 5 but its right child's value is 4.

 problems:https://leetcode.com/problems/validate-binary-search-tree/description/
 * */
public class ValidateBinarySearchTree {

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, new TreeNode[1]);
    }

    public boolean isValidBST(TreeNode root, TreeNode[] tail) {
        if(root == null) { return true; }
        if(isValidBST(root.left, tail) && less(tail[0], root)) {
            tail[0] = root;
            return isValidBST(root.right, tail);
        } else {
            return false;
        }
    }

    private boolean less(TreeNode n, TreeNode o) {
        return n == null || o == null || (n.val < o.val);
    }
}
