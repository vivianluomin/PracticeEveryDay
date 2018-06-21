package com.example.asus1.learnthread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private  void startnewThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

}
