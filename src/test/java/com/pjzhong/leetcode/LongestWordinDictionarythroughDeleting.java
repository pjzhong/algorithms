package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Given a string and a string dictionary, find the longest string in the dictionary that can be
 * formed by deleting some characters of the given string. If there are more than one possible
 * results, return the longest word with the smallest lexicographical order. If there is no possible
 * result, return the empty string.
 *
 * Example 1: Input: s = "abpcplea", d = ["ale","apple","monkey","plea"]
 *
 * Output: "apple"
 *
 * @author ZJP
 * @link https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/
 * @since 2020年04月09日 21:03:26
 **/
public class LongestWordinDictionarythroughDeleting {

  @Test
  public void test() {
    String[] directory = {"abpcplea", "abpcplea"};

    List<List<String>> worlds = new ArrayList<>();
    worlds.add(Arrays.asList("ale", "apple", "monkey", "plea"));
    worlds.add(Arrays.asList("a", "b", "c"));

    int size = directory.length;
    for (int i = 0; i < size; i++) {
      System.out.println(findLongestWord(directory[i], worlds.get(i)));
    }
  }


  public String findLongestWord(String s, List<String> d) {
    String result = "";

    for (String str : d) {
      int idx = -1;
      boolean match = true;
      for (int i = 0, size = str.length(); i < size; i++) {
        idx = s.indexOf(str.charAt(i), idx + 1);
        if (idx < 0) {
          match = false;
          break;
        }
      }

      if (match) {
        int rL = result.length(), sL = str.length();
        if (rL < sL) {
          result = str;
        } else {
          result = rL == sL && str.compareTo(result) < 0 ? str : result;
        }
      }
    }

    return result;
  }

}
