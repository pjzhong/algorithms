package com.pjzhong.dsa.sort;


public class MergeSort<E extends Comparable<E>> extends AbstractSort<E> {

    @Override
    public E[] sort(E[] e, int lo, int hi) {
        Object[] temp = new Object[e.length];
        MergeSort(e, lo, hi - 1, temp);
        return e;
    }

    private void MergeSort(Comparable[] num, int begin, int end, Object[] temp) {
        if(begin < end) {
            int mid = (begin + end) / 2;
            MergeSort(num, begin, mid, temp);
            MergeSort(num, mid+1, end, temp);
            Merge(num, begin, mid, end, temp);
        }
    }

    private void Merge(Comparable[] num, int begin, int mid, int end, Object[] temp) {
        int left = begin,right = mid + 1;
        int start = 0;

        for(;left <= mid && right <= end;start++) {
            temp[start] = less(num[left], num[right]) ? num[left++] : num[right++];
        }

        //把left 到 mid 的元素复制到temp
        while(left <= mid) { temp[start++] = num[left++]; }

        //把right 到end 的元素复制到temp
        while(right <= end) { temp[start++] = num[right++]; }

        for(int loop = 0; loop < start; loop++) {
            num[begin + loop] = (Comparable) temp[loop];
        }
    }
}
