package vivian.com.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by asus1 on 2017/8/1.
 */

public class RemenAdapter extends RecyclerView.Adapter <RemenAdapter.MyHolder>{

    List<String> title;
    List<String> imgs;
    List<String> hrefs;
    Context context;
    RequestQueue queue;
    ImageLoader loader;
    ItemClick click;


    public  void setClick(ItemClick c){
        click =c;
    }


    public RemenAdapter(List<String> title, List<String> hrefs, List<String> imgs, Context context) {
        this.title = title;
        this.hrefs = hrefs;
        this.imgs = imgs;
        this.context = context;
        queue = Volley.newRequestQueue(context);
        loader = new ImageLoader(queue,new BitmapCache(10));

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(LayoutInflater.from(context).
                inflate(R.layout.rementuijian_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
          ImageView view = holder.imageView;
        ImageLoader.ImageListener listener = ImageLoader.
                getImageListener(view,R.drawable.default_image,R.drawable.default_image);
         loader.get(imgs.get(position),listener);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onItemClick(hrefs.get(position));
                }
            });

        holder.textView.setText(title.get(position));

    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.rementuijian_text);
            imageView = (ImageView)itemView.findViewById(R.id.rementuijian_image);
        }
    }




}
