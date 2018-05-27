package com.example.asus1.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.asus1.databasetest.provider/Book");
                ContentValues values = new ContentValues();
                values.put("name","a clash of kings");
                values.put("author","george");
                values.put("pages",1040);
                values.put("price",22.85);
                Uri newUri =getContentResolver().insert(uri,values);
               // Log.d("mainActivity",newUri.toString());
               newId = newUri.getPathSegments().get(1);
            }
        });
        Button query = (Button)findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.asus1.databasetest.provider/Book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null
                        );
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "book name is " + name);
                        Log.d("MainActivity", "book author is " + author);
                        Log.d("MainActivity", "book pages is " + pages);
                        Log.d("MainActivity", "book price is " + price);
                    }
                    cursor.close();
                }
            }
        });
        Button update = (Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.asus1.databasetest.provider" +
                        "/Book/"+newId);
                ContentValues values = new ContentValues();
                values.put("name","a strom of sword");
                values.put("price",24.05);
                values.put("pages",1216);
                getContentResolver().update(uri,values,null,null);
            }
        });
        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.asus1.databasetest.provider" +
                        "/Book/"+newId);
                getContentResolver().delete(uri,null,null);
            }
        });
    }
}
