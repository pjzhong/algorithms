package leetcode.Contest85;

import org.junit.Test;

/**
 *here are N dominoes in a line, and we place each domino vertically upright.

 In the beginning, we simultaneously push some of the dominoes either to the left or to the right.



 After each second, each domino that is falling to the left pushes the adjacent domino on the left.

 Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.

 When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.

 For the purposes of this question, we will consider that a falling domino expends no additional force to a falling or
 already fallen domino.

 Given a string "S" representing the initial state. S[i] = 'L', if the i-th domino has been pushed to the left;
 S[i] = 'R', if the i-th domino has been pushed to the right; S[i] = '.', if the i-th domino has not been pushed.

 Return a string representing the final state.

 Example 1:

 Input: ".L.R...LR..L.."
 Output: "LL.RR.LLRRLL.."
 Example 2:

 Input: "RR.L"
 Output: "RR.L"
 Explanation: The first domino expends no additional force on the second domino.


 I ran out of time before finish this question :(
 https://leetcode.com/problems/push-dominoes/description/
 * */
public class PushDominoes {

    @Test
    public void test() {
        String[] testCases = {
                "R.L...R",
                "R.R.L",
                "..R..",
                ".L.R...LR..L..",
                "RR.L"
        };

        for(String s : testCases) {
            System.out.println(pushDominoes(s));
        }
    }

    public String pushDominoes(String dominoes) {
        StringBuilder builder = new StringBuilder(dominoes);
        final int size = builder.length();

        int pushRight = -1;
        for(int i = 0; i < size; i++) {
            switch (builder.charAt(i)) {
                case 'L':{//I can safely push Left, because I know the [0, i](let part)  of dominoes
                    int pushLeft  = i - 1;
                    if(pushRight == -1) {
                       for(;0 <= pushLeft && builder.charAt(pushLeft) == '.';pushLeft--) {
                           builder.setCharAt(pushLeft, 'L');
                       }
                    } else {
                        while(pushRight < pushLeft) {
                            builder.setCharAt(pushRight, 'R');
                            builder.setCharAt(pushLeft, 'L');
                            pushRight++;
                            pushLeft--;
                        }
                    }
                    pushRight = -1;
                }break;
                case 'R':{//I not idea what the [i, size](right part) look like, I will try deal with it later.
                    if(pushRight != -1) {
                        while(pushRight < i && builder.charAt(pushRight) == '.') {
                            builder.setCharAt(pushRight++, 'R');
                        }
                    }
                    pushRight = i + 1;
                } break;
            }
        }
        if(pushRight != -1) {
            while(pushRight < size && builder.charAt(pushRight) == '.') { builder.setCharAt(pushRight++, 'R'); }
        }

        return builder.toString();
    }
}
