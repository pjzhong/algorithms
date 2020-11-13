package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a linked list and a value x, partition it such that all nodes less than x come before nodes
 * greater than or equal to x.
 *
 * You should preserve the original relative order of the nodes in each of the two partitions.
 *
 * Example:
 *
 * <p>Input: head = 1->4->3->2->5->2, x = 3</p>
 * <p>Output: 1->2->2->4->3->5</p>
 *
 * @author ZJP
 * @link https://leetcode.com/problems/partition-list/
 * @since 2020年04月09日 20:07:43
 **/
public class PartitionList {

  public ListNode partition(ListNode head, int x) {

    //Use dummy head replace ArrayList
    List<Integer> before = new ArrayList<>(), after = new ArrayList<>();
    ListNode t = head;
    while (t != null) {
      if (t.val < x) {
        before.add(t.val);
      } else {
        after.add(t.val);
      }
      t = t.next;
    }

    before.addAll(after);
    ListNode res = null;
    if (0 < before.size()) {
      res = new ListNode(before.get(0));
      ListNode temp = res;
      for (int i = 1, size = before.size(); i < size; i++) {
        temp.next = new ListNode(before.get(i));
        temp = temp.next;
      }
    }

    return res;
  }
}
