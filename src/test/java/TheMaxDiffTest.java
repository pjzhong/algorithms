
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by Administrator on 2018/1/9.
 */
public class TheMaxDiffTest {

    @Test
    public void start() {
        int numbers[]  = {10, 14, 21, 27, 12, 30};

        System.out.println(maxDiffByBucketSort(numbers));
        System.out.println(maxDiffByCountingSort(numbers));
        System.out.println(maxDiff(numbers));
    }

    //有一个无序整型数组，如何求出这个数组排序后的任意两个相邻元素的最大差值？要求时间和空间复杂度尽可能低。
    // （例如：无序数组 2,3,1,4,6，排序后是1,2,3,4,6，最大差值是6-4=2）
    // 我个人给n加上一个边界条件 0 < n <= Integer.MAX_VALUE;
    public int maxDiff(int[] numbers) {

      /*  int numbers[] = new int[numbers.length];
        System.arraycopy(numbers, 0, numbers, 0, numbers.length);
*/
        if(numbers.length <= 2) { return 0;}
        Arrays.sort(numbers);
        int max = Integer.MIN_VALUE;
        for(int i = numbers.length  - 1; i > 0; i--) {
            int temp = numbers[i] - numbers[i - 1];
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

    public int maxDiffByBucketSort(int[] numbers) {
        if(numbers == null || numbers.length < 2) { return 0; }
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for(int i : numbers) {
            if(i > max ) { max = i;}
            if(i < min ) { min = i;}
        }
        int gap = (int)Math.ceil((double)(max - min) / numbers.length);

        int[] bucketsMin = new int[numbers.length];//
        int[] bucketsMAX = new int[numbers.length];//不处理最大值， - 1
        Arrays.fill(bucketsMAX, Integer.MIN_VALUE);
        Arrays.fill(bucketsMin, Integer.MAX_VALUE);

        for(int i : numbers) {
            int index = (i - min) / gap;
            bucketsMAX[index] = Math.max(i, bucketsMAX[index]);
            bucketsMin[index] = Math.min(i, bucketsMin[index]);
        }

        int maxGap = Integer.MIN_VALUE;
        for(int i = 0, previous = min; i < numbers.length - 1; i++) {
            if(bucketsMAX[i] == Integer.MIN_VALUE && bucketsMin[i] == Integer.MAX_VALUE) { continue; }

            maxGap = Math.max(maxGap, bucketsMAX[i] - previous);
            previous = bucketsMAX[i];
        }
        return  maxGap;
    }
}
