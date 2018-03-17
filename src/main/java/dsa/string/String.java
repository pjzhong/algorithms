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

    private int match(char[] a, char[] b) {
        int aLength = a.length, bLength = b.length;
        int i = 0, j = 0;

        while(i < aLength && j < bLength) {
            if(a[i] == b[j]) {
                ++i; ++j;
            } else {
                i = i - (j - 1);
                j = 0;
            }
        }

        return i - j;
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
