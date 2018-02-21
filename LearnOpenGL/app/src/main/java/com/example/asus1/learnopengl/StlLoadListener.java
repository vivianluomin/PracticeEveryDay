package com.example.asus1.learnopengl;

/**
 * Created by asus1 on 2018/2/21.
 */

public interface StlLoadListener {

    public void onStart();
    void  onFinish();
    void  onLoading(int i,int facetCount);
    void onFailure(Exception e);
}
