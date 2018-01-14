import org.junit.Test;

/**
 * Created by Administrator on 1/14/2018.
 */
public class ClimbWays {

    @Test
    public void getClimbingWay() {
        int target = 10;

        System.out.println(F(target));
        System.out.println(FII(target));
        System.out.println(JumpFloorII(target));
    }

    public int F(int target) {
        int F[] = new int[target + 1];
        F[0] = 1;
        F[1] = 1;
        for(int i = 2; i <= target; i++) {
            F[i] = F[i - 2] + F[i - 1];
        }
        return F[target];
    }


    public int FII(int target) {
        int result[] = new int[target + 1];
        result[0] = result[1] = 1;

        for(int i = 2; i <= target; i++) {
            for(int j = i; j >= 0; j--) {
                result[i] += result[j];
            }
        }

        return result[target];
    }

    public int JumpFloorII(int target) {
        int a[] = new int[target + 1];
        a[0] = 1;
        a[1] = 1;
        for(int i = 2; i <= target; i++) {
            a[i] = 0;
            for(int j = i; j>= 0; j--) {
                a[i] += a[j];
            }
        }

        return a[target];
    }
}
