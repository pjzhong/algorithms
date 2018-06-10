package leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 You are given a binary tree in which each node contains an integer value.

 Find the number of paths that sum to a given value.

 The path does not need to start or end at the root or a leaf, but it must go downwards
 (traveling only from parent nodes to child nodes).

 The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

 Example:

 root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

       10
      /  \
     5   -3
    / \    \
   3   2   11
  / \   \
 3  -2   1

 Return 3. The paths that sum to 8 are:

 1.  5 -> 3
 2.  5 -> 2 -> 1
 3. -3 -> 11

 https://leetcode.com/problems/path-sum-iii/description/
 * */
public class PathSumIII {

    @Test
    public void test() {
        List<TreeNode> testCases = buildTest();
        int[] sum = {8};
        for(int i = 0; i < testCases.size(); i++) {
            System.out.println(pathSum(testCases.get(i), sum[i]));
        }
    }

    private int count = 0;
    public int pathSum(TreeNode root, int sum) {
        LinkedList<TreeNode> nodes = new LinkedList<>();
        if(root != null) { nodes.push(root); }

        TreeNode node;
        while(!nodes.isEmpty()) {
            node = nodes.pop();
            paths(node, sum);
            if(node.left != null) { nodes.push(node.left); }
            if(node.right != null) { nodes.push(node.right); }
        }

        return count;
    }

    private void paths(TreeNode node, int sum) {
        if(node == null) { return;}
        if(sum - node.val == 0) { count++; }
        paths(node.left, sum - node.val);
        paths(node.right, sum - node.val);
    }

    private List<TreeNode> buildTest() {
        List<TreeNode> result = new ArrayList<>();
        TreeNode root = new TreeNode(10);
        root.right = new TreeNode(-3);
        root.right.right = new TreeNode(11);
        root.left = new TreeNode(5);
        root.left.right = new TreeNode(2);
        root.left.right.right = new TreeNode(1);
        root.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.left.left = new TreeNode(3);
        result.add(root);

        return result;
    }

}
