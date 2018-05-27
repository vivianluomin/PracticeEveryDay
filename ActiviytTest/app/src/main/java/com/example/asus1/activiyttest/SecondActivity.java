package com.example.asus1.activiyttest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this,ThirdActivity.class));
            }
        });
    }
    public  static  void ActivityStart(Context context,String data1,String data2){
        Intent intent=new Intent(context,SecondActivity.class);
        intent.putExtra("data1",data1);
        intent.putExtra("data2",data2);
       context .startActivity(intent);

    }//这样更好

}
