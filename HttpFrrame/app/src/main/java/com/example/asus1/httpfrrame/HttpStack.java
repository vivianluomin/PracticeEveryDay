package com.example.asus1.httpfrrame;

/**
 * Created by asus1 on 2018/5/8.
 */

public interface HttpStack {

    public Response performRequest(Request<?> request);
}
