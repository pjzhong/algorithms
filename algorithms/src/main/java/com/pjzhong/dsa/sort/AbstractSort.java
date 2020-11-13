package com.pjzhong.dsa.sort;

import com.pjzhong.dsa.list.List;

import java.lang.reflect.Array;
import java.util.ListIterator;

public abstract class AbstractSort<E> implements Sort<E> {

  protected boolean less(Comparable a, Comparable b) {
    return a.compareTo(b) < 0;
  }

  protected boolean lessEq(Comparable a, Comparable b) {
    return a.compareTo(b) <= 0;
  }

  protected void swap(Comparable[] elements, int a, int b) {
    Comparable t = elements[a];
    elements[a] = elements[b];
    elements[b] = t;
  }

  @Override
  public List<E> sort(List<E> list) {
    E[] result = list.toArray((E[]) Array.newInstance(list.get(0).getClass(), list.size()));
    sort(result, 0, result.length);

    ListIterator<E> i = list.listIterator();
    for (Object e : result) {
      i.next();
      i.set((E) e);
    }

    return list;
  }
}
