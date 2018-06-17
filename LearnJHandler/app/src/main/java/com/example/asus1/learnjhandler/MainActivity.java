package com.example.asus1.learnjhandler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView view;
    private ThreadLocal<Boolean> mBooleanThreadaLocal  = new ThreadLocal<>();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         view = findViewById(R.id.tv_main);

        handler.post(new Runnable() {
            @Override
            public void run() {
                view.setText("XXXXXXXXXXXXXXXXXXX");

            }
        });

        handler.sendMessage(new Message());

        mBooleanThreadaLocal.set(true);
        Log.d(TAG,"[Thread#main]mBooleanThreadLocal = " +mBooleanThreadaLocal.get());

        new Thread("Thread#1"){
            @Override
            public void run() {
                mBooleanThreadaLocal.set(false);
                Log.d(TAG,"[Thread#1]mBooleanThreadLocal = " +mBooleanThreadaLocal.get());
            }
        }.start();

        new  Thread("Thread#2"){
            @Override
            public void run() {
                Log.d(TAG,"[Thread#2]mBooleanThreadLocal = " +mBooleanThreadaLocal.get());
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler();
                Looper.loop();
            }
        };
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


}
