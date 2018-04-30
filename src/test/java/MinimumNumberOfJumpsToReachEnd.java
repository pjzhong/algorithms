import org.junit.Test;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 Minimum number of jumps to reach end
 Given an array of integers where each element represents the max number of steps that can be made forward from
 that element. Write a function to return the minimum number of jumps to reach the end of the array
 (starting from the first element). If an element is 0, then cannot move through that element.

 Example:

 Input: arr[] = {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9}
 Output: 3 (1-> 3 -> 8 ->9)
 First element is 1, so can only go to 3. Second element is 3, so can make at most 3 steps eg to 5 or 8 or 9.

 https://www.geeksforgeeks.org/minimum-number-of-jumps-to-reach-end-of-a-given-array/
 https://leetcode.com/problems/jump-game-ii/description/
 * */
public class MinimumNumberOfJumpsToReachEnd {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int testCases = scanner.nextInt();
        for(int i = 0; i < testCases; ++i) {
            int[] test = new int[scanner.nextInt()];
            scanner.nextLine();String[] inputLines = scanner.nextLine().split(" ");
            for(int j = 0; j < test.length; ++j) {
                test[j] = Integer.parseInt(inputLines[j]);
            }

            System.out.println(minimumJumps(test));
        }
    }

    /**
     * Perfect, you can solved this question by yourself without looking at the answer!!!!
     * */
    public static int minimumJumps(int[] jumps) {
        final int MAX_VALUE = Integer.MAX_VALUE - 1;
        int[] dp = new int[jumps.length];

        for(int i = 1; i < dp.length; ++i) { dp[i] = MAX_VALUE;}
        dp[0] = 0;
        for(int i = 0, size = jumps.length; i < size; ++i) {
            for(int j = 1; j <= jumps[i] && j + i < size; ++j) {
                dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
            }
        }
        return dp[jumps.length - 1] == MAX_VALUE ? -1 : dp[jumps.length - 1];
    }

    /**
     * The official recursive solution, I an not quite understand this.
     * */
    public static int minimumJumpsRecursive(int[] jumps, int low, int hi) {//[low, hi]
        if(low == hi) { return 0;}
        if(jumps[low] == 0) { return Integer.MAX_VALUE; }

        int min = Integer.MAX_VALUE;
        for(int i = low + 1; i <= hi && i <= low + jumps[low]; ++i) {
            int steps = minimumJumpsRecursive(jumps, i, hi);
            if(steps != Integer.MAX_VALUE && steps + 1 < min) {
                min = steps + 1;
            }
        }
        return min;
    }
}
