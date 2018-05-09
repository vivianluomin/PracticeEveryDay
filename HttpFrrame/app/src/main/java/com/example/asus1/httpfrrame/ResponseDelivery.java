package com.example.asus1.httpfrrame;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by asus1 on 2018/5/8.
 */

public class ResponseDelivery implements Executor {

    Handler mResponseHandler = new Handler(Looper.getMainLooper());

    public void deliveryResponse(final Request<?> request,final Response response){
        Runnable respRunnale = new Runnable() {
            @Override
            public void run() {
                request.deliverResponse(response);
            }
        };

        execute(respRunnale);
    }

    @Override
    public void execute(@NonNull Runnable command) {

        mResponseHandler.post(command);
    }
}
