package com.example.asus1.searchhandlerthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.DateUtils;

import java.util.Arrays;
import java.util.List;

public class DownLoadThread extends HandlerThread implements Handler.Callback {

    private final String TAG = this.getClass().getSimpleName();
    private final String KEY_URL = "url";
    public static final int TYPE_START = 1;
    public static final int TYPE_FINISHED = 2;

    private Handler mWorkHandler;
    private Handler mUIHandler;

    private List<String> mDownLoadUrlList;

    public DownLoadThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mWorkHandler = new Handler(getLooper(),this);
        if (mUIHandler == null) {
            throw new IllegalArgumentException("Not set UIHandler!");
        }

        for(String url:mDownLoadUrlList){
            Message message = mWorkHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL,url);
            message.setData(bundle);
            mWorkHandler.sendMessage(message);
        }
    }

    public void setDownloadUrls(String... urls) {
        mDownLoadUrlList = Arrays.asList(urls);
    }

    public Handler getUIHandler() {
        return mUIHandler;
    }

    //注入主线程 Handler
    public DownLoadThread setUIHandler(final Handler UIHandler) {
        mUIHandler = UIHandler;
        return this;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if(msg == null || msg.getData() == null){
            return false;
        }

        String url = (String)msg.getData().get(KEY_URL);

        //开始下载

        Message startMessage = mUIHandler.obtainMessage(TYPE_START,
                "\n 开始下载 @"+System.currentTimeMillis()+"\n"+url);
        mUIHandler.sendMessage(startMessage);

        SystemClock.sleep(2000);

        Message finishMessage = mUIHandler.obtainMessage(TYPE_FINISHED,
                "\n 下载完成 @"+System.currentTimeMillis()+"\n"+url);
        mUIHandler.sendMessage(finishMessage);

        return true;

    }

    @Override
    public boolean quitSafely() {
        mUIHandler = null;
        return super.quitSafely();
    }


}
