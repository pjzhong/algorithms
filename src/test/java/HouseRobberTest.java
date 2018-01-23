import org.junit.Test;

/**
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing each of them is that adjacent houses have security system connected
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers representing the amount of money of each house,
 * determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Problems.198
 *
 * COPY FROM LEETCODE
 */
public class HouseRobberTest {

    @Test
    public void test() {
        int[][] testCases = {
                {1,2,5},
                {1,3,1},
                {1,3,1,3,1},
                {1,1,1},
                {1,1,1,1},
                {2}
             /*   {2,5,10,1},
                {2,1,1,2},
                {1000,1,1,10},
                {2,1,1,2,1000,1,1,100},
                {2,1,1,2,1000,1,1,100,10000, 1, 1, 100},
                {226,174,214,16,218,48,153,131,128,17,157,142,88,43,37,157,43,221,191,68,206,23,225,82,54,118,111,46,
                80,49,245,63,25,194,72,80,143,55,209,18,55,122,65,66,177,101,63,201,172,130,103,225,142,46,86,185,62,
                        138,212,192,125,77,223,188,99,228,90,25,193,211,84,239,119,234,85,83,123,120,131,203,219,10,
                        82,35,120,180,249,106,37,169,225,54,103,55,166,124}*/
        };
        for(int[] testCase : testCases) {
            hourRobber(testCase);
        }
    }

    private void hourRobber(int[] nums) {
     /*   System.out.println(hourRobberDp(nums));*/
        int result = Math.max(
                hourRobberRecursive(0, 0, nums),
                hourRobberRecursive(1, 0, nums)
        );
        System.out.println(result);
    }

    private int hourRobberDp(int[] nums) {
        if(nums == null || nums.length <= 0)  { return 0;}
        if(nums.length <= 2) { return nums.length == 2 ? Math.max(nums[0], nums[1]) : nums[0]; }

        int[] dp = new int[nums.length + 1];
        dp[1] = nums[0];

        for(int i = 3; i <= nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i - 1], dp[i - 3] + nums[i - 1]);
        }

        return dp[nums.length];
    }

    /**
     * BruteForce , the answer is right but timeLimitExceeded
     * */
    private int hourRobberRecursive(int start, int amount, int[] nums) {
        if(start >= nums.length) {
            return amount;
        }

        return Math.max(
                hourRobberRecursive(start + 2, amount + nums[start], nums),
                hourRobberRecursive(start + 3, amount + nums[start], nums)
        );
    }
}
