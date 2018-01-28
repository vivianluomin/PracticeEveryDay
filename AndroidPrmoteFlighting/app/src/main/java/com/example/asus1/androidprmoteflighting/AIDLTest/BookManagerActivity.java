package com.example.asus1.androidprmoteflighting.AIDLTest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.asus1.androidprmoteflighting.R;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = "BookManagerActivity";
    private IBookManager mBookManager;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.d(TAG,"receive new book :"+msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };

    private ServiceConnection mConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager manager = IBookManager.Stub.asInterface(service);
            mBookManager = manager;
            try {

                List<Book> list = manager.getBookList();
                Log.d(TAG,"list type: "+list.getClass().getCanonicalName());
                Log.d(TAG,"book list: "+list.toString());

                Book book = new Book(3,"web");
                mBookManager.addBook(book);
                mBookManager.registerListener(mOnNewBookListener);

            }catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
                mOnNewBookListener = null;
        }
    };

    private IOnNewBookArrivedListener mOnNewBookListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(1,newBook).sendToTarget();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,mConnection,BIND_AUTO_CREATE);
    }



    @Override
    protected void onDestroy() {
        if(mBookManager !=null &&mBookManager.asBinder().isBinderAlive()){
            try {
                mBookManager.unregisterListener(mOnNewBookListener);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }


        unbindService(mConnection);
        super.onDestroy();
    }
}
