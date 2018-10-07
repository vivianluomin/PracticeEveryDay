package com.example.asus1.remoteservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements Handler.Callback {


    private AIDL_Service mBinder;
    private static final String TAG = "MainActivity";

//    private class  ActivityHanlder extends Handler{
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case SecondService.SAY_HELLO:
//                    System.out.println("from service");
//                    break;
//            }
//        }
//    }
//
//    private Messenger mClient = new Messenger(new ActivityHanlder());
//    private Messenger mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = AIDL_Service.Stub.asInterface(service);
            try {
                mBinder.registerListener(new AIDL_Activity.Stub() {
                    private static final String TAG = "MainActivity";
                    @Override
                    public void onRespond(String s) throws RemoteException {
                        Log.d(TAG, "onRespond: "+s);
                    }
                });
            }catch (RemoteException ee){
                ee.printStackTrace();
            }

            try {

                mBinder.sendMessage(new MSG("parcelable MSG"));
            }catch (Exception e){
                e.printStackTrace();
            }
//            try {
//                mService = new Messenger(service);
//                Message message = new Message();
//                message.what = SecondService.REGISTER_CLIENT;
//                message.obj = mClient;
//                mService.send(message);
//
//                Message message1 = new Message();
//                message1.what = SecondService.SAY_HELLO;
//                //message1.obj = 2222;
//                mService.send(message1);
//            }catch (RemoteException e){
//                e.printStackTrace();
//            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private BinderPool mBinderPool;
    private ICalculate mCalculate;
    private IRect mRect;
    private Handler mHander = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this,RemoteService.class);
//        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBinderPool = BinderPool.getInstance(MainActivity.this);
                Log.d(TAG, "run: 11111111111");
                mHander.obtainMessage().sendToTarget();
                Log.d(TAG, "run: 22222222222222222");
            }
        }).start();




    }

    @Override
    public boolean handleMessage(Message msg) {

        mCalculate = ICalculate.Stub.asInterface(mBinderPool.queryBidner
                (BinderPool.BINDER_CALCULATE));
        mRect = IRect.Stub.asInterface(mBinderPool.queryBidner(BinderPool.BINDER_RECT));
        Log.d(TAG, "handleMessage: 333333333333333333333333");
        try {
            System.out.println(mCalculate.add(1,2));
            System.out.println(mRect.area(2,4));
        }catch (RemoteException e){
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    protected void onDestroy() {
        //unbindService(mConnection);
        mBinderPool.unBinder();
        super.onDestroy();
    }
}
