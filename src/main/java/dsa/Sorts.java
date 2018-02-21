package dsa;


import java.lang.reflect.Array;
import java.util.ListIterator;
import java.util.Random;

public class Sorts {

    private static Random random = new Random(System.currentTimeMillis());

    static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    static void swap(Comparable[] elements, int a, int b) {
        Comparable t = elements[a];
        elements[a] = elements[b];
        elements[b] = t;
    }

    private static void selectionSort(Comparable[] elements, int lo, int hi) {
        for(int outer = lo; outer < hi; outer++) {
            int min = outer;
            for(int i = outer + 1; i < hi; i++) {
                if(elements[i].compareTo(elements[min]) < 0) {
                    min = i;
                }
            }

            Comparable t = elements[outer];
            elements[outer] = elements[min];
            elements[min] = t;
        }
    }

    private static  void bubbleSort(Comparable[] elements, int lo, int hi) {
        boolean sorted = false;
        for(int i = 0, size = hi - lo; i < size && (sorted = !sorted); i++) {
            for(int n = (lo + 1), length = hi - i; n < length; n++) {
                if(elements[n - 1].compareTo(elements[n]) > 0) {
                    Comparable temp = elements[n];
                    elements[n] = elements[n - 1];
                    elements[n - 1] = temp;
                    sorted = false;
                }
            }
        }
    }

    public static class MergeSort {

        public static int MergeSort(Comparable[] num, int begin, int end, Comparable[] temp) {
            if(begin<end)
            {
                int mid=(begin+end)/2, inversion = 0;
                inversion += MergeSort(num, begin, mid, temp);
                inversion += MergeSort(num, mid+1, end, temp);
                return inversion += Merge(num, begin, mid, end, temp);
            }

            return 0;
        }

        private static int Merge(Comparable[] num, int begin, int mid, int end, Comparable[] temp) {
            int left=begin,right=mid+1;
            int start=0;

            int inversion = 0;
            while(left<=mid && right<=end) {
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

            for(int loop=0;loop<start;loop++)
                num[begin+loop]=temp[loop];

            return inversion;
        }

    }

    private static void insertionSort(Comparable[] elements, int lo, int hi) {
        for(int outer = lo, i = -1; outer < hi; outer++) {
            Comparable key = elements[outer];
            for(i = outer - 1; i >=  lo && less(key, elements[i]); i-- ) {
                elements[i + 1] = elements[i];
            }
            elements[i + 1] = key;
        }
    }

    public static <E extends Comparable<E>>  void sort(List<E> elements) {
        sort(elements, 0, elements.size());
    }


    public static <E extends Comparable<E>>  void sort(List<E> list, int lo, int hi) {
        E[] result = list.toArray((E[]) Array.newInstance(list.get(0).getClass(), list.size()));
        switch (random.nextInt(4)) {
            case 0: bubbleSort(result, lo, hi);break;
            case 1:{
                E[] temp =(E[]) Array.newInstance(list.get(0).getClass(), list.size());
                MergeSort.MergeSort(result, lo, hi, temp);
            } break;
            case 2: selectionSort(result, lo, hi); break;
            case 3:insertionSort(result, lo, hi);break;
        }

        ListIterator<E> i = list.listIterator();
        for(Object e : result) {
            i.next();
            i.set((E) e);
        }
    }

}
