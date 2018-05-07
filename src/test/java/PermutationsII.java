import org.junit.Test;

import java.util.*;

/**
 Given a collection of numbers that might contain duplicates, return all possible unique permutations.

 Example:

 Input: [1,1,2]
 Output:
 [
 [1,1,2],
 [1,2,1],
 [2,1,1]
 ]
 * */
public class PermutationsII {

    @Test
    public void test() {
        int[][] testCases = {
                {1, 1, 3},
                {1,1,2,2},
        };

        for(int[] test : testCases) {
            permute(test).forEach(System.out::println);
            System.out.println();
        }
    }

    private Set<String> results;
    public List<List<Integer>> permute(int[] nums) {
        results = new HashSet<>();
        return doPermute(nums, 0, new LinkedList<>());
    }

    private List<List<Integer>> doPermute(int[] nums, int start, List<List<Integer>> result) {
        if(nums.length <= start) {
            if(results.add(Arrays.toString(nums))) {
                result.add(asList(nums));
            }
            return result;
        }

        int temp;
        for(int i = start; i < nums.length; i++) {
            temp = nums[start]; nums[start] = nums[i]; nums[i] = temp;
            doPermute(nums, start + 1, result);
            temp = nums[start]; nums[start] = nums[i]; nums[i] = temp;
        }

        return result;
    }

    public List<Integer> asList(int[] nums) {
        List<Integer> list = new ArrayList<>(nums.length);
        for (int num : nums) { list.add(num); }
        return list;
    }
}
