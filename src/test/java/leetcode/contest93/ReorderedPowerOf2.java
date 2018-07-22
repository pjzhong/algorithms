package leetcode.contest93;

import org.junit.Test;

import java.util.Arrays;

public class ReorderedPowerOf2 {

    private String[] power = {"1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048", "4096", "8192", "16384", "32768", "65536", "131072", "262144", "524288", "1048576", "2097152", "4194304", "8388608", "16777216", "33554432", "67108864", "134217728", "268435456", "536870912",
    };

    public boolean reorderedPowerOf2(int N) {
        String n = Integer.toString(N);
        int[] count = count(n);

        boolean result = false;
        for(int i = 0; i < power.length && !result && power[i].length() <= n.length(); i++) {
            if(power[i].length() == n.length()) {
                result = true;
                int[] t = count(power[i]);
                for(int j = 0; j < 10; j++) {
                    if(count[j] != t[j]) {
                        result = false; break;
                    }
                }
            }
        }


        return result;
    }

    public int[] count(String n) {
        int[] count = new int[10];
        for(int i = 0; i < n.length(); i++) {
            count[n.charAt(i) - '0']++;
        }

        return count;
    }

    @Test
    public void test() {
        int[] testCases = {1, 10, 16, 24, 46};

        for(int i : testCases) {
            System.out.println(reorderedPowerOf2(i));
        }
    }
}
