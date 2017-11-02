package com.example.asus1.frontchange;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
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
            "Rolling",
            "keyFarme"
    };
    private ListAdapter mAdapter;
    private ObjectAnimator animator;
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                       // mTextView.clearAnimation();
                        if(animator!=null){
                            animator.setTarget(null);
                        }
                        rotationX();
                        Log.d("1111","11111");
                        break;
                    case 1:
                       // mTextView.clearAnimation();
                        if(animator!=null){
                            animator.setTarget(null);
                        }
                        rotationBottom();
                        Log.d("2222","2222");
                        break;
                    case 2:
                        ShakeAnimator();
                        break;
                    case 3:
                        SwingAnimator();
                        break;
                    case 4:
                        DropDownAnimator();
                        break;
                    case 5:
                        RollingAnimaor();
                        break;
                    case 6:
                        keyFrame();
                        break;


                }
            }
        });

    }

    private void rotationX(){
       animator= ObjectAnimator.
               ofFloat(mTextView,"rotationX",90,0)
               .setDuration(1000);
        animator.start();
    }

    private void rotationBottom(){

        PropertyValuesHolder rotationChange  =
                PropertyValuesHolder.ofFloat("rotationX",90,0);
        PropertyValuesHolder PivotX =
                PropertyValuesHolder.ofFloat("pivotY",mTextView.getHeight());
       animator= ObjectAnimator.
                ofPropertyValuesHolder(mTextView,rotationChange,PivotX)
                .setDuration(1000);
        animator.start();
    }

    private void ShakeAnimator(){
       ObjectAnimator tran1= ObjectAnimator.ofFloat(mTextView,"translationX",50).setDuration(60);
        ObjectAnimator tran2 =  ObjectAnimator.ofFloat(mTextView,"translationX",-100).setDuration(50);
        ObjectAnimator tran3 = ObjectAnimator.ofFloat(mTextView,"translationX",70).setDuration(40);
        ObjectAnimator tran4 = ObjectAnimator.ofFloat(mTextView,"translationX",-20).setDuration(30);
        AnimatorSet set = new AnimatorSet();
        set.play(tran1);
        set.play(tran2).after(tran1);
        set.play(tran3).after(tran2);
        set.play(tran4).after(tran3);
        set.setTarget(mTextView);

        set.start();

    }

    private void  SwingAnimator(){
        ObjectAnimator rota1= ObjectAnimator.ofFloat(mTextView,
                "rotation",0,-30).setDuration(90);
        ObjectAnimator rota2 =  ObjectAnimator.ofFloat(mTextView,
                "rotation",-30,20).setDuration(80);
        ObjectAnimator rota3 = ObjectAnimator.ofFloat(mTextView,
                "rotation",20,-10).setDuration(60);
        ObjectAnimator rota4 = ObjectAnimator.ofFloat(mTextView,
                "rotation",10,0).setDuration(50);
        AnimatorSet set = new AnimatorSet();
        set.play(rota1);
        set.play(rota2).after(rota1);
        set.play(rota3).after(rota2);
        set.play(rota4).after(rota3);
        set.setTarget(mTextView);
        set.start();
    }

    private void DropDownAnimator(){
        mTextView.setAlpha(0);
        ObjectAnimator tran1=
                ObjectAnimator.ofFloat
                        (mTextView,"translationY",0,-200).setDuration(10);
        ObjectAnimator tran2 =
                ObjectAnimator.ofFloat(mTextView,
                        "translationY",-200,0).setDuration(200);
        ObjectAnimator tran3 = ObjectAnimator.ofFloat(mTextView,
                "translationY",0,-20).setDuration(80);
        ObjectAnimator tran4 = ObjectAnimator.ofFloat(mTextView
                ,"translationY",-20,0).setDuration(40);
        ObjectAnimator tran5 = ObjectAnimator.ofFloat(mTextView,
                "translationY",0,-10).setDuration(30);
        ObjectAnimator tran6 = ObjectAnimator.ofFloat(mTextView,
                "translationY",-10,0).setDuration(30);
        AnimatorSet set = new AnimatorSet();
        set.play(tran1);
        set.play(tran2).after(tran1);
        set.play(tran3).after(tran2);
        set.play(tran4).after(tran3);
        set.play(tran5).after(tran4);
        set.play(tran6).after(tran5);
        set.setTarget(mTextView);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mTextView.setAlpha(1.0f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }

    private void RollingAnimaor(){
        mTextView.setAlpha(0.0F);
        mTextView.setX(-mTextView.getWidth());
        mTextView.setY(mTextView.getHeight());
        ObjectAnimator rota = ObjectAnimator.ofFloat(mTextView,
                "rotation",-90,0).setDuration(1000);
        ObjectAnimator tranX = ObjectAnimator.ofFloat(mTextView,
                "translationX",-mTextView.getWidth(),0).setDuration(1000);
       ObjectAnimator tranY = ObjectAnimator.ofFloat(mTextView,
               "translationY",mTextView.getHeight(),0)
               .setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(rota,tranX,tranY);
        set.setTarget(mTextView);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mTextView.setAlpha(1.0f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();


    }

    private  void  keyFrame(){

        Keyframe f1 = Keyframe.ofFloat(2,0);
        Keyframe f2 = Keyframe.ofFloat(3,360);
        Keyframe f3 = Keyframe.ofFloat(4,0);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("rotation",f1,f2,f3);
        ObjectAnimator.ofPropertyValuesHolder(mTextView,holder).setDuration(5000).start();

    }


}
