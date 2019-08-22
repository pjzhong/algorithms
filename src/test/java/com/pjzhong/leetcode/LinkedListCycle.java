package com.pjzhong.leetcode;

/**
 Given a linked list, determine if it has a cycle in it.

 Follow up:
 Can you solve it without using extra space?
 **/
public class LinkedListCycle {

    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(fast == slow) {
                return true;
            }
        }

        return false;
    }
}
