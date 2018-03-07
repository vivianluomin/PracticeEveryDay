package com.example.asus1.testmvp.Presenters;

/**
 * Created by asus1 on 2018/3/7.
 */

public interface ArticleAPI<T> {

    void fetchAeticel(DataListener<T> listener);

}
