package com.pjzhong.leetcode.content131;

import org.junit.Test;

/**
 * A valid parentheses string is either empty (""), "(" + A + ")", or A + B, where A and B are valid
 * parentheses strings, and + represents string concatenation.  For example, "", "()", "(())()", and
 * "(()(()))" are all valid parentheses strings.
 *
 * A valid parentheses string S is primitive if it is nonempty, and there does not exist a way to
 * split it into S = A+B, with A and B nonempty valid parentheses strings.
 *
 * Given a valid parentheses string S, consider its primitive decomposition: S = P_1 + P_2 + ... +
 * P_k, where P_i are primitive valid parentheses strings.
 *
 * Return S after removing the outermost parentheses of every primitive string in the primitive
 * decomposition of S.
 *
 *
 *
 * Example 1:
 *
 * Input: "(()())(())" Output: "()()()"
 *
 * Explanation: The input string is "(()())(())", with primitive decomposition "(()())" + "(())".
 * After removing outer parentheses of each part, this is "()()" + "()" = "()()()". Example 2:
 *
 * Input: "(()())(())(()(()))" Output: "()()()()(())"
 *
 * Explanation: The input string is "(()())(())(()(()))", with primitive decomposition "(()())" +
 * "(())" + "(()(()))". After removing outer parentheses of each part, this is "()()" + "()" +
 * "()(())" = "()()()()(())". Example 3:
 *
 * Input: "()()" Output: ""
 *
 * Explanation: The input string is "()()", with primitive decomposition "()" + "()". After removing
 * outer parentheses of each part, this is "" + "" = "".
 *
 *
 * Note:
 *
 * S.length <= 10000 S[i] is "(" or ")" S is a valid parentheses string
 *
 * @link https://com.pjzhong.leetcode.com/problems/remove-outermost-parentheses/
 */
public class RemoveOutermostParentheses {

  @Test
  public void test() {
    String[] tests = {"((()())(()()))", "(()())(())", "()()", "(()())(())(()(()))", ""};

    for (String t : tests) {
      System.out.printf("%s%n", removeOuterParentheses(t));
    }
  }

  /**
   * A more elegant solution
   *
   * @link https://com.pjzhong.leetcode.com/problems/remove-outermost-parentheses/discuss/270022/JavaC%2B%2BPython-Count-Opened-Parenthesis
   */
  public String removeOuterParentheses(String S) {
    if (S.isEmpty()) {
      return S;
    }

    StringBuilder builder = new StringBuilder(), temp = new StringBuilder();
    int delete = 0;
    for (char c : S.toCharArray()) {
      temp.append(c);
      if (c == ')') {
        delete += 2;
      }

      if (temp.length() - delete <= 0) {
        builder.append(temp.substring(1, temp.length() - 1));
        temp.setLength(0);
        delete = 0;
      }
    }
    return builder.toString();
  }
}
