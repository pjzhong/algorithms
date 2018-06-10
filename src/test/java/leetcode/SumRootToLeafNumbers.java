package leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.

 An example is the root-to-leaf path 1->2->3 which represents the number 123.

 Find the total sum of all root-to-leaf numbers.

 Note: A leaf is a node with no children.

 Example:

 Input: [1,2,3]
 1
 / \
 2   3
 Output: 25
 Explanation:
 The root-to-leaf path 1->2 represents the number 12.
 The root-to-leaf path 1->3 represents the number 13.
 Therefore, sum = 12 + 13 = 25.
 Example 2:

 Input: [4,9,0,5,1]
 4
 / \
 9   0
 / \
 5   1
 Output: 1026
 Explanation:
 The root-to-leaf path 4->9->5 represents the number 495.
 The root-to-leaf path 4->9->1 represents the number 491.
 The root-to-leaf path 4->0 represents the number 40.
 Therefore, sum = 495 + 491 + 40 = 1026.

 https://leetcode.com/problems/sum-root-to-leaf-numbers/description/
* */
public class SumRootToLeafNumbers {

    @Test
    public void test() {
        for(TreeNode node : buildTest()) {
            System.out.println(sumNumbers(node));
        }
    }

    //More concise than the previous
    public int sumNumbers(TreeNode root) {
        return sumNumbers(root, 0);
    }

    public int sumNumbers(TreeNode root, int result) {
        if(root == null) { return 0;}
        result = 10 * result + root.val;
        if(isLeaf(root)) {
            return  result;
        }

        return sumNumbers(root.left, result) +  sumNumbers(root.right, result);
    }

    private boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }

    public List<TreeNode> buildTest() {
        List<TreeNode> result = new ArrayList<>();
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(3);
        node.right = new TreeNode(2);
        result.add(node);

        return result;
    }
}
