package com.example.asus1.webviewandjavascripter;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;

public class ChoseImageActivity extends AppCompatActivity implements  DialogInterface.OnClickListener{


    private static final int OPENALBUM3 = 300;

    private static final  int OPENCAERAM3= 500;

    private static  final  int CROP = 700;
    public static  final  int RESULT = 100;

    private File mOutPutFile;

    private  String mOutPutPath ;
    private Uri mResultUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_image);
        showDialog();
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择相片")
                .setTitle("请选择打开方式")
                .setPositiveButton("打开相册", this)
                .setNegativeButton("拍照", this);
        builder.create().show();
        mOutPutPath = Environment.getExternalStorageDirectory()+"//"+System.currentTimeMillis()+".png";
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case AlertDialog.BUTTON_POSITIVE:
                openAlbum();
                break;
            case AlertDialog.BUTTON_NEGATIVE:
                break;

        }
    }

    private void  openAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,OPENALBUM3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case OPENALBUM3 :
                if(data!=null){
                    Log.d("PathA",data.getData().getPath());
                    Log.d("PathA",data.getData().toString());
                    if(Build.VERSION.SDK_INT >19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageViewBeforeKitKat(data);
                    }
                }

                Intent intent = new Intent();
                intent.setData(mResultUri);
                setResult(RESULT,intent);

                finish();


                break;
            case OPENCAERAM3:

                break;


        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void handleImageOnKitKat(Intent data){
        Uri path = data.getData();
        String imagePath = "";
        if(DocumentsContract.isDocumentUri(this,path)){
            String docid  = DocumentsContract.getDocumentId(path);
            Log.d("docid",docid);
            if("com.android.providers.media.documents".equals(path.getAuthority())){
                String id = docid.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath =getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.provider.downloads.documents".equals(path.getAuthority())){
                Uri contentUri = ContentUris
                        .withAppendedId
                                (Uri.parse("content://downloads/public_downloads"),Long.valueOf(docid));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(path.getScheme())){
            imagePath = getImagePath(path,null);
        }else if("file".equalsIgnoreCase(path.getScheme())){
            imagePath = path.getPath();


        }

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            mResultUri = getImageContentUri(this,imagePath);
        }else{
            Uri uri = Uri.fromFile(new File(imagePath));
            mResultUri = uri;
        }


    }

    private void handleImageViewBeforeKitKat(Intent data){
        Uri path = data.getData();
        String imagePath = getImagePath(path,null);
        Log.d("Path",imagePath);
        Uri uri = Uri.parse(imagePath);
        mResultUri = uri;
    }

    private String getImagePath(Uri uri ,String selection){
        String path ="";
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

        }
        cursor.close();
        Log.d("PathB",path);

        return  path;
    }

    public static Uri getImageContentUri(Context context, String imagePath) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{imagePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            File filePath = new File(imagePath);
            if (filePath.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA,imagePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    private void startPhotoZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
       mOutPutFile = new File(mOutPutPath);
        try {
            if(mOutPutFile.exists()){
                mOutPutFile.delete();
            }
            mOutPutFile.createNewFile();
            mResultUri = Uri.fromFile(mOutPutFile);
            intent.setDataAndType(uri,"image/*");
            //crop为true是设置在开启的intent中设置的View可以裁剪
            intent.putExtra("crop","true");
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);

            intent.putExtra("outputX",100);
            intent.putExtra("outputY",100);
            intent.putExtra("return-data",true);
            intent.putExtra("onFaceDetection",false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,mResultUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
            startActivityForResult(intent,CROP);
        }catch (IOException e){
            e.printStackTrace();
            setResult(RESULT,null);
        }



    }
}
