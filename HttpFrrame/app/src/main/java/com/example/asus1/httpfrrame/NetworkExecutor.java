package com.example.asus1.httpfrrame;


import android.util.Log;

import java.util.concurrent.BlockingQueue;

/**
 * Created by asus1 on 2018/5/8.
 */

public class NetworkExecutor extends Thread {

    private BlockingQueue<Request<?>> mRequestQueue;
    private static final String TAG = "NetworkExecutor";

    private HttpStack mHttpStack;

    private static ResponseDelivery mResponseDelivery = new ResponseDelivery();

    private  static Cache<String,Response> mReqCache = new LruMenCache<>();

    private boolean isStop = false;

    public NetworkExecutor(BlockingQueue<Request<?>> queue,HttpStack httpStack){
        mRequestQueue = queue;
        mHttpStack = httpStack;
    }

    @Override
    public void run() {
        try {
            while (!isStop){
                final Request<?> request = mRequestQueue.take();
                if(request.isCancel()){
                    Log.d(TAG, "run: 取消执行了");
                    continue;
                }

                Response response = null;
                if(isUseCache(request)){
                    response = mReqCache.get(request.getUrl());
                }else {
                    response = mHttpStack.performRequest(request);
                    Log.d(TAG, "run: "+response);
                    if(request.shouldCache()&&isSuccess(response)){
                        mReqCache.put(request.getUrl(),response);
                    }
                }
                mResponseDelivery.deliveryResponse(request,response);
            }
        }catch (InterruptedException e){
            Log.d(TAG, "run: 请求分发器退出");
        }
    }

    private boolean isSuccess(Response response){
        return response!=null &&response.getStatusCode() == 200;
    }

    private boolean isUseCache(Request<?> request){
        return request.shouldCache()&&mReqCache.get(request.getUrl())!=null;
    }

    public void quit(){
        isStop = true;
        interrupt();
    }
}
