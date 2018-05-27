package com.example.asus1.litepaltest;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createDatabase = (Button)findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
            }
        });
        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("the da vinci code");
                book.setAuthor("dan brown");
                book.setPages(454);
                book.setPrice(16.96);
                book.setPress("unknow");
                book.save();
            }
        });

        Button updateData = (Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Book book = new Book();
               book.setName("the lost");
                book.setAuthor("dan brown");
                book.setPages(510);
                book.setPrice(19.95);
                book.setPress("unknow");
                book.save();
//                book.setPrice(10.10);
//                book.save();
               // Book book = new Book();
                book.setPrice(15.15);
                book.setPress("anchor");
                book.updateAll("name = ? and author = ?","the lost","dan brown");
            }
        });

        Button deleteButton = (Button)findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class,"price < ?","15");
            }
        });

        Button query = (Button)findViewById(R.id.query_data);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = DataSupport.findAll(Book.class);
                for(Book book : books){
                    Log.d("MainActivity","book name is "+book.getName());
                    Log.d("MainActivity","book author is "+book.getAuthor());
                    Log.d("MainActivity","book pages is "+book.getPages());
                    Log.d("MainActivity","book price is "+book.getPrice());
                    Log.d("MainActivity","book press is "+book.getPress());
                }
            }
        });

    }
}
