package com.chenluo.algo;

public class LC328 {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode e2 = new ListNode(2);
        ListNode e3 = new ListNode(3);
        ListNode e4 = new ListNode(4);
        ListNode e5 = new ListNode(5);
        e4.next = e5;
        e3.next = e4;
        e2.next = e3;
        head.next = e2;
        ListNode result = new LC328().oddEvenList(head);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode evenHead = new ListNode();
        ListNode tmpEvenHead = evenHead;
        ListNode tmpHead = head;
        ListNode prevHead = null;
        int i = 1;
        while (tmpHead != null) {
            if (i % 2 == 0) {
                tmpEvenHead.next = tmpHead;
                tmpEvenHead = tmpHead;

                prevHead.next = tmpHead.next;
                tmpHead = tmpHead.next;
                tmpEvenHead.next = null;
            } else {
                prevHead = tmpHead;
                tmpHead = tmpHead.next;
            }
            i++;
        }
        prevHead.next = evenHead.next;
        return head;
    }
}
