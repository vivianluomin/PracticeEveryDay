package vivian.com.musicplayer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus1 on 2017/7/15.
 */

public class getMusic {

    public static List<music> list;

    public static void getMusic(ContentResolver Resolver) {
        ContentResolver contentResolver = Resolver;
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            Log.d("path",cursor.toString());
            for (int i = 0; i < cursor.getCount(); i++) {
                music m = new music();
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                int ismusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
                Log.d("aaaaa",MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString());



                if (ismusic != 0 && duration / (500 * 60) >= 1) {
                    m.setId(id);
                    m.setTitle(title);
                    m.setArtist(artist);
                    m.setDuration(duration);
                    m.setSize(size);
                    m.setUrl(url);
                    m.setAlbum(album);
                    m.setAlbum_id(album_id);

                    list.add(m);
                }
                cursor.moveToNext();

            }


        }


    }

    public static List<music> getList(ContentResolver contentResolver) {
        if (list == null || list.size() <= 0) {
            getMusic(contentResolver);
        }

        return list;
    }
    private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

    public static Bitmap getArtWorkFormFile(ContentResolver resolver,int songid,int album){
        Bitmap bitmap = null;
        if(album <0 &&songid <0){
            return null;
        }

        try{
            BitmapFactory.Options options = new BitmapFactory.Options();
            FileDescriptor fileDescriptor = null;
            if(album <0){
                Uri uri = Uri.parse("content://media/external/audio/media/"+songid+"/albumart");
                Log.d("aaaa",uri.toString());
                ParcelFileDescriptor parcelFileDescriptor = resolver.openFileDescriptor(uri,"r");
                if(parcelFileDescriptor !=null){
                    fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                }
            }else {
                Uri uri = ContentUris.withAppendedId(albumArtUri,album);
                ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri,"r");
                if(pfd != null){
                    fileDescriptor = pfd.getFileDescriptor();
                }
            }

            options.inSampleSize = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);

            options.inSampleSize = calculateInSampleSize(options,50,50);
            options.inJustDecodeBounds =false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return  bitmap;
    }



    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap getArtwork(ContentResolver resolver,int songid,int albumid ,boolean allowdefalut,boolean samll){
        if(albumid <0 ){
            if(songid <0){
                Bitmap bitmap = getArtWorkFormFile(resolver,songid,albumid);
                if(bitmap !=null){
                    return bitmap;
                }
            }
            return null;
        }
        Uri uri = ContentUris.withAppendedId(albumArtUri,albumid);
        if(uri != null){
            InputStream inputStream = null;
            try{
                inputStream = resolver.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStream,null,options);
                if(samll){
                    options.inSampleSize = calculateInSampleSize(options,50,50);
                }else {
                    options.inSampleSize = calculateInSampleSize(options,200,200);
                }
                options.inJustDecodeBounds= false;
                options.inDither=false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                inputStream = resolver.openInputStream(uri);
                return BitmapFactory.decodeStream(inputStream,null,options);


            }catch (FileNotFoundException e){
                Bitmap bitmap = getArtWorkFormFile(resolver,songid,albumid);
                if(bitmap != null){
                    if(bitmap.getConfig() == null){
                        bitmap = bitmap.copy(Bitmap.Config.RGB_565,false);
                        if(bitmap == null && allowdefalut){
                            return  null;
                        }
                    }
                }

                return bitmap;

            }
        }
        return null;
    }


}

