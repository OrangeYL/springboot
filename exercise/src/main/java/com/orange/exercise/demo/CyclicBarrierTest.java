package com.orange.exercise.demo;

import java.util.concurrent.*;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/2 10:05
 * @description: 同步屏障 cyclicBarrier测试，cyclicBarrier: 用于一组线程互相等待到某个状态，然后这组线程再同时执行
 */
public class CyclicBarrierTest {
    //请求的数量
    private static final int threadCount = 10;
    //需要同步的线程数量
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {
        //创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for(int i = 0;i < threadCount;i++){
            final int threadNum = i;
            Thread.sleep(1000);
            threadPool.execute(() -> {
                test(threadNum);
            });
        }
        threadPool.shutdown();
    }
    public static void test(int threadNum){
        System.out.println("threadNum : " + threadNum + " is ready");
        try {
            //等待60秒，保证子线程完全执行结束
            cyclicBarrier.await(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("---CyclicBarrierException---");
        }
        System.out.println("threadNum : " + threadNum + " is finish");
    }
}
