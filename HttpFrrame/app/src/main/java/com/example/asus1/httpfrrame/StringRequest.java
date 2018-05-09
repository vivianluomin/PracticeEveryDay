package com.example.asus1.httpfrrame;

public class StringRequest extends Request<String> {

    public StringRequest(HttpMethod method, String url, RequestListener<String> listener) {
        super(method, url, listener);
    }

    @Override
    public String parseResponse(Response response) {

        return response.getEntity().getContents();
    }
}
