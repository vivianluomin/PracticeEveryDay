package com.example.asus1.testwebview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by asus1 on 2017/9/29.
 */

public class LoaingView extends View {

    private Paint mCriclePaint;
    private Paint mLinePaith;
    private Paint mRectPaint;
    private Path mPath;
    private RectF mRectF;
    private float mwidth;
    private float mheight;
    private float mLinePercent;
    private float mCriclePercent;
    private float mPathPercent;
    private float mRisePercent;
    private boolean mDrawingFinish = false;
    private boolean mDrawStart = true;
    private boolean mLoadingStart = false;
    private  boolean mPathToLine = false;




    public LoaingView(Context context) {
        this(context,null);
    }

    public LoaingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoaingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mCriclePaint = new Paint();
        mLinePaith = new Paint();
        mPath = new Path();
        mRectF = new RectF();
        mRectPaint = new Paint();
        mLinePercent = 0;
        mPathPercent = 0;
        mCriclePercent = 0;
        mRisePercent = 0;
        setBackgroundColor(Color.parseColor("#1C86EE"));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY){
            mwidth = width;
        }else {
            mwidth = 200;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            mheight = height;
        }else {
            mheight = 200;
        }

        setMeasuredDimension((int) mwidth,(int) mheight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCriclePaint.setColor(Color.parseColor("#2ea4f2"));
        mCriclePaint.setStyle(Paint.Style.STROKE);
        mCriclePaint.setStrokeWidth(8);
        mCriclePaint.setAntiAlias(true);

        canvas.drawCircle(mwidth/2,mheight/2,mheight/2,mCriclePaint);
        mRectF.set(mwidth/2-mheight/2,0,mwidth/2+mheight/2,mheight);

        if(mDrawStart){
            mLinePaith.setColor(Color.WHITE);
            mLinePaith.setStrokeWidth(8);
            mLinePaith.setStyle(Paint.Style.STROKE);
            canvas.drawLine(mwidth/2,mheight/4,mwidth/2,mheight*0.7f,mLinePaith);

            mPath.moveTo(mwidth/2-mheight/2+10,mheight/2);
            mPath.lineTo(mwidth/2,mheight*0.75f);
            mPath.lineTo(mwidth/2+mheight/2-10,mheight/2);


            canvas.drawPath(mPath,mLinePaith);

            mDrawStart = false;
            mLoadingStart = true;

        }else if (mLoadingStart){
            if(mLinePercent <95){
                mLinePercent+=5;
                mPath.reset();
                mLinePaith.setColor(Color.WHITE);
                mLinePaith.setStrokeWidth(8);
                mLinePaith.setStyle(Paint.Style.STROKE);
                float temp = (mheight/4)*mLinePercent/100;
                canvas.drawLine(mwidth/2,mheight/4+temp,mwidth/2,mheight*0.75f-temp,mLinePaith);

                mPath.moveTo(mwidth/2-mheight/2+10,mheight/2);
                mPath.lineTo(mwidth/2,mheight*0.75f);
                mPath.lineTo(mwidth/2+mheight/2-10,mheight/2);
                canvas.drawPath(mPath,mLinePaith);

            }else {
                mLinePercent = 0;
            }

        }else if (mDrawingFinish){

            if(mPathToLine){
                mPath.reset();
                mLinePaith.setColor(Color.WHITE);
                mLinePaith.setStrokeWidth(8);
                mLinePaith.setStyle(Paint.Style.STROKE);
                if(mPathPercent<100){
                    mLinePaith.setStyle(Paint.Style.STROKE);
                    mPathPercent+=5;
                    mPath.reset();
                    mPath.moveTo(mwidth/2-mheight/2+10,mheight/2);
                    mPath.lineTo(mwidth/2,mheight*0.75f-mPathPercent/100f*0.25f*mheight);
                    mPath.lineTo(mwidth/2+mheight/2-10,mheight/2);

                    canvas.drawPath(mPath,mLinePaith);
                   canvas.drawCircle(mwidth/2,mheight/2,2.5f,mLinePaith);

                }else {

                    if(mRisePercent < 100){
                        mRisePercent+=5;
                        mLinePaith.setColor(Color.WHITE);
                        mLinePaith.setStrokeWidth(8);
                        mLinePaith.setStyle(Paint.Style.STROKE);
                        canvas.drawLine(mwidth/2-mheight/2+10,mheight/2,mwidth/2+mheight/2-10,mheight/2,mLinePaith);
                        canvas.drawCircle(mwidth/2,mheight/2-mheight/2*mRisePercent/100,2.5f,mLinePaith);
                        mRisePercent +=5;


                    }else {
                        if(mLinePercent < 100){
                            mLinePaith.setColor(Color.WHITE);
                            mLinePaith.setStrokeWidth(8);
                            mLinePaith.setStyle(Paint.Style.STROKE);
                            mLinePercent+=5;
                            mPath.reset();
                            mPath.moveTo(mwidth/2-mheight/2+5,mheight/2);
                            mPath.lineTo(mwidth/2,mheight/2+mLinePercent/100f*mheight*0.3f);
                            mPath.lineTo(mwidth/2+mheight/2-5,mheight/2-mLinePercent/100f*mheight*0.3f);
                            canvas.drawPath(mPath,mLinePaith);

                            if(mCriclePercent < 100){
                                canvas.drawArc(mRectF,270,-mCriclePercent/100f*360,false,mLinePaith);
                                mCriclePercent+=5;
                            }
                        }else{
                            mLinePaith.setColor(Color.WHITE);
                            mLinePaith.setStrokeWidth(8);
                            mLinePaith.setStyle(Paint.Style.STROKE);
                            mPath.reset();
                            mPath.moveTo(mwidth/2-mheight/2+10,mheight/2);
                            mPath.lineTo(mwidth/2,mheight*0.75f);
                            mPath.lineTo(mwidth/2+mheight/2-10,mheight*0.3f);
                            canvas.drawPath(mPath,mLinePaith);

                            canvas.drawArc(mRectF,270,-360,true,mLinePaith);
                        }

                    }
                }

            }

        }

        postInvalidateDelayed(10);
        super.onDraw(canvas);
    }

    public void start(){
        mDrawStart = true;
        mLoadingStart= false;
        mDrawingFinish = false;
        mLinePercent = 0;
        mPathPercent = 0;
        mCriclePercent = 0;
        mRisePercent = 0;
        invalidate();
    }



    public void  setLoadingFinish(){
        mLoadingStart = false;
        mDrawingFinish = true;
        mPathToLine = true;
        mLinePercent = 0;

        postInvalidateDelayed(10);
    }

}
