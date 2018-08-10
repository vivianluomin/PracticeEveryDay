package com.example.asus1.learnopengl.shaders;

import android.graphics.Matrix;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TriangleTexture {

    private  String mVertexShader=
            "attribute vec3 aPosition;" + //顶点位置
                    "attribute vec2 aTexCoord;" + //顶点纹理位置
                    "varying vec2 vTextureCoord;" + //用于传递给片元着色器的易变变量
                    "void main(){" +
                    "gl_Position = vec4(aPosition,1);" +
                    "vTextureCoord = aTexCoord;" +
                    "}";
    private String mFragmentShader =
            "precision mediump float;" +
                    "varying vec2 vTextureCoord;" +
                    "uniform sampler2D sTexture;" + //纹理采样器，代表一副纹理
                    "void main(){" +
                    "gl_FragColor = texture2D(sTexture,vTextureCoord);" +//进行纹理采样
                    "}";

    private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer mTexCoordBuffer;//顶点纹理坐标数据缓冲

    private int mvCount = 0;//顶点数量

    private int maPositionHandle;
    private int maTexCoordHandle;
    private int mProgram;

    public TriangleTexture(){
        initVertext();
        initShder();
    }

    private void  initVertext(){
        mvCount = 3;

        mvCount = 3;
        float vertices[] = new float[]{
                0,1,0,-1,0,0,1,0,0
        };//顶点位置

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序为本地操作系统顺序
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);//在缓冲区内写入数据
        mVertexBuffer.position(0);//设置缓冲区的起始位置

        float[] colors = new float[]{
                0.5f,0,
                0,1,
                1,1
        };//顶点颜色数组
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mTexCoordBuffer = cbb.asFloatBuffer();
        mTexCoordBuffer.put(colors);
        mTexCoordBuffer.position(0);
    }

    private void initShder(){
        mProgram = shaderUtil.createProgram(mVertexShader,mFragmentShader);
        maPositionHandle = GLES30.glGetAttribLocation(mProgram,"aPosition");
        maTexCoordHandle = GLES30.glGetAttribLocation(mProgram,"aTexCoord");
    }

    public void drawSelf(int textId){

        GLES30.glUseProgram(mProgram);

        GLES30.glVertexAttribPointer(maPositionHandle,3,
                GLES30.GL_FLOAT,false,3*4,mVertexBuffer);
        GLES30.glVertexAttribPointer(maTexCoordHandle,2,
                GLES30.GL_FLOAT,false,2*4,mTexCoordBuffer);
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        GLES30.glEnableVertexAttribArray(maTexCoordHandle);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0); //设置使用的纹理编号
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,textId); //绑定指定的纹理id

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,mvCount);


    }
}
