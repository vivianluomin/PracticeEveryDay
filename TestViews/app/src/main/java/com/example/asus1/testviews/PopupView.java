package com.example.asus1.testviews;

import android.content.Context;
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
 * Created by asus1 on 2017/9/1.
 */

public class PopupView extends View {

    private Paint mPaint;
    private RectF mBound;
    private Path mPath;
    private float mWidth;
    private float mHeight;
    private float RecWidth;
    private float RecHeight;



    public PopupView(Context context) {
        this(context,null);
    }

    public PopupView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mBound = new RectF();
        mPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setStyle(Paint.Style.FILL);
        mBound.set(0,0,RecWidth,RecHeight);

        canvas.drawRoundRect(mBound,10,10,mPaint);

        mPath.moveTo(RecWidth/2-30,RecHeight);
        mPath.lineTo(RecWidth/2,RecHeight+20);
        mPath.lineTo(RecWidth/2+30,RecHeight);
        mPath.close();
        canvas.drawPath(mPath,mPaint);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width =MeasureSpec.getSize(widthMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);

        if(modeW == MeasureSpec.EXACTLY){
            mWidth = width;
            RecWidth = mWidth*0.5f;

        }

        if(modeH == MeasureSpec.EXACTLY){
            mHeight = height;
            RecHeight = mHeight*0.5f;
        }


        setMeasuredDimension((int) mWidth,(int) mHeight);

    }
}
