package leetcode;

/**
 Merge two sorted linked lists and return it as a new list.
 The new list should be made by splicing together the nodes of the first two lists.

 Example:

 Input: 1->2->4, 1->3->4
 Output: 1->1->2->3->4->4

 https://leetcode.com/problems/merge-two-sorted-lists/description/
 * */
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class MergeTwoLists {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null || l2 == null) { return l1 == null ? l2 : l1;}
        ListNode sentinel = new ListNode(0);
        ListNode result = sentinel;
        while(l1 != null && l2 != null) {
            if(l1.val <= l2.val) {//尽量让出现机会更高的分支排前面，或者尽量写出这种条件。反例：去除 =
                sentinel.next = l1;
                l1 = l1.next;
            } else {
                sentinel.next = l2;
                l2 = l2.next;
            }
            sentinel = sentinel.next;
        }
        sentinel.next = l1 != null ? l1 : l2;

        return result.next;
    }
}
