
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
        int result = 0;
        result ^= 100;
        result ^= 99;

        System.out.println(result ^ 100);
        System.out.println(result ^ 99);


        int nums[] = new int[94];
        for(int i = 0, count = 1; i < 90;count++ ) {
            nums[i++] = count;
            nums[i++] = count;
        }
        Random random = new Random(System.currentTimeMillis());

        for(int i = 90; i < 93; i++) {
            int index = random.nextInt(nums.length);
            int temp = nums[index];
            nums[index] = 100;
            nums[i] = temp;
        }
        nums[93] = 99;
        findTheTwoOdds(nums);
    }

    //一个无序数组里有若干个正整数，范围从1到100，
    // 其中98个整数都出现了偶数次，只有两个整数出现了奇数次（比如1,1,2,2,3,4,5,5），如何找到这个出现奇数次的整数?
    //失败原因，思考时间过长
    private int findTheTwoOdds(int nums[]) {
        int result = 0;
        for(int i : nums) {
            result ^= i;
        }

        int a = result, b = result, bitSeparator = 1;
        for(;(result & bitSeparator) == 1;) {
            bitSeparator = 1 << bitSeparator;
        }

        for(int i : nums) {
            if((i & bitSeparator) == 0) {
                a ^= i;
            } else {
                b ^= i;
            }
        }

        System.out.println(a);
        System.out.println(b);

        return result;
    }
}
