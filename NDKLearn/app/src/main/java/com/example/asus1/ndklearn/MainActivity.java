package com.example.asus1.ndklearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        //accessFiled();
        tv.setText(str);

//        System.out.println(NUM);
//        accessStaticFiled();
//        System.out.println(NUM);

            accessMethd();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public  String str = "vivian";
    public  native void accessFiled();

    public static int NUM = 1;
    public native void  accessStaticFiled();

    public int genRandomInt(int max){
        System.out.println("genRandomInt 执行了... max = "+max);
        return new Random().nextInt(max);
    }

    public native void accessMethd();

    public native void accessStaticMathod();

    public static String getUUID(){
        System.out.println("getUUID 执行了....");
        return UUID.randomUUID().toString();
    }


    Human man = new Man();
    public native void accessNonVirtualMathod();

}
