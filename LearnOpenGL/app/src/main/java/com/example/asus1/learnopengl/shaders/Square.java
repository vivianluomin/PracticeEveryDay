package com.example.asus1.learnopengl.shaders;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Square {

    private int mProgram;
    private int maPositionHandle; //顶点位置的引用
    private int maColorHandle; //顶点颜色属性引用

    private String mVertexShader = "uniform mat4 uMVPMatrix;" +
            "attribute vec3 aPosition;" +
            "attribute vec4 aColor;" +
            "varying vec4 vColor;" +
            "void main(){" +
            "gl_Position =  vec4(aPosition,1);" + //根据总变换矩阵计算此次绘制此顶点的位置
            "vColor = aColor;" +
            "}";
    private String mFragmentShader = "precision mediump float;" +
            "varying vec4 vColor;" +
            "void main(){" +
            "gl_FragColor = vColor;" +
            "}";

    public static float[] mMMatrix = new float[16];//具体物体的3D变换矩阵

    private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer mFragmentBuffer;//顶点着色数据缓冲

    private ByteBuffer mIndexBuffer;//索引数据缓冲

    private int mvCount = 0;//顶点数量
    private int miCount = 0;//索引数

    public  Square(){
        initVertex();
        initShader();
    }

    private void initVertex(){
        mvCount = 4;

        float[] vertexs = new float[]{
                0.5f,0.5f,0,
                0.5f,-0.5f,0,
                -0.5f,-0.5f,0,
                -0.5f,0.5f,0
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexs.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertexs);
        mVertexBuffer.position(0);

        float[] colors = new float[]{
                1,0,0,1,
                0,1,0,1,
                0,1,1,1,
                0,0,1,1
        };

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mFragmentBuffer= cbb.asFloatBuffer();
        mFragmentBuffer.put(colors);
        mFragmentBuffer.position(0);

        miCount = 6;
        byte[] index = new byte[]{ //顶点索引
          0,1,2,0,2,3
        };

        mIndexBuffer = ByteBuffer.allocateDirect(index.length);
        mIndexBuffer.order(ByteOrder.nativeOrder());
        mIndexBuffer.put(index);
        mIndexBuffer.position(0);

    }

    private void initShader(){
        mProgram = shaderUtil.createProgram(mVertexShader,mFragmentShader);

        maPositionHandle = GLES30.glGetAttribLocation(mProgram,"aPosition");
        maColorHandle = GLES30.glGetAttribLocation(mProgram,"aColor");
    }

    public void drawSelf(){

        GLES30.glUseProgram(mProgram);

        //传入顶点位置数据
        GLES30.glVertexAttribPointer(maPositionHandle
                ,3,GLES30.GL_FLOAT,false,3*4,mVertexBuffer);

        //传入顶点颜色数据
        GLES30.glVertexAttribPointer(maColorHandle,
                4,GLES30.GL_FLOAT,false,4*4,mFragmentBuffer);
        GLES30.glEnableVertexAttribArray(maPositionHandle);//启用顶点位置数据
        GLES30.glEnableVertexAttribArray(maColorHandle);//启用顶点颜色数据
        GLES30.glDrawElements(GLES30.GL_TRIANGLES
                ,miCount,GLES30.GL_UNSIGNED_BYTE,mIndexBuffer);//开始绘制，传入索引
    }
}
