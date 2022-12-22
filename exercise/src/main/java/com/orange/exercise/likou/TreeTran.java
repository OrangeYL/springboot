package com.orange.exercise.likou;

import java.util.Arrays;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/16 16:11
 * @description:
 */
public class TreeTran {
    Node pre;
    Node head;
    public static void main(String[] args) {
        TreeTran treeTran = new TreeTran();

        Node node = new Node(4);
        node.left = new Node(2);
        node.right = new Node(5);

        treeTran.treeToDoublyList(node);


    }

    public static void print(Node root){
        if(root == null) return;
        System.out.println(root.val);
        print(root.left);
        print(root.right);
    }
    public Node treeToDoublyList(Node root) {
        if(root == null) return null;
        dfs(root);
        head.left = pre;
        pre.right = head;
        return head;
    }
    void dfs(Node cur) {
        if(cur == null) return;
        dfs(cur.left);
        if(pre != null) pre.right = cur;
        else head = cur;
        cur.left = pre;
        pre = cur;
        dfs(cur.right);
    }
}
class Node{
    public int val;
    public Node left;
    public Node right;

    public Node(){}

    public Node(int _val){
        val = _val;
    }
    public Node(int _val,Node _left,Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
}
