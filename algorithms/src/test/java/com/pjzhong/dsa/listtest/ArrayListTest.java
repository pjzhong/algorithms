package com.pjzhong.dsa.listtest;

import com.pjzhong.dsa.list.ArrayList;
import com.pjzhong.dsa.list.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ArrayListTest {

  private static List<Integer> list = new ArrayList<>();

  @BeforeClass
  public static void before() {
    int sizes = 100;
    for (int i = 0; i < 100; i++) {
      list.add(i);
    }
    Assert.assertEquals(sizes, list.size());
    Assert.assertFalse(list.isEmpty());
  }

  @Test
  public void clearTest() {
    List<Integer> test = new ArrayList<>();
    for (int i : list) {
      test.add(i);
    }

    for (int i = 0, size = list.size(); i < size; i++) {
      test.remove(0);
    }

    Assert.assertEquals(0, test.size());
    Assert.assertTrue(test.isEmpty());
  }

  @Test
  public void iterativeTest() {
    int expectedLoops = list.size(), count = 0;
    for (int i : list) {
      count++;
    }

    Assert.assertEquals(expectedLoops, count);
  }

  @Test
  public void equalsAndHastTest() {
    List<Integer> otherMe = new ArrayList<>();
    for (int i : list) {
      otherMe.add(i);
    }

    Assert.assertEquals(list, otherMe);
    Assert.assertEquals(list.hashCode(), otherMe.hashCode());
  }
}
