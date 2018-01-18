import org.junit.Test;

/**
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.
 *
 * Note:
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n2).
 * There is only one duplicate number in the array, but it could be repeated more than once.
 */
public class FindDuplicatesTest {

    @Test
    public void test() {
        int[][] testCases = {
                {1, 1, 1, 2, 3, 4},
                {}
        };

        for(int[] testCase : testCases) {
            System.out.println(findDuplicate(testCase));
        }
    }

    private int findDuplicate(int[] nums) {
        int result = 0;
        for(int i : nums) { result ^= i; }

        return result ;
    }
}
