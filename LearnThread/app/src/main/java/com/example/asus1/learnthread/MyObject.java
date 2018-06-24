package com.example.asus1.learnthread;

public class MyObject {

     public void methodA(){
        try {
            synchronized (this){
                System.out.println("begin methodA threadName = "+Thread.currentThread().getName());
                Thread.sleep(5000);
                System.out.println("end endTime = "+System.currentTimeMillis());
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

   synchronized public void methodB(){
        try {
            System.out.println("begin methodB threadName = "
                    +Thread.currentThread().getName()
                    + "  begin time = "+System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
