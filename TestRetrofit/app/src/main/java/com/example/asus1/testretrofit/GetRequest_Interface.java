package com.example.asus1.testretrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by asus1 on 2017/8/16.
 */

public interface GetRequest_Interface {


    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();
}
