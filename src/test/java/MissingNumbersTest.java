import org.junit.Test;

/**
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
 * Example 1
 * Input: [3,0,1]
 * Output: 2
 * Example 2
 *
 * Input: [9,6,4,2,3,5,7,0,1]
 * Output: 8
 *
 * Note:
 * Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
 */
public class MissingNumbersTest {

    @Test
    public void test() {
        int[][] testCases = {
                {0},
                {0, 1},
                {3, 0, 1},
                {9,6,4,2,3,5,7,0,1}
        };

        for(int[] testCase : testCases) {
            System.out.println(missingNumberSolvedSumFormula(testCase));
            System.out.println(missingNumber(testCase));
        }
    }

    public int missingNumberSolvedSumFormula(int[] nums) {
        int result = nums.length * (nums.length + 1) / 2;
        for(int i : nums) { result -= i; }
        return result;
    }

    public int missingNumber(int[] nums) {
        int max = Integer.MIN_VALUE, sum = 0, result = 0;

        for(int i : nums) {
            sum += i;
            if(i > max) { max = i;}
        }

        for(int i = 0, size = nums.length; i <= size; i++) { result += i;}

        return result - sum;
    }
}
