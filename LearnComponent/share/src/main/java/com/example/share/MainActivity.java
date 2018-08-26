package com.example.share;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.componentbase.ServiceFactory;

@Route(path = "/share/share")
public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        textView = findViewById(R.id.tv_text);
        if(getIntent()!=null){
            String content = getIntent().getStringExtra("share_content");
            textView.setText(content);
        }
        share();
    }

    private void share(){
        if(ServiceFactory.getInstance().getAccountService().isLogin()){

            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(this, "分享失败：用户未登录", Toast.LENGTH_SHORT);
        }
    }
}
