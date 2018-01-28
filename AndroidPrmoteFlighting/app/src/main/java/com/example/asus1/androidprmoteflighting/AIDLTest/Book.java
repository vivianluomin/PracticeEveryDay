package com.example.asus1.androidprmoteflighting.AIDLTest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asus1 on 2017/12/14.
 */

public class Book implements Parcelable {

    public int bookId;
    public String bookName;


    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    private Book(Parcel in){
        bookId = in.readInt();
        bookName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(bookId);
        parcel.writeString(bookName);

    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){

        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[i];
        }
    };
}

