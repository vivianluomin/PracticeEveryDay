package com.example.asus1.testviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by asus1 on 2017/9/10.
 */

public class CirclePercentView extends View {

    private Paint mPaith;
    private int mCircleRaduis;
    private int mCircleColor;
    private int mPercentColor;
    private int mCenterTextSize;
    private int mStartPercent;

    private int mPercent;

    private int mWdith;
    private int mHeight;
    private RectF mRectF;


    public CirclePercentView(Context context) {
        this(context,null);
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs,R.styleable.CirclePercentView,defStyleAttr,0);

        mCenterTextSize = array.getInt(R.styleable.CirclePercentView_CenterTextSize,12);
        mCircleColor = array.getColor(R.styleable.CirclePercentView_Circle_Color, Color.BLUE);
        mCircleRaduis = array.getInt(R.styleable.CirclePercentView_CircleRadius,30);
        mPercentColor = array.getColor(R.styleable.CirclePercentView_Percent_Color,Color.GRAY);
        mStartPercent = array.getInt(R.styleable.CirclePercentView_Start_Percent,30);

        array.recycle();
        init();


    }

    private void init(){

        mPaith = new Paint();
        mRectF = new RectF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int WidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int HeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int WidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int HeightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(WidthMode == MeasureSpec.EXACTLY){
            mWdith = WidthSize;
        }else {
            mWdith = mCircleRaduis*2+30;
        }

        if(HeightMode == MeasureSpec.EXACTLY){
            mHeight = HeightSize;
        }else {
            mHeight = mCircleRaduis*2+30;
        }

        setMeasuredDimension(mWdith,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaith.setColor(mCircleColor);
        mPaith.setAntiAlias(true);

        float cx = mWdith/2;
        float cy = mHeight/2;

        canvas.drawCircle(cx,cy,(float) mCircleRaduis,mPaith);
        mPaith.setColor(mPercentColor);
       mRectF.set(cx-mCircleRaduis,cy-mCircleRaduis,cx+mCircleRaduis,cy+mCircleRaduis);
        canvas.drawArc(mRectF,270,(float)mStartPercent,true,mPaith);

        mPaith.setColor(mCircleColor);
        canvas.drawCircle(cx,cy,mCircleRaduis-40,mPaith);

        mPaith.setColor(Color.WHITE);
        mPaith.setTextSize(18);
        String text = String.valueOf((int)(mStartPercent/3.6));
        canvas.drawText(text,cx-text.length()/2,cy,mPaith);

        super.onDraw(canvas);
    }

    public  void setCurrentPercent(int percent){
        if(percent> 100){
            return;
        }
        mPercent = percent;
        start();
    }

    private void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = mStartPercent;i<=mPercent*3.6;i++){
                    try {
                        Thread.sleep(15);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    mStartPercent = i;
                    CirclePercentView.this.postInvalidate();
                }
            }
        }).start();
    }
}
