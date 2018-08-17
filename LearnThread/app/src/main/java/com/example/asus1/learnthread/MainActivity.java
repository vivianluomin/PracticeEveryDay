package com.example.asus1.learnthread;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asus1.learnthread.AsyncTaskAnsy.AsyncDemo;
import com.example.asus1.learnthread.AsyncTaskAnsy.SimpleAsynDemo;
import com.example.asus1.learnthread.AsyncTaskAnsy.SimpleAsyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity implements AsyncDemo.onDownLoadInterface{

    private AlertDialog dialog;
    private static String URL = "http://img0.imgtn.bdimg.com/it/u=3986114508,3502931434&fm=27&gp=0.jpg";
   private ImageView mImageView;
   private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.iv_image);
        mBtn = findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleAsynDemo demo = new SimpleAsynDemo(MainActivity.this);
                demo.execute(URL);
            }
        });

       // AsyncDemo asyncDemo = new AsyncDemo(this);
        //asyncDemo.execute(URL);

    }



    @Override
    public void onStarting() {
        dialog = new AlertDialog.Builder(this)
                .setMessage("图片正在下载")
                .setTitle("下载")
                .create();
        dialog.show();
    }


    @Override
    public void onFinish(Bitmap bitmap) {
        dialog.dismiss();
        mImageView.setImageBitmap(bitmap);
    }
}
