package com.orange.exercise.likou;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/24 9:33
 * @description:
 */
public class reversePrint {

    static int i = 0;
    static int j = 0;
    static int[] res;
    public static void main(String[] args) {
        ListNode head = getList();
        int[] ans = reverse(head);
        for(Integer num:ans){
            System.out.println(num);
        }
    }
    public static int[] reverse(ListNode head){
        doChange(head);
        return res;
    }

    public static void doChange(ListNode head){
        if(head == null){
            res = new int[i];
            return;
        }
        i++;
        doChange(head.next);
        res[j] = head.val;
        j++;
    }
    public static ListNode getList(){
        ListNode listNode = new ListNode(1);
        ListNode head = listNode;
        listNode.next = new ListNode(2);
        listNode = listNode.next;
        listNode.next = new ListNode(3);
        return head;
    }
}

class ListNode{

    int val;

    ListNode next;

    ListNode(int x) { val = x; }

    ListNode(){}
}
