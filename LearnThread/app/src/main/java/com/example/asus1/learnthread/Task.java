package com.example.asus1.learnthread;

public class Task {

    private String getData1;
    private String gerData2;

    public void doLongTimeTask(){
        try {
            System.out.println("begin");
            Thread.sleep(3000);
            String privData1 = "处理任务后返回值为1 Thread Name = "+Thread.currentThread().getName();
            String privData2 = "处理任务后返回值为2 Thread Name = "+Thread.currentThread().getName();
            synchronized (this){
                getData1 = privData1;
                gerData2 = privData2;
            }

            System.out.println(getData1);
            System.out.println(gerData2);
            System.out.println("end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
