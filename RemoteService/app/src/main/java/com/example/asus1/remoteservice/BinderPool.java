package com.example.asus1.remoteservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NavUtils;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class BinderPool {

    public static final int BINDER_NONE = -1;
    public static final int BINDER_CALCULATE = 0;
    public static final int BINDER_RECT = 1;

    private static final String TAG = "BinderPool";

    private Context mContext;
    private static IBinderPool mBinderPool;
    private static BinderPool mInstance;
    private CountDownLatch mCountDownLatch;

    private BinderPool(Context context){
        mContext = context;
        connectionService();
    }

    public static BinderPool getInstance(Context context){
        if(mInstance == null){
            synchronized (BinderPool.class){
                if(mInstance == null){
                    mInstance = new BinderPool(context);
                }
            }
        }

        return mInstance;
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient,0);
            }catch (RemoteException e){

            }
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient,0);
            mBinderPool = null;
            if(mContext!=null){
                connectionService();
            }

        }
    };

    private  void connectionService(){
        mCountDownLatch = new CountDownLatch(1);
        mContext.bindService(new Intent(mContext,BinderPoolService.class)
                ,mBinderPoolConnection,Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();//同步工具，让Service绑定成功后，再执行
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public IBinder queryBidner(int binderCode){
        try {
            return mBinderPool.queryBinder(binderCode);
        }catch (RemoteException e){
            e.printStackTrace();
        }

        return null;
    }

    public static class BinderPoolIml extends IBinderPool.Stub{

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode){
                case BINDER_CALCULATE:
                    binder = new CalculateImpl();
                    break;
                case BINDER_RECT:
                    binder = new RectImpl();
                    break;
            }
            return binder;
        }
    }

    public void unBinder(){
        mContext.unbindService(mBinderPoolConnection);
        mContext=null;
    }
}
