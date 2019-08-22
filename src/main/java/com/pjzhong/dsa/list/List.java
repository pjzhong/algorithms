package com.pjzhong.dsa.list;

import java.util.ListIterator;

/**
 * Created by PJ_Z on 2/15/2018.
 */
public interface List<E>  extends Collection<E> {

    void add(E value);

    int indexOf(Object value);

    int lastIndexOf(Object o);

    E get(int index);

    E remove(int index);

    boolean remove(E value);

    E set(int index, E value);

    Object[] toArray();

    E[] toArray(E[] a);

    ListIterator<E> listIterator();
}
