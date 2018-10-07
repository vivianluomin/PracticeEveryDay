// ICalculate.aidl
package com.example.asus1.remoteservice;

// Declare any non-default types here with import statements

interface ICalculate {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   int add(int first,int second);
   int sub(int first,int second);
}
