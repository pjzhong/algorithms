package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.Random;

/**
 * Created by Administrator on 2018/1/8. 辗转相除法
 */
public class EuclideanAlgorithm {

  @Test
  public void getGreatestCommonDivisor() {
    Random random = new Random();
    for (int i = 0; i < 20; i++) {
      long a = Math.abs(random.nextLong());
      long b = 1L;
      long start = System.nanoTime();
      System.out.println(a > b ? gcdByMod(a, b) : gcdByMod(b, a));
      System.out.println("end:" + (System.nanoTime() - start));
      /* System.out.println( a > b ? gcdBySubtract(a, b) : gcdBySubtract(b, a));*/
      long start2 = System.nanoTime();
      System.out.println(gcd(a, b));
      System.out.println("end:" + (System.nanoTime() - start2));
      System.out.println();
    }
  }

  //两个正整数a和b（a>b），
  //它们的最大公约数等于a除以b的余数c和b之间的最大公约数
  private long gcdByMod(long a, long b) {
    if (a % b == 0) {
      return b;
    } else {
      return gcdByMod(b, a % b);
    }
  }

  private int gcdBySubtract(int a, int b) {
    if (a == b) {
      return a;
    }
    return a < b ? gcdBySubtract(b - a, a) : gcdBySubtract(a - b, b);
  }

  /*
   * 两个正整数a和b（a>b），它们的最大公约数等于a-b的差值c和较小数b的最大公约数
   *
   * 当a和b均为偶数，gcb(a,b) = 2*gcb(a/2, b/2) = 2*gcb(a>>1, b>>1)
   * 当a为偶数，b为奇数，gcb(a,b) = gcb(a/2, b) = gcb(a>>1, b)
   * 当a为奇数，b为偶数，gcb(a,b) = gcb(a, b/2) = gcb(a, b>>1)
   * 作者：程序员小灰
   * 链接：https://juejin.im/post/5a217bac51882531926e8656
   * 来源：掘金
   * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
   * */
  private long gcd(long a, long b) {

    if (a == b) {
      return a;
    } else {
      if (a < b) {
        b = a ^ b;
        a = b ^ a;
        b = b ^ a;
      }
      boolean aIsEven = (a & 1) == 0, bIsEven = (b & 1) == 0;
      if (aIsEven && bIsEven) {
        return gcd(a >> 1, b >> 1) << 1;
      } else if (aIsEven) {
        return gcd(a >> 1, b);
      } else if (bIsEven) {
        return gcd(a, b >> 1);
      } else {
        return gcd(b, a - b);
      }
    }
  }
}
