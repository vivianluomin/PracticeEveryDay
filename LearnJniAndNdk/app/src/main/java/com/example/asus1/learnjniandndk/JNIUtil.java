package com.example.asus1.learnjniandndk;

public class JNIUtil {

    static {
        System.loadLibrary("JNITestSample");
    }

    public static native String getWorld();


    public static native int getInt();
}
