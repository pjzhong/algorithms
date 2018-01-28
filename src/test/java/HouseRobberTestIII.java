import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * The thief has found himself a new place for his thievery again. There is only one entrance to this area,
 * called the "root." Besides the root, each house has one and only one parent house. After a tour,
 * the smart thief realized that "all houses in this place forms a binary tree".
 * It will automatically contact the police if two directly-linked houses were broken into on the same night.
 * Determine the maximum amount of money the thief can rob tonight without alerting the police.
 *
 * Example 1:
 *       3
 *     / \
 *    2   3
 *    \   \
 *     3   1
 *
 *  Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 *
 * Example 2:
 *      3
 *     / \
 *    4   5
 *   / \   \
 *  1   3   1
 *
 *  Maximum amount of money the thief can rob = 4 + 5 = 9.
 */
public class HouseRobberTestIII {

    @Test
    public void test() {
        TreeNode[] testCase = createTestCases();

        for(TreeNode root : testCase) {
            System.out.println(rob(root));
        }
    }

    private int rob(TreeNode root) {
        return robDFS(0, 0, 0 ,root);
    }

    /**
     * 这个大问题可以拆分成多个子问题，因为每一条二叉路径就是一个RobberHouse问题
     * */
    private int robDFS(int odd, int even, int start, TreeNode node) {
        if(node == null) {
            return 0;
        } else {
            if((start & 1) == 0) {
                even = Math.max(node.val + even, odd);
            } else {
                odd = Math.max(node.val + odd, even);
            }



            return Math.max(even, odd);
        }
    }

    private TreeNode[] createTestCases() {
        List<TreeNode> treeNodeList = new ArrayList<>();
        TreeNode testCaseOne = new TreeNode(30);
        testCaseOne.left = new TreeNode(2);
        testCaseOne.right = new TreeNode(3);
        testCaseOne.left.right = new TreeNode(3);
        testCaseOne.right.right = new TreeNode(1);
        treeNodeList.add(testCaseOne);
/*
        TreeNode testCaseTwo = new TreeNode(3);
        testCaseTwo.left = new TreeNode(4);
        testCaseTwo.right = new TreeNode(5);
        testCaseTwo.left.right = new TreeNode(3);
        testCaseTwo.left.left = new TreeNode(1);
        testCaseTwo.right.right = new TreeNode(1);
        treeNodeList.add(testCaseTwo);

        TreeNode testCaseThree = new TreeNode(10);
        testCaseThree.left = new TreeNode(2);
        testCaseThree.right = new TreeNode(3);
        testCaseThree.left.right = new TreeNode(3);
        testCaseThree.right.right = new TreeNode(1);
        treeNodeList.add(testCaseThree);

        TreeNode testCaseFour = new TreeNode(80);
        testCaseFour.left = new TreeNode(45);
        testCaseFour.right = new TreeNode(45);
        testCaseFour.left.right = new TreeNode(1000);
        testCaseFour.right.right = new TreeNode(1000);
        treeNodeList.add(testCaseFour);*/
        return treeNodeList.toArray(new TreeNode[treeNodeList.size()]);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TreeNode{");
        sb.append("val=").append(val);
        sb.append('}');
        return sb.toString();
    }
}
