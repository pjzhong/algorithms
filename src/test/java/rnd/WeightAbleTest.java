package rnd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public class WeightAbleTest {

  @Test
  public void malicious() {
    List<WeightTest> tests = new ArrayList<>();
    tests.add(new WeightTest(1));// 恶意份子
    int add = 5000;
    for (int i = 1; i <= 100000; i++) {
      tests.add(new WeightTest(RandomUtil.rnd(100, 5000)));
    }

    System.out.format("恶意%n");
    startRnd(tests, add);
  }

  @Test()
  public void noMalicious() {
    List<WeightTest> tests = new ArrayList<>();
    int add = 5000;
    for (int i = 0; i <= 100000; i++) {
      tests.add(new WeightTest(RandomUtil.rnd(100, 5000)));
    }

    System.out.format("无恶意%n");
    startRnd(tests, add);
  }

  @Test
  public void average() {
    List<WeightTest> tests = new ArrayList<>();
    int add = 5000;
    for (int i = 0; i < 100000; i++) {
      tests.add(new WeightTest(add));
    }

    System.out.format("平均%n");
    startRnd(tests, add);
  }

  private void startRnd(List<WeightTest> tests, int add) {
    int totalSize = tests.size();
    long oldStart = System.currentTimeMillis();
    for (int i = add; i <= totalSize; i += add) {
      List<WeightTest> subList = tests.subList(0,
          Math.min(i, totalSize));
      oldTest(subList);
    }
    System.out.format("old totalCost:%sms%n", System.currentTimeMillis() - oldStart);

    long newStart = System.currentTimeMillis();
    for (int i = add; i <= totalSize; i += add) {
      List<WeightTest> subList = tests.subList(0,
          Math.min(i, totalSize));
      newTest(subList);
    }
    System.out.format("new totalCost:%sms%n", System.currentTimeMillis() - newStart);

  }


  private void oldTest(List<WeightTest> target) {
    if (true) {
      return;
    }

    long time = System.currentTimeMillis();
    int count = 0;
    Set<WeightTest> add = new HashSet<>(target.size());
    for (int i = 0, size = target.size(); i < size; count++) {
      WeightTest t = RandomUtil.random(target);
      if (t != null && add.add(t)) {
        i++;
      }
    }

    System.out.format("old ran:%s count:%s, cost:%sms %n", target.size(),
        count, System.currentTimeMillis() - time);
  }

  private void newTest(List<WeightTest> target) {
    long time = System.currentTimeMillis();
    int count = 0, sum = target.stream().mapToInt(WeightTest::getWeight).sum();
    Set<WeightTest> add = new HashSet<>(target.size());
    for (int i = 0, size = target.size() - 1; i < size; count++) {
      WeightTest t = RandomUtil.random(target, sum);
      if (t != null && add.add(t)) {
        i++;
      }
    }

    System.out.format("new ran:%s count:%s, cost:%sms %n", target.size(),
        count, System.currentTimeMillis() - time);
  }

  public static class WeightTest implements WeightAble<WeightTest> {

    private int wegith;

    public WeightTest(int wegith) {
      this.wegith = wegith;
    }

    @Override
    public int getWeight() {
      return wegith;
    }

    @Override
    public WeightTest getV() {
      return this;
    }
  }

  @Test
  public void jdkRandom() {
    int add = 5000;
    Random random = new Random();
    Set<Integer> set = new HashSet<>();
    for (int size = add; size <= 100000; size += add) {
      set.clear();
      int loop = 0;
      long start = System.currentTimeMillis();
      for (int count = 0; count < size; loop++) {
        if (set.add(random.nextInt(size))) {
          count++;
        }
      }
      System.out
          .format("rnd:%s, loop:%s cost:%sms%n", size, loop, System.currentTimeMillis() - start);

    }
  }

}
