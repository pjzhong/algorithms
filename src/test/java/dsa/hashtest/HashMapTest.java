package dsa.hashtest;

import dsa.hash.HashMap;
import dsa.hash.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashMapTest {

    Map<Integer, Integer> map = new HashMap<>();

    @Before
    public void before() {
        int loop = 1000;
        for(int i = 0; i < loop; i++) {
            map.put(i, i);
        }

        Assert.assertEquals(loop, map.size());
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
