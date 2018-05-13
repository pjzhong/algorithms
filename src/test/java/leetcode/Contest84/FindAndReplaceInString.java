package leetcode.Contest84;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 Find And Replace in String
 User Accepted: 15
 User Tried: 29
 Total Accepted: 15
 Total Submissions: 33
 Difficulty: Medium
 To some string S, we will perform some replacement operations that replace groups of letters with new ones
 (not necessarily the same size).

 Each replacement operation has 3 parameters: a starting index i, a source word x and a target word y.
 The rule is that if x starts at position i in the original string S, then we will replace that occurrence of x with y.
 If not, we do nothing.

 For example, if we have S = "abcd" and we have some replacement operation i = 2, x = "cd", y = "ffff",
 then because "cd" starts at position 2 in the original string S, we will replace it with "ffff".

 Using another example on S = "abcd", if we have both the replacement operation i = 0, x = "ab", y = "eee",
 as well as another replacement operation i = 2, x = "ec", y = "ffff", this second operation does nothing because
 in the original string S[2] = 'c', which doesn't match x[0] = 'e'.

 All these operations occur simultaneously.  It's guaranteed that there won't be any overlap in replacement:
 for example, S = "abc", indexes = [0, 1], sources = ["ab","bc"] is not a valid test case.

 Example 1:

 Input: S = "abcd", indexes = [0,2], sources = ["a","cd"], targets = ["eee","ffff"]
 Output: "eeebffff"
 Explanation: "a" starts at index 0 in S, so it's replaced by "eee".
 "ed" starts at index 2 in S, so it's replaced by "ffff".
 Example 2:

 Input: S = "abcd", indexes = [0,2], sources = ["ab","ec"], targets = ["eee","ffff"]
 Output: "eeecd"
 Explanation: "a" starts at index 0 in S, so it's replaced by "eee".
 "ec" doesn't starts at index 2 in the original S, so we do nothing.
 Notes:

 0 <= indexes.length = sources.length = targets.length <= 100
 0 < indexes[i] < S.length <= 1000
 All characters in given inputs are lowercase letters.

 https://leetcode.com/problems/find-and-replace-in-string/description/
 * */
public class FindAndReplaceInString {

    @Test
    public void test() {
        String[] S = {
                "wreorttvosuidhrxvmvo",
                "orrzhblogiyltpmuvbpgoooiebjegtkqkouurhsejcmwmghxyi",
                "abcd",
                "abcd",
                "abcd"};
        int[][] index = {
                {14,12,10,5,0,18},
                {25,48,43,15,29,5,12,0,19,35,17,8,45,21,40,32},
                {0, 2},
                {0, 2},
                {0, 2}};
        String[][] soruce = {
                {"rxv","dh","ui","ttv","wreor","vo"},
                {"bje","yi","wm","uv","tk","bl","tpm","orrz","go","ur","bp","giy","gh","ooie","jcm","ko"},
                {"a", "cd"},
                {"ab", "ec"},
                {"a", "cd"}};
        String[][] target = {
                {"frs","c","ql","qpir","gwbeve","n"},
                {"fnb","r","j","eqq","t","b","bra","fhbdm","un","iw","wfr","okz","p","bgk","tu","d"},
                {"eee", "ffff"},
                {"e", "ffff"},
                {"", "f"}};

        for(int i = 0; i < S.length; i++) {
            System.out.println(findReplaceString(S[i], index[i], soruce[i], target[i]));
        }
    }

    public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        StringBuilder builder = new StringBuilder(S);
        int SIZE = indexes.length;
        List<Replace> re = new ArrayList<>(SIZE);
        for(int i = 0 ; i < SIZE; i++) {
            if(S.indexOf(sources[i], indexes[i]) == indexes[i]) {
                re.add(new Replace(indexes[i], sources[i], targets[i]));
            }
        }

        Collections.sort(re);
        int offset = 0, start;
        for(Replace r : re) {
            start = r.index + offset;
            builder.replace(start, start + r.soruce.length(), r.target);
            offset = builder.length() - S.length();
        }

        return builder.toString();
    }

    private class Replace implements Comparable<Replace> {
        int index;
        String soruce;
        String target;

        public Replace(int index, String soruce, String target) {
            this.index = index;
            this.soruce = soruce;
            this.target = target;
        }

        @Override
        public int compareTo(Replace o) {
            return this.index - o.index;
        }
    }
}
