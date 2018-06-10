package leetcode;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 Given a binary tree, find the length of the longest path where each node in the path has the same value. \
 This path may or may not pass through the root.

 Note: The length of path between two nodes is represented by the number of edges between them.

 Example 1:

 Input:

     5
   / \
  4   5
 / \   \
 1   1   5
 Output:2
 Example 2:

 Input:

    1
   / \
  4   5
 / \   \
 4   4   5
 Output:2

 https://leetcode.com/problems/longest-univalue-path/description/

 这条路不能分叉......被自己坑死了。就是不能走回头路, 整条路不能有分叉点......
   5
  / \
 1   5
 / \   \
 4   4   5 最长是2，整条右子树
 * */
public class LongestUnivaluePath {
    private int max = 0;

    @Test
    public void test() {
        for (TreeNode node : buildTestCase()) {
            System.out.println(longestUnivaluePath(node));
        }
    }

    private int longestUnivaluePath(TreeNode root) {
        max = 0;
        recursive(root);
        return max;
    }


    /*Official solution, failed to solved by myself*/
    private int recursive(TreeNode node) {
        if(node == null) { return 0; }

        int left = recursive(node.left);
        int right = recursive(node.right);

        int toLeft = 0, toRight = 0;
        if(node.left != null && node.val == node.left.val) {
            toLeft = 1 + left;
        }

        if(node.right != null && node.val == node.right.val) {
            toRight = 1 + right;
        }

        max = Math.max(max, toLeft + toRight);
        return Math.max(toLeft, toRight);
    }

    private TreeNode[] buildTestCase() {
        List<TreeNode> testCases = new LinkedList<>();
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(4);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(4);
        root.left.right.right = new TreeNode(4);

        root.right = new TreeNode(5);
        root.right.right = new TreeNode(5);
        root.right.right.right = new TreeNode(5);
        testCases.add(root);

        root = new TreeNode(1);
        root.left = new TreeNode(4);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(4);

        root.right = new TreeNode(5);
        root.right.left = new TreeNode(5);
        testCases.add(root);

        return testCases.toArray(new TreeNode[0]);
    }

}
