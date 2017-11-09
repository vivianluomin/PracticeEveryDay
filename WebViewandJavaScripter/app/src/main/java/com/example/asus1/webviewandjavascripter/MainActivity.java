package com.example.asus1.webviewandjavascripter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
    private ImageView mInit;
    private ImageView mSave;
    private Button mBtn;
    private String[] mColors = {
     "#f00",
   "#f05b72",
   "#f8aba6",
    "#f47920",
    "#8f4b2e",
   "#ae6642",
    "#228fdb",
    "#1d953f",
   "#00ae9d",
   "#472d56"
    };

    private static  int GETIAMGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //testJStoAndroid();
        //textEvaluate();
        setWebView();


    }

    private void  init(){



        mWebView = (WebView)findViewById(R.id.wv_webview);
        mRootLayout = (LinearLayout)findViewById(R.id.ll_layout);
//        mBtn = (Button)findViewById(R.id.btn);
//        mBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String value) {
//
//                    }
//                });
//            }
//        });

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

        mInit = (ImageView)findViewById(R.id.iv_return);
        if(mInit!=null){
            mInit.setOnClickListener(this);
        }

        mSave = (ImageView)findViewById(R.id.iv_save);
        if(mSave!=null){
            mSave.setOnClickListener(this);
        }
    }

    private void setWebView(){


        mSettings = mWebView.getSettings();
        //让webView自适应屏幕
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setLoadsImagesAutomatically(true);
        //不支持缩放
        mSettings.setSupportZoom(false);
        //设置与JS交互的权限
        mSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/TextEdit.html");


        mWebView.addJavascriptInterface(new AndroidtoJs(MainActivity.this),"test");

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
               showFontSize(v);

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
               showColors(v);

                break;
            case R.id.iv_addImage:
                startActivityForResult(new Intent(MainActivity.this,ChoseImageActivity.class),GETIAMGE);
                break;

            case R.id.iv_return:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:Init()");
                    }
                });
                break;
            case R.id.iv_save:
                File file = new File(getFilesDir().getAbsolutePath()+"//test.html");
                Log.d("file",file.getAbsolutePath());
                if(file.exists()){
                    file.delete();

                }

                try {
                    file.createNewFile();
                    mWebView.evaluateJavascript("javascript:Save(\'"+"file://"+file.getAbsolutePath()+"\')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            if(value.equals("true")){
                                Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"文件创建失败",Toast.LENGTH_SHORT).show();

                }


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

    private void showFontSize(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.fontsizemenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.font1:
                        setFontSize(1);
                        break;
                    case R.id.font2:
                        setFontSize(2);
                        break;
                    case R.id.font3:
                        setFontSize(3);
                        break;
                    case R.id.font4:
                        setFontSize(4);
                        break;
                    case R.id.font5:
                        setFontSize(5);
                        break;
                    case R.id.font6:
                        setFontSize(6);
                        break;
                    case R.id.font7:
                        setFontSize(7);
                        break;



                }

                return true;
            }
        });

        popupMenu.show();
    }

    private void  showColors(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.colormenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.color1:
                        setColor(0);
                        break;
                    case R.id.color2:
                        setColor(1);
                        break;

                    case R.id.color3:
                        setColor(2);
                        break;

                    case R.id.color4:
                        setColor(3);
                        break;

                    case R.id.color5:
                        setColor(4);
                        break;

                    case R.id.color6:
                        setColor(5);
                        break;

                    case R.id.color7:
                        setColor(6);
                        break;

                    case R.id.color8:
                        setColor(7);
                        break;

                    case R.id.color9:
                        setColor(8);
                        break;

                    case R.id.color10:
                        setColor(9);
                        break;

                }

                return false;
            }
        });

        popupMenu.show();
    }

    private void  setFontSize(final int i){


        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:size("+i+")");
            }
        });

    }

    private void setColor(final int id){

        mWebView.evaluateJavascript("javascript:setFontColor(\"" + mColors[id] + "\")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d("color",value);
            }
        });

//        mWebView.post(new Runnable() {
//            @Override
//            public void run() {
//                mWebView.loadUrl("javascript:setFontColor(\""+mColors[id]+"\")");
//            }
//        });
    }


    private void textEvaluate(){
        mSettings = mWebView.getSettings();
        //让webView自适应屏幕
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setLoadsImagesAutomatically(true);
        //不支持缩放
        mSettings.setSupportZoom(false);
        //设置与JS交互的权限
        mSettings.setJavaScriptEnabled(true);

        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.loadUrl("file:///android_asset/javascript.html");


        mWebView.setWebChromeClient(new WebChromeClient(){
            //拦截JS的prompt的对话框，其实这里应该弄一个dialog，让其输入
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

               mBtn.setText(message);
                result.confirm("VIVIAN");
                return true;
            }
        });
    }

    private void testJStoAndroid(){
        mSettings = mWebView.getSettings();
        //让webView自适应屏幕
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setLoadsImagesAutomatically(true);
        //不支持缩放
        mSettings.setSupportZoom(false);
        //设置与JS交互的权限
        mSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("file:///android_asset/JStoAndroid.html");

       /// mWebView.addJavascriptInterface(new AndroidtoJs(MainActivity.this),"test");

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(Uri.parse(url).getHost().equals("www.baidu.com")){
                   mWebView.loadUrl(url);
                    return  true;
                }

                return false;
            }
        });


    }
}
