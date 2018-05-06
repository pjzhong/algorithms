import org.junit.Test;

/**
 * Given an array nums of integers, you can perform operations on the array.
 *
 * In each operation, you pick any nums[i] and delete it to earn nums[i] points.
 * After, you must delete EVERY element equal to nums[i] - 1 or nums[i] + 1.
 *
 * You start with 0 points. Return the maximum number of points you can earn by applying such operations.
 *
 * Example 1:
 *
 * Input: nums = [3, 4, 2]
 *
 * Output: 6
 * Explanation:
 * Delete 4 to earn 4 points, consequently 3 is also deleted.
 * Then, delete 2 to earn 2 points. 6 total points are earned.
 *
 * Example 2:
 *
 * Input: nums = [2, 2, 3, 3, 3, 4]
 * Output: 9
 * Explanation:Delete 3 to earn 3 points, deleting both 2's and the 4.Then, delete 3 again to earn 3 points,
 * and 3 again to earn 3 points.9 total points are earned.
 *
 * Note:The length of nums is at most 20000.
 * Each element nums[i] is an integer in the range [1, 10000].
 *
 * Problems.740
 *
 * https://leetcode.com/problems/delete-and-earn/description/
 */
public class deleteAndEarnTest {

    @Test
    public void test() {
        int[][] testCases = {
                {8,3,4,7,6,6,9,2,5,8,2,4,9,5,9,1,5,7,1,4},
                {8,10,4,9,1,3,5,9,4,10},
                {1,2,5},
                {2},
                {2,5,10,1},
                {3,4, 2},
                {2,2,3,3,3,4}
        };
        for(int[] testCase : testCases) {
            System.out.println(deleteAndEarn(testCase));
        }
    }

    private int deleteAndEarn(int[] nums) {
        int[] count = new int[10001];

        int rob = 0, not_rob = 0;
        for(int i : nums) { count[i] += i;}//reduce it to house robber problems
        for(int i = 1; i < count.length; i++) {
           /*if((i & 1) == 0) {
                rob = Math.max(count[i] + rob, not_rob);
            } else {
                not_rob = Math.max(count[i] + not_rob, rob);
            }*/
            int temp = rob;//an excellent example of do not use if to program
            rob = not_rob + count[i];
            not_rob = Math.max(not_rob, temp);// dp[i] = Math.max(dp[i - 2] + num[i], dp[i - 1]);
        }

        return Math.max(not_rob, rob);
    }
}
