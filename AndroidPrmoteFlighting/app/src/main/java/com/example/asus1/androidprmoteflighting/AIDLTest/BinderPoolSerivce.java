package com.example.asus1.androidprmoteflighting.AIDLTest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BinderPoolSerivce extends Service {

    private static final String TAG = "BinderPoolService";

    private Binder mBinderPool = new BinderPool.BinderPoolImpl();

    public BinderPoolSerivce() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"onBind");
        return mBinderPool;
    }
}
