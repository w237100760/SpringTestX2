package com.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    /*
    inputString: k1=v1;k2=v2;k3=v3;k4=v41,v42
    输出的Map有四个kv键值对（->之前为key，之后为value）
    outputString: Map<k1->v1, k2->v2, k3->v3, k4->v41,v42>
    */
    public static int lastIndex2 = 0;

    public static Map<String, String> extract(String inputString) {
        Matcher matcherKey = Pattern.compile("(?<!\")(k\\d=)").matcher(inputString);

        Map<String,String> map = new HashMap<>();
        int index1 = 0;
        while (matcherKey.find(index1)){
            String key = matcherKey.group().replace("=","");
            index1 = matcherKey.end();
            int index2 = -1;
            Matcher matcherTail;
            if (inputString.charAt(index1) != '"'){
                matcherTail = Pattern.compile(";|$").matcher(inputString);
                if (matcherTail.find(index1) && inputString.charAt(index1) != ';'){
                    index2 = matcherTail.end();
                }
            } else {
                matcherTail = Pattern.compile("\"+$|\"+;").matcher(inputString);
                int temp = index1;
                while (matcherTail.find(temp)){
                    index2 = matcherTail.end();
                    temp = index2;
                }
            }

            if (matcherKey.start() >= lastIndex2 && index2 != -1){
                String value = inputString.substring(index1,index2).replaceAll("(;)|(\")","");
                map.put(key, value);
                lastIndex2 = index2;
            }
        }
        return map;
    }

    public static void main(String[] args) {
        Map<String,String> map = extract("v2;k3=v3;k4=\"\\\"k5=v5\\\";k6=v6\"");
        map.forEach((k,v) -> System.out.println("key:"+ k+" value:"+v));
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