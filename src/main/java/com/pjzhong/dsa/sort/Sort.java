package com.pjzhong.dsa.sort;

import com.pjzhong.dsa.list.List;

public interface Sort<E> {

    E[] sort(E[] e, int lo, int hi);

    List<E> sort(List<E> list);
}
