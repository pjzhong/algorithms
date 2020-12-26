package com.pjzhong.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 *
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 *
 *
 * Example 1:
 *
 * <p>Input: lists = [[1,4,5],[1,3,4],[2,6]]</p>
 * <p>Output: [1,1,2,3,4,4,5,6]</p>
 * <p>Explanation: The linked-lists are:</p>
 * <p>[
 * <p>  1->4->5,</p>
 * <p>  1->3->4,</p>
 * <p>  2->6</p>
 * <p>]</p>
 * <p>merging them into one sorted list:</p>
 * <p>1->1->2->3->4->4->5->6</p>
 *
 *
 * Example 2:
 *
 * <p>Input: lists = []</p>
 * <p>Output: []</p>
 * <p>Example 3:</p>
 *
 * <p>Input: lists = [[]]</p>
 * <p>Output: []</p>
 *
 *
 * Constraints:
 *
 * <p>k == lists.length</p>
 * <p>0 <= k <= 10^4</p>
 * <p>0 <= lists[i].length <= 500</p>
 * <p>-10^4 <= lists[i][j] <= 10^4</p>
 * <p>lists[i] is sorted in ascending order.</p>
 * <p>The sum of lists[i].length won't exceed 10^4.</p>
 *
 * @since 2020年12月26日 21:01:29
 **/
public class MergekSortedLists {

  public ListNode mergeKLists(ListNode[] lists) {
    if (lists.length <= 0) {
      return null;
    }

    Queue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(l -> l.val));
    for (ListNode n : lists) {
      if (n != null) {
        queue.add(n);
      }
    }

    ListNode head = null, point = null;
    while (!queue.isEmpty()) {
      ListNode cur = queue.poll();

      if (head == null) {
        head = point = cur;
      } else {
        point = point.next = cur;
      }

      if (cur.next != null) {
        queue.add(cur.next);
      }
    }

    return head;
  }

}
