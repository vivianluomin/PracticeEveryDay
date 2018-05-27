package com.example.asus1.testviews;

import android.content.Context;

/**
 * Created by asus1 on 2017/9/4.
 */

public class PxUtill {

    public static int dpTopx(int value, Context context){

        float density = context.getResources().getDisplayMetrics().density;

        return (int)(value*density+0.5f);
    }
}
