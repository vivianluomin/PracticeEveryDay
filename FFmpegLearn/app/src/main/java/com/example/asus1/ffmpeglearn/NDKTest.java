package com.example.asus1.ffmpeglearn;

public class NDKTest {

    static {
        System.loadLibrary("com_example_asus1_ffmpeglearn_NDKTest");
    }

    public static native String getString();
}
