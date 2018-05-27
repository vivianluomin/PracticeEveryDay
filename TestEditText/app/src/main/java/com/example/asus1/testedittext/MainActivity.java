package com.example.asus1.testedittext;

import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.style.MaskFilterSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements InputMethodListLayout.OnSizeChangedListener {

    InputMethodManager methodManager;
    private InputMethodListLayout layout;
    private ListView mListView;
    private float density;

    private String[] data = {
            "a","b","c","d","e",
            "a","b","c","d","e",
            "a","b","c","d","e"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (InputMethodListLayout) findViewById(R.id.inputlayout);
        mListView = (ListView)findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(this,R.layout.layout_item,data));
        density = getResources().getDisplayMetrics().density;
        layout.setOnSizeChangedListener(this);
        methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        final EditText editText = findViewById(R.id.edit);


        ((TextView) findViewById(R.id.text_replay))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });


//        RelativeLayout layout = (RelativeLayout)findViewById(R.id.relative);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    if(methodManager.isActive(editText))
//                    methodManager.hideSoftInputFromWindow(editText.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
//
//            }
//        });
    }

        @Override
        public void onSizeChange ( boolean flag){
            if (flag) {
                layout.setPadding(0, -159, 0, 0);
            } else {
                layout.setPadding(0, 0, 0, 0);
            }
        }

        @Override
        public boolean onTouchEvent (MotionEvent event){
            if (event.getAction() == MotionEvent.ACTION_UP) {
                methodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
            return super.onTouchEvent(event);
        }
    }
