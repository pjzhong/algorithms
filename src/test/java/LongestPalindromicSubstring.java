import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

 Example 1:

 Input: "babad"
 Output: "bab"
 Note: "aba" is also a valid answer.
 Example 2:

 Input: "cbbd"
 Output: "bb"

 https://leetcode.com/problems/longest-palindromic-substring/solution/
 * */
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in));

        int testCases = in.nextInt();
        in.nextLine();
        String input;
        for(; testCases > 0; --testCases) {
            input = in.nextLine();
            //System.out.println(recursive(input));
            //System.out.println(iterative(input));
            System.out.println(longestPalindromicSubstring(input));
        }
    }

    /**
     * 1) Optimal Substructure:
     *     If the first and last character is equal, then the LPS(0, n, l) =  LPS(1, n - 1, l)
     *     If not than the LPS(0, n, l) = MAX(LPS(1, n, 0), LPS(0, n - 1, 0))
     * 2) Overlapping Subproblem:1
     *     figure it out, on your self
     *
     * brute force solution
     * */
    private static int recursive(String str) {
        int[][] dp = new int[str.length()][str.length()];
        int result = recursive(str, 0, str.length() - 1, 0);
        return result;
    }

    private static int recursive(String str, int lo, int hi, int count) {//[lo, hi]
        if(lo == hi) { return count + 1; }//There only one character;

        if(str.charAt(lo) == str.charAt(hi)) {
            if(lo + 1 == hi) { return count + 2;}
            return  recursive(str, lo + 1, hi - 1, count + 2);
        } else {

            return Math.max(recursive(str, lo + 1, hi, 0), recursive(str, lo, hi - 1, 0));
        }
    }

    /**
     * a newbie's dp solution, no elegance again.......................
     * this algorithms require O(n^3) and extra 0(n^2) space`
     * */
    private static String iterative(String str) {
        if(str.isEmpty()) { return ""; }

        int size = str.length();
        int max = 0, start = -1, end = -1;
        boolean[][] dp = new boolean[size][size];
        for(int lo = 0; lo < size; lo++) {
            for(int hi = size - 1, length = 0; 0 <= hi && !dp[lo][hi]; hi--) {
                for(int l = lo, h = hi; l <= h;l++, h--) {
                    if(str.charAt(l) == str.charAt(h)) {
                        length += (l == h) ? 1 : 2;
                    } else {
                        length = 0;
                        break;
                    }
                }
                if(max < length) {
                    max = length;
                    start = lo; end = hi;
                }
                if(length > 0) {
                    for(int l = lo; l <= hi; l++) { dp[l][hi] = true;}
                    for(int h = hi; lo <= h; h--) {  dp[lo][h] = true;}
                }
            }
        }

        return str.substring(start, end + 1);
    }

    /**
     * The official solution, require O(n^2) time and O(1) constant space
     * expand around center
     * */
    private static String longestPalindromicSubstring(String s) {
        int start = 0, end = 0;
        for(int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if(len < end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right, S = s.length();

        while(0 <= L && R < S && s.charAt(L) == s.charAt(R)) {
            L--;R++;
        }
        return  R - L - 1;
    }
}
