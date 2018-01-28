package com.example.asus1.androidprmoteflighting.AIDLTest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by asus1 on 2017/12/28.
 */

public class BinderPool  {

    private static final String TAG = "BinderPool";
    public static final int BINDE_COMPUTE = 200;
    public static  final  int BINDE_SECRITY = 100;
    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile  BinderPool sInstance;
    //为了控制线程，让service绑定成功后，才能进行其他操作
    private CountDownLatch mConnectBinderPollCountDownLatacj;

    private BinderPool(Context context){
        mContext = context.getApplicationContext();
        connectBinderPoolSerivce();
    }

    public static BinderPool getsInstance(Context context){
        if(sInstance == null){
            synchronized (BinderPool.class){
                sInstance = new BinderPool(context);
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolSerivce(){

        mConnectBinderPollCountDownLatacj = new CountDownLatch(1);

        Intent intent = new Intent(mContext,BinderPoolSerivce.class);
        mContext.bindService(intent,mBinderPoolConnection,Context.BIND_AUTO_CREATE);

        try {
            mConnectBinderPollCountDownLatacj.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }



    }


    public IBinder queryBinder(int binderCode){

        IBinder binder = null;
        Log.d("query","qqqqqqqqqqqq11111111111111");
        try {
            if(mBinderPool != null){
                Log.d("query","qqqqqqqqqqqq00000");
                binder = mBinderPool.queryBinder(binderCode);
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return binder;
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            Log.d("connection","cccccccccccccc");
            try {
                Log.d("connection","cccccccccccccc0000");
                mBinderPool.asBinder().linkToDeath(mBinderPollDeathRecipient,0);
            }catch (RemoteException e){
                e.printStackTrace();
            }

            mConnectBinderPollCountDownLatacj.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mBinderPollDeathRecipient  = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPollDeathRecipient,0);
            mBinderPool = null;
            connectBinderPoolSerivce();
        }
    };

    public static class BinderPoolImpl extends IBinderPool.Stub{
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            Log.d("query","qqqqqqqqqqqq");
            switch (binderCode){
                case BINDE_SECRITY:
                    binder = new SecurityCenterImpl();
                    break;
                case BINDE_COMPUTE:
                    binder = new ComputeImpl();
                    break;
            }

            return binder;

        }
    }
}
