package com.example.asus1.learnopengl.shaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.AttributeSet;

import com.example.asus1.learnopengl.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SquareTextView extends GLSurfaceView {

    private SquareTexRender mRender;
    private int mTextureId;

    public SquareTextView(Context context) {
        this(context,null);
    }

    public SquareTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRender = new SquareTexRender();
        setEGLContextClientVersion(3);
        setRenderer(mRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private class SquareTexRender implements Renderer{

        private SquareTexture mSquare;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(1,1,1,1);
            mSquare = new SquareTexture();
            initTexture();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0,0,width,height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
            mSquare.drawSelf(mTextureId);
        }
    }

    private void initTexture(){

        int[] textures = new int[1];
        GLES30.glGenTextures(1,textures,0);
        mTextureId = textures[0];
        //绑定纹理id，将对象绑定到环境的纹理单元
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,mTextureId);

        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);//设置MIN 采样方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);//设置MAG采样方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE);//设置S轴拉伸方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);//设置T轴拉伸方式


        //加载图片
        Bitmap bitmap = BitmapFactory.decodeResource(
                SquareTextView.this.getResources(), R.mipmap.brick);
        GLUtils.texImage2D( //实际加载纹理进显存
                GLES30.GL_TEXTURE_2D, //纹理类型
                0, //纹理的层次，0表示基本图像层，可以理解为直接贴图
                bitmap, //纹理图像
                0 //纹理边框尺寸
        );

        bitmap.recycle();

    }

}
