package com.example.asus1.androidprmoteflighting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

//        Intent intent = new Intent(this,ThirdActivity.class);
//        startActivity(intent);

        SharedPreferences.Editor editor;
        SharedPreferences preferences = getSharedPreferences("Preference_1",MODE_MULTI_PROCESS);
       String name =  preferences.getString("name","none");
       String age = preferences.getString("age","0");
        Log.d("shared",name +"-----"+age);
    }
}
