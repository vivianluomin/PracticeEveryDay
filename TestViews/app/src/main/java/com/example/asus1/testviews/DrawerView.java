package com.example.asus1.testviews;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

/**
 * Created by asus1 on 2017/9/7.
 */

public class DrawerView extends ViewGroup implements View.OnClickListener{

    private Context mContext;
    int t;


    private boolean misChange = false;

    public DrawerView(Context context) {
        this(context,null);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        measureChildren(widthMeasureSpec,heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if(!misChange){
            layoutBottom();
            int ChildCount = getChildCount();
            for(int i = 1 ;i < ChildCount;i++){
                ImageButton imageButton = (ImageButton) getChildAt(i);
                int childWidth  = imageButton.getMeasuredWidth();
                int childHeight = imageButton.getMeasuredHeight();
                imageButton.layout(0,getMeasuredHeight()-(childHeight*(i+1)+childHeight/3*i),childWidth,getMeasuredHeight()-childHeight*(i)-childHeight/3*i);
                imageButton.setVisibility(GONE);
            }
        }

    }

    private void layoutBottom(){
        ImageButton imageButton =(ImageButton) getChildAt(0);

        imageButton.setOnClickListener(this);
        int width = imageButton.getMeasuredWidth();
        int height  = imageButton.getMeasuredHeight();
        int l = 0;
        t = getMeasuredHeight()-height;
        int r = width;
        int b = getMeasuredHeight();

        imageButton.layout(l,t,r,b);


    }

    @Override
    public void onClick(View v) {
        int childCount = getChildCount();
        if(!misChange){
            misChange = true;

            for(int i =1 ;i<childCount ;i++){
                ImageButton imageButton = (ImageButton)getChildAt(i);
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        -imageButton.getMeasuredWidth(),0,0,0);
                translateAnimation.setDuration(1000+i*100);
                imageButton.startAnimation(translateAnimation);
                imageButton.setVisibility(VISIBLE);
            }
        }else {
            misChange  = false;
            for(int i =1;i<childCount;i++){
                ImageButton imageButton = (ImageButton)getChildAt(i);
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        0,-imageButton.getMeasuredWidth(),0,0);
                translateAnimation.setDuration(1000+i*100);
                imageButton.startAnimation(translateAnimation);
                imageButton.setVisibility(GONE);
            }
        }

    }


}
