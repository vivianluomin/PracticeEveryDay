package com.example.asus1.remoteservice;

import android.os.RemoteException;

public class RectImpl extends IRect.Stub {

    @Override
    public int area(int length, int width) throws RemoteException {
        return length*width;
    }

    @Override
    public int perimeter(int length, int width) throws RemoteException {
        return (length+width)*2;
    }
}
