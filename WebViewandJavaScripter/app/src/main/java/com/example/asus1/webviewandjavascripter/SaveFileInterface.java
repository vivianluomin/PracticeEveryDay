package com.example.asus1.webviewandjavascripter;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by asus1 on 2017/11/11.
 */

public class SaveFileInterface {

    private Context mContext;
    private String mFilePath;

    public SaveFileInterface(Context context,String filePath){

        mContext = context;
        mFilePath = filePath;
    }

    @JavascriptInterface
    public void SaveFile(String html){

        File file = new File(mFilePath);
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
            html = "<head>"+html+"</head>";
            FileWriter writer = new FileWriter(file);
            writer.write(html,0,html.length());
            writer.flush();
            Log.d("filePath",file.getAbsolutePath());
            Log.d("fileSize",String.valueOf(file.length()));


        }catch (IOException e){
            e.printStackTrace();
        }


    }

}
