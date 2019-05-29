package leetcode;

import org.junit.Test;

/**
 * Given an integer array with no duplicates. A maximum tree building on this array is defined as
 * follow:
 *
 * The root is the maximum number in the array. The left subtree is the maximum tree constructed
 * from left part subarray divided by the maximum number. The right subtree is the maximum tree
 * constructed from right part subarray divided by the maximum number. Construct the maximum tree by
 * the given array and output the root node of this tree.
 *
 * @link https://leetcode.com/problems/maximum-binary-tree/
 * @since 2019年05月29日 23:53:27
 **/
public class MaximumBinaryTree {

  @Test
  public void test() {
    int[][] tests = {
        {3, 2, 1, 6, 0, 5},
        {1}
    };

    for (int[] t : tests) {
      TreeNode node = constructMaximumBinaryTree(t);
      System.out.println(node);
    }
  }

  public TreeNode constructMaximumBinaryTree(int[] nums) {
    return construct(nums, 0, nums.length - 1);
  }

  private TreeNode construct(int[] nums, int start, int end) {
    if (end < start) {
      return null;
    }

    int max = maximumIdx(nums, start, end);
    TreeNode node = new TreeNode(nums[max]);
    node.left = construct(nums, start, max - 1);
    node.right = construct(nums, max + 1, end);
    return node;
  }

  /**
   * find the maximum element'idx in range [start ,end]
   *
   * @param nums the input array
   * @param start where to start
   * @param end where to end(including)
   * @since 2019年05月29日 23:54:59
   */
  private int maximumIdx(int[] nums, int start, int end) {
    int res = start;
    for (int i = start + 1; i <= end; i++) {
      if (nums[res] < nums[i]) {
        res = i;
      }
    }
    return res;
  }
}
