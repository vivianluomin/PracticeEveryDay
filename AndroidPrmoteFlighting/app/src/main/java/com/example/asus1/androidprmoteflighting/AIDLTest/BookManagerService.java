package com.example.asus1.androidprmoteflighting.AIDLTest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.asus1.androidprmoteflighting.AIDLTest.Book;
import com.example.asus1.androidprmoteflighting.AIDLTest.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private static final  String TAG = "BMS";
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListeners = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {

            mListeners.register(listener);


        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListeners.unregister(listener);
        }
    };

    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"android"));
        mBookList.add(new Book(2,"Ios"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    private void onNewBookArrived(Book book) throws RemoteException{
        mBookList.add(book);
        int n = mListeners.beginBroadcast();
        for(int i =0;i<n;i++){
            IOnNewBookArrivedListener listener = mListeners.getBroadcastItem(i);
            if (listener!=null){
                listener.onNewBookArrived(book);
            }

        }

        mListeners.finishBroadcast();
    }

    private class ServiceWorker implements Runnable{
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                int bookId = mBookList.size()+1;
                Book book = new Book(bookId,"new Book#"+bookId);
                try {
                    onNewBookArrived(book);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
