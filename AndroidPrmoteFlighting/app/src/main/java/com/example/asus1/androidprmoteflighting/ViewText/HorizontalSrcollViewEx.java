package com.example.asus1.androidprmoteflighting.ViewText;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by asus1 on 2018/1/2.
 */

public class HorizontalSrcollViewEx extends ViewGroup {

    private static final String TAG = "HorizontalSrcollViewEx";

    private int mChildrenSize=0;
    private int mChildWidth=0;
    private int mChildIndex;
    //分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    private Context mContext;

    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private Scroller mSrcoller;
    private VelocityTracker mVelocityTracker;


    public HorizontalSrcollViewEx(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public HorizontalSrcollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public HorizontalSrcollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        mSrcoller = new Scroller(mContext);
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    //是否拦截某个事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int)ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mSrcoller.isFinished()){
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x -mLastXIntercept;
                int deltaY = y-mLastYIntercept;
                Log.d(TAG,"deltaX,deltaY: "+deltaX+" "+deltaY);
                if(Math.abs(deltaX)>Math.abs(deltaY)){
                    intercepted = true;
                }else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }

        Log.d(TAG,"intercepted="+intercepted);
        mLastX = x;
        mLastY = y;

        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!mSrcoller.isFinished()){
                   mSrcoller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                int scrollToChlidIndex = scrollX/mChildWidth;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if(Math.abs(xVelocity)>=50){
                    mChildIndex = xVelocity>0?mChildIndex-1:mChildIndex+1;
                }else {
                    mChildIndex = (scrollX+mChildWidth/2)/mChildWidth;
                }
                mChildIndex = Math.max(0,Math.min(mChildIndex,mChildrenSize-1));
                int dx = mChildWidth*mChildIndex-scrollX;
                smoothSrcollBy(dx,0);
                Log.d(TAG,"childIndex---"+mChildIndex);
                mVelocityTracker.clear();
                break;

        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothSrcollBy(int dx,int dy){
        mSrcoller.startScroll(getScrollX(),0,dx,0,500);
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeigth = 0;
        int childCount = getChildCount();
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int heigthSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(childCount == 0){
            setMeasuredDimension(0,0);
        }else if(widthMode == MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
            View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth()*childCount;
            measuredHeigth = childView.getMeasuredHeight();
            setMeasuredDimension(measuredWidth,measuredHeigth);
        }else if(heightMode == MeasureSpec.AT_MOST){
            View childView = getChildAt(0);
            measuredHeigth = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize,measuredHeigth);
        }else if(widthMode == MeasureSpec.AT_MOST){
            View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth()*childCount;
            setMeasuredDimension(measuredWidth,heigthSpaceSize);
        }

    }

    @Override
    public void computeScroll() {
        if(mSrcoller.computeScrollOffset()){
            scrollTo(mSrcoller.getCurrX(),mSrcoller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childleft = 0;
        int childCount  = getChildCount();
        mChildrenSize = childCount;
        for(int i =0;i<childCount;i++){
            View viewchild = getChildAt(i);
            if(viewchild.getVisibility()!=GONE){
                int childwidth = viewchild.getMeasuredWidth();
                mChildWidth = childwidth;
                viewchild.layout(childleft,0,childleft+childwidth,viewchild.getMeasuredHeight());
                childleft+=childwidth;
            }
        }
    }
}
