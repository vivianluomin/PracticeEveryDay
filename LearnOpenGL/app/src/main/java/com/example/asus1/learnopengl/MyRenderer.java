package com.example.asus1.learnopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by asus1 on 2018/2/21.
 */

public class MyRenderer implements GLSurfaceView.Renderer {

    private int program;
    private int vPosition;
    private int uColor;

    private float[] mTraiangleArray = {
            0f,1f,0f,
            -1f,-1f,0f,
            1f,-1f,0f
    };

    private float[] mColor = new float[]{
          1,1,0,1,
          0,1,1,1,
          1,0,1,1
    };

    private FloatBuffer mTriangleBuffer;
    private FloatBuffer mColorBuffer;


    public MyRenderer(){
        //点相关
        ByteBuffer bb = ByteBuffer.allocateDirect(mTraiangleArray.length*4);
        bb.order(ByteOrder.nativeOrder());
        mTriangleBuffer = bb.asFloatBuffer();
        mTriangleBuffer.put(mTraiangleArray);
        mTriangleBuffer.position(0);

        //颜色相关
        ByteBuffer bb2 = ByteBuffer.allocateDirect(mColor.length*4);
        bb2.order(ByteOrder.nativeOrder());
        mColorBuffer = bb2.asFloatBuffer();
        mColorBuffer.put(mColor);
        mColorBuffer.position(0);
    }


    private int loadShader(int shaderType,String sourceCode){

        int shader = GLES20.glCreateShader(shaderType);

        if(shader != 0){
            GLES20.glShaderSource(shader,sourceCode);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS
                    ,compiled,0);
            if (compiled[0] == 0){
                Log.e("ES20_ERROR","Could not compile shader "+shaderType+" :");
                Log.e("ES20_ERROE",GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }

        return shader;

    }

    private int createProgram(String vertexSource,String fragmentSource){
        int vertecShader = loadShader(GLES20.GL_VERTEX_SHADER,vertexSource);
        if(vertecShader==0){
            return  0;
        }

        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentSource);
        if(pixelShader == 0){
            return  0;
        }

        int program = GLES20.glCreateProgram();

        if(program !=0){
            GLES20.glAttachShader(program,vertecShader);
            GLES20.glAttachShader(program,pixelShader);
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program,GLES20.GL_LINK_STATUS,linkStatus,0);
            if(linkStatus[0]!=GLES20.GL_TRUE ){
                Log.e("ES20_ERROR","Could not link program :");
                Log.e("ES20_ERROE",GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
       return program;
    }

    private FloatBuffer getVertices(){
        float vertices[] = {
                0.0f,0.5f,
                -0.5f,-0.5f,
                0.5f,-0.5f
        };

        ByteBuffer  vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuf = vbb.asFloatBuffer();
        vertexBuf.position(0);

        return vertexBuf;
    }

    @Override
    public void onDrawFrame(GL10 gl) {

//        FloatBuffer vertices = getVertices();
//
//        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
//                | GLES20.GL_COLOR_BUFFER_BIT);
//        GLES20.glUseProgram(program);
//        GLES20.glVertexAttribPointer(vPosition,
//                2,GLES20.GL_FLOAT,
//                false,0,vertices);
//        GLES20.glEnableVertexAttribArray(vPosition);
//
//        GLES20.glUniform4f(uColor,0.0f,1.0f,0.0f,1.0f);
//
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,3);

        //清楚屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);

        //重置当前的模型观察矩阵
        gl.glLoadIdentity();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glTranslatef(0f,0f,-2f);

        gl.glVertexPointer(3,GL10.GL_FLOAT,0,mTriangleBuffer);

        gl.glColorPointer(4,GL10.GL_FLOAT,0,mColorBuffer);

        gl.glDrawArrays(GL10.GL_TRIANGLES,0,3);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glFinish();



    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

//       GLES20.glViewport(0,0,width,height);

        float ratio = (float)width/height;
        gl.glViewport(0,0,width,height);

        //设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        //设置视口大小
        gl.glFrustumf(-ratio,ratio,-1,1,1,10);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();


    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        program = createProgram(verticesShader,fragmentShader);
//
//        vPosition = GLES20.glGetAttribLocation(program,"vPosition");
//        uColor = GLES20.glGetUniformLocation(program,"uColor");
//
//        GLES20.glClearColor(1.0f,0,0,1.0f);

        gl.glClearColor(1,1,1,1);

    }


    private static final String verticesShader
            = "attribute vec2 vPosition;            \n" // 顶点位置属性vPosition
            + "void main(){                         \n"
            + "   gl_Position = vec4(vPosition,0,1);\n" // 确定顶点位置
            + "}";

    // 片元着色器的脚本
    private static final String fragmentShader
            = "precision mediump float;         \n" // 声明float类型的精度为中等(精度越高越耗资源)
            + "uniform vec4 uColor;             \n" // uniform的属性uColor
            + "void main(){                     \n"
            + "   gl_FragColor = uColor;        \n" // 给此片元的填充色
            + "}";



}
