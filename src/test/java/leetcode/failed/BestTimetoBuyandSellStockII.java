package leetcode.failed;

import org.junit.Test;

/**
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * <p>
 * Design an algorithm to find the maximum profit.
 * You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).
 * <p>
 * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy
 * again).
 * <p>
 * Example 1:
 * <p>
 * Input: [7,1,5,3,6,4]
 * Output: 7
 * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
 * Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
 * Example 2:
 * <p>
 * Input: [1,2,3,4,5]
 * Output: 4
 * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 * Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
 * engaging multiple transactions at the same time. You must sell before buying again.
 * Example 3:
 * <p>
 * Input: [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transaction is done, i.e. max profit = 0.
 * <p>
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/description/
 */
public class BestTimetoBuyandSellStockII {

    @Test
    public void test() {
        int[][] testCases = {
                {2, 6, 8, 7, 8, 7, 9, 4, 1, 2, 4, 5, 8},
                {7, 1, 5, 3, 6, 4},
                {7, 1, 5, 1, 6, 4},
                {1, 2, 3, 4, 5},
                {7, 6, 4, 3, 1}
        };

        for (int[] t : testCases) {
            System.out.println(maxProfit(t));
        }
    }

    public int maxProfit(int[] prices) {
        int cash = 0, hold = Integer.MIN_VALUE;

        for (int p : prices) {
            int cashOld = cash;
            cash = Math.max(cash, hold + p);
            hold = Math.max(hold, cashOld - p);
        }

        return cash;
    }

    //Time Limit Exceeded
    int result = 0;

    private void maxProfit(int[] prices, int buy, int sell, int profit) {
        if (prices.length <= buy || prices.length <= sell) {
            result = Math.max(result, profit);
            return;
        } else if (prices[buy] < prices[sell]) {
            maxProfit(prices, sell + 1, sell + 2, profit + (prices[sell] - prices[buy]));
        }

        maxProfit(prices, buy + 1, buy + 2, profit);
        maxProfit(prices, buy, sell + 1, profit);
    }

    public int maxProfitVerbose(int[] prices) {
        int[][] dp = new int[prices.length][prices.length];
        for (int i = 0; i < prices.length; i++) {

        }

        return -1;
    }
}
