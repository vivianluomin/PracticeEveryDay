package com.example.asus1.learnthread.AsyncTaskAnsy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncDemo extends AsyncTask<String,Integer,Bitmap>{


    private String mUrl;
    private onDownLoadInterface mCallback;

    public interface onDownLoadInterface{
        void onStarting();
        void onFinish(Bitmap bitmap);
    }

    public AsyncDemo(onDownLoadInterface callback){
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarting();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        mUrl =strings[0];
        Bitmap bitmap = null;
        URLConnection connection;
        InputStream in;
        try {

            connection = new URL(mUrl).openConnection();
            in = connection.getInputStream();

            //为了看到效果
            Thread.sleep(2000);

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
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mCallback.onFinish(bitmap);
    }
}
