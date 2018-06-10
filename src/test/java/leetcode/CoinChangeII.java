package leetcode;

import org.junit.Test;

/**
 You are given coins of different denominations and a total amount of money.
 Write a function to compute the number of combinations that make up that amount.
 You may assume that you have infinite number of each kind of coin.

 Note: You can assume that

 0 <= amount <= 5000
 1 <= coin <= 5000
 the number of coins is less than 500
 the answer is guaranteed to fit into signed 32-bit integer
 Example 1:

 Input: amount = 5, coins = [1, 2, 5]
 Output: 4
 Explanation: there are four ways to make up the amount:
 5=5
 5=2+2+1
 5=2+1+1+1
 5=1+1+1+1+1
 Example 2:

 Input: amount = 3, coins = [2]
 Output: 0
 Explanation: the amount of 3 cannot be made up just with coins of 2.
 Example 3:

 Input: amount = 10, coins = [10]
 Output: 1

 https://leetcode.com/problems/coin-change-2/description/
 */
public class CoinChangeII {

    @Test
    public void test() {
        int[][] testCases = {
                {1,2,5},
                {2},
                {2,5,10,1},
                {10}
        };
        int[] amount = {5, 3, 27,10};
        for(int i = 0; i < amount.length; i++) {
            coinChangeII(testCases[i], amount[i]);
        }
    }

    private int coinChangeII(int[] coins, int amount) {
        System.out.println(coinChangeDp(coins, amount));
        return coinChangeRecursive(coins, amount, coins.length - 1);
    }

    private int coinChangeDp(int[] coins, int amount) {
        int[] sum = new int[amount + 1];

        sum[0] = 1;
        for(int coin : coins) {
            for(int i = coin; i <= amount;  ++i) {
                sum[i] = sum[i] + sum[i - coin];
            }
        }

        return sum[amount];
    }

    /**
     * To count number solutions, we can divide all set solutions in two sets
     * 1） Solutions that do not contain coins[m] coin
     * 2)  Solutions that contain at least one coins[m] coin
     *
     * fore more info, see the link below
     * https://www.geeksforgeeks.org/dynamic-programming-set-7-coin-change/
     * */
    private int coinChangeRecursive(int[] coins, int amount, int m) {
        if(amount == 0) {
            return 1;
        } else if(m < 0 || amount < 0) {
            return 0;
        }

        return coinChangeRecursive(coins, amount, m - 1) + coinChangeRecursive(coins, amount - coins[m], m);
    }
}
