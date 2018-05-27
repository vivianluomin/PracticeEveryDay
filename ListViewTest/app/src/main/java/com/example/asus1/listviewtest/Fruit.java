package com.example.asus1.listviewtest;

/**
 * Created by asus1 on 2017/4/11.
 */

public class Fruit {
    private String name;
    private int imageId;
    public Fruit(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }
}
