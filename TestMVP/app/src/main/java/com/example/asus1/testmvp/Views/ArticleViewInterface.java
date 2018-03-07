package com.example.asus1.testmvp.Views;

import java.util.List;

/**
 * Created by asus1 on 2018/3/7.
 */

public interface ArticleViewInterface<T> {

     void showLoading();
     void hideLoading();
    void showArticel(List<T> results);
}
