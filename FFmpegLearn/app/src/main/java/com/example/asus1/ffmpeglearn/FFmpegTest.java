package com.example.asus1.ffmpeglearn;

public class FFmpegTest {

    static {
        System.loadLibrary("com_example_asus1_ffmpeglearn_NDKTest");
    }

    public native static String getFFmpegCodeInfo();
}
