package com.example.asus1.httpfrrame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (WebView) findViewById(R.id.tv_text);
        RequestQueue queue = SimpleNet.newRequestQueue();
        StringRequest request = new StringRequest(Request.HttpMethod.GET,
                "https://www.baidu.com",
                new Request.RequestListener<String>() {
                    @Override
                    public void onComplete(int stCode, String response, String errMsg) {
                        Log.e(TAG, "onComplete: "+stCode);
                        mText.loadData(response,"text/html","utf-8");
                    }
                });

        request.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader("Accept-Encoding","gzip, deflate, br,utf-8");

        queue.addRequest(request);
        queue.start();
    }
}
