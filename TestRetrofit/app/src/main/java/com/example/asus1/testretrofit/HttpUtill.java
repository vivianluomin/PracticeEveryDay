package com.example.asus1.testretrofit;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asus1 on 2017/8/16.
 */

public class HttpUtill {

    public static void request(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<Translation> call = request.getCall();

        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                Log.d("response",response.body().getContent().getOut()+
                        "  "+response.body().getContent().getFrom());

            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });

    }

    public  static  void doPost(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

        Call<Trans> call = request.getCall("I love you");

        call.enqueue(new Callback<Trans>() {
            @Override
            public void onResponse(Call<Trans> call, Response<Trans> response) {
                Log.d("response",response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            @Override
            public void onFailure(Call<Trans> call, Throwable t) {

            }
        });
    }
}
