package com.example.asus1.learnthread;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorDemo {

    private static final int MAX = 10;

    public static void main(String[] args){

        try {
           // fixedThreadPool(3);
            scheduldPoolThread();
        }catch (Exception e){

        }

    }

    private static void fixedThreadPool(int size)throws InterruptedException,ExecutionException{
        ExecutorService service = Executors.newFixedThreadPool(size);
        for(int i =0;i<MAX;i++){
            Future<Integer> task = service.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("执行线程："+Thread.currentThread().getName());
                    return fibc(40);
                }
            });
            System.out.println("第"+i+"次计算，结果："+task.get());
        }
    }
    private static int fibc(int num){
        if(num ==0){
            return 0;
        }
        if(num == 1){
            return  1;
        }

        return fibc(num-1)+fibc(num-2);
    }

    private static void scheduldPoolThread() throws CancellationException{

        final long last = System.currentTimeMillis();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);


        //循环任务，按照上一次任务的发起时间计算下一次任务的开始时间
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread: "+Thread.currentThread().getName()
                +" ,定时计算; "+System.currentTimeMillis());
                System.out.println("结果："+(System.currentTimeMillis()-last));
            }
        },1,2, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread: "+Thread.currentThread().getName()
                        +" ,定时计算2; "+System.currentTimeMillis());
                System.out.println("结果2："+(System.currentTimeMillis()-last));
            }
        },1,2, TimeUnit.SECONDS);


        //延时任务
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread: "
                        +Thread.currentThread().getName()+"-延时定时；");
                System.out.println("延时结果："+(System.currentTimeMillis()-last));
            }
        },1,TimeUnit.SECONDS);


        //循环结果，以上一次任务的结束时间计算下一次任务的开始时间
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread: "+Thread.currentThread().getName()
                        +" ,定时计算1; "+System.currentTimeMillis());
                System.out.println("结果1："+(System.currentTimeMillis()-last));
            }
        },1,1,TimeUnit.SECONDS);

        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread: "+Thread.currentThread().getName()
                        +" ,定时计算2; "+System.currentTimeMillis());
                System.out.println("结果2："+(System.currentTimeMillis()-last));
            }
        },1,1,TimeUnit.SECONDS);
    }
}
