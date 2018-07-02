package leetcode.failed;

import org.junit.Test;

import java.util.LinkedList;

/**
 * https://leetcode.com/problems/score-of-parentheses/description/
 * */
//Failed to solve this question
public class ScoreofParentheses {

    @Test
    public void test() {
        String[] testCases = {
                "()",
                "()()",
                "(())",
                "(()())",
                "((()()))",
                "(()(()))",
                "(()(((()))))",
                "(()())(()())",
                "((()())(()()))"
        };

        for(String t : testCases) {
            System.out.format("%s:%d\n", t, scoreOfParentheses(t));
        }
    }

    //Official solution from LeetCode
    public int scoreOfParentheses(String S) {
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(0);

        for(char c : S.toCharArray()) {
            if(c == '(') {
                stack.push(0);
            } else {
                int v = stack.pop();
                int w = stack.pop();
                stack.push(w + Math.max(2 * v, 1));
            }
        }

        return stack.pop();
    }

    //Wrong answer, you didn't understand the question
    public int scoreOfParenthesesWrong(String S) {
        char[] array = S.toCharArray();
        int result = 0;

        LinkedList<Integer> cals = new LinkedList<>();
        LinkedList<Integer> idxs = new LinkedList<>();
        for(int i = 0; i < array.length; i++) {
            if(array[i] == '(') {
                idxs.push(i);
                if(cals.isEmpty() || cals.peek() != 0) {
                    cals.push(0);
                }
            } else {
                int left = idxs.poll();
                if(left == i - 1) {//empty parentheses
                    if(idxs.isEmpty()) {
                        result += 1;
                    } else {
                        cals.push(cals.poll() + 1);
                    }
                } else {
                    if(idxs.isEmpty()) {
                        int cur = cals.poll();
                        while(!cals.isEmpty()) {
                            cur += cals.poll();
                        }
                        result += cur * 2;
                    } else {
                        cals.push(cals.poll() * 2);
                    }
                }
            }
        }

        return result;
    }

}
