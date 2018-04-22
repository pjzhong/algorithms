import org.junit.Test;

import java.util.*;

/**
 Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that
 can be built with those letters.

 This is case sensitive, for example "Aa" is not considered a palindrome here.

 Note:
 Assume the length of given string will not exceed 1,010.

 Example:

 Input:
 "abccccdd"

 Output:
 7

 Explanation:
 One longest palindrome that can be built is "dccaccd", whose length is 7.

 * */
public class LongestPalindrome {

    @Test
    public void test() {
        String[] testCases = {
                "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth",
                "civilwartesting",
                "abccccdd",
                "aaabb",
                "aaccddd",
                "",
                "a",
                "aa",
                "aaabbb",
                "aaabbbc",
        };

        for(String test : testCases) {
            System.out.println(longestPalindrome(test));
        }
    }

    /**
     * My solution.
     * What can I say? ugly, hard to understand
     * */
    private int longestPalindrome(String s) {
        int result = 0;
        Map<Character, Integer> count = new HashMap<>(s.length());
        char c;
        for(int i = 0, tmp, size = s.length(); i < size; i++) {
             c = s.charAt(i);
            if(count.containsKey(c)) {
                tmp = count.get(c);
                result += tmp <= 1 ? 2 : 1;
                count.put(c, ++tmp);
            } else {
               count.put(c, 1);
           }
        }

        int oddsCount = 0;
        for(Integer i : count.values()) {
            if(1 < i && !isEven(i)) {
                oddsCount++;
            }
        }

        if(2 <= oddsCount) {
            result -= isEven(oddsCount) ? oddsCount - 1 :  oddsCount;
        }

        if(result < s.length() && isEven(result)) {
            result++;
        }

        return result;
    }

    private boolean isEven(int i) {
        return (i & 1) == 0;
    }
}
