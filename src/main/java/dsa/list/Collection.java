package dsa.list;

/**
 * Created by Administrator on 2018/3/1.
 */
public interface Collection<E> extends Iterable<E> {

    int size();

    boolean isEmpty();

    void clear();
}
