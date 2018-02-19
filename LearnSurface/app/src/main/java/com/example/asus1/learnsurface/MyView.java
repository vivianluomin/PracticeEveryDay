package com.example.asus1.learnsurface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by asus1 on 2018/2/19.
 */

public class MyView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private Context mContext;
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean mIsdrawing;

    private Path mPath;
    private Paint mPaint;

    private int x = 0;
    private int y = 0;

    private final int TIME_IN_FRAME = 30;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsdrawing = true;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        new Thread(this).start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
       setBackgroundColor(Color.WHITE);

    }

    @Override
    public void run() {

        while (mIsdrawing){
            long startTime = System.currentTimeMillis();
            synchronized (mHolder){
                mCanvas = mHolder.lockCanvas();
                if(x<=getWidth()){
                    x+=1;

                }else{
                    x = 1;
                }
                y = (int)(100*Math.sin(x*2*Math.PI/180)+400);
                mPath.lineTo(x,y);
                mCanvas.drawPath(mPath,mPaint);

                mHolder.unlockCanvasAndPost(mCanvas);
            }

            long entTime = System.currentTimeMillis();
            int difftime = (int)(entTime - startTime);
            while(difftime <= TIME_IN_FRAME){
                difftime = (int)(System.currentTimeMillis() - startTime);
                Thread.yield();
            }
        }


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        mIsdrawing = false;

    }
}
