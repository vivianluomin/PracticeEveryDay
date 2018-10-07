package com.example.asus1.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;

public class SecondService extends Service {

    private Messenger mServiceMess = new Messenger(new ServiceHandler());
    private Messenger mClientMess;
    public static final int REGISTER_CLIENT = 100;
    public static final int SAY_HELLO = 200;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mServiceMess.getBinder();
    }

    private class ServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REGISTER_CLIENT:
                    mClientMess = (Messenger) msg.obj;
                    break;
                case SAY_HELLO:
                    System.out.println("from client");
                    try {
                        Message message = new Message();
                        message.what = SAY_HELLO;
                        mClientMess.send(message);
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }
}
