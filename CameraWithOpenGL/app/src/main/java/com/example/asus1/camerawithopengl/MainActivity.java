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
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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

    private TestRenderer glRenderer;



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
//        mPreview = (CameraPreview)findViewById(R.id.surface);
//        mImageView = (ImageView)findViewById(R.id.image);
//        requestPremission();

        final SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surface);
        mImageView = (ImageView)findViewById(R.id.image);
        glRenderer = new TestRenderer();
        GLSurface glPbufferSurface = new GLSurface(512,512);
        glRenderer.addSurface(glPbufferSurface);
        glRenderer.startRender();
        glRenderer.requestRender();


        //glRenderer.start();

        glRenderer.postRunnable(new Runnable() {
            @Override
            public void run() {
                IntBuffer i = IntBuffer.allocate(512*512);
                GLES20.glReadPixels(0, 0, 512, 512, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, i);

                final Bitmap bitmap = frameToBitmap(512,512,i);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                   // glRenderer.render(holder.getSurface(),width,height);
                GLSurface glWindowSurface = new GLSurface(holder.getSurface(),width,height);
                glRenderer.addSurface(glWindowSurface);
                glRenderer.requestRender();


            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });



    }

    @Override
    protected void onDestroy() {
       // mPreview.destoryCamera();
        glRenderer.release();
        glRenderer = null;
        super.onDestroy();

    }

    public void setImage(Bitmap bitmap){
        mImageView.setImageBitmap(bitmap);
    }


    /**
     * 将数据转换成bitmap(OpenGL和Android的Bitmap色彩空间不一致，这里需要做转换)
     *
     * @param width 图像宽度
     * @param height 图像高度
     * @param ib 图像数据
     * @return bitmap
     */
    private static Bitmap frameToBitmap(int width, int height, IntBuffer ib) {
        int pixs[] = ib.array();
        // 扫描转置(OpenGl:左上->右下 Bitmap:左下->右上)
        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width; x++) {
                int pos1 = y * width + x;
                int pos2 = (height - 1 - y) * width + x;

                int tmp = pixs[pos1];
                pixs[pos1] = (pixs[pos2] & 0xFF00FF00) | ((pixs[pos2] >> 16) & 0xff) | ((pixs[pos2] << 16) & 0x00ff0000); // ABGR->ARGB
                pixs[pos2] = (tmp & 0xFF00FF00) | ((tmp >> 16) & 0xff) | ((tmp << 16) & 0x00ff0000);
            }
        }
        if (height % 2 == 1) { // 中间一行
            for (int x = 0; x < width; x++) {
                int pos = (height / 2 + 1) * width + x;
                pixs[pos] = (pixs[pos] & 0xFF00FF00) | ((pixs[pos] >> 16) & 0xff) | ((pixs[pos] << 16) & 0x00ff0000);
            }
        }

        return Bitmap.createBitmap(pixs, width, height, Bitmap.Config.ARGB_8888);
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
      //  mPreview.stopCamera();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
       // openCamera();
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
