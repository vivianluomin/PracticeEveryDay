package com.example.asus1.learnopengl.shaders;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SuqareView extends GLSurfaceView {

    private SuqureRender mRender;

    public SuqareView(Context context) {
        this(context,null);
    }

    public SuqareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRender = new SuqureRender();
        setEGLContextClientVersion(3);
        setRenderer(mRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private class SuqureRender implements Renderer{
        private Square mSquare;
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(1,1,1,1);
            mSquare = new Square();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0,0,width,height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
            mSquare.drawSelf();
        }
    }
}
