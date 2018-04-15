
import org.junit.Assert;
import org.junit.Test;

/**
 Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)

 You have the following 3 operations permitted on a word:

 a) Insert a character
 b) Delete a character
 c) Replace a character

 https://leetcode.com/problems/edit-distance/description/

 if you want to know more about the solution, check the link(s) below
 https://www.geeksforgeeks.org/dynamic-programming-set-5-edit-distance/
 * */
public class EditDistance {

    private int min = Integer.MAX_VALUE;

    @Test
    public void test() {
        String[][] testCases = {
                {"horse", "ros"},
                {"sunday", "saturday"},
                {"cat", "cut"},
                {"ab", "a"},
                {"", "b"},
                {"b", ""},
                {"abc", "abeabc"},
                {"geek", "gesek"},
                {"pneumonoultramicroscopicsilicovolcanoconiosis", "ultramicroscopicsilicovolcanoconiosis"},
              //  {"pneumonoultramicroscopicsilicovolcanoconiosis", "ultramicroscopically"}// worst  case
        };

        for(String[] test : testCases) {
           minDistance(test[0], test[1]);
        }
    }

    public void minDistance(String str1, String str2) {
        Assert.assertEquals(my(str1, str2), editDist(str1, str2, str1.length(), str2.length()), editDistDp(str1, str2));
    }

    /**
     * ugly code....... my code
     *
     * failed to solve this problem by myself,
     * */
    private int my(String str1, String str2) {
        min = Integer.MAX_VALUE;
        StringBuilder builder = new StringBuilder(str2.length()).append(str1);
        edit(builder, str2, 0, 0);
        return min;
    }

    private void edit(StringBuilder builder, String target, int index, int count) {
        if(min < count) {
            return;
        } else if(isEquals(builder, target)) {
            min = Math.min(count, min);
            return;
        }

        if(index < builder.length() && index < target.length()) {
            if(builder.charAt(index) == target.charAt(index)) {
                edit(builder, target, index + 1, count);
            } else {
                char tmp = builder.charAt(index);

                //替换
                builder.setCharAt(index, target.charAt(index));
                edit(builder, target, index + 1, count + 1);
                builder.setCharAt(index, tmp);

                //删除
                builder.deleteCharAt(index);
                edit(builder, target, index, count + 1);
                builder.insert(index, tmp);

                //插入
                builder.insert(index, target.charAt(index));
                edit(builder, target, index + 1, count + 1);
                builder.deleteCharAt(index);
            }
        } else {
            if(builder.length() < target.length()) {
                builder.append(target.charAt(index));
                edit(builder, target, index + 1, count + 1);
                builder.deleteCharAt(builder.length() - 1);
            } else {
                char temp = builder.charAt(index);
                builder.deleteCharAt(index);
                edit(builder, target, index, count + 1);
                builder.insert(index, temp);
            }
        }
    }

    private boolean isEquals(StringBuilder builder, String target) {
        if(builder.length() == target.length()) {
            for(int i = 0, size = builder.length(); i < size; i++) {
                if(builder.charAt(i) != target.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    //ugly code....... my code -- end

    //concise, but fast enough
    private int editDist(String str1, String str2, int m, int n) {
        if(m == 0) { return n;}
        if(n == 0) { return m;}

        if(str1.charAt(m - 1) == str2.charAt(n - 1)) {
            return editDist(str1, str2, m - 1, n - 1);
        }

        // One of the keys to solved this problem, untouched the two Strings to find the answer.
        //try to draw a graph for these three operations, if you can't understand
        return 1 + Math.min(
                Math.min(editDist(str1, str2, m, n - 1), //Insert
                        editDist(str1, str2, m - 1, n)),//remove
                 editDist(str1,str2, m - 1, n - 1)//Replace
        );
    }

    //find in bottom up manner
    private int editDistDp(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for(int m = 0, mSize = str1.length(); m <= mSize; ++m) {
            for(int n = 0, nSize = str2.length(); n <= nSize; ++n) {
                if(m == 0) { dp[m][n] = n;}
                else if(n == 0) { dp[m][n] = m;}
                else if(str1.charAt(m - 1) == str2.charAt(n - 1)) {
                    dp[m][n] = dp[m - 1][n - 1];
                } else {
                    dp[m][n] = 1 + Math.min(
                              Math.min(
                                      dp[m][n - 1], //Insert
                                      dp[m - 1][n] //Remove
                              ),
                                      dp[m - 1][n - 1]//Replace
                            );
                }

            }
        }

        return dp[str1.length()][str2.length()];
    }
}
