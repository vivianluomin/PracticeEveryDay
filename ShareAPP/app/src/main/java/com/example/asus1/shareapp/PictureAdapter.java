package com.example.asus1.shareapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by asus1 on 2017/7/12.
 */

public class PictureAdapter extends BaseAdapter {
    Context context;
    int[] reseIds;
    int layId;

    public PictureAdapter(Context context,int[] reseIds,int layId) {
        super();
        this.context=context;
        this.reseIds = reseIds;
        this.layId=layId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(layId,parent,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.item_image);
        //imageView.setImageResource(reseIds[position]);
        Log.d("getView",String.valueOf(reseIds[position]));
         loadBitmap(reseIds[position],imageView);

        return view;
    }

    @Override
    public int getCount() {
        return reseIds.length;
    }

    @Override
    public Object getItem(int position) {
        return reseIds[position];
    }

    @Override
    public long getItemId(int position) {
        return reseIds[position];
    }

    public void loadBitmap(int resId, ImageView imageView){
        if(cancelPotentialWork(resId,imageView)){
            BitmapWorkerTask task = new BitmapWorkerTask(imageView,context.getResources());
            Bitmap holder = BitmapFactory.decodeResource(context.getResources(),resId);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(context.getResources(),holder,task);
            imageView.setImageDrawable(asyncDrawable);
            Log.d("load",String.valueOf(resId));
            task.execute(resId);
        }
//        BitmapWorkerTask task = new BitmapWorkerTask(imageView,context.getResources());
//        task.execute(resId);


    }

    static class AsyncDrawable extends BitmapDrawable{
        private final WeakReference reference;

        public  AsyncDrawable(Resources resources, Bitmap bitmap,BitmapWorkerTask bitmapWorkerTask){
            super(resources,bitmap);
            reference= new WeakReference(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitTask(){
            return  (BitmapWorkerTask)reference.get();
        }
    }

    public static boolean cancelPotentialWork(int data,ImageView imageView){
        final BitmapWorkerTask bitmapWorkerTask = getBitmapTask(imageView);

        if(bitmapWorkerTask !=null){
            final  int bitmapData = bitmapWorkerTask.data;
            if(bitmapData == 0 || bitmapData != data){
                bitmapWorkerTask.cancel(true);
            }else {
                return false;
            }
        }
        return true;
    }
    public static BitmapWorkerTask getBitmapTask(ImageView imageView){
        if(imageView !=null){
            final Drawable drawable = imageView.getDrawable();
            if(drawable instanceof  AsyncDrawable){
                final AsyncDrawable asyncDrawable = (AsyncDrawable)drawable;
                return asyncDrawable.getBitTask();
            }
        }
        return null;
    }

}
