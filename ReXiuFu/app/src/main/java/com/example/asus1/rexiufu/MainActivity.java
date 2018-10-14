package com.example.asus1.rexiufu;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //BaseDexClassLoader
        //PathClassLoader
        //DexClassLoader

        ActivityCompat.requestPermissions(this,permissions,100);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dexFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .getPath()+File.separator+"TestClass.dex");
                Log.d(TAG, "onClick: "+dexFile.toString());
                if(!dexFile.exists()){
                    Log.d(TAG, "onClick: rrrrr");
                    return;
                }

                DexClassLoader dexClassLoader = new DexClassLoader(dexFile.getAbsolutePath()
                        ,getExternalCacheDir().getAbsolutePath(),null,null);
                Log.d(TAG, "onClick: 11111111111");
                try {
                    Class clazz = dexClassLoader.
                            loadClass("com.example.asus1.rexiufu.TestClass");
                    Log.d(TAG, "onClick: 22222222222222");

                    com.example.asus1.rexiufu.TestClass testClass =
                            (com.example.asus1.rexiufu.TestClass)clazz.newInstance();


                    System.out.println(testClass.showToast());

                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }catch (IllegalAccessException e){
                    e.printStackTrace();

                }catch (InstantiationException e){
                    e.printStackTrace();

                }

            }
        });
//
//        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HotFixEngine.copyDexFileToAppAndFix(MainActivity.this,"classes_fix.dex",true);
//            }
//        });

//        ClassLoader loader = getClassLoader();
//        while (loader!=null){
//            System.out.println(loader.toString());
//            loader = loader.getParent();
//        }
    }
}
