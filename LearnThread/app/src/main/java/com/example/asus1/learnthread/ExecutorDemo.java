package com.example.asus1.learnthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorDemo {

    private static final int MAX = 10;

    public static void main(String[] args){

        try {
            fixedThreadPool(3);
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

}
