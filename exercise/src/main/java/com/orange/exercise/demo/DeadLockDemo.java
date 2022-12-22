package com.orange.exercise.demo;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/20 11:34
 * @description:
 */
public class DeadLockDemo {

    private static Object resource1 = new Object(); //资源1
    private static Object resource2 = new Object(); //资源2

    public static void main(String[] args) {

        new Thread(() -> {
           synchronized (resource1){
               System.out.println(Thread.currentThread().getName() + " get resource1");
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println(Thread.currentThread().getName() + " waiting resource2");
               synchronized (resource2){
                   System.out.println(Thread.currentThread().getName() + " get resource2");
               }
           }
        },"线程1").start();

        new Thread(() -> {
            synchronized (resource2){
                System.out.println(Thread.currentThread().getName() + " get resource2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " waiting resource1");
                synchronized (resource1){
                    System.out.println(Thread.currentThread().getName() + " get resource1");
                }
            }
        },"线程2").start();
    }
}
