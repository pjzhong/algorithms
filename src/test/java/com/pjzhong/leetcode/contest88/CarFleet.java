package com.pjzhong.leetcode.contest88;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * N cars are going to the same destination along a one lane road.  The destination is target miles
 * away.
 *
 * Each car i has a constant speed speed[i] (in miles per hour), and initial position position[i]
 * miles towards the target along the road.
 *
 * A car can never pass another car ahead of it, but it can catch up to it, and drive bumper to
 * bumper at the same speed.
 *
 * The distance between these two cars is ignored - they are assumed to have the same position.
 *
 * A car fleet is some non-empty set of cars driving at the same position and same speed.  Note that
 * a single car is also a car fleet.
 *
 * If a car catches up to a car fleet right at the destination point, it will still be considered as
 * one car fleet.
 *
 *
 * How many car fleets will arrive at the destination?
 *
 *
 *
 * Example 1:
 *
 * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3] Output: 3 Explanation: The cars
 * starting at 10 and 8 become a fleet, meeting each other at 12. The car starting at 0 doesn't
 * catch up to any other car, so it is a fleet by itself. The cars starting at 5 and 3 become a
 * fleet, meeting each other at 6. Note that no other cars meet these fleets before the destination,
 * so the answer is 3.
 *
 * Note:
 *
 * 0 <= N <= 10 ^ 4 0 < target <= 10 ^ 6 0 < speed[i] <= 10 ^ 6 0 <= position[i] < target All
 * initial positions are different.
 *
 * https://leetcode.com/contest/weekly-contest-89/problems/car-fleet/
 */
public class CarFleet {

  @Test
  public void test() {
    int[] targets = {31, 17, 10, 10, 12, 13};
    int[][] positions = {
        {5, 26, 18, 25, 29, 21, 22, 12, 19, 6},
        {8, 12, 16, 11, 7},//4
        {8, 3, 7, 4, 6, 5},//6
        {6, 8},//2
        {10, 8, 0, 5, 3},//3
        {7, 5, 4, 3, 3, 1, 3}//4
    };
    int[][] speeds = {
        {7, 6, 6, 4, 3, 4, 9, 7, 6, 4},
        {6, 9, 10, 9, 7},
        {4, 4, 4, 4, 4, 4},
        {3, 2},
        {2, 4, 1, 1, 3},
        {2, 4, 1, 1, 3, 1, 3}
    };

    for (int i = 0; i < targets.length; i++) {
      System.out.println(carFleet(targets[i], positions[i], speeds[i]));
    }
  }

  //The correct answer, amazing and concise.......from leetcoode - dotuas
  public int carFleet(int target, int[] position, int[] speed) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < position.length; i++) {
      map.put(position[i], speed[i]);
    }
    Arrays.sort(position);
    int total = 0;
    for (int i = position.length - 1; 0 <= i; ) {
      //T = V/S 时间=路程/速度
      double time = (target - position[i]) * 1.0 / map.get(position[i]);
      ++total;
      --i;
      while (0 <= i) {
        double t = (target - position[i]) * 1.0 / map.get(position[i]);
        if (time < t) {
          break;
        } else {
          i--;
        }
      }
    }
    return total;
  }


  /* code by yourself, you code such complicated*/
  public int carFleetMy(int target, int[] position, int[] speed) {
    int size = position.length;
    if (size == 0) {
      return 0;
    }

    Car[] cars = new Car[size];
    for (int i = 0; i < size; i++) {
      cars[i] = new Car(target, position[i], speed[i]);
    }
    Arrays.sort(cars);

    int fleet = 1;
    Car theHead = cars[0];
    for (int i = 1; i < size; i++) {
      if (theHead.time < cars[i].time) {
        theHead = cars[i];
        fleet++;
      }
    }

    return fleet;
  }

  private class Car implements Comparable<Car> {

    int position;
    double time;

    public Car(int target, int position, int speed) {
      this.position = position;
      time = (target - position) * 1.0 / speed;
    }

    @Override
    public int compareTo(Car o) {
      return -(this.position - o.position);
    }


    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Car{");
      sb.append("position=").append(position);
      sb.append(", com.pjzhong.time=").append(time);
      sb.append('}');
      return sb.toString();
    }
  }
}
