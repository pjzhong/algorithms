package leetcode.failed;

import org.junit.Test;

/**
 Given two strings A and B of lowercase letters, return true if and only if we can swap two letters in A so that
 the result equals B.



 Example 1:

 Input: A = "ab", B = "ba"
 Output: true
 Example 2:

 Input: A = "ab", B = "ab"
 Output: false
 Example 3:

 Input: A = "aa", B = "aa"
 Output: true
 Example 4:

 Input: A = "aaaaaaabc", B = "aaaaaaacb"
 Output: true
 Example 5:

 Input: A = "", B = "aa"
 Output: false


 Note:

 0 <= A.length <= 20000
 0 <= B.length <= 20000
 A and B consist only of lowercase letters.

 https://leetcode.com/problems/buddy-strings/description/
 * */
public class BuddyStrings {

    @Test
    public void test() {
        String[][] testCases = {
                {"abc", "abc"},
                {"abcaaaaaaaaaaaaaaaa", "bcaaaaaaaaaaaaaaaaa"},
                {"abcabc", "abcabc"},
                {"abcd", "abcd"},
                {"ab", "ba"},
                {"ab", "ab"},
                {"aa", "aa"},
                {"aaaaaaabc", "aaaaaaacb"},
                {"aaaaaaaaaabbbbbbbbbbbbbbbbb", "aaaaaaaaaabbbbbbbbbbbbbbbbb"},
                {"abb", "bba"},
                {"abb", "bbc"},
                {"abcd", "acdb"},
                {"", "aa"},
                {"bbb", "bbb"},
                {"cde", "ced"},
                {"c", "c"},
                {"", ""},
        };

        for(String[] t : testCases) {
            System.out.format("%s:%s,%s\n",t[0], t[1], buddyStrings(t[0], t[1]));
        }
    }

    public boolean buddyStrings(String A, String B) {
        int[] a = new int[26], b = new int[26];
        char[] AC = A.toCharArray(), BC = B.toCharArray();

        boolean swapAble = false,//Is each character present twice or more in A?
                allPresent = false;//does A miss some chars, but B does not. vice vera
        int diff = 0;//How many swaps is need to make A equal to B?
        if(AC.length == BC.length) {
            for(int i = 0; i < AC.length; i++) {
                a[AC[i] - 'a']++;
                b[BC[i] - 'a']++;
                if(AC[i] != BC[i]) {  ++diff;}
            }
            allPresent = true;
            for(int i = 0; i < 26 && allPresent; i++) {
                if(a[i] != b[i]) {
                    allPresent = false;
                }
                if(2 <= a[i]) { swapAble = true;}
            }
        }
        return ( 2 == diff || (diff == 0 && swapAble)) && allPresent;
    }


    //Ugly
   /* public boolean buddyStrings(String A, String B) {
        if(2 <= A.length() && A.length() == B.length()) {
            Map<Character, Integer> aChars = new HashMap<>(), bChars = new HashMap<>();
            Set<Character> aDiff = new HashSet<>(), bDiff = new HashSet<>();
            char[] a = A.toCharArray(), b = B.toCharArray();
            for(int i = 0; i < a.length; ++i) {
                if(a[i] != b[i]) {
                    aDiff.add(a[i]);
                    bDiff.add(b[i]);
                }
                aChars.put(a[i], aChars.getOrDefault(a[i], 0) + 1);
                bChars.put(b[i], aChars.getOrDefault(b[i], 0) + 1);
            }
            if(aDiff.size() <= 2) {
                Set<Character> tDiff = new HashSet<>(aDiff);
                tDiff.removeAll(bDiff);
                if(tDiff.isEmpty()) {
                    if(aDiff.isEmpty()) {
                        for(Integer i : aChars.values()) {
                            if(2 <= i) {
                                return true;
                            }
                        }
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }*/
}
