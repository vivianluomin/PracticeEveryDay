package com.example.asus1.litepaltest;

import org.litepal.crud.DataSupport;

/**
 * Created by asus1 on 2017/4/18.
 */

public class Book extends DataSupport{
    private int id;
    private String author;
    private double price;
    private int pages;
    private  String name;
    private String press;

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getPages() {
        return pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
