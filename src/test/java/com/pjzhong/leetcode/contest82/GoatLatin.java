package com.pjzhong.leetcode.contest82;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A sentence S is given, composed of words separated by spaces. Each word consists of lowercase and
 * uppercase letters only.
 *
 * We would like to convert the sentence to "Goat Latin" (a made-up language similar to Pig Latin.)
 *
 * The rules of Goat Latin are as follows:
 *
 * If a word begins with a vowel (a, e, i, o, or u), append "ma" to the end of the word. For
 * example, the word 'apple' becomes 'applema'.
 *
 * If a word begins with a consonant (i.e. not a vowel), remove the first letter and append it to
 * the end, then add "ma". For example, the word "goat" becomes "oatgma".
 *
 * Add one letter 'a' to the end of each word per its word index in the sentence, starting with 1.
 * For example, the first word gets "a" added to the end, the second word gets "aa" added to the end
 * and so on. Return the final sentence representing the conversion from S to Goat Latin.
 *
 *
 *
 * Example 1:
 *
 * Input: "I speak Goat Latin" Output: "Imaa peaksmaaa oatGmaaaa atinLmaaaaa" Example 2:
 *
 * Input: "The quick brown fox jumped over the lazy dog" Output: "heTmaa uickqmaaa rownbmaaaa
 * oxfmaaaaa umpedjmaaaaaa overmaaaaaaa hetmaaaaaaaa azylmaaaaaaaaa ogdmaaaaaaaaaa"
 *
 * https://leetcode.com/contest/weekly-contest-82/problems/goat-latin/
 */
public class GoatLatin {

  @Test
  public void test() {
    String[] testCase = {
        "I speak Goat Latin",
    };

    for (String s : testCase) {
      System.out.println(toGoatLatin(s));
    }
  }

  public String toGoatLatin(String S) {
    String[] strs = S.split(" ");
    Set<Character> vowel = new HashSet<>();
    vowel.addAll(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < strs.length; i++) {
      if (vowel.contains(strs[i].charAt(0)) || strs[i].startsWith("or")) {
        sb.append(strs[i]).append("ma");
      } else {
        if (2 <= strs[i].length()) {
          sb.append(strs[i], 1, strs[i].length()).append(strs[i].charAt(0)).append("ma");
        } else {
          sb.append(strs[i]).append("ma");
        }
      }

      for (int j = 0; j <= i; j++) {
        sb.append('a');
      }

      if (i != strs.length - 1) {
        sb.append(' ');
      }
    }

    return sb.toString();
  }
}
