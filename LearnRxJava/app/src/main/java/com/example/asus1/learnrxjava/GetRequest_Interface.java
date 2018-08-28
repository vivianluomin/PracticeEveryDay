package com.example.asus1.learnrxjava;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetRequest_Interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<Translation> getcall();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<Translation2> getCall_2();

}
