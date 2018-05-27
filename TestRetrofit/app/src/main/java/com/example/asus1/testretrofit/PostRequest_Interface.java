package com.example.asus1.testretrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by asus1 on 2017/8/16.
 */

public interface PostRequest_Interface {


    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<Trans> getCall(@Field("i")String tartgetSentence);
}
