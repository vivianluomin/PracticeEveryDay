package com.example.asus1.learnthread;

public class ThreadA extends Thread {
    private  MyObject object;
    private Task task;

    private Service service;

    public ThreadA(MyObject object) {
        super();
        this.object = object;
    }

    public ThreadA(Task task){
        super();
        this.task = task;
    }

    public ThreadA(Service service){
        super();
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        //object.methodA();
        //task.doLongTimeTask();
        service.setUsernamePassword("a","aa");
    }
}
