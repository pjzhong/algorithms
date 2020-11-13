package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * Note: Do not modify the linked list.
 *
 * Follow up: Can you solve it without using extra space?
 */
public class LinkedListCycleII {

  public List<ListNode> buildTests() {
    List<ListNode> testCases = new ArrayList<>();
    ListNode Sentinel = new ListNode(-1);

    ListNode t1 = Sentinel;
    for (int i = 1; i < 9; i++) {
      t1.next = new ListNode(i);
      t1 = t1.next;
    }
    t1.next = Sentinel.next.next.next;
    testCases.add(Sentinel.next);
    Sentinel.next = null;

    return testCases;
  }

  @Test
  public void test() {
    for (ListNode t : buildTests()) {
      ListNode begin = detectCycle(t);
      System.out.println(begin != null ? begin.val : null);
    }
  }


  public ListNode detectCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (fast == slow) {
        fast = head;
        while (fast != slow) {
          fast = fast.next;
          slow = slow.next;
        }
        return slow;
      }
    }

    return null;
  }
}
