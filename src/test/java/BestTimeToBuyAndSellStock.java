import org.junit.Test;

/**
 Say you have an array for which the ith element is the price of a given stock on day i.

 If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock),
 design an algorithm to find the maximum profit.

 Note that you cannot sell a stock before you buy one.

 Example 1:

 Input: [7,1,5,3,6,4]
 Output: 5
 Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 Not 7-1 = 6, as selling price needs to be larger than buying price.
 Example 2:

 Input: [7,6,4,3,1]
 Output: 0
 Explanation: In this case, no transaction is done, i.e. max profit = 0.

 * */
public class BestTimeToBuyAndSellStock {

    @Test
    public void test() {
        int[][] testCases = {
                {7,1,5,3,6,4},
                {7,6,4,3,1}
        };

        for(int[] prices : testCases) {
            System.out.println(maxProfit(prices));
            System.out.println(maxProfitDp(prices));
        }
    }

    /**
     * Brute force solution O(n^2), correct through not efficient
     * */
    public int maxProfit(int[] prices) {
        int result = 0;

        for(int i = 0; i < prices.length; i++) {
            for(int j = i + 1; j < prices.length; j++) {
                if(prices[i] < prices[j]) {
                    result = Math.max(result, prices[j] - prices[i]);
                }
            }
        }

        return result;
    }

    //After optimize time complexity O(n), you still can optimize the space complexity
    public int maxProfitDp(int[] prices) {
        if(prices == null || prices.length == 0) { return 0;}

        final int size = prices.length;
        int[] dp = new int[size];dp[0] = prices[0];//the minimum buy price

        int result = 0;
        for(int i = 1; i < size; i++) {
            dp[i] = Math.min(prices[i], dp[i - 1]);
        }

        for(int i = 1; i < size; i++) {
            if(dp[i - 1] < prices[i] && result < prices[i] - dp[i - 1]) {
                result = prices[i] - dp[i - 1];
            }
        }

        return result;
    }
}
