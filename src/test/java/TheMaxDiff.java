
import org.junit.Test;
import java.util.Arrays;


/**
 * Created by Administrator on 2018/1/9.
 */
public class TheMaxDiff {

    @Test
    public void start() {
        int numbers[]  = {10,2,3,1,33,4,6,25,32};

        System.out.println(maxDiffByCountingSort(numbers));
        System.out.println(maxDiff(numbers));
    }

    //有一个无序整型数组，如何求出这个数组排序后的任意两个相邻元素的最大差值？要求时间和空间复杂度尽可能低。
    // （例如：无序数组 2,3,1,4,6，排序后是1,2,3,4,6，最大差值是6-4=2）
    //题目有很多边界条件没给这样会增加很多难度， 我个人加上 0 < n <= Integer.MAX_VALUE;
    public int maxDiff(int[] numbers) {
        int input[] = new int[numbers.length];
        System.arraycopy(numbers, 0, input, 0, numbers.length);

        Arrays.sort(input);
        int max = Integer.MIN_VALUE;
        for(int i = input.length  - 1; i > 0; i--) {
            int temp = input[i] - input[i - 1];
            if(temp > max) { max = temp; }
        }

        return max;
    }

    //1.找到数组中的max和min
    //2.创建一个长度为(max - min + 1), 然后把
    public int maxDiffByCountingSort(int[] numbers) {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for(int i : numbers) {
            if(i > max ) { max = i;}
            if(i < min ) { min = i;}
        }
        int count[] = new int[max - min + 1];
        for(int i : numbers) {
            count[i - min] = i;//最小值在首位，i - min 计算偏差值
        }

        int maxBlack = Integer.MIN_VALUE;
        for(int i = 0, temp = 0, size = count.length; i < size; i++) {
            if(count[i] == 0) {
                temp++;
            } else {
                if(temp + 1 > maxBlack) {
                    maxBlack = temp + 1;
                }
                temp = 0;
            }
        }

        return  maxBlack;
    }
}
