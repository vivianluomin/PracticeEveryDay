package com.example.asus1.shareapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    static  int[] resids = {
            R.drawable.png_0001,
            R.drawable.png_0002,
            R.drawable.png_0003,
            R.drawable.png_0004,
            R.drawable.png_0005, R.drawable.png_0006,
            R.drawable.png_0007,
            R.drawable.png_0008, R.drawable.png_0009,
            R.drawable.png_0001,
            R.drawable.png_0002,
            R.drawable.png_0003,
            R.drawable.png_0004,
            R.drawable.png_0005, R.drawable.png_0006,
            R.drawable.png_0007,
            R.drawable.png_0008, R.drawable.png_0009
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.gridview);
        PictureAdapter adapter = new PictureAdapter(this,resids,R.layout.item);
        gridView.setAdapter(adapter);




    }
}
