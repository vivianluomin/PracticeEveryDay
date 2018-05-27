package vivian.com.uizidongyi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    view.postInvalidate();
                    Log.d("IIIIIIIII","IIIIIIIIIIIIII");

                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }
                }

        }).start();
    }



}
