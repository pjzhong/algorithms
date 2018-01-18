
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Administrator on 2018/1/8.
 * 缺失的整数
 */
public class TheMissing {

    @Test
    public void test() {
    }

    //一个无序数组里有99个不重复正整数，范围从1到100，唯独缺少一个整数。如何找出这个缺失的整数？
    private int findTheMissing(int[] nums) {
        int result = 5050;//1-100的和
        for(int i = 0 ,size = nums.length; i < size; i++) {
            result -= nums[i];
        }
        return result;
    }

    //一个无序数组里有若干个正整数，范围从1到100，其中99个整数都出现了偶数次，只有一个整数出现了奇数次（比如1,1,2,2,3,3,4,5,5），如何找到这个出现奇数次的整数？
    private int findTheOdd(int[] nums) {
        int result = 0;
        for(int i : nums) {
            result ^= i;
        }

        return result;
    }

    @Test
    public void findTheTwoOddsTest() {
        int[][] testCases = {
                {1,2,1,3,2,5},
        };
        for(int[] testCase  : testCases) {
            System.out.println(Arrays.toString(findTheTwoOdds(testCase)));
        }
    }

    //一个无序数组里有若干个正整数，范围从1到100，
    // 其中98个整数都出现了偶数次，只有两个整数出现了奇数次（比如1,1,2,2,3,4,5,5），如何找到这个出现奇数次的整数?
    //失败原因，思考时间过长
    private int[] findTheTwoOdds(int nums[]) {
        int result = 0;
        for(int i : nums) {
            result ^= i;
        }

        int a = result, b = result, bitSeparator = 1;
        for(;(result & bitSeparator) == 0;) {
            bitSeparator = 1 << bitSeparator;
        }

        for(int i : nums) {
            if((i & bitSeparator) == 0) {
                a ^= i;
            } else {
                b ^= i;
            }
        }

        return new int[]{a, b};
    }
}
