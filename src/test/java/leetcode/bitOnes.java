package leetcode;

import org.junit.Test;

/**
 * Created by Administrator on 1/7/2018.
 *
 */
public class bitOnes {

    @Test
    public void test() {
        int ones = 0, n = 15;
        System.out.println(Integer.bitCount(n));
        for(int i = 32, num = n;i> 0; i--) {
            if( (num & 1) == 1) { ones++; }
            num = num >>> 1;
        }
        System.out.println(ones);
        return;
    }
}
