package com.example.asus1.testgson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public  static  String JSONDATA = "{\n" +
            "  \"title\": \"Java Puzzlers: Traps, Pitfalls, and Corner Cases\",\n" +
            "  \"isbn-10\": \"032133678X\",\n" +
            "  \"isbn-13\": \"978-0321336781\",\n" +
            "  \"authors\": [\n" +
            "    \"Joshua Bloch\",\n" +
            "    \"Neal Gafter\"\n" +
            "  ]\n" +
            "}\n" +
            "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                GsonBuilder gsonBuilder = new GsonBuilder();
//                gsonBuilder.registerTypeAdapter(LargeData.class,new LargeDataSerialiser());
                gsonBuilder.registerTypeAdapter(LargeData.class,new LagerDataTypeAdaper());
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                LargeData data = new LargeData();
                data.create(1000);
               String json = gson.toJson(data);
                Log.d("json",json);

//                Book book = new Book();
//                book.setAuthor(new String[]{"Jshua Bioch","Neal Gafter"});
//                book.setIsbn10("032133678X");
//                book.setIsbn13("978-0321336781");
//                book.setTitle("Java Puzzlers: Traps, Pitfalls, and Corner Cases");



            }
        });


    }
}
