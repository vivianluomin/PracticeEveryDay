package com.example.asus1.camerawithopengl;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.tv.TvContract;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {


    private CameraGLView mGLSurfaceView;
    private String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private ImageView mImageView;
    private static final String TAG = "MainActivity";

    private CameraPreview mPreview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mGLSurfaceView = new CameraGLView(this);
////        mGLSurfaceView.setEGLContextClientVersion(2);
////        mGLSurfaceView.setRenderer(new CameraRenderer(mGLSurfaceView));

//        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
//        glSurfaceView.setEGLContextClientVersion(2);
//        glSurfaceView.setRenderer(new MyRenderer());
        setContentView(R.layout.activity_main);
        mPreview = (CameraPreview)findViewById(R.id.surface);
        mImageView = (ImageView)findViewById(R.id.image);
        requestPremission();



    }

    @Override
    protected void onDestroy() {
        mPreview.destoryCamera();
        super.onDestroy();

    }

    public void setImage(Bitmap bitmap){
        mImageView.setImageBitmap(bitmap);
    }

    private void requestPremission(){
        StringBuilder per = new StringBuilder();
        for(int i =0;i<permissions.length;i++){
            if(ActivityCompat.checkSelfPermission(this,permissions[i])!=
                    PackageManager.PERMISSION_GRANTED){
                per.append(permissions[i]);
                per.append("#");
            }
        }

        if(per.length()>0){
            String[] pers = per.toString().split("#");
            ActivityCompat.requestPermissions(this,pers,100);
        }else {
            Log.d(TAG, "requestPremission: yyyyy");
           openCamera();


        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        mPreview.stopCamera();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        openCamera();
        super.onRestart();
    }
    
    

    private void openCamera(){
        new Thread(new Runnable(){
            public void run(){
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mPreview.openCamera();
            }
        }).start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            for(int i = 0;i<grantResults.length;i++){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    return;
                }
            }
            openCamera();

        }
    }
}
