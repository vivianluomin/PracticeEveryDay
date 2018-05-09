package com.example.asus1.httpfrrame;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus1 on 2018/5/7.
 */

public abstract class Request<T> implements Comparable<Request<T>> {

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    public static final String HEADE_CONTENT_TYPE = "Content-Type";
    protected  int mSerialNum = 0;//请求序列号
    private Priority mPriority = Priority.NORMAL;
    protected  boolean isCancel = false;//是否取消请求
    private boolean mShouldCache = false;//该请求是否应该缓存

    protected  RequestListener<T> mRequestListener;

    private String mUrl = "";
    HttpMethod mHttpMethod = HttpMethod.GET;
    private Map<String,String> mHeaders = new HashMap<>();//请求的header
    private Map<String,String> mBodyParams = new HashMap<>();

    public Request(HttpMethod method,String url,RequestListener<T> listener){
        mHttpMethod = method;
        mUrl = url;
        mRequestListener = listener;
    }

    public abstract T parseResponse(Response response);

    public final void deliverResponse(Response response){
        T result = parseResponse(response);
        if(mRequestListener!=null){
            int stCode = response!=null?response.getStatusCode():-1;
            String msg = response!=null?response.getMessage():"unkown error";
            mRequestListener.onComplete(stCode,result,msg);
        }
    }

    protected String getParamEncoding(){
        return DEFAULT_PARAMS_ENCODING;
    }

    public String getBodyContentType(){
        return "application/x-www-form-urlencode;charset="+getParamEncoding();
    }

    public byte[] getBody(){
        Map<String,String> params = getParams();
        if(params!=null && params.size()>0){
            return encodeParameters(params,getParamEncoding());
        }
        return null;
    }

    private Map<String,String> getParams(){

        return mBodyParams;
    }

    private byte[] encodeParameters(Map<String,String> params,String paramsEncoding){
        StringBuilder encodeParams = new StringBuilder();
        try {
            for (Map.Entry<String,String> entry :params.entrySet()){
                encodeParams.append(URLEncoder.encode(entry.getKey(),paramsEncoding));
                encodeParams.append('=');
                encodeParams.append(URLEncoder.encode(entry.getValue(),paramsEncoding));
                encodeParams.append('&');
            }
            return encodeParams.toString().getBytes(paramsEncoding);
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException("Encoding not support: "+paramsEncoding,e);
        }
    }

    @Override
    public int compareTo(@NonNull Request<T> o) {
        Priority myPriority = this.getPriority();
        Priority anotherPriority = o.getPriority();

        return myPriority.equals(o)? this.getSerialNumber() -o.getSerialNumber()
                :myPriority.ordinal()-anotherPriority.ordinal();
    }

    private Priority getPriority(){

        return mPriority;
    }

    private int getSerialNumber(){
        return  mSerialNum;
    }


    public boolean isCancel(){
        return isCancel;
    }

    public String getUrl(){
        return mUrl;
    }

    public Map<String,String> getHeaders(){
        return mHeaders;
    }

    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    public boolean shouldCache(){
        return mShouldCache;
    }

    public void setSerialNumber(int serialNumber){
        mSerialNum = serialNumber;
    }

    public void addHeader(String name,String value){
        mHeaders.put(name,value);
    }
    public void addParams(String name,String value){
        mBodyParams.put(name,value);
    }

    public static enum HttpMethod{
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private String mHttpMethod = "";

        private HttpMethod(String method){
            mHttpMethod = method;
        }

        @Override
        public String toString() {
            return mHttpMethod;
        }
    }

    public static enum Priority{
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE;
    }

    public static interface RequestListener<T>{
        void onComplete(int stCode,T response ,String errMsg);
    }
}
