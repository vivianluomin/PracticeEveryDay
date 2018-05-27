package com.example.asus1.activitypractice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
   private  TextView tv;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();

       // Bundle data = i.getBundleExtra("data");
        tv= (TextView) findViewById(R.id.textView3);
        tv.setText(i.getStringExtra("data"));
        editText=(EditText) findViewById(R.id.editText2);

        //tv.setText(String.format("name=%s,age=%d",data.getString("name"),data.getInt("age")));
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("data",editText.getText().toString());
                setResult(1,i);
                finish();
            }
        });

    }
}
