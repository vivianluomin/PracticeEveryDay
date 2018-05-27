package com.example.asus1.testgson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus1 on 2017/9/13.
 */

public class Book {
    private String[] author;

    @SerializedName("isbn-10")
    private String isbn10;

    @SerializedName("isbn-13")
    private String isbn13;

    private String title;

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
