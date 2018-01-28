package com.example.asus1.androidprmoteflighting.ViewText;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus1.androidprmoteflighting.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DemoActivity_1 extends AppCompatActivity {

    private static final String TAG = "DemoActivity";
    private HorizontalSrcollViewEx mListContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_1);
        initView();
    }

    private void initView(){
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalSrcollViewEx)findViewById(R.id.container);
        final  int screenWidth = getResources().getDisplayMetrics().widthPixels;
        final int screenHeigth = getResources().getDisplayMetrics().heightPixels;
        for(int i =0;i<3;i++){
            View layout = (View) inflater.inflate(R.layout.content_layout,mListContainer,false);
            layout.getLayoutParams().width = screenWidth;
           layout.getLayoutParams().height = screenHeigth;
            TextView textView = (TextView)layout.findViewById(R.id.title);
            textView.setText("page "+(i+1));
            layout.setBackgroundColor(Color.rgb(255/(i+1),255/(i+1),0));
            createList(layout);
            Log.d(TAG,"I="+i);
            mListContainer.addView(layout);

        }

    }

    private void createList(View layout){
        ListView listView = (ListView)layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<>();
        for(int i =0;i<50;i++){
            datas.add("name "+i);
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,R.layout.content_list_content,datas);
        listView.setAdapter(adapter);
    }
}
