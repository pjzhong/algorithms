package leetcode.contest95;

import leetcode.ListNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *Given a non-empty, singly linked list with head node head, return a middle node of linked list.

 If there are two middle nodes, return the second middle node.



 Example 1:

 Input: [1,2,3,4,5]
 Output: Node 3 from this list (Serialization: [3,4,5])
 The returned node has value 3.  (The judge's serialization of this node is [3,4,5]).
 Note that we returned a ListNode object ans, such that:
 ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, and ans.next.next.next = NULL.
 Example 2:

 Input: [1,2,3,4,5,6]
 Output: Node 4 from this list (Serialization: [4,5,6])
 Since the list has two middle nodes with values 3 and 4, we return the second one.
 *
 * @link https://leetcode.com/problems/middle-of-the-linked-list/description/
 * */
public class MiddleOftheLinkedList {

    @Test
    public void test() {
        List<Integer> ints = Arrays.asList(1,2,3,5,6);
        int size = ints.size();
        int middle = Math.min(size - 1, size / 2 + (size % 2 == 0 ? 1 : 0) );
        System.out.println(ints.get(middle));
    }

    public ListNode middleNode(ListNode head) {
        if(head == null) { return  head;}
        List<ListNode> nodes= new ArrayList<>(100);

        for(ListNode node = head; node != null; node = node.next) {
            nodes.add(node);
        }

        int size = nodes.size();
        int middle = Math.min(size - 1, (size / 2));
        return nodes.get(middle);
    }
}
