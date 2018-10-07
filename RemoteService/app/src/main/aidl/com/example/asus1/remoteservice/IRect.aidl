// IRect.aidl
package com.example.asus1.remoteservice;

// Declare any non-default types here with import statements

interface IRect {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int area(int length,int width);
    int perimeter(int length,int width);
}
