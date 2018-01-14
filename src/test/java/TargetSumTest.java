import org.junit.Test;

/**
 * QUESTION COPY FROM LEETCODE
 *
 * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S.
 * Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.
 * Find out how many ways to assign symbols to make sum of integers equal to target S.
 *
 *
 * Example 1:
 * Input: nums is [1, 1, 1, 1, 1], S is 3.
 * Output: 5
 * Explanation:
 *
 * -1+1+1+1+1 = 3
 * +1-1+1+1+1 = 3
 * +1+1-1+1+1 = 3
 * +1+1+1-1+1 = 3
 * +1+1+1+1-1 = 3
 *
 * There are 5 ways to assign symbols to make the sum of nums be target 3.
 *
 * Note:
 * The length of the given array is positive and will not exceed 20.
 * The sum of elements in the given array will not exceed 1000.
 * Your output answer is guaranteed to be fitted in a 32-bit integer.
 */
public class TargetSumTest {

    @Test
    public void test() {
        int[] targetCaseOne ={1,1,1,1,1};
        int targetOne = 3;

        System.out.println(targetSumRecursive(targetCaseOne, targetOne));
    }

    private int targetSumRecursive(int[] integers, int target) {
        return doTargetSumRecursive(target, 0, integers);
    }

    private int doTargetSumRecursive(int target, int start,  int [] integer) {
        if(start >= integer.length) {return target == 0 ? 1 : 0;}

        int count = 0;
        count += doTargetSumRecursive(target + integer[start], start + 1, integer);
        count += doTargetSumRecursive(target - integer[start], start + 1, integer);
        return count;
    }

    /**
     *           F(target) == 1 target == 0
       边界值：
                 F(target) == 0 target < 0

                          | F(target + integers[i])
       子结构：F(target)
                         | F(target - integers[i])

       状态转移公式: F(target) = F(target + integers[i]) + F(target - integers[i])
       这个是递归的结构，那动态规划的思想是怎样的？
     *
     * */
    private int targetSumDp(int[] integers, int target) {
        int[] negativeIntegers = new int[integers.length];
        for(int i = 0, size = integers.length; i < size; i++) {
            negativeIntegers[i] = integers[i] * -1;
        }

        int a = target, b = target;
        for(int i = 0, size = integers.length; i < size;i ++) {}
        return -1;
    }


}
