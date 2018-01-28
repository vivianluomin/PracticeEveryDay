// IBookManager.aidl
package com.example.asus1.androidprmoteflighting.AIDLTest;


import com.example.asus1.androidprmoteflighting.AIDLTest.Book;
import com.example.asus1.androidprmoteflighting.AIDLTest.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);

}
