package com.example.asus1.imagesearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = findViewById(R.id.iv_image);
        mBitmap = BitmapFactory.decodeResource(getResources()
                ,R.mipmap.ic_image);
        fudiao();
    }

    private void heibai(){
        ColorMatrix matrix = new ColorMatrix();
        Bitmap bitmap =mBitmap.copy(Bitmap.Config.ARGB_8888,true);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);
        matrix.set(new float[]{
                0.213f,0.715f,0.072f,0,0,
                0.213f,0.715f,0.072f,0,0,
                0.213f,0.715f,0.072f,0,0,
                0,0,0,1,0
        });
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(bitmap,0,0,paint);
        mImage.setImageBitmap(bitmap);
    }

    private void baohedu(){
        ColorMatrix matrix = new ColorMatrix();
        Bitmap bitmap =mBitmap.copy(Bitmap.Config.ARGB_8888,true);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);
        matrix.set(new float[]{
                1.0f,0.0f,0.0f,0,50,
                0.0f,1.0f,0.0f,0,50,
                0.0f,0.0f,1.0f,0,50,
                0,0,0,1,0
        });
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(bitmap,0,0,paint);
        mImage.setImageBitmap(bitmap);
    }

    private void huaijiu(){
        ColorMatrix matrix = new ColorMatrix();
        Bitmap bitmap =mBitmap.copy(Bitmap.Config.ARGB_8888,true);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);
        matrix.set(new float[]{
                0.393f,0.769f,0.189f,0,0,
                0.349f,0.686f,0.168f,0,0,
                0.272f,0.534f,0.131f,0,0,
                0,0,0,1,0
        });
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(bitmap,0,0,paint);
        mImage.setImageBitmap(bitmap);
    }

    private void fudiao(){

        Bitmap bitmap =mBitmap.copy(Bitmap.Config.ARGB_8888,true);

       int[] newP = new int[bitmap.getWidth()*bitmap.getHeight()];
        int[] old = new  int[bitmap.getWidth()*bitmap.getHeight()];

        bitmap.getPixels(old,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());

        for(int i = 1;i<old.length;i++){
            int oldcolor = old[i-1];
            int r0 = Color.red(oldcolor);
            int g0 = Color.green(oldcolor);
            int b0 = Color.blue(oldcolor);
            int a0 = Color.alpha(oldcolor);

            int color = old[i];
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            int a = Color.alpha(color);

            int r1 = r-r0+128;
            if(r1<0){
                r1 = 0;
            }else if(r1>255){
                r1 = 255;
            }

            int g1 = g-g0+128;
            if(g1<0){
                g1 = 0;
            }else if(g1>255){
                g1 = 255;
            }


            int b1 = b-b0+128;
            if(b1<0){
                b1 = 0;
            }else if(b1>255){
                b1 = 255;
            }

            newP[i] = Color.argb(a,r1,g1,b1);

        }

       // paint.setColorFilter(new ColorMatrixColorFilter(matrix));
       // canvas.drawBitmap(bitmap,0,0,paint);
        bitmap.setPixels(newP,0,bitmap.getWidth(),0,0,
                bitmap.getWidth(),bitmap.getHeight());
        mImage.setImageBitmap(bitmap);
    }
}
