import org.junit.Test;

import java.util.*;

/**
 Belong to Graph, Cycle detect


 There are a total of n courses you have to take, labeled from 0 to n-1.

 Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
 which is expressed as a pair: [0,1]

 Given the total number of courses and a list of prerequisite pairs,
 is it possible for you to finish all courses?

 Example 1:

 Input: 2, [[1,0]]
 Output: true
 Explanation: There are a total of 2 courses to take.
 To take course 1 you should have finished course 0. So it is possible.
 Example 2:

 Input: 2, [[1,0],[0,1]]
 Output: false
 Explanation: There are a total of 2 courses to take.
 To take course 1 you should have finished course 0, and to take course 0 you should
 also have finished course 1. So it is impossible.
 Note:

 The input prerequisites is a graph represented by a list of edges, not adjacency matrices.
 Read more about how a graph is represented.
 You may assume that there are no duplicate edges in the input prerequisites.
 Hints:

 This problem is equivalent to finding if a cycle exists in a directed graph. If a cycle exists,
 no topological ordering exists and therefore it will be impossible to take all courses.
 Topological Sort via DFS - A great video tutorial (21 minutes) on Coursera explaining
 the basic concepts of Topological Sort.
 Topological sort could also be done via BFS.

 https://leetcode.com/problems/course-schedule/description/
 * */
public class CourseSchedule {


    @Test
    public void test() {
        int[][][] testCases = {
                {},
        };
    }


    private boolean hasCycle = false;
    private Set<Integer> onStack;
    private Set<Integer> marked;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> adjenct = new HashMap<>();
        onStack = new HashSet<>();
        marked = new HashSet<>();

        List<Integer> list;
        for(int[] pre : prerequisites) {
            list = adjenct.get(pre[0]);
            if(list == null) { adjenct.put(pre[0], list = new ArrayList<>()); }
            list.add(pre[1]);
        }

        Set<Integer> keys = adjenct.keySet();
        for(int i : keys) {
            if(!marked.contains(i)) {
                dfs(adjenct, i);
            }
        }

        return !hasCycle;
    }

    private void dfs(Map<Integer, List<Integer>> adjenct, int from) {
        onStack.add(from); marked.add(from);
        if(adjenct.containsKey(from)) {
            for(int to : adjenct.get(from)) {
                if(hasCycle) { return;}
                else if(!marked.contains(to)) {
                    dfs(adjenct, to);
                } else if(onStack.contains(to)) { hasCycle = true; }
            }
        }
        onStack.remove(from);
    }
}
