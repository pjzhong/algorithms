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
                {2},
                {2,5,10,1}
        };
        for(int[] testCase : testCases) {
            System.out.println(deleteAndEarn(testCase));
        }
    }

    private int deleteAndEarn(int[] nums) {
        int result = -1;
        return result;
    }
}
