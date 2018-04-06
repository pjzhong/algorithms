

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Given a list of non negative integers, arrange them such that they form the largest number.
 * For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
 * Note: The result may be very large, so you need to return a string instead of an integer.
 *
 *
 * https://leetcode.com/problems/largest-number/description/
 * */
public class LargestNumber {

    private Random random = new Random();

    @Test
    public void notEqual() {
        int[] input = {4704,6306,9385,7536,3462,4798,5422,5529,8070,6241,9094,7846,663,6221,216,6758,8353,3650,3836,8183,3516,5909,6744,1548,5712,2281,3664,7100,6698,7321,4980,8937,3163,5784,3298,9890,1090,7605,1380,1147,1495,3699,9448,5208,9456,3846,3567,6856,2000,3575,7205,2697,5972,7471,1763,1143,1417,6038,2313,6554,9026,8107,9827,7982,9685,3905,8939,1048,282,7423,6327,2970,4453,5460,3399,9533,914,3932,192,3084,6806,273,4283,2060,5682,2,2362,4812,7032,810,2465,6511,213,2362,3021,2745,3636,6265,1518,8398};
        String ex = "989098279685953394569448938591490949026893989378398835381838108107807079827846760575367471742373217205710070326856680667586744669866365546511632763066265624162216038597259095784571256825529546054225208498048124798470444534283393239053846383636993664365036363575356735163462339932983163308430212970282274527326972465236223622313 2281 2 216 213206020001921763154815181495141713801147114310901048";
        String my = "989098279685953394569448938591490949026893989378398835381838108107807079827846760575367471742373217205710070326856680667586744669866365546511632763066265624162216038597259095784571256825529546054225208498048124798470444534283393239053846383636993664365036363575356735163462339932983163308430212970282274527326972465236223622313 2 2281216 213206020001921763154815181495141713801147114310901048";
        for(int i = 0, size = ex.length(); i < size; i++) {
            if(ex.charAt(i) != my.charAt(i)) {
                System.out.format("%s %s %d]\n", ex.charAt(i), my.charAt(i), i);
            }
        }
    }

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
                {1},
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

    public String solution(int[] nums) {
        sort(nums, 0, nums.length);
        StringBuilder builder = new StringBuilder(nums.length);

        if(nums[0] == 0) { return "0"; }//If the first element(the greatest element) is zero, then result is zero;
        for(int i : nums) { builder.append(i); }

        return builder.toString();
    }

    public int[] sort(int[] e, int lo, int hi) {
        return quickSort(e, lo, hi - 1);
    }

    private int[] quickSort(int[] e, int lo, int hi) {//[lo, hi]
        if(hi - lo < 1) { return e; }
        int mid = partition(e, lo, hi);
        quickSort(e, lo, mid);
        quickSort(e, mid + 1, hi);
        return e;
    }

    private int partition(int[] e, int lo, int hi) {//[lo, hi]
        swap(e, lo, lo + random.nextInt(hi - lo + 1));
        int pivot = e[lo];
        int mi = lo;
        for(int k = lo + 1; k <= hi; ++k) {
            if(less(pivot, e[k])) {
                swap(e, ++mi, k);
            }
        }
        swap(e, lo, mi);
        return mi;
    }

    private boolean less(int thisInt, int thatInt) {
        return compare(thisInt, thatInt) < 0;
    }

    /**
     * 这个比较实现没有保持传递性
     * ⌢(represents concatenation)
     *
     * a ⌢ b > b ⌢ a
     * b ⌢ c > c ⌢ b
     * a ⌢ c > c ⌢ b (it didn't preserves transitivity)
     *
     * */
    private int compare(int thisInt, int thatInt) {
        String a = Integer.toString(thisInt), b = Integer.toString(thatInt);
        int aLength = a.length(), bLength = b.length();
        int lim = Math.min(aLength, bLength) - 1;
        for(int i = 0; i <= lim; i++) {
            char c1 = a.charAt(i), c2 = b.charAt(i);
            if(c1 != c2) {
                return c1 - c2;
            }
        }
        if(aLength == bLength) { return 1; }


        int compareResult = aLength < bLength ? a.charAt(0) - b.charAt(lim + 1) : a.charAt(lim + 1) - b.charAt(0);
        if(0 <= compareResult) {
            if(compareResult == 0 && bLength < aLength) {
                return -1;
            }
            return 1;
        } else {
            return -1;
        }
    }

    private void swap(int[] num, int i, int j) {
        if(i == j) { return; }
        num[i] = num[i] + num[j];
        num[j] = num[i] - num[j];
        num[i] = num[i] - num[j];
    }
}
