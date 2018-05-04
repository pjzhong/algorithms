import org.junit.Test;

/**
 Given a positive integer n, break it into the sum of at least two positive integers and maximize the product of those
 integers. Return the maximum product you can get.

 For example, given n = 2, return 1 (2 = 1 + 1); given n = 10, return 36 (10 = 3 + 3 + 4).

 Note: You may assume that n is not less than 2 and not larger than 58.

 https://leetcode.com/problems/integer-break/discuss/114220/Python-Simple-Math
 * */
public class IntegerBreak {

    @Test
    public void test() {
        int[] testCases = {2, 10,9, 4, 58, };

        for(int n : testCases) {
            System.out.println(integerBreak(n));
        }
    }

    /**
     * Simple complete package problem
     * F(X, Y) = MAX(F(X - I, Y - I) * I | 0 <= kC <= Y );
     *
     * there more efficient solution, using math. But I not familiar with this.
     * */
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        for(int i = 1; i < n; i++) { dp[i] = i;}// each number is on it's own, except n

        for(int i = 1; i <= n; i++) {
            for(int s = i + 1; s <= n; s++) {
                dp[s] = Math.max(dp[s], dp[s - i] * i);
            }
        }

        return dp[n];
    }
}
