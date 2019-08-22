package com.pjzhong.dsa.string;

public final class SubStringMatchBruteForce {

    private String pattern;

    public SubStringMatchBruteForce(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 复杂度
     *     最好情况(只经过一轮比较，即可确定匹配)：O(b.length)
     *
     *     最坏情况(每轮都比对至b的末字符， 且每轮如此)
     *          每轮循环：比对 = b.length - 1(成功) + 1(失败) = bLength
     *          循环次数 = a.length - b.length = 1
     *          故 0(a.length * b.length)
     *
     *          最坏情况参考:
     *              000000000000000.....001
     *              000001
     *
     *      字符表越小， 最坏情况出现的概率越高
     *      b.length越大， 最坏情况的后果更严重
     * */
    public int match(String a) {
        int n = a.length(), i = 0;
        int m = pattern.length(), j = 0;

        while(j < m && i < n) {
            if(a.charAt(i) == pattern.charAt(j)) {
                ++i; ++j;
            } else {
                i = i - (j - 1); j = 0;
            }
        }

        int result = i - j;
        return j == pattern.length()  ? result : -1;
    }
}
