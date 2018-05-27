package vivian.com.musicplayer;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static vivian.com.musicplayer.MainActivity.nowMusic;

/**
 * Created by asus1 on 2017/7/22.
 */

public class WordView extends TextView {

    String  path;

  List<String> words = new ArrayList<>();

    private Paint LoseFocus;
    private Paint OnFoucse;
    int index = 0;
    float x = 0;
    float y = 0;
    float middleY = 0;
    private static final int DY = 50;


    public WordView(Context context) {
        super(context);
        path = nowMusic.getUrl();
        init();
    }


    public WordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = nowMusic.getUrl();
        init();
    }

    public WordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = nowMusic.getUrl();
        init();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        Paint p = LoseFocus;
        p.setTextAlign(Paint.Align.CENTER);
        Paint p2 = OnFoucse;
        p2.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(words.get(index),x,middleY,p2);

        int alphaValue = 25;
        float tempY = middleY;
        for(int i= index-1;i>=0;i--){
            tempY-=DY;
            if(tempY<0){
                break;
            }

            p.setColor(Color.argb(255-alphaValue,245,245,245));
            canvas.drawText(words.get(i),x,tempY,p);
            alphaValue+=25;
        }

        alphaValue = 25;
        tempY = middleY;
        for(int i = index+1;i<words.size();i++){
            tempY+=DY;
            if(tempY>y){
                break;
            }
            p.setColor(Color.argb(255-alphaValue,245,245,245));
            canvas.drawText(words.get(i),x,tempY,p);
            alphaValue+=25;
        }
        index++;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x = w*0.5f;
        y = h;
        middleY = h*0.3f;
    }

    private void init(){
        setFocusable(true);
        LrcHandle handle = new LrcHandle();
        handle.ReadLRC(path);
        words = handle.getWords();
        for(int i =0;i<words.size();i++){
            Log.d("LRC",words.get(i));
        }
        LoseFocus = new Paint();
        LoseFocus.setAntiAlias(true);
        LoseFocus.setTextSize(22);
        LoseFocus.setColor(Color.WHITE);
        LoseFocus.setTypeface(Typeface.SERIF);

        OnFoucse = new Paint();
        OnFoucse.setAntiAlias(true);
        OnFoucse.setTextSize(30);
        OnFoucse.setColor(Color.YELLOW);
        OnFoucse.setTypeface(Typeface.SANS_SERIF);

    }
}
