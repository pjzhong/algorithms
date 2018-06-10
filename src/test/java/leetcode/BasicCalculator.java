package leetcode;

import org.junit.Test;

import java.util.LinkedList;

/**
 Implement a basic calculator to evaluate a simple expression string.

 The expression string may contain open ( and closing parentheses ), the plus + or minus sign -,non-negative integers
 and empty spaces ' ' .

 Example 1:

 Input: "1 + 1"
 Output: 2
 Example 2:

 Input: " 2-1 + 2 "
 Output: 3
 Example 3:

 Input: "(1+(4+5+2)-3)+(6+8)"
 Output: 23
 Note:
 You may assume that the given expression is always valid.
 Do not use the eval built-in library function.

 https://leetcode.com/problems/basic-calculator/description/
 * */
public class BasicCalculator {

    @Test
    public void test() {
        String[] testCases = {
                "(1) + (2) - (10)", "19", "3-(5-6)","1 + 1", " 2-1 + 2", "(1+(4+5+2)-3)+(6+8)"
        };

        for(String s : testCases) {
            System.out.println(calculate(s));
        }
    }

    /**
     * Well, you need to improve your ability to  write more readable codes
     * */
    public int calculate(String s) {
        if(s == null || s.isEmpty()) { return  0;}

        LinkedList<Character> optr = new LinkedList<>();
        LinkedList<Integer> opnd = new LinkedList<>();

        StringBuilder builder = new StringBuilder();
        char c;
        for(int i = 0, size = s.length(); i <= size; i++) {
            c = (i < size) ? s.charAt(i) : '$';
            if(Character.isDigit(c)) {
                builder.append(c);
            } else if (c != ' ') {
                if(0 < builder.length()) {
                    opnd.push(Integer.valueOf(builder.toString()));
                    builder.setLength(0);
                }
                switch (c) {
                    case '$':case '(':
                    case '-':case '+':{
                        if(!optr.isEmpty() && c != '(' && optr.peek() != '(') {
                            doCacluate(optr, opnd);
                        }
                        if(c != '$') { optr.push(c);}
                    }break;
                    case ')': {
                        if(optr.peek() != '(') {
                            doCacluate(optr, opnd);
                        }
                        optr.pop();//(
                    } break;
                }
            }
        }

        return opnd.pop();
    }

    private void doCacluate(LinkedList<Character> optr, LinkedList<Integer> opnd) {
        Integer opnd2 = opnd.pop(), opnd1 = opnd.pop();
        opnd.push(optr.pop() == '+' ? opnd1 + opnd2 : opnd1 - opnd2);
    }
}
