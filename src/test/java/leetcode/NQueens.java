package leetcode;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/n-queens/description/
 * */
public class NQueens {

    @Test
    public void test() {
        int[] testCases = {4, 5};

        for(int i : testCases) {
            solveNQueens(i).forEach(l -> {
                l.forEach(System.out::println);
                System.out.println();
            });
        }
    }

    private char[] builder;
    public List<List<String>> solveNQueens(int n) {
        Point[] queens = new Point[n];
        builder = new char[n];
        for(int i = 0; i < n; i++) { builder[i] = '.';}
        return place(0, queens, new LinkedList<>());
    }

    private List<List<String>> place(int row, Point[] queens, List<List<String>> result) {
        if(row == queens.length) {
            result.add(doPlace(queens));
        } else {
            boolean place = true;
            if(queens[row] == null) { queens[row] = new Point();}
            for(int col = 0; col < queens.length; col++) {
                place = true;
                for(int q = 0; q < row && place; q++) {
                    if(queens[q].y == col || Math.abs(row - queens[q].x) == Math.abs(col - queens[q].y)) {
                        place = false;
                    }
                }
                if(place) {
                    queens[row].x = row;queens[row].y = col;
                    place(row + 1, queens, result);
                    queens[row].x = queens[row].y = -1;
                }
            }
        }
        return result;
    }

    private List<String> doPlace(Point[] queens) {
        List<String> list = new ArrayList<>(queens.length);
        for(Point q : queens) {
            builder[q.y] = 'Q';
            list.add(String.valueOf(builder));
            builder[q.y] = '.';
        }

        return list;
    }
}
