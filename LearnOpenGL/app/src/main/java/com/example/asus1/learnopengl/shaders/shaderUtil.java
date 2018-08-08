package com.example.asus1.learnopengl.shaders;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

public class shaderUtil {

    private static final String ERROR   = "ES30_ERROR";

    public  static  int loadShader(int shaderType,String shaderSource){

        int shader = GLES30.glCreateShader(shaderType); //创建shader并记录它的id
        if(shader != 0){
            GLES30.glShaderSource(shader,shaderSource);//加载着色器代码
            GLES30.glCompileShader(shader);//编译

            int[] compiled = new int[1];
            //获取shader编译情况
            GLES30.glGetShaderiv(shader,GLES30.GL_COMPILE_STATUS,compiled,0);
            if(compiled[0] == 0){ //如果编译失败
                Log.e(ERROR,"Could not compile shader "+shaderType);
                Log.e(ERROR, GLES30.glGetShaderInfoLog(shader) );
                GLES30.glDeleteShader(shader);
                shader = 0;
            }
        }

        return  shader;
    }

    /*
     通过调用loadShader方法，分别加载顶点着色器与片元着色器的源代码进GPU，并分别进行编译
     然后创建一个着色器程序，分别将相应的顶点与片元着色器添加其中
     ，最后将两个着色器链接为一个整体着色器程序
     */

    public static  int createProgram(String vertexSource,String fragmentSource){

        //加载顶点着色器
        int vextexShder = loadShader(GLES30.GL_VERTEX_SHADER,vertexSource);
        if(vextexShder == 0){
            return  0;
        }

        //加载片元着色器
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER,fragmentSource);
        if(fragmentShader == 0){
            return  0;
        }

        int program = GLES30.glCreateProgram();
        if(program != 0){
            GLES30.glAttachShader(program,vextexShder);
            checkGlError("glAttachShader");
            GLES30.glAttachShader(program,fragmentShader);
            checkGlError("glAttachShader");
            GLES30.glLinkProgram(program);//链接程序

            int[] linkStatus = new int[1];
            GLES30.glGetProgramiv(program,GLES30.GL_LINK_STATUS,linkStatus,0);
            if(linkStatus[0] != GLES30.GL_TRUE){
                Log.e(ERROR, "Could not link program" );
                Log.e(ERROR, GLES30.glGetProgramInfoLog(program));
                GLES30.glDeleteProgram(program);
                program = 0;
            }


        }
       return  program;
    }

    public  static  void checkGlError(String op){
        int error;
        if((error = GLES30.glGetError())!=GLES30.GL_NO_ERROR){
            Log.e(ERROR,op+": glError "+error);
            throw  new RuntimeException(op+": glError "+error);
        }
    }
}
