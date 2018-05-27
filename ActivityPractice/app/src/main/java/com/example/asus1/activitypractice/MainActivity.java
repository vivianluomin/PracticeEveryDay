package com.example.asus1.activitypractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView) findViewById(R.id.textView2);
        findViewById(R.id.MainButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                i.putExtra("data","hello vivian");
               /* Bundle b = new Bundle();
                b.putString("name","vivian");
                b.putInt("age",20);
                i.putExtra("data",b);
                startActivity(i);*/
                startActivityForResult(i,0);
            }
        }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tv.setText(data.getStringExtra("data"));
    }
}
