import org.junit.Test;

/**
 *
 * QUESTION COPY FROM LEETCODE
 *
 Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

 Note:
 Each of the array element will not exceed 100.
 The array size will not exceed 200.
 Example 1:

 Input: [1, 5, 11, 5]

 Output: true

 Explanation: The array can be partitioned as [1, 5, 5] and [11].
 Example 2:

 Input: [1, 2, 3, 5]

 Output: false

 Explanation: The array cannot be partitioned into equal sum subsets.
 */
public class PartitionEqualSubsetSumTest {

    @Test
    public void test() {
        int[][] testCases = {
                {1,5,11,5},
                {1,2,3,5},
                {1,2,5},
                {18,40,62,33,83,64,10,92,67,31,42,51,10,97,41,7,82,71,80,81,41,38,88,25,38,89,24,89,90,12,97,21,22,85,11,59,83,6,51,47,32,82,83,100,29,78,36,32,1,31,36,19,35,3,36,21,24,93,42,33,10,26,2,39,36,62,85,24,41,5,66,53,7,1,59,53,40,59,41,95,7,67,20,29,80,59,49,49,51,90,13,52,6,90,5,6,31,81,95,26}
        };

        for(int[] testCase : testCases) {
            System.out.println(canPartition(testCase));
        }

    }

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int i : nums) { sum += i;}

        if((sum & 1) != 0) {
            return false;
        } else {
            int half  = sum / 2;
            int[] dp = new int[half + 1];

            for(int i : nums) {
                if(i <= half) {
                    for(int j = half; j >= i; j--) {
                        if(dp[j] == 0) {
                            dp[j] = i;
                        } else {
                            dp[j] = (dp[j - i] + i ) <= half ? dp[j - i] + i : dp[j - i];
                        }
                    }
                }
            }

            return dp[half] == half;
        }
    }
}
