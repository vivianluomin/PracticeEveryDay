package vivian.com.musicplayer;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by asus1 on 2017/8/7.
 */

public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap> mCache;
    public BitmapCache(int size){
        mCache = new LruCache<String, Bitmap>(size){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };


    }


    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        mCache.put(s,bitmap);

    }

    @Override
    public Bitmap getBitmap(String s) {
        return mCache.get(s);
    }
}
