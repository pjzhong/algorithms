package dsa.string;

public class SubStringMatchBMBC {

    private final String pattern;
    private final int[] BC; //bad character, 与字母表等长。 note:看下Algorithms-4-edition的KMP实现

    public SubStringMatchBMBC(String pattern) {
        this.pattern = pattern;
        this.BC = buildBC(pattern);
    }

    private int[] buildBC(String pattern) {
        int[] bc = new int[256];

        for(int i = 0, size = bc.length; i < size; i++) { bc[i] = -1; }

        //每一个字符在pattern中最后出现的位置
        for(int i = 0, size = pattern.length(); i < size; i++) {
            bc[pattern.charAt(i)] = i;
        }

        return bc;
    }

    public int match(String txt) {
        int size = txt.length(), patternLength = pattern.length() - 1;
        int skip;
        for(int i = 0; (i + patternLength) < size; i += skip) {
            skip = 0;
            for(int j = patternLength; 0 <= j; j--) {
                if(pattern.charAt(j) != txt.charAt(i + j)) {
                    skip = j - BC[txt.charAt(i + j)];
                    if(skip < 1) { skip = 1;}
                    break;
                }
            }

            if(skip <= 0) {
                return i;
            }
        }

        return -1;
    }
}
