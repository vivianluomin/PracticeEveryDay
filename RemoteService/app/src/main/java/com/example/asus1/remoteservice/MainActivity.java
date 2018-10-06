package com.example.asus1.remoteservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    private AIDL_Service mBinder;
    private static final String TAG = "MainActivity";

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
                mBinder.sendMessage();
            }catch (RemoteException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this,RemoteService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

    }

}
