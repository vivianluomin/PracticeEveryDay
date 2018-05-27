package com.example.asus1.shareapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by asus1 on 2017/7/12.
 */

public class BitmapWorkerTask extends AsyncTask<Integer,Integer,Bitmap> {

    private final WeakReference imageViewReference;
    public int data = 0;
    Resources resources;
    public BitmapWorkerTask(ImageView imageView,Resources resources){
        imageViewReference = new WeakReference(imageView);
        this.resources = resources;

    }


    @Override
    protected Bitmap doInBackground(Integer... params) {
        data = params[0];
        Log.d("back",String.valueOf(data));
        return decodeSampleBitmapFromResource(resources,data,100,150);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if(imageViewReference !=null && bitmap !=null){
            final ImageView imageView = (ImageView) imageViewReference.get();
            final BitmapWorkerTask bitmapWorkerTask = PictureAdapter.getBitmapTask(imageView);
            Log.d("post0","aaaaa");
            if(this == bitmapWorkerTask && imageView != null){
                Log.d("post","aaaaa");
                imageView.setImageBitmap(bitmap);
            }
//            if(imageView !=null){
//                imageView.setImageBitmap(bitmap);
//            }

            }
    }

    public static Bitmap decodeSampleBitmapFromResource(Resources resources,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options= new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(resources,resId,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources,resId,options);
    }

    public  static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height/2;
            final int halfWidth = width/2;

            while((halfHeight /inSampleSize)>reqHeight &&(halfWidth / inSampleSize)>reqWidth){
                inSampleSize *=2;
            }
        }
        return inSampleSize;
    }
}
