package leetcode.contest100;

import org.junit.Test;

/**
 * An array is monotonic if it is either monotone increasing or monotone decreasing.
 * <p>
 * An array A is monotone increasing if for all i <= j, A[i] <= A[j].  An array A is monotone decreasing if for all i
 * <= j, A[i] >= A[j].
 * <p>
 * Return true if and only if the given array A is monotonic.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: [1,2,2,3]
 * Output: true
 * Example 2:
 * <p>
 * Input: [6,5,4,4]
 * Output: true
 * Example 3:
 * <p>
 * Input: [1,3,2]
 * Output: false
 * Example 4:
 * <p>
 * Input: [1,2,4,5]
 * Output: true
 * Example 5:
 * <p>
 * Input: [1,1,1]
 * Output: true
 * <p>
 * <p>
 * Note:
 * <p>
 * 1 <= A.length <= 50000
 * -100000 <= A[i] <= 100000
 *
 * @link https://leetcode.com/contest/weekly-contest-100/problems/monotonic-array/
 */
public class MonotonicArray {

    @Test
    public void test() {
        int[][] testCases = {
                {1, 1, 1},
                {1, 2, 3, 4},
                {2, 3, 1, 2}
        };

        for (int[] t : testCases) {
            System.out.println(isMonotonic(t));
        }
    }

    public boolean isMonotonic(int[] A) {
        final int SIZE = A.length;
        boolean result = true;
        for (int i = 1; i < SIZE; i++) {
            if (!(A[i - 1] <= A[i])) {
                result = false;
                break;
            }
        }

        if (!result) {
            result = true;
            for (int i = 1; i < SIZE; i++) {
                if (!(A[i] <= A[i - 1])) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
