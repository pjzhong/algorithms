package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * Given a string s containing only lower case English letters and the '?' character, convert all
 * the '?' characters into lower case letters such that the final string does not contain any
 * consecutive repeating characters. You cannot modify the non '?' characters.
 *
 * It is guaranteed that there are no consecutive repeating characters in the given string except
 * for '?'.
 *
 * Return the final string after all the conversions (possibly zero) have been made. If there is
 * more than one solution, return any of them. It can be shown that an answer is always possible
 * with the given constraints.
 *
 * @link https://leetcode.com/contest/weekly-contest-205/problems/replace-all-s-to-avoid-consecutive-repeating-characters/
 **/
public class ReplaceAllstoAvoidConsecutiveRepeatingCharacters {

  @Test
  public void test() {
    String[] tests = {
        "a?b???????c????????b",
        "?zs",
        "ubv?w",
        "??yw?ipkj?"
    };

    for (String s : tests) {
      System.out.println(modifyString(s));
    }
  }

  public String modifyString(String s) {
    char[] array = s.toCharArray();
    int limit = array.length - 1;

    for (int i = 0; i < array.length; i++) {
      if (array[i] == '?') {
        char left = array[Math.max(0, i - 1)], right = array[Math.min(i + 1, limit)];
        array[i] = find(left, right);
      }
    }

    return new String(array);
  }

  public char find(char left, char right) {
    char start = 'a';
    for (char i = start; i < 'z'; i++) {
      if (i != left && i != right) {
        return i;
      }
    }

    return '?';
  }


}
