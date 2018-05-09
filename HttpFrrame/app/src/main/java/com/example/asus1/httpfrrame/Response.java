package com.example.asus1.httpfrrame;

import android.util.Log;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by asus1 on 2018/5/8.
 */

public class Response  {

    private int mStatusCode = 0;

    public byte[] rawData = new byte[0];
    private HttpEntity mEntity;
    private StatusLine mStatusLine;

    private List<Header> mHeaders = new ArrayList<>();

    public Response(StatusLine statusLine){
        mStatusLine = statusLine;
    }

    public Response(ProtocolVersion ver,int code,String reason){

    }

    public void setEntity(HttpEntity entity){
        rawData = entityToBytes(getEntity());
        mEntity = entity;
        Log.d("setEn", "setEntity: set");
    }

    public byte[] getRawData(){
        return rawData;
    }

    private byte[] entityToBytes(HttpEntity entity){
        try {
            return EntityUtils.toByteArray(entity);
        }catch (IOException e){
            e.printStackTrace();
        }

        return new byte[0];
    }

    public int getStatusCode(){
        return mStatusCode;
    }

   public String getMessage(){

        return "";
   }

   public void addHeader(Header h){
        mHeaders.add(h);
   }

   public HttpEntity getEntity(){
       Log.d("getEn", "setEntity: get");
        return mEntity;
   }
}
