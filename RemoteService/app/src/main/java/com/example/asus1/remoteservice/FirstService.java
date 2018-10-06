package com.example.asus1.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class FirstService extends Service {


    private CallBackInterface mCall;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new myBinder();
    }

    private class myBinder extends Binder{

        public FirstService getService(){

            return FirstService.this;
        }

    }

    public void response(){
        if(mCall!=null){
            mCall.response();
        }
    }

    public void registerInterface(CallBackInterface callBackInterface){
        mCall = callBackInterface;
    }

    public interface CallBackInterface{

        void response();
    }
}
