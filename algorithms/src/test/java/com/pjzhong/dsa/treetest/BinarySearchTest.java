package com.pjzhong.dsa.treetest;

import com.pjzhong.dsa.tree.BinarySearchTree;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;
import java.util.function.Consumer;

public class BinarySearchTest {

  private static BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();
  private static int randomLimit = 10000000;

  @BeforeClass
  public static void before() {
    Random random = new Random();
    int loops = 100000;
    for (int i = 1, next = -1; i <= loops; i++) {
      next = random.nextInt(randomLimit);
      tree.put(next, String.valueOf(next));
    }
  }

  @Test
  public void inOrderTraversalTest() {
    Consumer<String> consumer = (i) -> System.out.format("%s ", i);
    tree.inOrderTraversal(consumer);
  }

  @Test
  public void deleteMaxMin() {
    int expectedSize = tree.size() - 2;
    String maximum = tree.max(), minimum = tree.min();
    String AfterDeleteMaximum = tree.deleteMax(), AfterDeleteMinimum = tree.deleteMin();

    Assert.assertEquals(expectedSize, tree.size());
    Assert.assertEquals(maximum, AfterDeleteMaximum);
    Assert.assertEquals(minimum, AfterDeleteMinimum);
    Assert.assertNull(tree.get(Integer.valueOf(maximum)));
    Assert.assertNull(tree.get(Integer.valueOf(minimum)));
  }

  @Test
  public void randomDeleteTest() {
    String testTarget = null;
    Random random = new Random();
    int loops = 100000;
    for (int i = 0; i <= loops && testTarget == null; i++) {
      testTarget = tree.get(random.nextInt(randomLimit));
    }

    Assert.assertNotNull("testTarget is null", testTarget);

    int expectedSize = tree.size() - 1;
    String afterDelete = tree.delete(Integer.valueOf(testTarget));
    Assert.assertEquals(testTarget, afterDelete);
    Assert.assertEquals(expectedSize, tree.size());
    Assert.assertNull(tree.get(Integer.valueOf(testTarget)));
  }

}
