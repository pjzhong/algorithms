import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Given a sequence, find the length of the longest palindromic subsequence in it.
 *
 * Input: GEEKSFORGEEKS
 * Output:5
 *
 * The longest palindromic subsequence is EEKEE, EESEE, EEFEE............
 *
 * As another example, if the given sequence is “BBABCBCAB”, then the output should be 7 as “BABCBAB”
 * is the longest palindromic subseuqnce in it. “BBBBB” and “BBCBB” are also palindromic subsequences of
 * the given sequence, but not the longest ones.
 *
 *
 * for more info, see the links below
 * https://www.geeksforgeeks.org/dynamic-programming-set-12-longest-palindromic-subsequence/
 * https://leetcode.com/problems/longest-palindromic-subsequence/description/
 * */
public class LongestPalindromicSubsequence {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in));

        int testCases = in.nextInt();
        in.nextLine();
        String input;
        for(; testCases > 0; --testCases) {
            input = in.nextLine();
            System.out.println(recursive(input));
        }
    }

    /**
     * 1) Optimal Substructure:
     *     If the first and last character is equal, then the LPS(0, n) = 2 + LPS(1, n - 1)
     *     If not than the LPS(0, n) = MAX(LPS(1, n), LPS(0, n - 1))
     * 2) Overlapping Subproblem:
     *     figure it out, on your self
     *
     * Your were failed to figure out this on you own..........
     * */

    private static int recursive(String str) {
        int[][] dp = new int[str.length()][str.length()];
        return recursive(str, 0, str.length() - 1, dp);
    }

    private static int recursive(String str, int lo, int hi, int[][] dp) {//[lo, hi]
        if(lo == hi) { return  dp[lo][hi] = 1; }//There only one character;
        else if(dp[lo][hi] != 0) { return dp[lo][hi];}

        if(str.charAt(lo) == str.charAt(hi)) {
            if(lo + 1 == hi) { return dp[lo][hi] = 2;}
            return dp[lo][hi] = 2 + recursive(str, lo + 1, hi - 1, dp);
        } else {
            return dp[lo][hi] = Math.max(recursive(str, lo + 1, hi, dp), recursive(str, lo, hi - 1, dp));
        }
    }
}
