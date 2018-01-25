import org.junit.Test;

/**
 * After robbing those houses on that street,
 * the thief has found himself a new place for his thievery so that he will not get too much attention.
 * This time, all houses at this place are arranged in a circle.
 * That means the first house is the neighbor of the last one.
 * Meanwhile, the security system for these houses remain the same as for those in the previous street.
 *
 * Given a list of non-negative integers representing the amount of money of each house,
 * determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Problem.213
 */
public class HouseRobberTestII {

    @Test
    public void test() {
        int[][] testCases = {
                {1,2,5},
                {1,3,1},
                {1,3,1,3,1},
                {1,1,1},
                {1,1,1,1},
                {2},
                {2,5,10,1},
                {2,1,1,2},
                {1000,1,1,10},
                {2,1,1,2,1000,1,1,100},
                {2,1,1,2,1000,1,1,100,10000, 1, 1, 100},
                {226,174,214,16,218,48,153,131,128,17,157,142,88,43,37,157,43,221,191,68,206,23,225,82,54,118,111,46,
                80,49,245,63,25,194,72,80,143,55,209,18,55,122,65,66,177,101,63,201,172,130,103,225,142,46,86,185,62,
                        138,212,192,125,77,223,188,99,228,90,25,193,211,84,239,119,234,85,83,123,120,131,203,219,10,
                        82,35,120,180,249,106,37,169,225,54,103,55,166,124}
        };
        for(int[] testCase : testCases) {
            System.out.println(houseRobber(testCase));
        }
    }
    
    /*
    * 与houseRobber的屋子是直线排列，所以会有非常明确的边界值。但现在是以圆圈排列，边界值消失了
    * */
    private int houseRobber(int[] nums) {
        int even = 0, odd = 0;

        for(int i = 0, size = nums.length; i < size; i++) {
            if((i & 1) == 0) {
                even = Math.max(nums[i] + even, odd);
            } else {
                odd = Math.max(nums[i] + odd, even);
            }
        }

        return Math.max(even, odd);
    }
}
