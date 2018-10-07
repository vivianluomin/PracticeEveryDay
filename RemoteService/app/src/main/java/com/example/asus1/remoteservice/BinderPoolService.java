package com.example.asus1.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BinderPoolService extends Service {

    private Binder mBinderPool = new BinderPool.BinderPoolIml();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }
}
