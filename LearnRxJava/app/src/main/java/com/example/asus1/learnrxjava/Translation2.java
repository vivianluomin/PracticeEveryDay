package com.example.asus1.learnrxjava;

import android.util.Log;

public class Translation2 {


        private int status;
        private content content;

        private static class content{
            private String from;
            private String to;
            private String vender;
            private String out;
            private int errNo;
        }

        public void show(){
            Log.d("RxJava", "翻译内容 = "+content.out);
        }


}
