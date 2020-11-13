package com.pjzhong.leetcode.contest81;

import org.junit.Test;

import java.util.Arrays;

/**
 Given a string S and a character C, return an array of integers representing the shortest distance
 from the character C in the string.

 Example 1:

 Input: S = "loveleetcode", C = 'e'
 Output: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]


 Note:

 S string length is in [1, 10000].
 C is a single character, and guaranteed to be in string S.
 All letters in S and C are lowercase.

 https://leetcode.com/problems/shortest-distance-to-a-character/description/
 * */
public class ShortestDistanceToCharacter {

    @Test
    public void test() {
        String[] testCaes = {
               "loveleetcode",
                "zjpjjppp",
                "jjasjdlkfjalksdjz"
        };
        char[] c = {'e', 'z', 'z'};

        for(int i = 0; i < testCaes.length;i++) {
            System.out.println(Arrays.toString(shortestToChar(testCaes[i], c[i])));
        }
    }

    public int[] shortestToChar(String S, char C) {
        int[] result = new int[S.length()];

        for(int i = 0, previous = -1; i < S.length(); i++) {
            result[i] = Integer.MAX_VALUE;
            if(S.charAt(i) == C) {
                for(int current = i, distance = 0; previous < current; current--) {
                    result[current] = Math.min( previous == -1 ? Integer.MAX_VALUE :current - previous, distance);
                    distance++;
                }
                previous = i;
            } else {
                result[i] = Math.min(result[i], i - previous);
            }
        }

        return result;
    }
}
