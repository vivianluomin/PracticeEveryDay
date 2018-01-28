package com.example.asus1.androidprmoteflighting.AIDLTest;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.asus1.androidprmoteflighting.R;

public class BinderPoolActivity extends AppCompatActivity {

    private ISecurityCenter securityCenter;
    private ICompute Mcompute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork(){
        try {
            Log.d("doWork","wwwwwwwwwwwwwwww0000");
            BinderPool binderPool = BinderPool.getsInstance(this);
            Log.d("doWork","wwwwwwwwwwwwwwww");
            IBinder securityBinder = binderPool.queryBinder(100);
            if(securityBinder!=null){
                securityCenter =(ISecurityCenter)SecurityCenterImpl.asInterface(securityBinder);
                String msg = "an zhop";
                String password  = securityCenter.encrypt(msg);
                Log.d("encrypt: ","--------"+password);
                Log.d("decrypt: ",securityCenter.decrypt(password));
            }


            IBinder compute = binderPool.queryBinder(200);
            Mcompute = (ICompute)ComputeImpl.asInterface(compute);
           Log.d("3+5: ",""+Mcompute.add(3,5));
        }catch (RemoteException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }
}
