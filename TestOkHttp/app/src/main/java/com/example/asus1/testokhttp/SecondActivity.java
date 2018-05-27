package com.example.asus1.testokhttp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("222","onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("222","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("222","onStop");
    }
}
