package com.example.asus1.learnopengl.shaders;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TraigelView  extends GLSurfaceView{

    private TriangleRender mRender;
    private static final float ANGLE_SPAN = 0.375F;
    public TraigelView(Context context) {
        this(context,null);
    }

    public TraigelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setEGLContextClientVersion(3);
        mRender = new TriangleRender();
        setRenderer(mRender);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//持续渲染
    }

   private class  TriangleRender implements  Renderer{

       public  Triangle mTriangle;
       private RotateThread mThread;

       @Override
       public void onSurfaceCreated(GL10 gl, EGLConfig config) {
           GLES30.glClearColor(1,1,1,1.0f);//设置屏幕背景色
           mTriangle = new Triangle();
           GLES30.glEnable(GLES30.GL_DEPTH_TEST);
           mThread = new RotateThread();
           mThread.start();

       }

       @Override
       public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0,0,width,height);
            float ration = (float)width/height;
           Matrix.frustumM(Triangle.mProjectMatirx,0,
                   -ration,ration,-1,1,1,10);//设置透视投影

           Matrix.setLookAtM(Triangle.mVMatrix,0,
                   0,0,3,0,
                   0f,0,0f,1.0f,0f);//设置摄像机

       }

       @Override
       public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT|GLES30.GL_COLOR_BUFFER_BIT);
            mTriangle.drawSelf();
       }
   }

   public  class RotateThread extends  Thread{
        public  boolean flag = true;
       @Override
       public void run() {
           while (flag){
               mRender.mTriangle.setXAngle(ANGLE_SPAN);
               try {
                   Thread.sleep(20);
               }catch (InterruptedException e){
                   e.printStackTrace();
               }
           }
       }
   }
}
