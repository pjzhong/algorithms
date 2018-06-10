package leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
/**
 * Given a list of non negative integers, arrange them such that they form the largest number.
 * For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
 * Note: The result may be very large, so you need to return a string instead of an integer.
 *
 *
 * https://leetcode.com/problems/largest-number/description/
 * */
public class LargestNumber {

    @Test
    public void test() {
        int[][] testCases = {
                {2281,2,216},
                {228,29},
                {121,12},
                {12,121},
                {824,8247},
                {724,7246},
                {223,22},
                {3,32},
                {824,938,1399,5607,6973,5703,9609,4398,8247},//expected "9609938824824769735703560743981399"
                {1,2},
                {0,1},
                {0,0},
                {1,2,3,4,5,6,7,8,9},
                {0,0,0,0,0,1},
                {3, 30, 34, 5, 9}
        };

        for(int[] test : testCases) {
           System.out.println(solution(test));
        }
    }

    /**
     * official solution
     * */
    public String solution(int[] nums) {
        String[] strs = new String[nums.length];
        for(int i = 0, size = nums.length; i < size; i++) {
            strs[i] = Integer.toString(nums[i]);
        }

        Comparator<String> comparator = (s1, s2) -> {
            String order1 = s1 + s2, order2 = s2 + s1;
            return order2.compareTo(order1);
        };

        Arrays.sort(strs, comparator);
        if(strs[0].equals("0")) { return "0"; }
        StringBuilder builder = new StringBuilder(strs.length);
        for(String str : strs) {
            builder.append(str);
        }

        return builder.toString();
    }

}
