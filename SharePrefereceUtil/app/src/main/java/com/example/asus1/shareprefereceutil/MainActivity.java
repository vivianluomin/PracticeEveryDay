package com.example.asus1.shareprefereceutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager manager = PreferenceManager.getInstace();
        manager.putStringData(this,"text","pass","pass");
        String pass = manager.getStringData(this,"text","pass");
        Log.d(TAG, "onCreate: pass");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.color);
        if(bitmap!=null){
            manager.putImage(this,"test","image",bitmap);
            Bitmap bitmap1 = manager.getImage(this,"test","image");

            if(bitmap1!=null){
                ((ImageView)findViewById(R.id.image)).setImageBitmap(bitmap1);
            }
        }



    }
}
