package com.example.asus1.shareprefereceutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;


/**
 * Created by asus1 on 2018/3/10.
 */

public class PreferenceManager {

    private static PreferenceManager Instance = new PreferenceManager();

    private PreferenceManager(){

    }

    public static PreferenceManager getInstace(){
        return Instance;
    }

    public  void putStringData(Context context,String filename,String key,String value){

        SharedPreferences.Editor editor = getEditor(context,filename);
        editor.putString(key,value);
        editor.commit();
    }

    private  SharedPreferences.Editor getEditor(Context context,String filename){

        return getPreference(context,filename).edit();
    }

    public  String getStringData(Context context,String filename,String key){
        SharedPreferences preferences = getPreference(context,filename);
        String value =  preferences.getString(key,"");

        return value;
    }

    private  SharedPreferences getPreference(Context context,String filename){
        return  context.getSharedPreferences(filename,Context.MODE_PRIVATE);
    }


    private void removeStringData(Context context,String filename,String key){
        SharedPreferences.Editor editor = getEditor(context,filename);
        editor.remove(key);
        editor.commit();
    }

    public  void putImage(Context context, String filename, String key, Bitmap bitmap){

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,out);
        byte[] bytes = out.toByteArray();
        String imageString = new String(Base64.encodeToString(bytes, android.util.Base64.DEFAULT));
        putStringData(context,filename,key,imageString);

    }

    public  Bitmap getImage(Context context, String filename, String key){
        String imageString = getStringData(context,filename,key);
        if(!imageString.equals("")){
            byte[] bytes = Base64.decode(imageString,Base64.DEFAULT);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            return BitmapFactory.decodeStream(in);
        }

        return null;
    }

}
