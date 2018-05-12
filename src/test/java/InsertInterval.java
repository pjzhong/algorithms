import org.junit.Test;

import java.util.*;

/**
 Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

 You may assume that the intervals were initially sorted according to their start times.

 Example 1:

 Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 Output: [[1,5],[6,9]]
 Example 2:

 Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 Output: [[1,2],[3,10],[12,16]]
 Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].

 https://leetcode.com/problems/insert-interval/description/
 * */
public class InsertInterval {

    @Test
    public void test() {
        List<Interval> list = new ArrayList<>(Arrays.asList(new Interval(2, 6), new Interval(7, 9)));
        Interval interval = new Interval(15, 18);

        insert(list, interval);
    }

    /**
     * binarySearch solution, time complexity:O(logn + n).
     * You din't utilize  the ordered property
     * */
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        int index = binarySearch(intervals, newInterval);
        intervals.add(index + 1, newInterval);
        List<Interval> result = new ArrayList<>(intervals.size());
        Interval that = intervals.get(0);
        for(int i = 0; i < intervals.size(); i++) {
            Interval temp = intervals.get(i);
            if(temp.start <= that.end) {
                that.end = Math.max(temp.end, that.end);
            } else {
                result.add(that);
                that = temp;
            }
        }
        result.add(that);

        return result;
    }

    private int binarySearch(List<Interval> value, Interval key) {
        Comparator<Interval> comparator = (i1, i2) -> i1.start - i2.start;
        int lo = 0, hi = value.size();
        while (lo < hi) {
            int mid = (lo + hi) >>> 1, compareResult = comparator.compare(key, value.get(mid));
            if(compareResult < 0 ) {
                hi = mid;
            } else {
                lo = mid + 1;
            }//[lo, mi)或(mi, hi)
        }//出口时 A[lo = hi] 为大于e的最小元素
        return --lo;//lo - 1既不大于value的元素的最大index
    }
}
