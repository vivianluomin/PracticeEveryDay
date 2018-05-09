package com.example.asus1.httpfrrame;



public interface  Cache <String,Respose>{

    Response get(java.lang.String url);
    void put(java.lang.String url,Response response);
}
