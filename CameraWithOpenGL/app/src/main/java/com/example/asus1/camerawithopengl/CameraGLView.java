package com.example.asus1.camerawithopengl;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CameraGLView extends GLSurfaceView {

    public Camera mCamera;

    private static final String TAG = "CameraGLView";

    public CameraGLView(Context context) {
        this(context,null);
    }

    public CameraGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "CameraGLView: ");
        
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    

}


 class CameraRenderer implements GLSurfaceView.Renderer {

    private int vertexShader;
    private int fragmentShader;
    private int mShaderProgram;
    private FloatBuffer mDataBuffer;

    private Camera mCamera;

     private static final String TAG = "CameraRenderer";
    
    private int mOESTextureId;
    private SurfaceTexture mSurfaceTexture;

    private float[] transformMatrix = {
            0,1,0,1,
            0,0,0,1,
            1,0,0,1,
            1,1,0,1

    };

    private static final String VERTEX_SHADER = "" +
            //顶点坐标
            "attribute vec4 aPosition;\n" +
            //纹理矩阵
            "uniform mat4 uTextureMatrix;\n" +
            //自己定义的纹理坐标
            "attribute vec4 aTextureCoordinate;\n" +
            //传给片段着色器的纹理坐标
            "varying vec2 vTextureCoord;\n" +
            "void main()\n" +
            "{\n" +
            //根据自己定义的纹理坐标和纹理矩阵求取传给片段着色器的纹理坐标
            "  vTextureCoord = (uTextureMatrix * aTextureCoordinate).xy;\n" +
            "  gl_Position = aPosition;\n" +
            "}\n";

    private static final String FRAGMENT_SHADER = "" +
            //使用外部纹理必须支持此扩展
            "#extension GL_OES_EGL_image_external : require\n" +
            "precision mediump float;\n" +
            //外部纹理采样器
            "uniform samplerExternalOES uTextureSampler;\n" +
            "varying vec2 vTextureCoord;\n" +
            "void main() \n" +
            "{\n" +
            //获取此纹理（预览图像）对应坐标的颜色值
            "  vec4 vCameraColor = texture2D(uTextureSampler, vTextureCoord);\n" +
            //求此颜色的灰度值
            "  float fGrayColor = (0.3*vCameraColor.r + 0.59*vCameraColor.g + 0.11*vCameraColor.b);\n" +
            //将此灰度值作为输出颜色的RGB值，这样就会变成黑白滤镜
            "  gl_FragColor = vec4(fGrayColor, fGrayColor, fGrayColor, 1.0);\n" +
            "}\n";

    //每行前两个值为顶点坐标，后两个为纹理坐标
    private static final float[] vertexData = {
            1f,  1f,  1f,  1f,
            -1f,  1f,  0f,  1f,
            -1f, -1f,  0f,  0f,
            1f,  1f,  1f,  1f,
            -1f, -1f,  0f,  0f,
            1f, -1f,  1f,  0f
    };
    private CameraGLView mGLSurfaceView;

    public CameraRenderer(CameraGLView parent) {
        mGLSurfaceView = parent;
        int PreviewWidth = 0;
        int PreviewHeight = 0;
        mCamera = Camera.open(1);
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

        // 如果sizeList只有一个我们也没有必要做什么了，因为就他一个别无选择
        if (sizeList.size() > 1) {
            Iterator<Camera.Size> itor = sizeList.iterator();
            while (itor.hasNext()) {
                Camera.Size cur = itor.next();
                if (cur.width >= PreviewWidth
                        && cur.height >= PreviewHeight) {
                    PreviewWidth = cur.width;
                    PreviewHeight = cur.height;
                    break;
                }
            }
        }
        parameters.set("orientation","portrait");
        //parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        parameters.setPreviewSize(PreviewWidth,PreviewHeight);
        mCamera.setDisplayOrientation(90);
        mCamera.setParameters(parameters);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mOESTextureId = createOESTextureObject();
        vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
        fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
        mShaderProgram = linkProgram(vertexShader, fragmentShader);
        mDataBuffer = createBuffer(vertexData);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        Log.d(TAG, "onDrawFrame: ");
        try {
            initSurfaceTexture();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(mSurfaceTexture!=null){
            mSurfaceTexture.updateTexImage();
            mSurfaceTexture.getTransformMatrix(transformMatrix);
        }

        //获取Shader中定义的变量在program中的位置
        int aPositionLocation = GLES20.glGetAttribLocation(mShaderProgram, "aPosition");
        int aTextureCoordLocation = GLES20.glGetAttribLocation(mShaderProgram, "aTextureCoordinate");
        int uTextureMatrixLocation = GLES20.glGetUniformLocation(mShaderProgram, "uTextureMatrix");
        int uTextureSamplerLocation = GLES20.glGetUniformLocation(mShaderProgram, "uTextureSampler");

        //激活纹理单元0
        GLES20. glActiveTexture(GLES20.GL_TEXTURE0);
        //绑定外部纹理到纹理单元0
        GLES20. glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mOESTextureId);
        //将此纹理单元床位片段着色器的uTextureSampler外部纹理采样器
        GLES20. glUniform1i(uTextureSamplerLocation, 0);

        //将纹理矩阵传给片段着色器
        GLES20. glUniformMatrix4fv(uTextureMatrixLocation, 1, false, transformMatrix, 0);

        //将顶点和纹理坐标传给顶点着色器
        if (mDataBuffer != null) {
            //顶点坐标从位置0开始读取
            mDataBuffer.position(0);
            //使能顶点属性
            GLES20. glEnableVertexAttribArray(aPositionLocation);
            //顶点坐标每次读取两个顶点值，之后间隔16（每行4个值 * 4个字节）的字节继续读取两个顶点值
            GLES20.glVertexAttribPointer(aPositionLocation, 2,GLES20. GL_FLOAT, false, 16, mDataBuffer);

            //纹理坐标从位置2开始读取
            mDataBuffer.position(2);
            GLES20.glEnableVertexAttribArray(aTextureCoordLocation);
            //纹理坐标每次读取两个顶点值，之后间隔16（每行4个值 * 4个字节）的字节继续读取两个顶点值
            GLES20. glVertexAttribPointer(aTextureCoordLocation, 2, GLES20.GL_FLOAT, false, 16, mDataBuffer);
        }

        //绘制两个三角形（6个顶点）
        GLES20. glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

    }

    public static int createOESTextureObject(){
        int[] tex = new int[1];
        GLES20.glGenTextures(1,tex,0);//生成纹理
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,tex[0]);//将此纹理绑定到外部纹理
        //设置纹理过滤参数
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        //解除纹理绑定
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        return tex[0];


    }

    public boolean initSurfaceTexture() throws IOException{
        //根据外部纹理ID创建SurfaceTexture
        mSurfaceTexture = new SurfaceTexture(mOESTextureId);
        mSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                //每获取到一帧数据时请求OpenGL ES进行渲染
                mGLSurfaceView.requestRender();
            }
        });
        //讲此SurfaceTexture作为相机预览输出
      mCamera.setPreviewTexture(mSurfaceTexture);
        //开启预览
       mCamera.startPreview();
        return true;
    }


    public FloatBuffer createBuffer(float[] vertexData) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        buffer.put(vertexData, 0, vertexData.length).position(0);
        return buffer;
    }

    //加载着色器，GL_VERTEX_SHADER代表生成顶点着色器，GL_FRAGMENT_SHADER代表生成片段着色器
    public int loadShader(int type, String shaderSource) {
        //创建Shader
        int shader = GLES20.glCreateShader(type);
        if (shader == 0) {
            throw new RuntimeException("Create Shader Failed!" + GLES20.glGetError());
        }
        //加载Shader代码
        GLES20. glShaderSource(shader, shaderSource);
        //编译Shader
        GLES20. glCompileShader(shader);
        return shader;
    }

    //将两个Shader链接至program中
    public int linkProgram(int verShader, int fragShader) {
        //创建program
        int program = GLES20.glCreateProgram();
        if (program == 0) {
            throw new RuntimeException("Create Program Failed!" +GLES20. glGetError());
        }
        //附着顶点和片段着色器
        GLES20. glAttachShader(program, verShader);
        GLES20. glAttachShader(program, fragShader);
        //链接program
        GLES20. glLinkProgram(program);
        //告诉OpenGL ES使用此program
        GLES20.  glUseProgram(program);
        return program;
    }


}
