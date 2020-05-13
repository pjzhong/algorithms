package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Given an array equations of strings that represent relationships between variables, each string
 * equations[i] has length 4 and takes one of two different forms: "a==b" or "a!=b".  Here, a and b
 * are lowercase letters (not necessarily different) that represent one-letter variable names.
 *
 * Return true if and only if it is possible to assign integers to variable names so as to satisfy
 * all the given equations.
 *
 *
 *
 * Example 1:
 *
 * <p>Input: ["a==b","b!=a"]</p>
 * <p>Output: false </p>
 * Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the
 * second.  There is no way to assign the variables to satisfy both equations.
 *
 * @link https://leetcode.com/problems/satisfiability-of-equality-equations/
 * @author ZJP
 * @since 2020年05月13日 15:56:13
 **/
public class SatisfiabilityofEqualityEquations {

  @Test
  public void test() {
    String[][] tests = {
        {"b!=c"},
        {"a!=a"},
        {"a==b", "c==d", "a!=d", "e==f", "g==h", "e!=h", "a!=e", "c!=g", "a!=g"},
        {"e!=c", "b!=b", "b!=a", "e==d"},
        {"a==a", "a!=a"},
        {"c==c", "f!=a", "f==b", "b==c"},
        {"a==b", "b==a"},
        {"a==b", "b!=c", "c==a"},
        {"a==b", "b==c", "c==d", "e!=a"},
        {"c==c", "b==d", "x!=z"},
        {"a==b", "b==c", "a==c"},
        {"a==b", "b!=a"},
    };

    for (String[] test : tests) {
      System.out.println(equationsPossible(test));
    }
  }

  public boolean equationsPossible(String[] equations) {
    List<Character>[] graphs = new ArrayList[26];

    for (String s : equations) {
      if (s.charAt(1) == '=') {
        int left = s.charAt(0) - 'a', right = s.charAt(3) - 'a';
        if (graphs[left] == null) {
          graphs[left] = new ArrayList<>();
        }
        if (graphs[right] == null) {
          graphs[right] = new ArrayList<>();
        }
        graphs[s.charAt(0) - 'a'].add(s.charAt(3));
        graphs[s.charAt(3) - 'a'].add(s.charAt(0));
      }
    }

    int assign = 1;
    int[] color = new int[graphs.length];
    for (int i = 0; i < color.length; i++) {
      if (graphs[i] != null && color[i] == 0) {
        color[i] = assign++;
        travel((char) (i + 'a'), color, graphs);
      }
    }

    for (String e : equations) {
      if (e.charAt(1) == '!') {
        int left = e.charAt(0) - 'a';
        int right = e.charAt(3) - 'a';
        if (left == right || (color[right] != 0 && color[left] != 0
            && color[right] == color[left])) {
          return false;
        }
      }
    }
    return true;
  }

  private void travel(char name, int[] color, List<Character>[] graphs) {
    int from = name - 'a', num = color[from];
    for (char c : graphs[from]) {
      int idx = c - 'a';
      if (color[idx] == 0) {
        color[idx] = num;
        travel(c, color, graphs);
      }
    }
  }


}
