package dsa;

/**
 * Created by PJ_Z on 2/15/2018.
 */
public interface List<T> {

    int size();

    boolean isEmpty();

    int indexOf(Object value);

    int lastIndexOf(Object o);

    T get(int index);

    T remove(int index);

    boolean remove(T value);

    T set(int index, T value);

    void clear();
}
