package com.example.asus1.listviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus1 on 2017/4/11.
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {
    private  int resourceId;
    public FruitAdapter(Context context, int textVieResourceId, List<Fruit> objects){
        super(context,textVieResourceId,objects);
        resourceId=textVieResourceId;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position);
       // View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        //提高效率，convertView用于将之前加载好的布局进行缓存
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.fruitImage=(ImageView) view.findViewById(R.id.fruit_image);
            viewHolder.fruitName=(TextView) view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder);//将ViewHolder存储在View中
        }else{
            view = convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
       // ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        //TextView  fruitNmae = (TextView) view.findViewById(R.id.fruit_name);
      //  fruitImage.setImageResource(fruit.getImageId());
        //fruitNmae.setText(fruit.getName());
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        return view;


    }
    class ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
    }
}
