package com.example.asus1.androidprmoteflighting.AIDLTest;

import android.os.RemoteException;

/**
 * Created by asus1 on 2017/12/28.
 */

public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRRT_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i =0;i<chars.length;i++){
            chars[i] ^=SECRRT_CODE;
        }

        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
         return encrypt(password);
    }
}
