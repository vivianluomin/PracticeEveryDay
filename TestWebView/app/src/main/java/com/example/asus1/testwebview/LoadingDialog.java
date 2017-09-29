package com.example.asus1.testwebview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

/**
 * Created by asus1 on 2017/9/29.
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        this(context,0);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }






}
