package com.example.asus1.androidprmoteflighting;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.example.asus1.androidprmoteflighting.AIDLTest.BinderPoolActivity;
import com.example.asus1.androidprmoteflighting.AIDLTest.BookManagerActivity;
import com.example.asus1.androidprmoteflighting.ViewText.DemoActivity_1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";
    private Messenger mService;
    private MessengerService messengerService;
    private TextView mTextView;




    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            Message msg = Message.obtain(null,1);
            Bundle data = new Bundle();
            data.putString("msg","hello,this is client.");
            msg.setData(data);
            msg.replyTo = messenger;
            try {
                mService.send(msg);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private Messenger messenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    Log.d(TAG,msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mTextView =  ((TextView)findViewById(R.id.btn));
       mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DemoActivity_1.class);
                startActivity(intent);
//                try {
//                    if(mService!=null){
//                        Message message = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("msg","hello again");
//                        message.setData(bundle);
//                        message.what = 1;
//                        message.replyTo = messenger;
//                        mService.send(message);
//                    }
//                }catch (RemoteException e){
//                    e.printStackTrace();
////                }
            }
        });
////        SharedPreferences.Editor editor;
//        editor = getSharedPreferences("Preference_1",MODE_PRIVATE).edit();
//        editor.putString("name","vivian");
//        editor.putString("age","21");
//        editor.commit();

       // Intent intent = new Intent(this,MessengerService.class);
       // bindService(intent,mConnection,BIND_AUTO_CREATE);



    }

    int mLastX=0;
    int mLastY=0;

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getRawX();
//        int y = (int) event.getRawY();
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int deltaX = x-mLastX;
//                int deltaY = y-mLastY;
//               int TranslationX =(int) mTextView.getTranslationX()+deltaX;
//               int TranslationY = (int)mTextView.getTranslationY()+deltaY;
//               mTextView.setTranslationX(TranslationX);
//               mTextView.setTranslationY(TranslationY);
//               break;
//
//            case MotionEvent.ACTION_UP:
//                break;
//
//        }
//        mLastX = x;
//        mLastY = y;
//
//        return true;
//    }
}
