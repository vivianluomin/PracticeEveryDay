package com.example.asus1.testmvp.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.example.asus1.testmvp.Models.Articel;

import java.util.List;

/**
 * Created by asus1 on 2018/3/7.
 */

public class ArticelAdapter extends ArrayAdapter<Articel> {

    public ArticelAdapter(@NonNull Context context, int resource, @NonNull List<Articel> objects) {
        super(context, resource, objects);
    }

    @Nullable
    @Override
    public Articel getItem(int position) {
        return super.getItem(position);
    }
}
