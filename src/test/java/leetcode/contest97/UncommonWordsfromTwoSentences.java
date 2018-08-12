package leetcode.contest97;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * We are given two sentences A and B.  (A sentence is a string of space separated words.  Each word consists only of lowercase letters.)
 * <p>
 * A word is uncommon if it appears exactly once in one of the sentences, and does not appear in the other sentence.
 * <p>
 * Return a list of all uncommon words.
 * <p>
 * You may return the list in any order.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: A = "this apple is sweet", B = "this apple is sour"
 * Output: ["sweet","sour"]
 * Example 2:
 * <p>
 * Input: A = "apple apple", B = "banana"
 * Output: ["banana"]
 * <p>
 * <p>
 * Note:
 * <p>
 * 0 <= A.length <= 200
 * 0 <= B.length <= 200
 * A and B both contain only spaces and lowercase letters.
 *
 * @link https://leetcode.com/contest/weekly-contest-97/problems/uncommon-words-from-two-sentences/
 */
public class UncommonWordsfromTwoSentences {

    public String[] uncommonFromSentences(String A, String B) {
        String[] a = A.split(" "), b = B.split(" ");
        Map<String, Integer> map = new HashMap<>();
        for (String s : a) {
            int count = map.getOrDefault(s, 0);
            map.put(s, count + 1);
        }
        for (String s : b) {
            int count = map.getOrDefault(s, 0);
            map.put(s, count + 1);
        }

        List<String> result = new ArrayList<>();
        map.forEach((k, v) -> {
            if (v == 1) {
                result.add(k);
            }
        });

        return result.toArray(new String[result.size()]);
    }
}
