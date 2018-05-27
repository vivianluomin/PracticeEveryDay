package com.example.asus1.testcontentprovider2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri User = Uri.parse("content://cn.vivian.myprovider/user");

        ContentValues values = new ContentValues();
        values.put("_id",4);
        values.put("name","TIM");

        ContentResolver resolver = getContentResolver();
        resolver.insert(User,values);

        Cursor cursor = resolver.query(User,new String[]{"_id","name"},null,null,null);
        while (cursor.moveToNext()){
            System.out.println("query data"+cursor.getInt(0)+" "+cursor.getString(1));
        }
    }
}
