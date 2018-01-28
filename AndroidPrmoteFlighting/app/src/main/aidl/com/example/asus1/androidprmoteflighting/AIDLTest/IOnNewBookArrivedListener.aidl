// IOnNewBookArrivedListener.aidl
package com.example.asus1.androidprmoteflighting.AIDLTest;

// Declare any non-default types here with import statements

import com.example.asus1.androidprmoteflighting.AIDLTest.Book;

interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book newBook);
}
