package dsa.string;

public final class String {

    public int length() {
        return 0;
    }

    public char charAt(int i) {
        return '1';
    }

    public String substr(int begin, int end) {
        return null;
    }

    public String prefix(int k) {
        return null;
    }

    public String suffix(int k) {
        return null;
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
    private int match(char[] a, char[] b) {
        int result = -1;
        for(int i = 0, j = 0, bLength = b.length, aLength = a.length - bLength + 1; i < aLength; i++) {
            for(j = 0; j < bLength; j++) {
                if(a[i + j] != b[j]) {
                    break;
                }
            }
            if(bLength <= j) { result = i; break; }
        }

        return result;
    }

    public int indexOf(String s) {
        return -1;
    }

    public String concat(String string) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
