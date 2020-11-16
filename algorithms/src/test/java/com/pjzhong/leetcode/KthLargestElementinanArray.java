package com.pjzhong.leetcode;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Assert;
import org.junit.Test;

/**
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the
 * sorted order, not the kth distinct element.
 *
 * Example 1:
 *
 * <p>Input: [3,2,1,5,6,4] and k = 2</p>
 * <p>Output: 5 </p>
 * Example 2:
 *
 * <p>Input: [3,2,3,1,2,4,5,5,6] and k = 4</p>
 * <p>Output: 4 </p>
 * Note: You may assume k is always valid, 1 ≤ k ≤ array's length.
 *
 * @link https://leetcode.com/problems/kth-largest-element-in-an-array/
 */
public class KthLargestElementinanArray {

  @Test
  public void test() {
    int[] test = new int[10];
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < test.length; j++) {
        test[j] = random.nextInt(1, Integer.MAX_VALUE);
      }
      int k = random.nextInt(test.length) + 1;
      int kth = findKthLargest(test, k);

      Arrays.sort(test);

      System.out.println(k);
      Assert.assertEquals(Arrays.toString(test), test[test.length - k], kth);
    }
  }

  public int findKthLargest(int[] nums, int k) {
    return findKthLargest(nums, k - 1, 0, nums.length);
  }


  /**
   * @param k the Kth largest
   * @param start include
   * @param end exclude
   **/
  private int findKthLargest(int[] nums, int k, int start, int end) {
    swap(nums, start, start + (end - start) / 2);
    int pivot = nums[start];
    int mid = start;
    for (int i = start + 1; i < end; ++i) {
      if (pivot < nums[i]) {
        swap(nums, ++mid, i);
      }
    }
    swap(nums, start, mid);
    if (mid == k) {
      return pivot;
    } else if (mid < k) {
      return findKthLargest(nums, k, mid + 1, end);
    } else {
      return findKthLargest(nums, k, start, mid);
    }
  }


  private void swap(int[] nums, int a, int b) {
    if (a == b) {
      return;
    }
    nums[a] ^= nums[b];
    nums[b] ^= nums[a];
    nums[a] ^= nums[b];
  }

}
