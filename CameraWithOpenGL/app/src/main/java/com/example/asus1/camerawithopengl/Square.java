package com.example.asus1.camerawithopengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer drawListBuffer;

    private int mProgram;

    private int mVertexHandler;
    private int mFragemntHandler;

    public static final int COODS_PRE_VERT = 3;

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    public static float[] squareCoods = {
      -0.5f,0.5f,0.0f,
      -0.5f,-0.5f,0.0f,
      0.5f,-0.5f,0.0f,
      0.5f,0.5f,0.0f
    };

    public short drawOder[] = {0,1,2,0,2,3};
    float[] color = {1, 1, 0, 1,
            0, 1, 1, 1,
            1, 0, 1, 1,
            0,1,1,1
                    };

    public Square(){
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoods.length*4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoods);
        vertexBuffer.position(0);

        bb = ByteBuffer.allocateDirect(drawOder.length*2);
        bb.order(ByteOrder.nativeOrder());
        drawListBuffer = bb.asShortBuffer();
        drawListBuffer.put(drawOder);
        drawListBuffer.position(0);

        bb = ByteBuffer.allocateDirect(color.length*4);
        bb.order(ByteOrder.nativeOrder());
        colorBuffer = bb.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram,vertexShader);
        GLES20.glAttachShader(mProgram,fragmentShader);
        GLES20.glLinkProgram(mProgram);

    }

    public void draw(){
        GLES20.glUseProgram(mProgram);
        mVertexHandler = GLES20.glGetAttribLocation(mProgram,"vPosition");
        GLES20.glEnableVertexAttribArray(mVertexHandler);
        GLES20.glVertexAttribPointer(mVertexHandler,
                COODS_PRE_VERT,
                GLES20.GL_FLOAT,false,COODS_PRE_VERT*4,vertexBuffer);

        mFragemntHandler = GLES20.glGetUniformLocation(mProgram,"vColor");
       // GLES20.glUniform4fv(mFragemntHandler,1,color,0);

        GLES20.glUniform4fv(mFragemntHandler,1,colorBuffer);


        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                drawOder.length,GLES20.GL_UNSIGNED_SHORT,drawListBuffer);

        GLES20.glDisableVertexAttribArray(mVertexHandler);
    }
}
