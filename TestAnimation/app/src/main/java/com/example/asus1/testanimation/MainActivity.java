package com.example.asus1.testanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.graphics.drawable.AnimationDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        property();

    }

    private void property(){
        View view = findViewById(R.id.iv_ball);
        Animator set = AnimatorInflater.loadAnimator(this,R.animator.propety);
        set.setTarget(view);
        set.start();

    }

    private void frame(){
//        ImageView imageView = (ImageView)findViewById(R.id.iv_ball);
//        AnimationDrawable drawable = (AnimationDrawable) imageView.getDrawable();
//        drawable.start();

    }

    private void startBall(){
//        ImageView imageView = (ImageView)findViewById(R.id.iv_ball);
//        Animation animation = AnimationUtils.loadAnimation(this,R.anim.aniset);
//        imageView.startAnimation(animation);
    }
}
