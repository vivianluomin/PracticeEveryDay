package com.example.asus1.testcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by asus1 on 2017/11/12.
 */

public class MyProvider extends ContentProvider {

    private Context mContext;
    private  DBHelper mDBHelper = null;
    private SQLiteDatabase mDb;

    public  static  final  String AUTHOURITH = "cn.vivian.myprovider";
    //设置唯一标识符

    public static final  int User_Code = 1;
    public  static  final  int Job_Code = 2;

    private static  final UriMatcher mMatcher;


    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHOURITH,"user",User_Code);
        mMatcher.addURI(AUTHOURITH,"job",Job_Code);
        // 若URI资源路径 = content://cn.vivian.myprovider/user ，则返回注册码User_Code
        // 若URI资源路径 = content:/cn.vivian.myprovider/job ，则返回注册码Job_Code
    }


    @Override
    public boolean onCreate() {

        mContext = getContext();
        mDBHelper = new DBHelper(mContext);
       mDb = mDBHelper.getWritableDatabase();
        mDb.execSQL("delete from user");
        mDb.execSQL("insert into user values(1,\"vivian\")");
        mDb.execSQL("insert into user values(2,\"Tom\")");

        mDb.execSQL("delete from job");
        mDb.execSQL("insert into job values(1,\"android\")");
        mDb.execSQL("insert into job values(2,\"IOS\")");

        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return mContext.getContentResolver().getType(uri) ;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        String tableName = getTableName(uri);
        if(tableName!=null){
            mDb.insert(tableName,null,values);
            mContext.getContentResolver().notifyChange(uri,null);
        }

        return uri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return  0;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);

        return mDb.query(tableName,projection,selection,selectionArgs,null,null,sortOrder);

    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private String getTableName(Uri uri){
       String tableName = null;
         switch (mMatcher.match(uri)){
             case User_Code:
                 tableName = DBHelper.USER_TABLE_NAME;
                 break;
             case Job_Code:
                 tableName = DBHelper.JOB_TABLE_NAME;
                 break;
         }


        return tableName;
    }
}
