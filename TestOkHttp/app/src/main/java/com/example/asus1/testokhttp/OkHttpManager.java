package com.example.asus1.testokhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by asus1 on 2017/9/21.
 */

public class OkHttpManager {
    private static  final OkHttpClient mClient = new OkHttpClient();

    public  interface  CallBack{
       void erro(Call call ,Exception e);
        void Response(Call call,Response response);

    }
    public  static  void getData(String url, final CallBack callBack){
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = mClient.newCall(request);

        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(Call call, IOException e) {

                                callBack.erro(call,e);
                         }

                         @Override
                         public void onResponse(Call call, Response response) throws IOException {

                             callBack.Response(call,response);
                         }

                     }

        );
    }


}
