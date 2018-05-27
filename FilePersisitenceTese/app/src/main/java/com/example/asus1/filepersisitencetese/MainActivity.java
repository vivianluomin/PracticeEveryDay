package com.example.asus1.filepersisitencetese;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.edit);
        String input = load();
       Log.d("text",input);

        if(!TextUtils.isEmpty(input)){
            //Log.d("text","bu wei kong a ");
            editText.setText(input);
            editText.setSelection(input.length());
            Toast.makeText(this,"Restoring succeeded",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = editText.getText().toString();
        save(inputText);
    }

    public  void save(String inputText){

        FileOutputStream  out = null;
        BufferedWriter writer=null ;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText,0,inputText.length());
            writer.flush();
            Toast.makeText(this,"save",Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(out!=null) out.close();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public  String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                Log.e("asd",line);
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(in!=null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


        return  content.toString();
    }

}
