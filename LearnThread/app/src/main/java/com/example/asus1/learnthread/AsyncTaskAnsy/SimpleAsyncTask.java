package com.example.asus1.learnthread.AsyncTaskAnsy;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.sql.CallableStatement;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SimpleAsyncTask<Parama,Progress,Result> {

     public abstract void onPreExecute();
     public abstract Result doInBackground(Parama parama);
     public abstract void onPostResult(Result result);
     public abstract void onProgressUpdate(Progress progress);

     private static final  int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int KEEP_ALIVE_SECONDS = 30;

     private InnerHandler mHandler;
     private FutureTask<Result> mFuture;
     private ExecutorTask mExecutor;
     private WorkRunnable mWork;
     private boolean mIsCanclen = false;

     private static final int POST_RESULT = 1;
     private static final int POST_PROGRESS = 2;

    private static final String TAG = "SimpleAsyncTask";

     private static ThreadPoolExecutor mThreadPool;
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);
     private static ThreadFactory mThreadFactory = new ThreadFactory() {
         AtomicInteger ctl = new AtomicInteger(1);
         @Override
         public Thread newThread(@NonNull Runnable r) {
             return  new Thread(r,"Thread###"+ctl.getAndIncrement());
         }
     };

     static {
         mThreadPool =  new ThreadPoolExecutor(CORE_POOL_SIZE,CORE_POOL_SIZE,
                 KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,sPoolWorkQueue,
                 mThreadFactory
                 );
         mThreadPool.allowCoreThreadTimeOut(true);
     }


    public SimpleAsyncTask(){
        mHandler = new InnerHandler();
        mWork = new WorkRunnable();
        mFuture = new FutureTask<Result>(mWork){
            @Override
            protected void done() {
                Log.d(TAG, "done: ");
            }
        };
        mExecutor = new ExecutorTask();

    }

     private void postResult(Result result){
         Message message = mHandler.obtainMessage(POST_RESULT,
                 new AsynTaskResult(this,result));
         message.sendToTarget();

     }

     private void setCancled(boolean cancled){
         mIsCanclen = cancled;
     }


    public void execute(Parama parama){
         onPreExecute();
         mWork.mParama = parama;
         //mExecutor.execute(mFuture);
        mThreadPool.execute(mFuture);
    }

    private class WorkRunnable implements Callable<Result>{
        public Parama mParama;
        @Override
        public Result call() throws Exception {
            Result result = null;
            Log.d(TAG, "call: ");
            try {
                 result = doInBackground(mParama);
            }catch (Throwable r){
                setCancled(true);
                throw  r;
            }finally {
                postResult(result);
            }
            return result;
        }
    }

    private class InnerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case POST_RESULT:
                    AsynTaskResult tr = (AsynTaskResult) msg.obj;
                    tr.mTask.onPostResult(tr.result);
                    break;
                case POST_PROGRESS:


            }
        }
    }

    private class ExecutorTask implements Executor{
        private static final String TAG = "ExecutorTask";
        @Override
        public synchronized void execute(@NonNull final Runnable command) {
            Log.d(TAG, "execute: "+CORE_POOL_SIZE);
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run:1111111111111111111111111111111 ");
                    command.run();
                }
            });
        }
    }

    private class AsynTaskResult{
         SimpleAsyncTask mTask;
         Result result;

        public AsynTaskResult(SimpleAsyncTask task, Result result) {
            this.mTask = task;
            this.result = result;
        }
    }

}
