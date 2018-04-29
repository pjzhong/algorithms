package leetcode.Contest82;

import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *Most Profit Assigning Work
 User Accepted: 0
 User Tried: 0
 Total Accepted: 0
 Total Submissions: 0
 Difficulty: Medium
 We have jobs: difficulty[i] is the difficulty of the ith job, and profit[i] is the profit of the ith job.

 Now we have some workers. worker[i] is the ability of the ith worker, which means that this worker can only complete a
 job with difficulty at most worker[i].

 Every worker can be assigned at most one job, but one job can be completed multiple times.

 For example, if 3 people attempt the same job that pays $1, then the total profit will be $3.
 If a worker cannot complete any job, his profit is $0.

 What is the most profit we can make?

 Example 1:

 Input: difficulty = [2,4,6,8,10], profit = [10,20,30,40,50], worker = [4,5,6,7]
 Output: 100
 Explanation: Workers are assigned jobs of difficulty [4,4,6,6] and they get profit of [20,20,30,30] seperately.
 Notes:

 1 <= difficulty.length = profit.length <= 10000
 1 <= worker.length <= 10000
 difficulty[i], profit[i], worker[i]  are in range [1, 10^5]
 * */
public class MostProfitAssigningWork {

    @Test
    public void test() {
        int[][] difficultys = {
                {2,4,6,8,10}
        };
        int[][] profits = {
                {10,20,30,40,50}
        };
        int[][] workers = {
                {4,5,6,7}
        };

        for(int i = 0; i < difficultys.length; i++) {
            System.out.println(maxProfitAssignment(difficultys[i], profits[i], workers[i]));
        }
    }

    /**
      你的大脑转弯太慢了................
     * */
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int[] dp = new int[100001];
        int maxProfit = 0;
        for(int w : worker) {
            if(0 < dp[w]) {
                maxProfit += dp[w];
            } else if (dp[w] == 0){
                Integer wProfit = 0;
                for(int i = 0; i < difficulty.length; i++) {
                    if(difficulty[i] <= w) {
                        wProfit = Math.max(profit[i], wProfit);

                    }
                }
                maxProfit += wProfit;
                dp[w] = wProfit == 0 ? -1 : wProfit;
            }
        }
        return maxProfit;
    }


    public int maxProfitAssignmentOfficialSolution(int[] difficulty, int[] profit, int[] worker) {
        final int N = difficulty.length;
        Point[] jobs = new Point[N];
        for(int i = 0; i < N; i++) { jobs[i] = new Point(difficulty[i], profit[i]);}
        Arrays.sort(jobs, (p1, p2) -> p1.x - p2.x);
        Arrays.sort(worker);

        int ans = 0, i = 0, tmp = 0;
        for(int w : worker) {
            while(i < N && jobs[i].x <= w) {
                tmp = Math.max(tmp, jobs[i++].y);
            }
            ans += tmp;
        }

        return ans;
    }
}


