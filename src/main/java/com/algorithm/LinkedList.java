package com.algorithm;

public class LinkedList {
    public static ListNode ReverseList(ListNode head){
        ListNode newHead = null;
        while (head != null){
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }
        return newHead;
    }

    /**
     *
     * @param head ListNode类 头结点
     * @param m int整型 起始位置
     * @param n int整型 结束位置
     * @return ListNode类
     */
    public static ListNode reverseBetween (ListNode head, int m, int n) {
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        //1. 保存m-1结点
        ListNode leftNode = dummyNode;
        for(int i=0; i<m-1; i++){
            leftNode = leftNode.next;
        }
        //2. 保存n+1结点
        ListNode p = leftNode;
        for(int i=0; i<n-m+1; i++){
            p = p.next;
        }
        ListNode rightNode = p.next;
        //3. 切断sub链表
        p.next = null;

        //4. sub链表头结点 然后reverse
        ListNode subHead = leftNode.next;
        subHead = ReverseList(subHead);//reverse
        //5. 拼接
        leftNode.next = subHead;
        ListNode p1 = subHead;
        while (p1.next != null){
            p1 = p1.next;
        }
        p1.next = rightNode;

        return dummyNode.next;
    }

    public static void main(String[] args) {
/*        ListNode head = new ListNode(1);
        ListNode node = head;
        for (int i=2; i<=5; i++){
            node.next = new ListNode(i);
            node = node.next;
        }*/
        ListNode head = new ListNode(3);
        head.next = new ListNode(5);

        head = reverseBetween(head, 1, 2);
        print(head);
    }

    private static void print(ListNode head){
        ListNode node = head;
        while (node != null){
            System.out.println(node.val);
            node = node.next;
        }
    }
}


class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}