package com.example.asus1.camerawithopengl;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.tv.TvContract;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {


    private CameraGLView mGLSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mGLSurfaceView = new CameraGLView(this);
//        mGLSurfaceView.setEGLContextClientVersion(2);
//        mGLSurfaceView.setRenderer(new CameraRenderer(mGLSurfaceView));
        GLSurfaceView surfaceView = new GLSurfaceView(this);
        surfaceView.setEGLContextClientVersion(2);
        surfaceView.setRenderer(new MyRenderer());
        setContentView(surfaceView);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
