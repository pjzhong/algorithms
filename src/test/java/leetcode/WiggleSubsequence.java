package leetcode;

import org.junit.Test;

/**
 * A sequence of numbers is called a wiggle sequence if the differences between successive numbers strictly alternate
 * between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence
 * with fewer than two elements is trivially a wiggle sequence.
 * <p>
 * For example, [1,7,4,9,2,5] is a wiggle sequence because the differences (6,-3,5,-7,3) are alternately positive and
 * negative. In contrast, [1,4,7,2,5] and [1,7,4,5,5] are not wiggle sequences, the first because its first two
 * differences are positive and the second because its last difference is zero.
 * <p>
 * Given a sequence of integers, return the length of the longest subsequence that is a wiggle sequence. A
 * subsequence is obtained by deleting some number of elements (eventually, also zero) from the original sequence,
 * leaving the remaining elements in their original order.
 * <p>
 * Example 1:
 * <p>
 * Input: [1,7,4,9,2,5]
 * Output: 6
 * Explanation: The entire sequence is a wiggle sequence.
 * Example 2:
 * <p>
 * Input: [1,17,5,10,13,15,10,5,16,8]
 * Output: 7
 * Explanation: There are several subsequences that achieve this length. One is [1,17,10,13,10,16,8].
 * Example 3:
 * <p>
 * Input: [1,2,3,4,5,6,7,8,9]
 * Output: 2
 * Follow up:
 * Can you do it in O(n) time?
 */
public class WiggleSubsequence {

    @Test
    public void test() {
        int[][] tests = {
                {1, 7, 4, 9, 2, 5},
                {1, 17, 5, 10, 13, 15, 10, 5, 16, 8},
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {9, 8, 7, 6, 5, 4, 3, 2, 1},
                {1, 1},
                {1, 1, 2, 2, 2, 2, 2, 2},
                {1, 1, 2, 2, 2, 2, 2, 2, 3},};

        for (int[] t : tests) {
            System.out.println(wiggleMaxLength(t));
        }
    }

    /**
     * 只有正负两种状态，持续相加就可以了。
     * 想什么的呢？？？？？
     */
    public int wiggleMaxLength(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int pos = 1, neg = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) {
                pos = neg + 1;
            } else if (nums[i] < nums[i - 1]) {
                neg = pos + 1;
            }
        }

        return Math.max(pos, neg);
    }
}
