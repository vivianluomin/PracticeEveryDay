package com.example.asus1.httpfrrame;

import android.preference.PreferenceActivity;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by asus1 on 2018/5/8.
 */

public class HttpUrlConnStack implements HttpStack {

    private static final String TAG = "HttpUrlConnStack";

    @Override
    public Response performRequest(Request<?> request) {
        HttpURLConnection urlConnection = null;

        try{
            urlConnection = createUrlConnection(request.getUrl());
            setRequestHeaders(urlConnection,request);
            setRequestParams(urlConnection,request);
            return fetchResponse(urlConnection);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private HttpURLConnection createUrlConnection(String url)throws IOException{
        URL newUrl = new URL(url);
        URLConnection urlConnection = newUrl.openConnection();
        urlConnection.setReadTimeout(8000);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        return (HttpURLConnection) urlConnection;
    }

    public void setRequestHeaders(HttpURLConnection connection,Request<?> request){
        Set<String> headersKeys = request.getHeaders().keySet();
        for(String headerNmae :headersKeys){
            connection.addRequestProperty(headerNmae,request.getHeaders().get(headerNmae));
        }
    }


    protected  void setRequestParams(HttpURLConnection connection
            ,Request<?> request)throws ProtocolException ,IOException{

        Request.HttpMethod method = request.getHttpMethod();
        connection.setRequestMethod(method.toString());
        byte[] body = request.getBody();
            if(body!=null){
                connection.setDoOutput(true);
                connection.addRequestProperty(Request.HEADE_CONTENT_TYPE,
                        request.getBodyContentType());
                DataOutputStream dataOutputStream = new DataOutputStream(
                        connection.getOutputStream()
                );

                dataOutputStream.write(body);
                dataOutputStream.close();
            }


    }

    private Response fetchResponse(HttpURLConnection connection)throws IOException{
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP",
                1,1);
        int responseCode = connection.getResponseCode();
        Log.d(TAG, "fetchResponse: "+responseCode);
        if(responseCode == -1){
            throw  new
                    IOException("Could not retrieve response code from HttpUrlConnection.");

        }

        StatusLine responseStatus = new StatusLine(protocolVersion,
                connection.getResponseCode(),connection.getResponseMessage());
        Response response = new Response(responseStatus);
        response.setEntity(entityFromURLConnection(connection));
        addHeadersToResponse(response,connection);
        return response;
    }

    private HttpEntity entityFromURLConnection(HttpURLConnection connection){
        HttpEntity entity = new HttpEntity();
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
        }catch (IOException e){
            e.printStackTrace();
            inputStream = connection.getErrorStream();
        }

        entity.setContent(inputStream);
        entity.setContentLength(connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());
        return entity;
    }


    private void addHeadersToResponse(Response response,HttpURLConnection connection){
        for(Map.Entry<String,List<String>> header :connection.getHeaderFields().entrySet()){
            if(header.getKey()!=null){
                Header h = new Header(header.getKey(),header.getValue().get(0));
                response.addHeader(h);
            }
        }
    }

}
