package com.example.asus1.testgilde2;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    ViewTarget viewTarget;

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            imageView.setImageBitmap(resource);
        }
    };

    String gifUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502467649569&di=e6d315292c65259dc3b78b951c8334c3&imgtype=0&src=http%3A%2F%2Fww1.sinaimg.cn%2Fbmiddle%2F7eca0c3cjw1ebk2u481t8g207i07sgoa.gif";

    String url = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1130998693,2672446652&fm=26&gp=0.jpg";
    ViewPropertyAnimation.Animator animator = new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(View view) {
            view.setAlpha(0f);

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view,"alpha",0f,1f);
            fadeAnim.setDuration(2500);
            fadeAnim.start();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);

        Glide.with(this)
                .load(url)
                .animate(animator)
                .placeholder(R.mipmap.ic_launcher_foreground)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
