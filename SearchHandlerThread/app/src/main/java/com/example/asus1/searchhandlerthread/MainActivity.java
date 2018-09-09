package com.example.asus1.searchhandlerthread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Handler.Callback {


    private TextView mStart;
    private TextView mFinish;
    private Button mDownLoad;
    private DownLoadThread mDownLoadThread;
    private Handler mUIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStart = findViewById(R.id.tv_start);
        mFinish = findViewById(R.id.tv_finish);
        mDownLoad = findViewById(R.id.btn_start);
        mDownLoadThread = new DownLoadThread("DownLoad");
        mUIHandler = new Handler(this);
        mDownLoadThread.setUIHandler(mUIHandler);
        mDownLoadThread.setDownloadUrls("http://pan.baidu.com/s/1qYc3EDQ",
                "http://bbs.005.tv/thread-589833-1-1.html", "http://list.youku.com/show/id_zc51e1d547a5b11e2a19e.html?");
        mDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownLoadThread.start();
                mDownLoad.setText("正在下载");
                mDownLoad.setEnabled(false);
            }
        });

    }


    @Override
    public boolean handleMessage(Message msg) {

        if(msg == null ){
            return false;
        }

        switch (msg.what){
            case DownLoadThread.TYPE_START:
                mStart.setText(mStart.getText().toString()+
                "\n"+msg.obj);
                break;

            case DownLoadThread.TYPE_FINISHED:
                mFinish.setText(mFinish.getText().toString()+
                        "\n"+msg.obj);
                break;
        }

        return true;
    }
}
