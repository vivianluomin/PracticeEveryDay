package com.example.asus1.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by asus1 on 2017/4/23.
 */

public class MyIntentService extends IntentService {

    public MyIntentService(){
        super("MyIntentSerivce");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("MyIntentService","Thread id is "+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService","onDestroy");
    }
}
