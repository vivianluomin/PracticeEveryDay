package com.example.asus1.testuri;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private ImageView mImageView;
    private File mCropFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView)findViewById(R.id.tv_textview);
        mImageView = (ImageView)findViewById(R.id.iv_image);


        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},2);

        }else{
            openFile();
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode){
           case 2:
               if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   openFile();
               }else{
                   Toast.makeText(MainActivity.this,"拒绝了",Toast.LENGTH_SHORT);
               }
               break;
       }
    }

    private void openFile(){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,1,null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       switch (requestCode){
           case 1:
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                //mTextView.setText(uri.toString());
                Log.d("pathPart",uri.toString());
                Log.d("pathPart",uri.getPath());
                Log.d("pathPart",uri.getAuthority());
                Log.d("pathPart",uri.getHost());
                Log.d("pathPart",uri.getScheme());
                Log.d("mediaPath", MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                handleImage(uri);


            }
            break;
           case 3:
              Log.d("crop",mCropFile.getAbsolutePath());
               Log.d("Size",mCropFile.length()+" wwwww");
               Bundle bundle = data.getExtras();
               if(bundle!=null){
                   Bitmap bitmap = bundle.getParcelable("data");
                   mImageView.setImageBitmap(bitmap);
               }
               //setImage(mCropFile.getAbsolutePath());
               break;



        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void  handleImage(Uri uri){

        String imagePath = null;
        if(DocumentsContract.isDocumentUri(MainActivity.this,uri)){
            String mId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = mId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + " = " + id;
                imagePath = getPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.
                        withAppendedId
                                (Uri.parse("content://downloads/public_downloads")
                                        ,Long.valueOf(mId));
                imagePath = getPath(contentUri,null);

            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getPath(uri,null);

        }else  if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }


        //setImage(imagePath);
        //Log.d("filePath",imagePath);
        Uri uri1 = Uri.fromFile(new File(imagePath));
        Uri inputuri = FileProvider.getUriForFile(MainActivity.this,"com.vivian.provider",new File(imagePath));

       System.out.println(uri1.toString());
        System.out.println(inputuri.toString());
        startPhotoZoom(inputuri);

    }

    private String getPath(Uri uri,String selection){
        String path = "";

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }
        cursor.close();

        Log.d("filePath",path);

        return path;

    }

    private void setImage(String path){

        mImageView.setImageBitmap(startPhotoZoom(path));
    }

    private Bitmap startPhotoZoom(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
       options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path);
        options.inSampleSize = 5;
        options.inJustDecodeBounds= false;
        return BitmapFactory.decodeFile(path,options);
    }

    public void startPhotoZoom(Uri inputUri) {
        if (inputUri == null) {
           Log.d("eee","The uri is not exist.");
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        //sdk>=24
        mCropFile = new File(getFilesDir().getAbsoluteFile()+"//crop.jpg");
//        if(mCropFile.exists()){
//            mCropFile.delete();
//        }
//        try{
//            mCropFile.createNewFile();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri outPutUri = Uri.fromFile(mCropFile);
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

//        } else {
//            Uri outPutUri = Uri.fromFile(mCropFile);
//            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                String url = GetImagePath.getPath(getContext(), inputUri);
//                //这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
//                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
//            } else {
//                intent.setDataAndType(inputUri, "image/*");
//            }
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
//        }

            // 设置裁剪
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("return-data", true);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
            startActivityForResult(intent, 3);//这里就将裁剪后的图片的Uri返回了
//        }catch (IOException e){
//            e.printStackTrace();
//        }

    }


    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
