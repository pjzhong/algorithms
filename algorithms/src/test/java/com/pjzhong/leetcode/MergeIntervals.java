package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * Given a collection of intervals, merge all overlapping intervals.
 * For example,
 * Given [1,3],[2,6],[8,10],[15,18],
 * return [1,6],[8,10],[15,18].
 *
 * https://leetcode.com/problems/merge-intervals/description/
 * */
public class MergeIntervals {

    @Test
    public void test() {
        List<List<Interval>>  testCases = new ArrayList<>();
        testCases.add(Arrays.asList(new Interval(15, 18), new Interval(8, 10), new Interval(2, 6), new Interval(1, 3)));
        testCases.add(Arrays.asList(new Interval(50, 100), new Interval(60, 65), new Interval(2, 6), new Interval(1, 3)));

        for(List<Interval> testCase : testCases) {
            List<Interval> result = merge(testCase);
            System.out.println(result);
        }
    }

    public List<Interval> merge(List<Interval> intervals) {
        if(intervals.isEmpty()) { return intervals; }
        Comparator<Interval> comparator = (i1, i2) -> i1.start - i2.start;
        Collections.sort(intervals, comparator);

        List<Interval> result = new ArrayList<>(intervals.size());

        Interval that = intervals.get(0);
        for(int i = 0, size = intervals.size(); i < size; i++) {
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
}

class Interval {
    int start;
    int end;
    Interval() { start = 0; end = 0; }
    Interval(int s, int e) { start = s; end = e; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(start);
        sb.append(", ").append(end);
        sb.append('}');
        return sb.toString();
    }
}
