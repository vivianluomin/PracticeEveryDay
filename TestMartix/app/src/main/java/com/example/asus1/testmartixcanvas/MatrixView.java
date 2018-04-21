package com.example.asus1.testmartixcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by asus1 on 2018/4/21.
 */

public class MatrixView extends FrameLayout {

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
        matrix.reset();
        matrix.setTranslate(x,y);
        postInvalidate();
    }

    public void scale(int sx,int sy){
        matrix.reset();
        matrix.setScale(sx,sy);
        postInvalidate();
    }

    public void scale(int sx,int sy,int px,int py){
        matrix.reset();
        matrix.setScale(sx,sy,px,py);
        postInvalidate();
    }

  public void sinSoc (int sin,int cos){
      matrix.reset();
      matrix.setSinCos(sin,cos);
      postInvalidate();
  }

    public void sinSoc (int sin,int cos,int px,int py){
        matrix.reset();
        matrix.setSinCos(sin,cos,px,py);
        postInvalidate();
    }

    public void setSkew(int kx,int ky){
        matrix.reset();
        matrix.setSkew(kx,ky);
        postInvalidate();
    }

    public void setSkew(int kx,int ky,int px,int py){
        matrix.reset();
        matrix.setSkew(kx,ky,px,py);
        postInvalidate();
    }




}
