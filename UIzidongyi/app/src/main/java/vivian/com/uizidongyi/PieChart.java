package vivian.com.uizidongyi;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by asus1 on 2017/7/27.
 */

public class PieChart extends View {


    int width;
    int height;

    Paint textPaint;
    public static String[] strings = new String[]{
            "aaaaaaaaaaaaaa",
            "bbbbbbbbbbbbbb",
            "cccccccccccccc",
            "dddddddddddddd",
            "eeeeeeeeeeeeee",
            "fffffffffffffff",
            "ggggggggggggggg",
    };

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public PieChart(Context context) {
        super(context);
        init();
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public static int i = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(i>=strings.length){
            i=0;
        }
        canvas.drawText(strings[i],width,height,textPaint);
        Log.d("ssssssss",strings[i]);
        i++;

    }

    private  void init(){
        textPaint  = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(80f);
        textPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w/2;
        height = h/2;

    }
}
