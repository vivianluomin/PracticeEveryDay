package com.example.asus1.remoteservice;

import android.app.Service;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AidlBinder extends AIDL_Service.Stub {

    private RemoteService mService;
    private List<AIDL_Activity> mListeners = new ArrayList<>();

    public AidlBinder(RemoteService service){
        mService = service;
    }

    private static final String TAG = "RemoteService";

    @Override
    public void sendMessage(MSG msg)throws RemoteException {
        Log.d(TAG, "sendMessage: "+"通信了  "+msg.msg);
        for(int i = 0;i<mListeners.size();i++){
            mListeners.get(i).onRespond("message from service");
        }
    }

    @Override
    public void registerListener(AIDL_Activity lisntener) {
        mListeners.add(lisntener);
    }
}
