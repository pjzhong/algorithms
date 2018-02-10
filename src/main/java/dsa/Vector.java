package dsa;

/**
 * Created by Administrator on 2/5/2018.
 */
public interface Vector<T> {

    //取秩为r的元素
     T get(int r)
            throws ArrayIndexOutOfBoundsException;

    //插入obj，作为秩为r的元素；返回该元素
     T insert(int r, T obj)
            throws ArrayIndexOutOfBoundsException;

    //删除秩为r的元素
     T remove(int r)
            throws ArrayIndexOutOfBoundsException;

    //返回向量中元素数目
    int size();

    //判断向量是否为空
    boolean isEmpty();
}
