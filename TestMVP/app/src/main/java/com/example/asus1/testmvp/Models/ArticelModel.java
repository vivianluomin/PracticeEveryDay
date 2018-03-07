package com.example.asus1.testmvp.Models;

import com.example.asus1.testmvp.Presenters.DataListener;

import java.util.List;

/**
 * Created by asus1 on 2018/3/7.
 */

public interface ArticelModel<T>  {

    void saveArticels(List<T> results);
    void loadArticelsFromCache(DataListener<List<T>> listener);
}
