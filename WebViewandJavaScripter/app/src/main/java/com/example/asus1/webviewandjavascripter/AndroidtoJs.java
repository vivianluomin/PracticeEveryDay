package com.example.asus1.webviewandjavascripter;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by asus1 on 2017/11/5.
 */

public class AndroidtoJs {

    private Context mContext;

    public  AndroidtoJs(Context context){
        mContext = context;
    }

    //必须加这个，代表JS要调用的
    @JavascriptInterface
    public  void  hello(String s){
        Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
        Log.d("Hello",s);
    }

}
