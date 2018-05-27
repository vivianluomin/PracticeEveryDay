package vivian.com.testgilde;

import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    ListView listView;
    public static String[] eatFoodyImages = {
            "http://img3.imgtn.bdimg.com/it/u=1130998693,2672446652&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2929741107,3781080846&fm=26&gp=0.jpg",

            "http://img3.imgtn.bdimg.com/it/u=1130998693,2672446652&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2929741107,3781080846&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1130998693,2672446652&fm=26&gp=0.jpg",

            "http://img3.imgtn.bdimg.com/it/u=1130998693,2672446652&fm=26&gp=0.jpg",

            "http://img5.imgtn.bdimg.com/it/u=3246667522,2585452571&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1130998693,2672446652&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3246667522,2585452571&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1130998693,2672446652&fm=26&gp=0.jpg",

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        listView = (ListView)findViewById(R.id.listview);
//        listView.setAdapter(new Adapter(this,R.layout.list_item,eatFoodyImages));
        imageView = (ImageView)findViewById(R.id.img);

         SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                imageView.setImageBitmap(resource);
            }
        };

        Glide.with(this)
                .load("http://img3.imgtn.bdimg.com/it/u=1130998693,2672446652&fm=26&gp=0.jpg")
                .error(R.drawable.default_img)
        
              ;


    }

}
