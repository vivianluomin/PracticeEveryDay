package com.example.asus1.learnthread.AsyncTaskAnsy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SimpleAsynDemo extends SimpleAsyncTask<String,Integer,Bitmap>{

    private static final String TAG = "SimpleAsynDemo";
        private String mUrl;
        private com.example.asus1.learnthread.AsyncTaskAnsy.AsyncDemo.onDownLoadInterface mCallback;
        
        public SimpleAsynDemo(com.example.asus1.learnthread.AsyncTaskAnsy.AsyncDemo.onDownLoadInterface callback){
            super();
            mCallback = callback;
        }

        @Override
        public void onPreExecute() {
            mCallback.onStarting();
        }

    @Override
    public Bitmap doInBackground(String s) {
        mUrl = s;
        Bitmap bitmap = null;
        URLConnection connection;
        InputStream in;
        try {

            connection = new URL(mUrl).openConnection();
            in = connection.getInputStream();

            //为了看到效果
            Thread.sleep(2000);
            Log.d(TAG, "doInBackground: ");
            BufferedInputStream bis =
                    new BufferedInputStream(in);
            bitmap = BitmapFactory.decodeStream(bis);
            in.close();
            bis.close();

        }catch (MalformedURLException e){

            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onPostResult(Bitmap bitmap) {
        Log.d(TAG, "onPostResult: ");
        mCallback.onFinish(bitmap);
    }

    @Override
    public void onProgressUpdate(Integer i) {

    }


}
