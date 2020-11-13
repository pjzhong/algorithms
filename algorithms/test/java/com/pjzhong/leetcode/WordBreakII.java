package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add
 * spaces in s to construct a sentence where each word is a valid dictionary word. Return all such
 * possible sentences.
 *
 * Note:
 *
 * The same word in the dictionary may be reused multiple times in the segmentation. You may assume
 * the dictionary does not contain duplicate words. Example 1:
 *
 * <p>Input: s = "catsanddog"</p>
 * <p>wordDict = ["cat", "cats", "and", "sand", "dog"]</p>
 * <p>Output: [ "cats and dog", "cat sand dog" ]</p>
 *
 * @author ZJP
 * @link https://leetcode.com/problems/word-break-ii/
 * @since 2020年06月21日 22:00:19
 **/
public class WordBreakII {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void test() {

    String[] worlds = {
        "catsanddog",
        "pineapplepenapple",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    };
    String[][] disc = {
        {"cat", "cats", "and", "sand", "dog"},
        {"apple", "pen", "applepen", "pine", "pineapple"},
        {"aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa",
            "aaaaaaaaaa"}
    };

    for (int i = 0; i < worlds.length; i++) {
      String s = worlds[i];
      try {
        List<String> result = wordBreak(s, Arrays.asList(disc[i]));
        result.forEach(logger::info);
      } catch (Exception e) {
        logger.error(s, e);
      }


    }
  }

  /**
   * 将s分解为n个wordDict所包含的字符串, wordDict的字符串可以复用 (backtrack原路返回一个实际应用。这从尾部开始追踪。之前思考都是从头部开始的)
   * 以后可以观察下递归的分支(从小的例子开始，哪些分支是重复，缓存起来可以加快计算)
   *
   * @return true:s能被完全分解, false:s不能被完全分解
   * @link 非常好的解答 https://leetcode.com/problems/word-break-ii/discuss/44167/My-concise-JAVA-solution-based-on-memorized-DFS
   * @since 2020年06月20日 17:12:16
   */
  public List<String> wordBreak(String s, List<String> wordDict) {
    return wordBreak(0, s, wordDict, new StringBuilder(), new HashMap<>());
  }

  // The Best Practice
  public List<String> wordBreak(int idx, String s, List<String> wordDict, StringBuilder buildCache,
      Map<Integer, List<String>> result) {
    List<String> res = result.get(idx);
    if (res != null) {
      return res;
    }

    if (s.length() <= idx) {
      return Collections.singletonList("");
    }

    res = new ArrayList<>();
    for (String word : wordDict) {
      if (s.startsWith(word, idx)) {
        List<String> temp = wordBreak(idx + word.length(), s, wordDict, buildCache, result);
        buildCache.setLength(0);
        buildCache.append(word);
        int length = word.length();
        for (String ts : temp) {
          res.add(
              buildCache
                  .append((ts.isEmpty() ? "" : ' '))
                  .append(ts)
                  .toString()
          );
          buildCache.setLength(length);
        }
      }
    }

    result.put(idx, res);
    return res;
  }
}
