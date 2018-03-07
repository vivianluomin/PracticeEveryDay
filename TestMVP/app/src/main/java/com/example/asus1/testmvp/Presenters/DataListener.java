package com.example.asus1.testmvp.Presenters;

import java.util.List;

/**
 * Created by asus1 on 2018/3/7.
 */

public interface DataListener<T> {

    void  onComplete(T result );
}
