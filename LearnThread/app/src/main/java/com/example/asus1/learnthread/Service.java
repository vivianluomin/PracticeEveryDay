package com.example.asus1.learnthread;

public class Service {
    private String username;
    private String password;
    private String anyString ;
    public void setUsernamePassword(String username,String password){
        try {
            anyString = new String();
            synchronized (anyString){
                System.out.println("线程名： "+Thread.currentThread().getName()+
                "在 "+System.currentTimeMillis() +"进入同步块");
                this.username = username;
                Thread.sleep(3000);
                this.password = password;
                System.out.println("线程名： "+Thread.currentThread().getName()+
                        "在 "+System.currentTimeMillis() +"离开同步块");
            }
        }catch (InterruptedException e){

        }
    }
}
