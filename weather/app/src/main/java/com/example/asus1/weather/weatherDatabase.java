package com.example.asus1.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus1 on 2017/5/17.
 */

public class weatherDatabase extends SQLiteOpenHelper {

    public  static  final  String Create_Weather = "Create table Weather ("
            +"id integer primary key autoincrement,"
            +"city text,"
            +"date text,"
            +"day text,"
            +"night text,"
            +"tempMax integer," +
            "tempMin integer)";


    public  weatherDatabase(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(Create_Weather);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Weather");
        onCreate(db);
    }

}
