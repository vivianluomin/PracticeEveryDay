package com.example.asus1.webviewandjavascripter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    private WebView mWebView;
    private WebSettings mSettings;
    private LinearLayout mRootLayout;
    private ImageView mBold;
    private ImageView mDeleteLine;
    private ImageView mFrontColor;
    private ImageView mAddImage;
    private ImageView mChangeSize;
    private ImageView mAddTitle;
    private AndroidtoJs mAJ;
    private static  int GETIAMGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setWebView();


    }

    private void  init(){

        mWebView = (WebView)findViewById(R.id.wv_webview);
        mRootLayout = (LinearLayout)findViewById(R.id.ll_layout);

        mBold = (ImageView)findViewById(R.id.iv_bold);
        if(mBold!=null){
            mBold.setOnClickListener(this);
        }
        mDeleteLine = (ImageView)findViewById(R.id.iv_deleteLine);
        if(mDeleteLine!=null){
            mDeleteLine.setOnClickListener(this);
        }
        mFrontColor = (ImageView)findViewById(R.id.iv_frontColor);
        if(mFrontColor!= null){
            mFrontColor.setOnClickListener(this);
        }
        mAddImage = (ImageView)findViewById(R.id.iv_addImage);
        if(mAddImage!=null){
            mAddImage.setOnClickListener(this);
        }
        mChangeSize = (ImageView)findViewById(R.id.iv_size);
        if(mChangeSize!=null){
            mChangeSize.setOnClickListener(this);
        }
        mAddTitle = (ImageView)findViewById(R.id.iv_addTitle);
        if(mAddTitle!=null){
            mAddTitle.setOnClickListener(this);
        }
    }

    private void setWebView(){


        mSettings = mWebView.getSettings();
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setLoadsImagesAutomatically(true);
        mSettings.setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(new AndroidtoJs(MainActivity.this),"test");
        mWebView.loadUrl("file:///android_asset/TextEdit.html");


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_addTitle:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:AddTitle()");
                    }
                });

                break;
            case R.id.iv_bold:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:Bold()");
                    }
                });

                break;
            case R.id.iv_size:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:size(5)");
                    }
                });

                break;

            case R.id.iv_deleteLine:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:deleteLine()");
                    }
                });

                break;

            case R.id.iv_frontColor:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:setFontColor(\"#f00\")");
                    }
                });

                break;
            case R.id.iv_addImage:
                startActivityForResult(new Intent(MainActivity.this,ChoseImageActivity.class),GETIAMGE);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == ChoseImageActivity.RESULT){
            if(data!=null){
                Uri path = data.getData();
                if(path!=null){
                    Log.d("path",path.toString());
                    insertImage(path);
                }

            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void  insertImage(final Uri path){
        mWebView.post(new Runnable() {
            @Override
            public void run() {

                mWebView.loadUrl("javascript:AddImage(\'"+path.toString()+"\')");

            }
        });
    }
}
