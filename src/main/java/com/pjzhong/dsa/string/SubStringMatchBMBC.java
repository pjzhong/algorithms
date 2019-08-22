package com.pjzhong.dsa.string;

public class SubStringMatchBMBC {

  private final String pattern;
  private final int[] BadCharacters; //bad character, 与字母表等长。 note:看下Algorithms-4-edition的KMP实现
  private final int[] goodSuffix;

  public SubStringMatchBMBC(String pattern) {
    this.pattern = pattern;
    this.BadCharacters = buildBC(pattern);
    this.goodSuffix = buildGoodSuffix(pattern);
  }

  private int[] buildBC(String pattern) {
    int[] bc = new int[256];

    for (int i = 0, size = bc.length; i < size; i++) {
      bc[i] = -1;
    }

    //每一个字符在pattern中最后出现的位置
    for (int i = 0, size = pattern.length(); i < size; i++) {
      bc[pattern.charAt(i)] = i;
    }

    return bc;
  }

  //还有好后缀策略，目前理解不了...........是缺了什么吗。 2018-3-21
  private int[] buildSuffixLengthArray(String pattern) {
    int[] suffixLength = new int[pattern.length()];
    suffixLength[pattern.length() - 1] = pattern.length();//对最后一个字符而言，与之匹配的最长后缀就是整个字符串

    int length = pattern.length() - 1;
    //以下，从倒数第二个字符起自由向左扫描p, 依次计算出suffixLength[]各项值
    for (int lo = length, hi = length, index = lo - 1; 0 <= index; --index) {
      if ((lo < index) && (suffixLength[length - hi + index] <= index - lo)) {
        suffixLength[index] = suffixLength[length - hi + index];
      } else {
        hi = index;
        lo = Math.min(lo, hi);
        while ((0 <= lo) && (pattern.charAt(lo) == pattern.charAt(length - hi + lo))) {
          --lo;
        }
        suffixLength[index] = hi - lo;
      }
    }

    return suffixLength;
  }

  private int[] buildGoodSuffix(String pattern) {
    int m = pattern.length();
    int[] suffixLength = buildSuffixLengthArray(pattern);
    int[] goodSuffix = new int[m];
    for (int i = 0; i < m; i++) {
      goodSuffix[i] = m;
    }

    for (int i = 0, j = m - 1; 0 <= j; --j) {
      if (j + 1 == suffixLength[j]) {
        int shift = m - j - 1;
        while (i < shift) {
          goodSuffix[i++] = shift;
        }
      }
    }

    for (int j = 0, size = m - 1; j < size; j++) {
      goodSuffix[m - suffixLength[j] - 1] = m - j - 1;
    }

    return goodSuffix;
  }

  public int match(String txt) {
    int size = txt.length(), patternLength = pattern.length() - 1;
    int skip;
    for (int i = 0; (i + patternLength) < size; i += skip) {
      skip = 0;
      for (int j = patternLength; 0 <= j; j--) {
        if (pattern.charAt(j) != txt.charAt(i + j)) {
          skip = Math.max(goodSuffix[j], j - BadCharacters[txt.charAt(i + j)]);
          break;
        }
      }

      if (skip <= 0) {
        return i;
      }
    }

    return -1;
  }
}
