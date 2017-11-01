package com.example.asus1.frontchange;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView mTextView;
    private ListView mListView;
    private String[] strings = {
            "rotationX",
            "rotationBottom",
            "shake",
            "swing",
            "DropDown",
            "Rolling"
    };
    private ListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView)findViewById(R.id.tv_text);
        mListView = (ListView)findViewById(R.id.lv_list);
        mAdapter = new ArrayAdapter<String>(this,R.layout.view_list_item,strings);
        mListView.setAdapter(mAdapter);
       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               switch (i){
                   case 0:rotationX();
                       break;
                   case 1:
                        rotationBottom();
                       break;
               }

           }
       });

    }

    private void rotationX(){
        ObjectAnimator.ofFloat(mTextView,"rotationX",90,0).setDuration(1000).start();
    }

    private void rotationBottom(){
//      Animator animator = AnimatorInflater.loadAnimator(this,R.animator.rotationbottom);
//        animator.setTarget(mTextView);
//        animator.setDuration(1000).start();

        PropertyValuesHolder rotationX  = PropertyValuesHolder.ofFloat("rotationX",90,0);
        PropertyValuesHolder PivotX = PropertyValuesHolder.ofFloat("pivotX",0,mTextView.getHeight());
        ObjectAnimator.ofPropertyValuesHolder(mTextView,rotationX,PivotX).setDuration(1000).start();
    }


}
