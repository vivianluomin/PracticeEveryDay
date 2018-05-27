package com.example.asus1.testviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by asus1 on 2017/9/4.
 */

public class WaveView extends View {

    private Paint mPaint;
    private RectF mRectF;
    private int mWidth;
    private int mHeight;
    private int mMode;
    private int mWaveCount;
    private int mWaveWidth;
    private int mWaveHeight;
    private Context mContext;
    private int mColor;
    private int mRectWidth;
    private int mRectHeight;

    private static final int CICLE = 0;
    private static final int TRIANGLE = 1;


    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.
                obtainStyledAttributes(attrs,R.styleable.WaveView,defStyleAttr,0);

        mMode = array.getInteger(R.styleable.WaveView_Mode,0);
        mWaveCount = array.getInteger(R.styleable.WaveView_WaveCount,10);
        mWaveWidth = array.getInteger(R.styleable.WaveView_WaveWidth,20);
        mColor = array.getColor(R.styleable.WaveView_android_color, Color.parseColor("#ff0000"));

        array.recycle();
        init();

    }

    private void init(){
        mPaint = new Paint();
        mRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(width_mode == MeasureSpec.EXACTLY){
            mWidth = width;

        }else if (width_mode == MeasureSpec.AT_MOST){

            mWidth = PxUtill.dpTopx(300,mContext);

        }

        mRectWidth = (int) (mWidth*0.8);

        if(height_mode == MeasureSpec.EXACTLY){
            mHeight = height;

        }else if(height_mode == MeasureSpec.AT_MOST){
            mHeight = PxUtill.dpTopx(200,mContext);

        }
        mRectHeight = (int)(mHeight*0.8);

        setMeasuredDimension(mWidth,mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mColor);

        mWaveHeight = mRectHeight/mWaveCount;


        float leftPadding = (mWidth - mRectWidth)/2;
        float topPadding = (mHeight -mRectHeight)/2;

        mRectF.set(leftPadding,topPadding,leftPadding+mRectWidth,topPadding+mRectHeight);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mRectF,mPaint);

        if(mMode == TRIANGLE){
            Path path = new Path();
            path.moveTo(leftPadding+mRectWidth,topPadding);
            for(int i = 0;i < mWaveCount; i++){

                path.lineTo(leftPadding+mRectWidth+mWaveWidth,topPadding+mWaveHeight*i+mWaveHeight/2);
                path.lineTo(leftPadding+mRectWidth,topPadding+mWaveHeight*(i+1));

            }
            path.close();
            canvas.drawPath(path,mPaint);

            path.moveTo(leftPadding,topPadding);

            for(int i = 0; i < mWaveCount; i++){
                path.lineTo(leftPadding-mWaveWidth,topPadding+mWaveHeight*i+mWaveHeight/2);
                path.lineTo(leftPadding,topPadding+mWaveHeight*(i+1));
            }

            path.close();
            canvas.drawPath(path,mPaint);
        }


        super.onDraw(canvas);

    }
}
