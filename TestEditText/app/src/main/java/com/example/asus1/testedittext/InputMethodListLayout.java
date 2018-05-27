package com.example.asus1.testedittext;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.RelativeLayout;

/**
 * Created by asus1 on 2017/8/23.
 */

public class InputMethodListLayout extends RelativeLayout {

    private int screenHeight;
    private boolean sizeChanged = false;
    private OnSizeChangedListener onSizeChangedListener;
    private int wight;
    private int hight;

    public InputMethodListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Display localDisplay = ((Activity)context).getWindowManager().getDefaultDisplay();
        screenHeight = localDisplay.getHeight();
    }

    public InputMethodListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputMethodListLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.hight = heightMeasureSpec;
        this.wight = widthMeasureSpec;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(h>=oldh|| (Math.abs(h-oldh)<=1*this.screenHeight/4)){
            sizeChanged = false;
        }else if(h<=oldh || (Math.abs(h-oldh)<= 1*this.screenHeight/4)){
            sizeChanged = true;
        }
        this.onSizeChangedListener.onSizeChange(sizeChanged);
        measure(this.wight -w +getWidth(),this.hight-h+getHeight());
    }

    public void setOnSizeChangedListener(OnSizeChangedListener sizeChangedListener){
        this.onSizeChangedListener = sizeChangedListener;
    }

    public interface OnSizeChangedListener{
        void onSizeChange(boolean flag);
    }
}
