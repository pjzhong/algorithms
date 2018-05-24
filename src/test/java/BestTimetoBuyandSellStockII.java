import org.junit.Test;

/**
 Say you have an array for which the ith element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit.
 You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).

 Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).

 Example 1:

 Input: [7,1,5,3,6,4]
 Output: 7
 Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
 Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
 Example 2:

 Input: [1,2,3,4,5]
 Output: 4
 Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
 engaging multiple transactions at the same time. You must sell before buying again.
 Example 3:

 Input: [7,6,4,3,1]
 Output: 0
 Explanation: In this case, no transaction is done, i.e. max profit = 0.

 https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/description/
 * */
public class BestTimetoBuyandSellStockII {

    @Test
    public void test() {
        int[][] testCases = {
                {2,6,8,7,8,7,9,4,1,2,4,5,8},
                {7,1,5,3,6,4},
                {1,2,3,4,5},
                {7,6,4,3,1}
        };

        for(int[] t : testCases) {
            System.out.println(maxProfit(t));
        }
    }


    //failed to solve this by yourself, I can't understand what behind this solution
    //Please see this explain:https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/discuss/39404/Shortest-and-fastest-solution-with-explanation.-You-can-never-beat-this.
    public int maxProfit(int[] prices) {
        int result = 0;
        for(int i = 1; i < prices.length; i++) {
            if(prices[i - 1] < prices[i]) {
                result += prices[i] - prices[i - 1];
            }
        }
        return result;
    }

    //Time Limit Exceeded
    int result = 0;
    private void maxProfit(int[] prices, int buy, int sell, int profit) {
        if(prices.length <= buy || prices.length <= sell) {
            result = Math.max(result, profit);return;
        } else if(prices[buy] < prices[sell]) {
            maxProfit(prices, sell + 1, sell + 2, profit + (prices[sell] - prices[buy]));
        }

        maxProfit(prices, buy + 1, buy + 2, profit);
        maxProfit(prices, buy , sell + 1, profit);
    }
}
