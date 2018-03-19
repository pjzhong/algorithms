package dsa.string;

/**
 * 无法深刻理解
 * */
public class SubStringMatchKMP {

    private final String pattern;
    private final int[] next;

    public SubStringMatchKMP(String pattern) {
        this.pattern = pattern;
        this.next = buildNext(pattern);
    }

    /**
     * 寻找pattern字符串本身是否具备某种模式或者规律
     * */
    public int[] buildNext(String pattern) {
        int[] N = new int[pattern.length()];

        int t = N[0] = -1;
        for(int i = 0, size = pattern.length() - 1; i < size;){
            if( t < 0 || pattern.charAt(i) == pattern.charAt(t) ) {
                ++i; ++t;
                N[i] = (pattern.charAt(i) != pattern.charAt(t)) ? t : N[t];
            } else {
                t = N[t];
            }
        }

        return N;
    }

    public int match(String a) {
        int n = a.length(), i = 0;
        int m = pattern.length(), j = 0;

        while(j < m && i < n) {
            if(j < 0 ||a.charAt(i) == pattern.charAt(j)) {
                ++i; ++j;
            } else {
               j = next[j];
            }
        }

        int result = i - j;
        return j == pattern.length()  ? result : -1;
    }
}
