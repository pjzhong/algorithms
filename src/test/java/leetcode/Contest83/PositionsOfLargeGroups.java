package leetcode.contest83;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 In a string S of lowercase letters, these letters form consecutive groups of the same character.

 For example, a string like S = "abbxxxxzyy" has the groups "a", "bb", "xxxx", "z" and "yy".

 Call a group large if it has 3 or more characters.  We would like the starting and ending positions of every
 large group.

 The final answer should be in lexicographic order.



 Example 1:

 Input: "abbxxxxzzy"
 Output: [[3,6]]
 Explanation: "xxxx" is the single large group with starting  3 and ending positions 6.
 Example 2:

 Input: "abc"
 Output: []
 Explanation: We have "a","b" and "c" but no large group.
 Example 3:

 Input: "abcdddeeeeaabbbcd"
 Output: [[3,5],[6,9],[12,14]]


 Note:  1 <= S.length <= 1000
 * */
public class PositionsOfLargeGroups {

    @Test
    public void test() {
        String[] testCases = {
                "aaabbb",
                "abbxxxxzzy",
                "abc",
                "abcdddeeeeaabbbcd",
        };

        for(String s : testCases) {
            for(List<Integer> nums : largeGroupPositions(s)) {
                System.out.print(nums + " ");
            }
        }
    }

    public List<List<Integer>> largeGroupPositions(String S) {
        List<List<Integer>> result = new LinkedList<>();

        char group = S.charAt(0);
        int start = 0, end = 0;
        for(int i = 1, size = S.length(); i < size; i++) {
            if(group == S.charAt(i)) {
                end = i;
            } else {
                if(3 <= (end - start + 1)) {
                    result.add(Arrays.asList(start, end));
                }
                start = i;end = i;
                group = S.charAt(i);
            }
        }
        if(3 <= (end - start + 1)) { result.add(Arrays.asList(start, end)); }

        return result;
    }
}
