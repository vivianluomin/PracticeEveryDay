package com.example.asus1.testviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by asus1 on 2017/9/7.
 */

public class TestViewGroup extends ViewGroup {

    private int mChildCount;
    private Context mContext;

    public TestViewGroup(Context context) {
        this(context,null);
    }

    public TestViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(mContext,attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //获取此ViewGroup上级容器所推荐的宽度和高度，及其计算模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeheight = MeasureSpec.getSize(heightMeasureSpec);



        //计算出所有childView的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        //如果是wrap_content，设置宽和高
        int  wdith;
        int height;


        mChildCount = getChildCount();
        int cWidth=0;
        int cHeight=0;

        //两者取最大
        int tWidth=0;
        int bWidth=0;

        //两者取最大
        int lHeight=0;
        int rHeight=0;
        MarginLayoutParams layoutParams = null;

        for(int i = 0 ;i < mChildCount;i++){
            View child  = getChildAt(i);
            cWidth = child.getMeasuredWidth();
            cHeight = child.getMeasuredHeight();
            layoutParams =(MarginLayoutParams) child.getLayoutParams();

            if(i == 0 || i ==1){
                tWidth+=(cWidth+layoutParams.leftMargin+layoutParams.rightMargin);
            }

            if(i == 2 || i == 3){
                bWidth +=(cWidth+layoutParams.leftMargin+layoutParams.rightMargin);
            }

            if(i == 0 || i == 2){
                lHeight += (cHeight+layoutParams.topMargin+layoutParams.bottomMargin);
            }

            if(i== 1 || i == 3){
                rHeight += (cHeight+layoutParams.topMargin+layoutParams.bottomMargin);
            }


            wdith = Math.max(tWidth,bWidth);
            height = Math.max(lHeight,rHeight);

            setMeasuredDimension((widthMode == MeasureSpec.EXACTLY)? sizeWidth:wdith
            ,(heightMode == MeasureSpec.EXACTLY)? sizeheight:height);

        }





    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mChildCount = getChildCount();
        int cWdith = 0;
        int cHeight = 0;
        MarginLayoutParams layoutParams = null;

        for(int i = 0; i< mChildCount ;i++){
            View childView  = getChildAt(i);
            cWdith = childView.getMeasuredWidth() ;
            cHeight = childView.getMeasuredHeight();
            layoutParams =(MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr = 0,cb = 0;

            switch (i){
                case 0:
                    cl=layoutParams.leftMargin;
                    ct=layoutParams.rightMargin;
                    break;
                case 1 :
                    cl = getWidth()-cWdith-layoutParams.rightMargin;
                    ct = layoutParams.topMargin;
                    break;
                case 2:
                    cl = layoutParams.rightMargin;
                    ct = getHeight()-cHeight-layoutParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth()-cWdith-layoutParams.rightMargin;
                    ct = getHeight()-cHeight-layoutParams.bottomMargin;
                    break;

            }

            cr = cl+cWdith;
            cb = ct+cHeight;

            childView.layout(cl,ct,cr,cb);
        }


    }
}
