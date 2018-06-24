package com.example.asus1.learnthread;

public class Run {

    public  static  void  main(String[] args){
        MyObject object = new MyObject();
        Service service = new Service();
        ThreadA a = new ThreadA(service);
        a.setName("A");
        ThreadB b = new ThreadB(service);
        b.setName("B");
        a.start();
        b.start();

//        Task task = new Task();
//        ThreadA a = new ThreadA(task);
//        a.setName("A");
//        ThreadB b = new ThreadB(task);
//        b.setName("B");
//        a.start();
//        b.start();
    }
}
