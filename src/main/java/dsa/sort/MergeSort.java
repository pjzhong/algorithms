package dsa.sort;

import java.lang.reflect.Array;

public class MergeSort<E extends Comparable<E>> extends AbstractSort<E> {

    @Override
    public E[] sort(E[] e, int lo, int hi) {
        E[] temp =(E[]) Array.newInstance(e.getClass().getComponentType(), e.length);
        MergeSort(e, lo, hi - 1, temp);
        return e;
    }

    private int MergeSort(Comparable[] num, int begin, int end, Comparable[] temp) {
        if(begin<end) {
            int mid=(begin+end)/2, inversion = 0;
            inversion += MergeSort(num, begin, mid, temp);
            inversion += MergeSort(num, mid+1, end, temp);
            return inversion + Merge(num, begin, mid, end, temp);
        }

        return 0;
    }

    private int Merge(Comparable[] num, int begin, int mid, int end, Comparable[] temp) {
        int left=begin,right=mid+1;
        int start=0;

        int inversion = 0;
        while(left <= mid && right <= end) {
            if(less(num[left], num[right])) {
                temp[start++] = num[left++];
            } else {
                temp[start++] = num[right++];
                inversion += mid - left + 1;
            }
        }

        while(left<=mid) {//把left 到 mid 的元素复制到temp
            temp[start++] = num[left++];
        }

        while(right<=end) {//把right 到end 的元素复制到temp
            temp[start++] = num[right++];
        }

        for(int loop=0; loop < start; loop++)
            num[begin+loop]=temp[loop];

        return inversion;
    }
}
