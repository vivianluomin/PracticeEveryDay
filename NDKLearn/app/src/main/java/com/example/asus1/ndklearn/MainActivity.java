package com.example.asus1.ndklearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");

    }

    public String key = "qqq";

    private static final String TAG = "MainActivity";

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

           // accessMethd();

        //accessNonVirtualMathod();

//        long time = accessConstructor();
//        System.out.println(time);

//        testChineseIn("我爱你");
//        System.out.println(testChinsesOut());

//        int[] arr = {3,2,4,5,1,0};
//        sortArray(arr);
//        System.out.println(Arrays.toString(arr));

        try {
            testException();
            Log.d(TAG, "程序无法继续执行时，这句话不会被打印\n");
        }catch (Throwable t){
            Log.d(TAG, "捕获到JNI抛出的异常（Throwable）："+t.getMessage()+"\n");
        }

        Log.d(TAG, "程序继续执行2，这句话会被打印\n");
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

    //调用Date的构造函数
    public native long accessConstructor();

    public native void testChineseIn(String chinese);//传进去
    public native String testChinsesOut();//取出来

    //数组处理
    public native void sortArray(int array[]);


    //局部引用
    public native void localRef();

    //创建JNI全局引用
    public native void createGlobalRef();

    //获取JNI全局引用
    public native String getGlobalRef();

    //删除JNI全局引用
    public native void deleteGlobalRef();


    public native void testException();

}
