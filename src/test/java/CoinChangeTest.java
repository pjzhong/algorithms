import org.junit.Test;

/**
 * You are given coins of different denominations and a total amount of money amount. Write a function to compute the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
 *
 * Example 1:
 * coins = [1, 2, 5], amount = 11
 * return 3 (11 = 5 + 5 + 1)
 *
 * Example 2:
 * coins = [2], amount = 3
 * return -1.
 *
 * Note:
 * You may assume that you have an infinite number of each kind of coin.
 *
 * COPY FROM LEETCODE
 */
public class CoinChangeTest {

    @Test
    public void test() {
        int[][] testCases = {
                {1,2,5},
                {2},
                {2,5,10,1}
        };
        int[] amount = {11, 3, 27};
        for(int i = 0; i < amount.length; i++) {
            System.out.println(coinChange(testCases[i], amount[i]));
        }
    }

    public int coinChange(int[] coins, int amount) {
        int[] result = new int[amount + 1];
        int[] sum = new int[amount + 1];
        for(int i = 1; i <= amount;i++) { result[i] = Integer.MAX_VALUE - 1; }// 防止+1越界

        for(int coin : coins) {
            for(int i = coin; i <= amount; i++) {
                result[i] = Math.min(result[i], result[i - coin] + 1);
                sum[i] = Math.max(sum[i], sum[i - coin] + coin);//判断给定硬币是否组成目标金额
            }
        }

        return sum[amount] == amount ? result[amount] : - 1;
    }

}
