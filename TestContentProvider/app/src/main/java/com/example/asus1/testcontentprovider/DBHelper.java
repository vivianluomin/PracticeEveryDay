package com.example.asus1.testcontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus1 on 2017/11/12.
 */

public class DBHelper extends SQLiteOpenHelper{

    //数据库名
    private static final  String DATABASE_NAME = "vivian.db";

    //表名
    public  static  final  String USER_TABLE_NAME = "user";
    public  static  final  String JOB_TABLE_NAME = "job";

    //版本号
    private static final  int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建两个表格:用户表 和职业表
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + USER_TABLE_NAME
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " name TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + JOB_TABLE_NAME
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " job TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
