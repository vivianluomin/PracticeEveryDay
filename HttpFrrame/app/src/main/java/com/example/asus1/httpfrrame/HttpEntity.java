package com.example.asus1.httpfrrame;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpEntity {

    private byte[] Content = new byte[1024];
    private StringBuilder contents = new StringBuilder();
    private int ContentLenght;
    private String ContentEncoding;
    private String ContentType;
    public void setContent(InputStream inputStream){
        try {
            if((inputStream.read(Content)!=0)){
                contents.append(new String(Content));

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void setContentLength(int length){
        ContentLenght = length;
    }

    public void setContentEncoding(String encoding){
        ContentEncoding = encoding;
    }

    public void setContentType(String type){
        ContentType = type;
    }

    public String getContents() {
        return contents.toString();
    }

    public int getContentLenght() {
        return ContentLenght;
    }

    public String getContentEncoding() {
        return ContentEncoding;
    }

    public String getContentType() {
        return ContentType;
    }
}
