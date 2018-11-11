package leetcode;

import org.junit.Test;

import java.util.Arrays;

/**
 * Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's
 * in their binary representation and return them as an array.
 * <p>
 * Example 1:
 * <p>
 * Input: 2
 * Output: [0,1,1]
 * Example 2:
 * <p>
 * Input: 5
 * Output: [0,1,1,2,1,2]
 * Follow up:
 * <p>
 * It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in linear time O
 * (n) /possibly in a single pass?
 * Space complexity should be O(n).
 * Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other
 * language.
 *
 * @link https://leetcode.com/problems/counting-bits/
 */
public class CountingBits {

    @Test
    public void test() {
        int[] tests = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        for (int t : tests) {
            System.out.println(Arrays.toString(countBits(t)));
        }
    }

    public int[] countBits(int num) {
        if (num == 0) {
            return new int[]{0};
        }

        int[] result = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            int same = i & (i - 1), diff = i ^ (i - 1) & same;
            result[i] = result[same] + ((i <= diff) ? 1 : result[diff]);
        }
        return result;
    }
}
