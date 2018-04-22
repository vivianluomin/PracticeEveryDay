package com.example.asus1.testmartixcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by asus1 on 2018/4/21.
 */

public class MatrixView extends View {

    private Context mContext;
    private Bitmap bitmap;
    private Matrix matrix;
    private Paint paint;
    public MatrixView(Context context) {
        this(context,null);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        matrix = new Matrix();
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.bitmap);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,matrix,paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void translate(int x,int y){
        //matrix.reset();
        matrix.setTranslate(x,y);
        postInvalidate();
    }

    public void scale(float sx,float sy){
        matrix.reset();
        matrix.setScale(sx,sy);
        postInvalidate();
    }

    public void scale(float sx,float sy,float px,float py){
        matrix.reset();
        matrix.setScale(sx,sy,px,py);
        postInvalidate();
    }

    public void rotate(int degree,float px,float py){
        matrix.reset();
        matrix.setRotate(degree,bitmap.getWidth()*px,bitmap.getHeight()*py);
        postInvalidate();
    }

  public void sinSoc (int sin,int cos){
      matrix.reset();
      matrix.setSinCos(sin,cos);
      postInvalidate();
  }

    public void sinSoc (float sin,float cos,float px,float py){
        matrix.reset();
        matrix.setSinCos(sin,cos,bitmap.getWidth()*px,bitmap.getHeight()*py);
        postInvalidate();
    }

    public void setSkew(float kx,float ky){
        matrix.reset();
        matrix.setSkew(kx,ky);
        postInvalidate();
    }

    public void setSkew(float kx,float ky,int px,int py){
        matrix.reset();
        matrix.setSkew(kx,ky,px,py);
        postInvalidate();
    }

    public void setPoly(){
        matrix.reset();
//        float src[] = {0,0};
//        float dts[] = {300,300};

        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();
//        float[] src = {bw / 2, bh / 2, bw, 0};
//        float[] dst = {bw / 2, bh / 2, bw / 2 + bh / 2, bh / 2 + bw / 2};
//        float[] src = {bw / 2, bh / 2, bw, 0};
//        float[] dst = {bw / 4, bh / 4, bw / 2 , 0};
//        float[] src = {0,0, 0,bh,bw,bh};
//        float[] dst = {0,0,200,bh,bw+200,bh};

        float[] src = {0,0, bw,0,0,bh,bw,bh};
        float[] dst = {100,0,bw-100,0,0,bh,bw,bh};
        matrix.setPolyToPoly(src,0,dst,0,4);
    }


    public void setInvert(){
//        int bw = bitmap.getWidth();
//        int bh = bitmap.getHeight();
//        matrix.reset();
//        matrix.setScale(-1,1);
//        matrix.postTranslate(bw,0);

        matrix.reset();
        float[] values = {
                1,0,400,
                0,1,400,
                0.05f,0f,1
        };
        matrix.setValues(values);

        postInvalidate();
    }


}
