package com.example.asus1.camerawithopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class PreviewGLSurfaceView extends GLSurfaceView {

    public PreviewGLSurfaceView(Context context) {
        this(context,null);
    }

    public PreviewGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setEGLContextClientVersion(2);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setRenderer(new PreviewGLRender());
    }
}
