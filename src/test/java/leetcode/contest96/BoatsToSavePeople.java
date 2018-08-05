package leetcode.contest96;

import org.junit.Test;

import java.util.Arrays;

/**
 The i-th person has weight people[i], and each boat can carry a maximum weight of limit.

 Each boat carries at most 2 people at the same time, provided the sum of the weight of those people is at most limit.

 Return the minimum number of boats to carry every given person.
 (It is guaranteed each person can be carried by a boat.)



 Example 1:

 Input: people = [1,2], limit = 3
 Output: 1
 Explanation: 1 boat (1, 2)
 Example 2:

 Input: people = [3,2,2,1], limit = 3
 Output: 3
 Explanation: 3 boats (1, 2), (2) and (3)
 Example 3:

 Input: people = [3,5,3,4], limit = 5
 Output: 4
 Explanation: 4 boats (3), (3), (4), (5)


 Note:
 1 <= people.length <= 50000
 1 <= people[i] <= limit <= 30000

 @link https://leetcode.com/contest/weekly-contest-96/problems/boats-to-save-people/
 * */
public class BoatsToSavePeople {

    @Test
    public void test() {
        int[][] weight = {
            {1,2},
            {3,2,2,1},
            {3,5,3,4},
            {3,5,3,4,5},
            {1,1,3,4,5},
                {1},
                {1,2}
        };
        int[] limit = {3,3,5,5,5,5,1};

        for(int i = 0; i < weight.length; i++) {
            System.out.println(numRescueBoats(weight[i], limit[i]));
        }
    }

    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int boats = 0;
        int least = 0, highest = people.length - 1;
        for(; least <= highest;) {
            if(people[least] + people[highest] <= limit) {
                least++; highest--;
            } else {
                highest--;
            }
            boats++;
        }
        return boats;
    }
}
