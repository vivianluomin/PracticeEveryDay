package com.example.asus1.httpfrrame;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by asus1 on 2018/5/8.
 */

public class RequestQueue {

    private BlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<>();

    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors()+1;

    private int mDiapatcherNums = DEFAULT_CORE_NUMS;

    private NetworkExecutor[] mDispatchers = null;

    private HttpStack mHttpStack;

    protected  RequestQueue(int coreNum ,HttpStack httpStack){
        mDiapatcherNums = coreNum;
        mHttpStack = httpStack !=null?httpStack:HttpStackFactory.createHttpStack();

    }

    private final void startNetworkExecutor(){
        mDispatchers = new NetworkExecutor[mDiapatcherNums];
        for(int i =0;i<mDiapatcherNums;i++){
            mDispatchers[i] = new NetworkExecutor(mRequestQueue,mHttpStack);
            mDispatchers[i].start();
        }
    }

    public void start(){
        stop();
        startNetworkExecutor();
    }

    public void stop(){
        if(mDispatchers !=null &&mDispatchers.length>0){
            for(int i = 0;i<mDispatchers.length;i++){
                mDispatchers[i].quit();
            }
        }
    }

    public void addRequest(Request<?> request){
        if(!mRequestQueue.contains(request)){
            request.setSerialNumber(this.generateSerialNumber());
            mRequestQueue.add(request);
        }
    }

    private int generateSerialNumber(){
        return mSerialNumGenerator.incrementAndGet();
    }

}
