package com.example.asus1.testpropertyanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity
        implements  View.OnClickListener,CheckBox.OnCheckedChangeListener{

    private Button mBall;
    private LinearLayout mLaout;
    private Button mBtn;
    private GridLayout mGridView;
    private CheckBox  mAppear, mChangeAppear, mDisAppear, mChangeDisAppear;
    private ImageView mBall2;
    private LayoutTransition mLayoutTransition;
    private int mValue = 1;
    ObjectAnimator mScaleX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBall = (Button)findViewById(R.id.iv_image);
//        mBall2 = (ImageView)findViewById(R.id.iv_image1);
       //mLaout = (LinearLayout) findViewById(R.id.rl_layout);
//        mBtn = (Button)findViewById(R.id.btn_add);
//        mBtn.setOnClickListener(this);
//        mGridView = (GridLayout) findViewById(R.id.gv_gridView);
//        mGridView.setColumnCount(5);
//        mAppear = (CheckBox)findViewById(R.id.cb_1);
//        mChangeAppear = (CheckBox)findViewById(R.id.cb_2);
//        mDisAppear = (CheckBox)findViewById(R.id.cb_3);
//        mChangeDisAppear = (CheckBox)findViewById(R.id.cb_4);
//        mLayoutTransition = new LayoutTransition();
//        mGridView.setLayoutTransition(mLayoutTransition);



    }


    public  void  ViewSetter(View view){
//        ViewWrap wrap = new ViewWrap(mBall);
        ObjectAnimator.ofFloat(mBall,"width",500)
                .setDuration(5000)
                .start();
        mBall.setWidth(100);

//        ScaleAnimation scaleAnimation  = new
//                ScaleAnimation(1.0f,3.0f,1.0f,1.0f,
//                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        scaleAnimation.setDuration(5000);
//        mBall.startAnimation(scaleAnimation);



    }

    public   class ViewWrap{
        private View mTarget;
        public ViewWrap(View view){
            mTarget = view;
        }
        public int getWdith(){
            return  mTarget.getLayoutParams().width;
        }
        public  void  setWidth(float width){
            mTarget.getLayoutParams().width = (int) width;
            mTarget.requestLayout();
        }
    }

   public void ViewAnim(final View view){
       mBall.animate()
               .y(mLaout.getHeight())
               .alpha(0.3f)
               .rotationBy(0)
               .rotationBy(360)
               .setDuration(3000)
               .withEndAction(new Runnable() {
                   @Override
                   public void run() {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               mBall.setY(0);
                               mBall.setAlpha(1.0f);
                           }
                       });
                   }
               })

               .start();
   }


    @Override
    public void onClick(View view) {
        final Button button =  new Button(this);
        button.setText(String.valueOf(mValue++));
        mGridView.addView(button,Math.min(1,mGridView.getChildCount()));
        mLayoutTransition.
                setAnimator(LayoutTransition.APPEARING,
                        (mAppear.isChecked()?
                                ObjectAnimator.ofFloat(button,"scaleX",0.0f,1.0f)
                                :
                                null));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGridView.removeView(button);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        mLayoutTransition = new LayoutTransition();
        mLayoutTransition.
                setAnimator(LayoutTransition.APPEARING,
                        (mAppear.isChecked()?
                                mLayoutTransition.getAnimator(LayoutTransition.APPEARING)
                              :
                        null));
        mLayoutTransition.
                setAnimator(LayoutTransition.CHANGE_APPEARING,
                        (mAppear.isChecked()?
                                mLayoutTransition.getAnimator(LayoutTransition.CHANGE_APPEARING):
                                null));
        mLayoutTransition.
                setAnimator(LayoutTransition.DISAPPEARING,
                        (mAppear.isChecked()?
                                mLayoutTransition.getAnimator(LayoutTransition.DISAPPEARING):
                                null));
        mLayoutTransition.
                setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
                        (mAppear.isChecked()?
                                mLayoutTransition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING):
                                null));
        mGridView.setLayoutTransition(mLayoutTransition);
    }

    public   void  startAnimation(final View view){
        ObjectAnimator
                .ofFloat(view,"rotationX",0.0f,360.0f)
                .setDuration(1000)
                .start();

        ObjectAnimator animator = ObjectAnimator
                .ofFloat(view,"foo",0.0f,1.0f)
                .setDuration(2000)
                ;
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float)valueAnimator.getAnimatedValue();
                view.setAlpha(value);
                view.setScaleX(value);
            }
        });

        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha",0.0f,1.0f);
        PropertyValuesHolder scale = PropertyValuesHolder.ofFloat("scaleX",0.0f,1.0f);
        PropertyValuesHolder rotate = PropertyValuesHolder.ofFloat("rotationX",0.0f,360.0f);
        ObjectAnimator.ofPropertyValuesHolder(view,alpha,scale,rotate).setDuration(2000)
                .start();


    }

    public  void  runVertical(final View view){
        ValueAnimator valueAnimator =
                ValueAnimator.ofFloat(0.0f,mLaout.getHeight()-mBall.getHeight());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float verticalValue = (float)valueAnimator.getAnimatedValue();
                view.setTranslationY(verticalValue);
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();

    }


    public void paowuxian(View view){
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(5000);
        valueAnimator.setTarget(mBall2);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setObjectValues(new PointF(0,0));
        valueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float v, Object o, Object t1) {
                PointF pointF = new PointF();
                pointF.set(200*v*3,(float)(0.5*200*v*v*9));
                return pointF;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF)valueAnimator.getAnimatedValue();
                mBall2.setX(pointF.x);
                mBall2.setY(pointF.y);
            }
        });
        valueAnimator.start();
    }

    public  void  playTogether(View view){

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view,"alpha",1.0f,0.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX",1.0f,0.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,"scaleY",1.0f,0.0f);
        ObjectAnimator translation =
                ObjectAnimator.ofFloat(view,"translationX",0,mLaout.getWidth()/3*2);

        set.playTogether(alpha,scaleX,scaleY,translation);
        set.setDuration(5000);
        set.start();
    }

    public  void  playAfter(View view){
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view,"alpha",1.0f,0.3f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX",1.0f,0.3f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,"scaleY",1.0f,0.3f);
        ObjectAnimator translation =
                ObjectAnimator.ofFloat(view,"translationX",0,mLaout.getWidth()/3);
        ObjectAnimator alphaAfter = ObjectAnimator.ofFloat(view,"alpha",0.3f,1.0f);
        ObjectAnimator scaleXAfter = ObjectAnimator.ofFloat(view,"scaleX",0.3f,1.5f);
        ObjectAnimator scaleYAfter = ObjectAnimator.ofFloat(view,"scaleY",0.3f,1.5f);
        set.setDuration(5000);
        set.play(alpha).with(scaleX).with(scaleY).with(translation);
        set.play(alphaAfter).with(scaleXAfter).with(scaleYAfter).after(translation);
        set.start();
    }

    public void scaleX(View view){
        Animator animator = AnimatorInflater.loadAnimator(this,R.animator.scalex);
        animator.setTarget(view);
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight()/2);
        animator.start();
    }

}
