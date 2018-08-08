package com.example.asus1.learnopengl.shaders;

import android.opengl.GLES30;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {

    public static  float[] mProjectMatirx = new float[16];//4x4投影矩阵
    public static  float[] mVMatrix = new float[16]; //摄像机位置朝向的参数矩阵
    public static  float[] mMVPMatrix = new float[16];//总变换矩阵

    private int mProgram;
    private int muMVPMatrixHandle;//总变换矩阵的引用
    private int maPositionHandle; //顶点位置的引用
    private int maColorHandle; //顶点颜色属性引用

    private String mVertexShader = "uniform mat4 uMVPMatrix;" +
            "attribute vec3 aPosition;" +
            "attribute vec4 aColor;" +
            "varying vec4 vColor;" +
            "void main(){" +
            "gl_Position = uMVPMatrix* vec4(aPosition,1);" + //根据总变换矩阵计算此次绘制此顶点的位置
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

    private int mvCount = 0;//顶点数量
    private float mxAngle = 0; //绕X轴旋转的角度

    public Triangle() {
        initVertexData();
        initShader();
    }

    private void initVertexData(){
        mvCount = 3;
        float vertices[] = new float[]{
          -1,0,0,0,-1,0,1,0,0
        };//顶点位置

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序为本地操作系统顺序
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);//在缓冲区内写入数据
        mVertexBuffer.position(0);//设置缓冲区的起始位置

        float[] colors = new float[]{
          1,0,0,1,0,1,0,1,0,0,1,1
        };//顶点颜色数组
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mFragmentBuffer = cbb.asFloatBuffer();
        mFragmentBuffer.put(colors);
        mFragmentBuffer.position(0);
    }

    private void initShader(){
        mProgram = shaderUtil.createProgram(mVertexShader,mFragmentShader);
        maPositionHandle = GLES30.glGetAttribLocation(mProgram,"aPosition");
        maColorHandle = GLES30.glGetAttribLocation(mProgram,"aColor");
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram,"uMVPMatrix");

    }

    public void drawSelf(){
        GLES30.glUseProgram(mProgram);
        Matrix.setRotateM(mMMatrix,0,0,0,1,0);//初始化变换矩阵
        Matrix.translateM(mMMatrix,0,0,0,1);//向z轴平移1位
        Matrix.rotateM(mMMatrix,0,mxAngle,1,0,0);//绕X轴旋转
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,
                false,getFinalMatrix(mMMatrix),0);//将最终的变换矩阵传进渲染管线。
        GLES30.glVertexAttribPointer(maPositionHandle,
                3,GLES30.GL_FLOAT,
                false,3*4,mVertexBuffer);//将顶点位置数据传进渲染管线
        GLES30.glVertexAttribPointer(
                maColorHandle,4,GLES30.GL_FLOAT,
                false,4*4,mFragmentBuffer);//将顶点颜色数据传进渲染管线
        GLES30.glEnableVertexAttribArray(maPositionHandle);//启用顶点位置数据
        GLES30.glEnableVertexAttribArray(maColorHandle);//启用顶点颜色数据
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,mvCount);//执行绘制
    }

    public static float[] getFinalMatrix(float[] spec){
        mMVPMatrix =new float[16];
        Matrix.multiplyMM(mMVPMatrix,0,
                mVMatrix,0,spec,0);//摄像机矩阵乘以变换矩阵

        Matrix.multiplyMM(mMVPMatrix,0,
                mProjectMatirx,0,mMVPMatrix,0);//投影矩阵乘以上一步的结果
        return mMVPMatrix;
    }

    public void setXAngle(float degree){
        mxAngle += degree;
    }


}
