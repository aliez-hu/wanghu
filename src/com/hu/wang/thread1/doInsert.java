package com.hu.wang.thread1;


import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class doInsert {
    public static void main(String[] args) {
        long startTimes = System.currentTimeMillis();
        String[] names = new String[]{"LXL","MQJ","JOE","JON","JACK","LILY","LUCY","NOB","FDSE","GTX"};
        int threadCount = 10;
        int total = 100000;
        int every = total/threadCount;
        final CountDownLatch latch = new CountDownLatch(threadCount);
//		ExecutorService executor = Executors.newFixedThreadPool(10);
        for(int i=0;i<threadCount;i++){
            new Thread(new Worker(latch,names[i],i*every,(i+1)*every)).start();
        }
        try {
            latch.await();
            long endTimes = System.currentTimeMillis();
            System.out.println("所有线程执行完毕:" + (endTimes - startTimes));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}

class Worker implements Runnable{

    static String sql = "INSERT INTO `tb_demo` (`name`,cre_date) VALUES (?, ?);";
    int start = 0;
    int end = 0;
    String name = "";
    CountDownLatch latch;
    public Worker(CountDownLatch latch,String name, int start,int end){
        this.start = start;
        this.end = end;
        this.name = name;
        this.latch = latch;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            System.out.println("线程" + Thread.currentThread().getName()+ "正在执行。。");
            Object[] params = new Object[] { name + i, new Date() };
            JdbcUtils.insert(sql, params);
        }
        latch.countDown();
    }

}