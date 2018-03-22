package dsa.sortest;

import dsa.sort.Majority;
import org.junit.Test;

public class MajorityTest {

    @Test
    public void majorityTest() {
        Integer[] numbers = {4,5,4,1,4,8,7,4,4,7,4,3};
        Majority<Integer> majority = new Majority<>();
        System.out.println(majority.majority(numbers));
    }
}
