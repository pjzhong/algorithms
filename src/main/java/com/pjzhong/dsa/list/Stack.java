package com.pjzhong.dsa.list;

public interface Stack<E> extends Collection<E> {

  void push(E value);

  E pop();

  E top();
}
