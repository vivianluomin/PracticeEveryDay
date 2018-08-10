package com.example.asus1.learnopengl.shaders;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class SquareTexture {

    private int maPositionHandle;
    private int maTextCoordHandle;

    private String mVertexShader =
            "attribute vec3 aPosition;" +
                    "attribute vec2 aTexCoord;" +
                    "varying vec2 vTextureCoord;" +
                    "void main(){" +
                    "gl_Position = vec4(aPosition,1);" +
                    "vTextureCoord = aTexCoord;" +
                    "}";

    private String mFragmentShader=
            "precision mediump float;" +
                    "varying vec2 vTextureCoord;" +
                    "uniform sampler2D sTexture;" +
                    "void main(){" +
                    "gl_FragColor = texture2D(sTexture,vTextureCoord);" +
                    "}";  //纹理采样器，代表一副纹理;

    private  int mvCount= 0;
    private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer mFragmentBuffer;//顶点着色数据缓冲

    private ByteBuffer mIndexBuffer;//索引数据缓冲
    private int mProgram;
    private int miCount = 0;

    public SquareTexture(){

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
                1,0,
                1,1,
                0,1,
                0,0

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
        maTextCoordHandle = GLES30.glGetAttribLocation(mProgram,"aTexCoord");
    }

    public void drawSelf(int textId){

        GLES30.glUseProgram(mProgram);
        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,
                false,3*4,mVertexBuffer);
        GLES30.glVertexAttribPointer(maTextCoordHandle,2,GLES30.GL_FLOAT,
                false,2*4,mFragmentBuffer);
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        GLES30.glEnableVertexAttribArray(maTextCoordHandle);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,textId);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES,
                miCount,GLES30.GL_UNSIGNED_BYTE,mIndexBuffer);

    }

}
