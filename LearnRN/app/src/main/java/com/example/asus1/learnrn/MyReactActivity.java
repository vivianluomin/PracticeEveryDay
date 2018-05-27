package com.example.asus1.learnrn;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

public class MyReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler{

    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                //.setJSMainModuleName("index.android")（这个已经没了）
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        // 注意这里的helloas必须对应“index.android.js”中的
        // “AppRegistry.registerComponent()”的第一个参数
        mReactRootView.startReactApplication(mReactInstanceManager, "helloas", null);

//        TextView textView = new TextView(this);
//        textView.setText("Hello world");
//        textView.setTextSize(20);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
//        textView.setLayoutParams(params);
//        textView.setGravity(Gravity.CENTER);
//        mReactRootView.addView(textView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }


        setContentView(mReactRootView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                    params.type = WindowManager.LayoutParams.TYPE_PHONE;
                    params.format = PixelFormat.RGBA_8888;
                    windowManager.addView(mReactRootView,params);
                }else {
                    Toast.makeText(this,"ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝",Toast.LENGTH_SHORT).show();;
                }
            }
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();


        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy();
        }
    }
    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }



}
