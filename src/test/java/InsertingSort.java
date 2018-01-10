/**
 * Created by Administrator on 2018/1/10.
 */
public class InsertingSort {

    public static <T extends Comparable<T>>  void sort(T[] array) {
        for(int i = 1, size = array.length; i < size; i++) {
            T item = array[i];
            int indexHole = i;
            while(indexHole > 0 && (array[indexHole - 1].compareTo(item)) > 0) {
                array[indexHole]  = array[--indexHole];
            }
            array[indexHole] = item;
        }
    }
}
