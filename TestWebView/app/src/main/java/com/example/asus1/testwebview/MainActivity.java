package com.example.asus1.testwebview;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTitle;
    private WebView mWebView;
    private WebSettings mWebSetting;
    private TextView mStartLoading;
    private TextView mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            init();

    }


    private void init(){
        mTitle = (TextView)findViewById(R.id.tv_title);
        mWebView = (WebView)findViewById(R.id.wv_webview);
        mStartLoading = (TextView)findViewById(R.id.tv_startloading);
        mLoading = (TextView)findViewById(R.id.tv_loading);


        mWebSetting = mWebView.getSettings();

        mWebSetting.setUseWideViewPort(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setSupportZoom(true);
        mWebSetting.setBuiltInZoomControls(true);
        mWebSetting.setDisplayZoomControls(false);

        mWebView.loadUrl("https://www.baidu.com");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mStartLoading.setText("开始加载");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mStartLoading.setText("加载结束");
                mWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if(request.isForMainFrame()){
                    mTitle.setText(String.valueOf(error.getErrorCode())+" 出错了");
                    mWebView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if(Build.VERSION.SDK_INT > 23){
                    Log.d("aaaaa","aaaaaaa");
                    return;
                }

                mTitle.setText(String.valueOf(errorCode)+"   出错了");
                Log.d("bbb","bbbb");
                mWebView.setVisibility(View.GONE);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();

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
                if(newProgress<=100){
                    mLoading.setText(" "+newProgress+"%");

                }

            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {

        if(mWebView!=null){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebView.clearHistory();

            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView=null;

        }

        super.onDestroy();
    }
}
