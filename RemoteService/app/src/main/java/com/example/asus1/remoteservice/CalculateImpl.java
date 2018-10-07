package com.example.asus1.remoteservice;

import android.os.RemoteException;

public class CalculateImpl extends ICalculate.Stub {

    @Override
    public int add(int first, int second) throws RemoteException {
        return first+second;
    }

    @Override
    public int sub(int first, int second) throws RemoteException {
        return first-second;
    }
}
