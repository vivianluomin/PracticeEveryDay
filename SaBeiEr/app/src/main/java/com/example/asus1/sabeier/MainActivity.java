package com.example.asus1.sabeier;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private SaBeiErLine mSabeier;
    private FrameLayout mroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSabeier = (SaBeiErLine)findViewById(R.id.sabeier);
        mroot = (FrameLayout)findViewById(R.id.rootlayout);
        mSabeier.setStyle(Color.BLUE,0,R.mipmap.ic_launcher);
        mSabeier.invalidate();

    }
}
