package com.example.asus1.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by asus1 on 2017/4/15.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"received in mybroadcastreceiver",Toast.LENGTH_LONG).show();
        abortBroadcast();
    }
}
