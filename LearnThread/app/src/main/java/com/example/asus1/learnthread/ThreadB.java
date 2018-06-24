package com.example.asus1.learnthread;

public class ThreadB extends Thread {

    private MyObject obeject;
    private Task task;

    private Service service;

    public ThreadB(MyObject obeject) {
        super();
        this.obeject = obeject;
    }

    public ThreadB(Task task){
        super();
        this.task = task;
    }

    public ThreadB(Service service){
        super();
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        //obeject.methodA();
        //task.doLongTimeTask();

        service.setUsernamePassword("b","bb");
    }
}
