package vivian.com.synui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import static vivian.com.synui.Content.*;
public class MainActivity extends AppCompatActivity implements Controller, View.OnClickListener{

    TextView textView ;
    Button button;
    Button to ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.main1);
        button = (Button)findViewById(R.id.change);
        button.setOnClickListener(this);
        to =(Button)findViewById(R.id.tomain2);
        to.setOnClickListener(this);

        resigter(this);
    }

    @Override
    public void change() {
        textView.setText("main 1 change");
        ifChange = true;
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.change:
              for (int i = 0;i<controllerList.size();i++){
                  controllerList.get(i).change();
              }
              break;
          case R.id.tomain2:
              Intent intent = new Intent(this,Main2Activity.class);
              startActivity(intent);
              break;
      }
    }
}
