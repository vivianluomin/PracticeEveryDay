package com.example.asus1.webviewandjavascripter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private WebView mWebView;
    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button)findViewById(R.id.btn_callJs);
        mWebView = (WebView)findViewById(R.id.wv_webview);


        setWebView();
    }

    private void setWebView(){

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:callJS()");
                    }
                });

            }
        });

        mSettings = mWebView.getSettings();

        mSettings.setJavaScriptEnabled(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.loadUrl("file:///android_asset/javascript.html");
        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Alter");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });

                builder.setCancelable(false);
                builder.create().show();

                return  true;
            }
        });

    }
}
