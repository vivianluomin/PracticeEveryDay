package com.example.asus1.httpfrrame;

public class SimpleNet {

    public static RequestQueue newRequestQueue(){
        return new
                RequestQueue(RequestQueue.DEFAULT_CORE_NUMS,
                HttpStackFactory.createHttpStack());
    }
}
