package com.example.asus1.testmartixcanvas;

import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MatrixView root = findViewById(R.id.root);

        //
        //root.scale(1.5f,1.5f,100,100);
        //root.rotate(90,0.5f,0.5f);
        //root.sinSoc(-1,0,0.5f,0.5f);
        //root.setSkew(0.5f,0);


        root.setInvert();
       // root.translate(200,200);root.setSkew(0.5f,0);
       // root.setInvert();

    }
}
