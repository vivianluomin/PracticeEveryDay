package com.example.asus1.httpfrrame;

import android.os.Build;

/**
 * Created by asus1 on 2018/5/8.
 */

public class HttpStackFactory {
    private static final int GINGERBREAD_SDK_NUM = 9;

    public static HttpStack createHttpStack(){

            return new HttpUrlConnStack();

    }
}
