import org.junit.Test;

/**
 * Created by Administrator on 2018/1/8.
 * 辗转相除法
 */
public class EuclideanAlgorithm {

    @Test
    public void getGreatestCommonDivisor() {
        long a = 444444444444444L;
        long b = 111111111111111L;
        long start = System.nanoTime();
        System.out.println( a > b ? gcdByMod(a, b) : gcdByMod(b, a));
        System.out.println("end:" + (System.nanoTime() - start));
       /* System.out.println( a > b ? gcdBySubtract(a, b) : gcdBySubtract(b, a));*/
        long start2 = System.nanoTime();
        System.out.println(gcd(a, b));
        System.out.println("end:" + (System.nanoTime() - start2));
    }

    //两个正整数a和b（a>b），
    //它们的最大公约数等于a除以b的余数c和b之间的最大公约数
    private long gcdByMod(long a, long b) {
        if(a % b == 0) {
            return b;
        } else {
            return gcdByMod(b, a % b);
        }
    }

    private int gcdBySubtract(int a, int b) {
        if(a == b) {
            return a;
        }
        return  a < b ? gcdBySubtract(b - a, a) : gcdBySubtract(a - b, b);
    }

    private long gcd(long a, long b) {

        if(a == b) {
            return a;
        } else {
            if(a < b) {
                b = a ^ b;
                a = b ^ a;
                b = b ^ a;
            }
            boolean aIsEven = (a&1) == 0, bIsEven = (b&1) == 0;
            if(aIsEven && bIsEven) {
                return gcd(a >> 1, b >> 1) << 1;
            } else if(aIsEven) {
                return gcd(a >> 1, b);
            } else if(bIsEven) {
                return gcd(a, b >> 1);
            } else {
                return gcd(b, a  - b);
            }
        }
    }
}
