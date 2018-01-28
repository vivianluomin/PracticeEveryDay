package com.example.asus1.androidprmoteflighting.AIDLTest;

import android.os.RemoteException;
import android.support.v4.text.ICUCompat;

/**
 * Created by asus1 on 2017/12/28.
 */

public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
