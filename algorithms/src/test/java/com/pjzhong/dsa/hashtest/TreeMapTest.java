package com.pjzhong.dsa.hashtest;

import com.pjzhong.dsa.hash.Map;
import com.pjzhong.dsa.hash.TreeMap;
import java.lang.annotation.Repeatable;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

public class TreeMapTest {

  private static Map<Integer, Integer> map = new TreeMap<>();

  @BeforeClass
  public static void before() {
    Random random = new Random();
    int loop = 1000;
    for (int i = 0, next; i < loop; i++) {
      next = random.nextInt();
      map.put(next, next);
    }

    map.put(100,10000);
    Assert.assertFalse(map.isEmpty());
  }

  @Test
  public void getAndPutTest() {
    int key = 100, value = 0;
    int expectedSize = map.size() + (map.contains(key) ? 0 : 1);
    map.put(key, value);

    int getResult = map.get(key);
    Assert.assertEquals(value, getResult);
    Assert.assertEquals(expectedSize, map.size());
    Assert.assertTrue(map.contains(key));
  }

  @Test
  public void removeTest() {
    int key = 100, value = 0;
    int expectedSize = map.size() - (map.contains(key) ? 1 : 0);
    map.put(key, value);
    int removeValue = map.remove(key);

    Assert.assertEquals(value, removeValue);
    Assert.assertEquals(expectedSize, map.size());
    Assert.assertFalse(map.contains(key));
  }
}
