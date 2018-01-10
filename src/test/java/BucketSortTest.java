import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class BucketSortTest {

    private final int DEFAULT_BUCKET_SIZE = 10;

    @Test
    public void test() {
        List<Integer> list = Arrays.asList(10,2,3,1,33,4,6,25,32,46,76,96,4,8,13,12,9,56,13,63,100);

        Integer resultBucketSort[] = new Integer[list.size()];
        list.toArray(resultBucketSort);
        sort(resultBucketSort, DEFAULT_BUCKET_SIZE);
        System.out.println(Arrays.toString(resultBucketSort));


        Collections.sort(list);
        System.out.println(list);
    }

    //有分治的思想，把整个数组均匀的分到n个桶(桶之间是有序的)里面然后各个桶进行排序, 然后链接起来；
    public void sort(Integer[] array, int bucketSize) {
        if(array.length == 0) { return; }

        int max = array[0];
        int min = array[0];
        for(int i : array) {
            if(i > max) { max = i;}
            if(i < min) { min = i;}
        }

        int bucketCount = (max - min) / bucketSize + 1;
        List<List<Integer>> buckets = new ArrayList<List<Integer>>(bucketCount);
        for(int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for(int i : array) { buckets.get((i - min) / bucketSize).add(i); }
        for(int i = 0, size = buckets.size(), currentIndex = 0; i < size; i++) {
            Integer[] bucketArray = new Integer[buckets.get(i).size()];
            bucketArray = buckets.get(i).toArray(bucketArray);
            InsertingSort.sort(bucketArray);
            for(int j = 0; j < bucketArray.length; j++) {
                array[currentIndex++] = bucketArray[j];
            }
        }
    }
}
