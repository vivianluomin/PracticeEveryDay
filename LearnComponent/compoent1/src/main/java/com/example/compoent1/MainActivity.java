package com.example.compoent1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/account/login")
public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mButtonl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTextView = findViewById(R.id.tv_text);
        mButtonl = findViewById(R.id.btn);
        mButtonl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginShare();
            }
        });
        updateState();

    }

    private void updateState(){
        mTextView.setText("这里是登录界面："+AccountUtils.userInfo == null?"为登录":AccountUtils.userInfo);

    }

    public void loginShare(){
        ARouter.getInstance().build("/share/share").withString("share_content", "分享数据到微博").navigation();
    }
}
