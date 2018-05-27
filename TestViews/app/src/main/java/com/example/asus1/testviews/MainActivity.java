package com.example.asus1.testviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView miv_loading;
    private TextView mtv_loading;
    private AnimationSet mImageAni;
    private AnimationSet mTextAni;

    CirclePercentView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        init();
//
//        TranslateAnimation translateAnimation = new TranslateAnimation(300,0,400,0);
//        translateAnimation.setDuration(5000);
//
//        RotateAnimation rotateAnimation = new
//                RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f
//                ,Animation.RELATIVE_TO_SELF,0.5f);
//
//        rotateAnimation.setDuration(5000);
//
//        mImageAni.addAnimation(translateAnimation);
//        mImageAni.addAnimation(rotateAnimation);
//
//
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1.0f,0,1.0f,
//                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        scaleAnimation.setDuration(5000);
//
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
//        alphaAnimation.setDuration(5000);
//        mTextAni.addAnimation(scaleAnimation);
//        mTextAni.addAnimation(alphaAnimation);
//
//        miv_loading.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                miv_loading.startAnimation(mImageAni);
//                mtv_loading.startAnimation(mTextAni);
//            }
//        });

        view  = (CirclePercentView)findViewById(R.id.cv_view);

        ((Button)(findViewById(R.id.btn))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setCurrentPercent(100);
            }
        });

    }




}
