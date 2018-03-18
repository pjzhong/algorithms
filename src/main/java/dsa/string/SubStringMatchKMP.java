package dsa.string;

public class SubStringMatchKMP {

    private String pattern;
    private int[] next;

    public SubStringMatchKMP(String pattern) {
        this.pattern = pattern;
        this.next = buildNext(pattern);
    }

    public int[] buildNext(String pattern) {
        int[] N = new int[pattern.length()];

        int t = N[0] = -1;
        for(int i = 0, size = pattern.length() - 1; i < size;){
            if( t < 0 || pattern.charAt(i) == pattern.charAt(t) ) {
                N[++i] = ++t;
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
