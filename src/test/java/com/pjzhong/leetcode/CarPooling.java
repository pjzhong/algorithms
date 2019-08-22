package com.pjzhong.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import org.junit.Test;

/**
 * You are driving a vehicle that has capacity empty seats initially available for passengers.  The
 * vehicle only drives east (ie. it cannot turn around and drive west.)
 *
 * Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains
 * information about the i-th trip: the number of passengers that must be picked up, and the
 * locations to pick them up and drop them off.  The locations are given as the number of kilometers
 * due east from your vehicle's initial location.
 *
 * Return true if and only if it is possible to pick up and drop off all passengers for all the
 * given trips.
 *
 * @link https://com.pjzhong.leetcode.com/problems/car-pooling/
 **/
public class CarPooling {

  @Test
  public void test() {
    int[][][] tests = {
        {{9, 3, 6}, {8, 1, 7}, {6, 6, 8}, {8, 4, 9}, {4, 2, 9}},
        {{9, 3, 4}, {9, 1, 7}, {4, 2, 4}, {7, 4, 5}},
        {{7, 5, 6}, {6, 7, 8}, {10, 1, 6}},
        {{2, 1, 5}, {3, 3, 7}},
        {{2, 1, 5}, {3, 3, 7}},
        {{2, 1, 5}, {3, 5, 7}},
        {{3, 2, 7}, {3, 7, 9}, {8, 3, 9}},
    };
    int[] testC = {28, 23, 16, 4, 5, 3, 11};

    for (int i = 0; i < testC.length; i++) {
      System.out.println(carPooling(tests[i], testC[i]));
    }
  }

  public boolean carPooling(int[][] trips, int capacity) {
    Map<Integer, Integer> onAndOff = new TreeMap<>();
    for (int[] trip : trips) {
      onAndOff.merge(trip[1], trip[0], Integer::sum);//上车位置，加
      onAndOff.merge(trip[2], -trip[0], Integer::sum);//下车位置，减
    }

    int count = 0;
    for (Integer i : onAndOff.values()) {
      count += i;
      if (capacity < count) {
        return false;
      }
    }

    return true;
  }

  private boolean useSortAndPriorityQueue(int[][] trips, int capacity) {
    Arrays.sort(trips, Comparator.comparingInt(a -> a[1]));//上车顺序
    Queue<int[]> passengers = new PriorityQueue<>(Comparator.comparingInt(a -> a[2])); //下车顺序
    int left = capacity;
    for (int[] trip : trips) {
      while (passengers.peek() != null) {
        int[] p = passengers.peek();
        if (p[2] <= trip[1]) {//是否到站
          left += p[0];
          passengers.poll();
        } else {
          break;
        }
      }

      left -= trip[0];
      if (left < 0) {
        return false;
      } else {
        passengers.add(trip);
      }
    }

    return true;
  }
}
