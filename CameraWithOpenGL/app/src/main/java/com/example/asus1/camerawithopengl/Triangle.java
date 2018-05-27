package com.example.asus1.camerawithopengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;

public class Triangle {

    private FloatBuffer vertexBuffer;

    private int mPositionHandler;
    private  int mColorHandler;

    private int program;

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

    public static int COODS_PRE_VERTEX = 3;
    public static float[] triangleCoords = {   // in counterclockwise order:
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    float[] color = {1.0f, 0.0f, 0.0f, 1.0f };

    public Triangle(){
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length*4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program,vertexShader);
        GLES20.glAttachShader(program,fragmentShader);

        GLES20.glLinkProgram(program);

    }

    public void draw(){
        GLES20.glUseProgram(program);

        mPositionHandler = GLES20.glGetAttribLocation(program,"vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandler);
        GLES20.glVertexAttribPointer(mPositionHandler
                ,COODS_PRE_VERTEX,
                GLES20.GL_FLOAT,false,COODS_PRE_VERTEX*4,vertexBuffer);

        mColorHandler = GLES20.glGetUniformLocation(program,"vColor");
        GLES20.glUniform4fv(mColorHandler,1,color,0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,COODS_PRE_VERTEX);

        GLES20.glDisableVertexAttribArray(mPositionHandler);


    }
}
