package com.pjzhong.dsa.listtest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.pjzhong.dsa.list.SkipListMap;
import java.util.Iterator;
import java.util.Random;
import org.junit.Test;

public class SkipListTest {

  @Test
  public void addTest() {
    SkipListMap<Integer, Integer> list = new SkipListMap<>();
    int ceil = 1000;
    for (int i = ceil; i > 0; i--) {
      list.insert(i, i);
    }

    assertEquals(ceil, list.size());
    for (int i = ceil; i > 0; i--) {
      assertThat(i, is(list.get(i)));
    }
  }

  @Test
  public void orderTest() {
    SkipListMap<Integer, Integer> list = new SkipListMap<>();
    Random rnd = new Random();
    int ceil = 1000;
    for (int i = ceil; i > 0; i--) {
      list.insert(Math.abs(rnd.nextInt(i)), i);
    }
    assertEquals(ceil, list.size());

    Iterator<Integer> iter = list.keys().iterator();
    Integer prev = iter.next();
    int cont = 1;
    while (iter.hasNext()) {
      cont++;
      Integer n = iter.next();
      assertTrue(prev.compareTo(n) <= 0);
    }
    assertEquals(cont, ceil);
  }

  @Test
  public void iterKeyTest() {
    SkipListMap<Integer, Integer> list = new SkipListMap<>();
    int ceil = 1000;
    for (int i = ceil; i > 0; i--) {
      list.insert(i, i);
    }
    assertEquals(ceil, list.size());

    int i = 1;
    for (int k : list.keys()) {
      assertEquals(i++, k);
    }
  }

  @Test
  public void iterValuesTest() {
    SkipListMap<Integer, Integer> list = new SkipListMap<>();
    int ceil = 1000;
    for (int i = ceil; i > 0; i--) {
      list.insert(i, i);
    }
    assertEquals(ceil, list.size());

    int i = 1;
    for (int k : list.values()) {
      assertEquals(i++, k);
    }
  }


  @Test
  public void removeAscTest() {
    SkipListMap<Integer, Integer> list = new SkipListMap<>();
    int ceil = 10;
    for (int i = 0; i < ceil; i++) {
      list.insert(i, i);
    }
    assertEquals(ceil, list.size());
    for (int i = 0; i < ceil; i++) {
      assertThat(i, is(list.remove(i)));
    }
    assertEquals(0, list.size());
  }

  @Test
  public void removeDescTest() {
    SkipListMap<Integer, Integer> list = new SkipListMap<>();
    int ceil = 10;
    for (int i = 0; i < ceil; i++) {
      list.insert(i, i);
    }
    assertEquals(ceil, list.size());
    for (int i = ceil - 1; i >= 0; i--) {
      assertThat(i, is(list.remove(i)));
    }
    assertEquals(0, list.size());
  }

  @Test
  public void removeNotExistTest() {
    SkipListMap<Integer, Integer> list = new SkipListMap<>();
    int ceil = 10;
    for (int i = 0; i < ceil; i++) {
      list.insert(i, i);
    }
    assertEquals(ceil, list.size());
    assertNull(list.remove(11));
    assertEquals(ceil, list.size());
  }

  @Test
  public void prettyTest() {
    SkipListMap<Integer, Integer> list = new SkipListMap<>();
    int ceil = 100;
    for (int i = ceil; i > 0; i--) {
      list.insert(i, i);
    }
    System.out.println(list.prettyString());
  }

}
