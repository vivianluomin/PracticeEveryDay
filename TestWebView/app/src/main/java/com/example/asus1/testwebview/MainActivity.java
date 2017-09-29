package com.example.asus1.testwebview;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTitle;
    private WebView mWebView;
    private WebSettings mWebSetting;
    private LoaingView loadingView;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            init();

    }


    private void init(){
        mTitle = (TextView)findViewById(R.id.tv_title);
        mWebView = (WebView)findViewById(R.id.wv_webview);
        mWebSetting = mWebView.getSettings();
        mDialog = new AlertDialog.Builder(MainActivity.this)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDialog.hide();
                    }
                }).create();
        loadingView = new LoaingView(mDialog.getContext());
        mDialog.setView(loadingView);


        mWebView.loadUrl("https://www.baidu.com");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                Log.d("aaa",request.getUrl().getPath());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                        mDialog.show();
                loadingView.start();

            }


        });

        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTitle.setText(title);
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    loadingView.setLoadingFinish();
                }

                super.onProgressChanged(view, newProgress);
            }
        });





    }

}
