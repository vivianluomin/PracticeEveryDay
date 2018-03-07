package com.example.asus1.testmvp.Models;

import com.example.asus1.testmvp.Presenters.DataListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by asus1 on 2018/3/7.
 */

public class ArticelModelImpl<T> implements ArticelModel<T> {

    List<T> mCacheArticels = new LinkedList<>();

    @Override
    public void saveArticels(List<T> results) {
        mCacheArticels.addAll(results);
    }

    @Override
    public void loadArticelsFromCache(DataListener<List<T>> listener) {
        listener.onComplete(mCacheArticels);
    }
}
