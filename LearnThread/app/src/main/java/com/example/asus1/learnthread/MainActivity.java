package com.example.asus1.learnthread;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //用于等待，唤醒的对象
    private static Object sLockObject = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private  void startnewThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    public  static  void  waitAndNotifyAll(){
        System.out.println("主线程运行");
        Thread thread = new WaitThread();
        thread.start();
        long startTime = System.currentTimeMillis();
        try {
            synchronized (sLockObject){
                System.out.println("主线程等待");
                sLockObject.wait();

            }
        }catch (InterruptedException e){

        }

        long timesMs = System.currentTimeMillis()-startTime;
        System.out.println("主线程继续 ->等待耗时："+timesMs +" ms");
    }

   static class WaitThread extends Thread{
        @Override
        public void run() {
            try {
                synchronized (sLockObject){
                    Thread.sleep(3000);
                    sLockObject.notifyAll();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    static void joinDemo(){
        Worker worker1 = new Worker("work-1");
        Worker worker2 = new Worker("work-2");
        worker1.start();
        System.out.println("启动线程1");
        try {
            worker1.join();
            System.out.println("启动线程2");
            worker2.start();
            worker2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("主线程继续执行");
    }


    static  class Worker extends Thread{

        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("work in "+ getName());
        }
    }


    static class YieldThread extends Thread{
        public YieldThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for(int i = 0;i<MAX_PRIORITY;i++){
                System.out.printf("%s[%d]------->%d\n",this.getName(),this.getPriority(),i);
                if(i == 2){
                    Thread.yield();
                }
            }
        }
    }

    static void  yieldDemo(){
        YieldThread t1 = new YieldThread("thread-1");
        YieldThread t2 = new YieldThread("thread-2");
        t1.start();
        t2.start();
    }
}
