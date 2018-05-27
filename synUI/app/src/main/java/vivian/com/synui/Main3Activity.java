package vivian.com.synui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static vivian.com.synui.Content.ifChange;
import static vivian.com.synui.Content.resigter;

public class Main3Activity extends AppCompatActivity implements Controller{

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textView = (TextView)findViewById(R.id.main3);
        resigter(this);
      if(ifChange){
          change();
      }
    }

    @Override
    public void change() {
        textView.setText("main 3 change");
    }
}
