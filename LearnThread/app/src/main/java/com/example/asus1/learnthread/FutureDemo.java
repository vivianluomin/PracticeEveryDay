package com.example.asus1.learnthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureDemo {

    //线程池
    static ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args){
        try {

            futureWithRunnable();
            futureWithCallable();
            futureTask();

        }catch(Exception e){

        }
    }

    //向线程池中提交Runnable对象
    private static void futureWithRunnable()throws InterruptedException,ExecutionException{
        Future<?> result = mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                 fibc(20);
            }
        });

        System.out.println("future result form runnable : "+result.get() );
    }


    //提交Callable对象，有返回值
    private static void futureWithCallable()throws InterruptedException,ExecutionException{
        Future<Integer> result2 = mExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fibc(20);
            }
        });

        System.out.println("future result form callable : "+result2.get());
    }

    //提交FutureTask对象
    private static void futureTask()throws InterruptedException,ExecutionException{

        FutureTask<Integer> futureTask = new FutureTask<>(
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return fibc(20);
                    }
                }
        );
        mExecutor.submit(futureTask);
        System.out.println("future result form futureTask : "+futureTask.get());
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

}
