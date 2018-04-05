import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            rob(root);
        }
    }


    /**
     * 这个大问题可以拆分成多个子问题，因为每一条二叉路径就是一个RobberHouse问题 - wrong
     * 思考方向又错了............虽然脑海里面有图像，但就是不能现实化
     * shame on you, you can't even write a bruteForce solution
     * */

    private void rob(TreeNode root) {
        System.out.println(robDFS(root, new HashMap<TreeNode, Integer>()));
        int[] result = robDp(root);
        System.out.println(Math.max(result[0], result[1]));
    }

    private int robDFS(TreeNode node, Map<TreeNode, Integer> memorize) {
        if(node == null) {
            return 0;
        } else {
            if(memorize.containsKey(node)) {
                return memorize.get(node);
            }

            int val = 0;


            if(node.left != null) {
                val += robDFS(node.left.left, memorize) + robDFS(node.left.right, memorize);
            }

            if(node.right != null) {
                val += robDFS(node.right.left, memorize) + robDFS(node.right.right, memorize);
            }

            //1.rob the node and the grandChilds of node
            //2.ignore this node ,go ahead to  rob the two childs of this node
            int result =  Math.max(val + node.val, robDFS(node.left, memorize) + robDFS(node.right, memorize));
            memorize.put(node, result);
            return result;
        }
    }

    /**
     * extremely beautiful solution
     * */
    private int[] robDp(TreeNode node) {
        int[] result = {0,0};
        if(node == null) {
            return result;
        } else {
            int[] left = robDp(node.left);
            int[] right = robDp(node.right);

            int notRob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
            int robbed = node.val + left[0] + right[0];

            result[0] = notRob;
            result[1] = robbed;
            return result;
        }
    }

    /**
     * 这个大问题可以拆分成多个子问题，因为每一条二叉路径就是一个RobberHouse问题
     * */
    private int ZjpRobDFS(int odd, int even, int start, TreeNode node) {
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
        treeNodeList.add(testCaseFour);
        return treeNodeList.toArray(new TreeNode[treeNodeList.size()]);
    }
}
