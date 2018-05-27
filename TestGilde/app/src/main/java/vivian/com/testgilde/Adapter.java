package vivian.com.testgilde;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by asus1 on 2017/8/8.
 */

public class Adapter extends ArrayAdapter<String>{
    Context context;
    int res;
    String[] imags;

    public Adapter(Context context, int resource, String[] objects) {
        super(context, resource,  objects);
        this.context = context;
        res = resource;
        imags = objects;

    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        ViewHolder holder;

        if(convertView == null){
            view= LayoutInflater.from(context).inflate(res,parent,false);
            holder = new ViewHolder();
            holder.imageView = (ImageView)view.findViewById(R.id.image);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.imageView = (ImageView)view.findViewById(R.id.image);

        Glide.with(context)
                .load(imags[position])
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .crossFade(1000)
                .override(600,600)
                .centerCrop()
                .into(holder.imageView);

        Log.d("aaaa",String.valueOf(position));

        return view;
    }

    class ViewHolder{
        ImageView imageView;
    }
}
