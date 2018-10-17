package com.example.asus1.remoteservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;


public class HandlerService extends IntentService {

    private static final String TAG = "HandlerService";

    public HandlerService() {
        super("HandlerService");
        Log.d(TAG, "HandlerService: "+Thread.currentThread().getName());
        
    }



    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {

        Log.d(TAG, "onStart: ");
        super.onStart(intent, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        Log.d(TAG, "HandlerService: "+Thread.currentThread().getName());
        String path = intent.getStringExtra("path");
        downloadTask(path);

    }

    private void downloadTask(String path){
        Log.d(TAG, "downloadTask: ");
//       try {
//           Thread.sleep(3*1000);
//       }catch (InterruptedException e){
//           e.printStackTrace();
//       }

      Intent intent = new Intent(MainActivity.BROCAST_MSG);
       intent.putExtra("path",path);
       sendBroadcast(intent);
    }





    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

}
