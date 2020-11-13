package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * The following is a description of the instance of this famous puzzle
 * involving n=2 eggs and a building with k=36 floors.
 *
 * Suppose that we wish to know which stories in a 36-story building are safe to drop eggs from, and which will cause
 * the eggs to break on landing. We make a few assumptions:
 *
 * …..An egg that survives a fall can be used again.
 * …..A broken egg must be discarded.
 * …..The effect of a fall is the same for all eggs.
 * …..If an egg breaks when dropped, then it would break if dropped from a higher floor.
 * …..If an egg survives a fall then it would survive a shorter fall.
 * …..It is not ruled out that the first-floor windows break eggs, nor is it ruled out that the 36th-floor
 do not cause an egg to break.

 * If only one egg is available and we wish to be sure of obtaining the right result,the experiment can be carried out
 * in only one way. Drop the egg from the first-floor window; if it survives, drop it from the second floor window.
 * Continue upward until it breaks. In the worst case, this method may require 36 droppings. Suppose 2 eggs are available.
 * What is the least number of egg-droppings that is guaranteed to work in all cases?The problem is not actually to find
 * the critical floor, but merely to decide floors from which eggs should be dropped
 * so that total number of trials are minimized.

 * for more info see the link below
 * https://www.geeksforgeeks.org/dynamic-programming-set-11-egg-dropping-puzzle/
 *
 * calm down, the key to find the solution to this problem is figure out the trivial case. But you were shock by this
 * description  and  lost your mind, so that is why you would failed.
 * */
public class EggDroppingPuzzle {

    @Test
    public void test() {
        int[][] testCases = {
                {2, 10},
                {1, 10},
        };

        for(int[] test : testCases) {
            System.out.format("recursive:%d\n", eggDropRecursive(test[0], test[1]));
            System.out.format("dp:%d\n", eggDropDP(test[0], test[1]));
        }
    }


    /**
     * The trivial cases or boundary conditions
     * example-1: Given 1 egg and 10 floors, what is min attempt?(worst case)
     * Answer-1:10
     *
     * example-2: given 10 eggs and 1 floor, what is min attempt?(worst case)
     * Answer-2: 1
     * */
    private int eggDropRecursive(int eggs, int floors) {
        if(floors <= 1) { return floors; }
        if(eggs <= 1) { return floors;}

        int min = Integer.MAX_VALUE, result;
        for(int i = 1; i <= floors; ++i) {
            result = Math.max(eggDropRecursive(eggs - 1, i -1), eggDropRecursive(eggs, floors - i));
            if(result < min) {
                min = result;
            }
        }

        return min + 1;
    }

    /**
     * I can't solved this question by myself.
     * Shame!!!!!!!!!
     * */
    private int eggDropDP(int eggs, int floors) {
        int[][] dp = new int[eggs + 1][floors + 1];

        for(int i = 1; i <= floors; ++i) { dp[1][i] = i;}
        for(int i = 1; i <= eggs; ++i) { dp[i][1] = 1;}

        int result = 0;
        for(int e = 2; e <= eggs; ++e) {
            for(int f = 2; f <= floors; ++f) {

                //The logic just a little different to the  recursive version
                dp[e][f] = Integer.MAX_VALUE;
                for(int x = 1; x <= f; ++x) {
                    result = 1 + Math.max(dp[e - 1][x - 1], dp[e][f - x]);
                    if(result < dp[e][f]) {
                        dp[e][f] = result;
                    }
                }

            }
        }

        return dp[eggs][floors];
    }
}
