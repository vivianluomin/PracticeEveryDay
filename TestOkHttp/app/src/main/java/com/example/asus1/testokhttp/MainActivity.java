package com.example.asus1.testokhttp;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(MainActivity.this,SecondActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("1111","onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("1111","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("1111","onStop");
    }

}
