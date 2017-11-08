package com.example.asus1.sabeier;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.io.IOError;

/**
 * Created by asus1 on 2017/11/8.
 */

public class SaBeiErLine extends View{

    private Paint mPaint;
    private Path mPath;
    private Context mContext;
    private float mstartX;
    private float mstartY;
    private float mEndX;
    private  float mEndY;
    private Bitmap mBackgournd;
    private Bitmap mIcon;
    private float mWidthLine;
    private int mColor;
    private float mX;
    private float mY ;
   private  float  mCX;
    private float mCY;
    private int mWdith;
    private int mHeight;



    public SaBeiErLine(Context context) {
        this(context,null);
    }

    public SaBeiErLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SaBeiErLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();
        mstartX = 200;
        mstartY = 400;
        mEndX = 600;
        mEndY = 400;
        mX = 400;
        mY = 400;
        mWidthLine = 5;
        mCX = (mEndX-mstartX)/2+mstartX;
        mCY = mstartY-40;

    }

    private void setData(float startX,float startY, float endX,float endY,float widthLine){
        mstartY = startY;
        mstartX = startX;
        mEndY = endY;
        mEndX = endX;
        mWidthLine = widthLine;
        mX = (mEndX-mstartX)/2+mstartX;
        mY = startY;
        if(mIcon!=null){
            mCX = (mEndX-mstartX)/2+mstartX-mIcon.getWidth()/2;
            mCY = mstartY-mIcon.getHeight();
        }else{
            mCX = (mEndX-mstartX)/2+mstartX;
            mCY = mstartY-30;
        }


    }
    public void setStyle(int color,int backgound,int icom){
        mColor = color;
        mPaint.setColor(mColor);
        if(backgound!=0){
            mBackgournd = BitmapFactory.decodeResource(getResources(),backgound);
        }
        if(icom!=0){
            mIcon = BitmapFactory.decodeResource(getResources(),icom);
            mCY = mstartY-mIcon.getHeight();
            mCX =(mEndX-mstartX)/2+mstartX-mIcon.getWidth()/2;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mBackgournd!=null){
            canvas.drawBitmap(mBackgournd,mCX,mCY,mPaint);
        }
        if(mIcon!=null){
            canvas.drawBitmap(mIcon,mCX,mCY,mPaint);
        }else{
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            Log.d("drawCY",String.valueOf(mCY));
            canvas.drawCircle(mCX,mCY,30,mPaint);
        }

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mWidthLine);

        mPath.reset();

        mPath.moveTo(mstartX,mstartY);
       mPath.quadTo(mX,mY,mEndX,mEndY);
        canvas.drawPath(mPath,mPaint);

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        ViewGroup parent =(ViewGroup) getParent();
        if(parent!=null){
            mWdith=parent.getWidth();
            mHeight = parent.getHeight();
            setData(200,mHeight/2,mWdith-200,mHeight/2,10);
            invalidate();
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
    

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mX = (mEndX-mstartX)/2+mstartX;
                mY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                while(mY>mEndY){
                    mY-=10;
                    invalidate();
                }

               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       while(mCY>0){
                           mCY-=20;
                           Log.d("mcy",String.valueOf(mCY));
                           try {
                               Thread.sleep(50);
                           }catch (InterruptedException e){
                               e.printStackTrace();
                           }

                           postInvalidate();
                       }

                       if(mIcon==null){
                           mCY = mstartY-40;
                       }else{
                           mCY = mstartY-mIcon.getHeight();
                       }

                      postInvalidate();
                   }
               }).start();



        }

        return true;
    }
}
