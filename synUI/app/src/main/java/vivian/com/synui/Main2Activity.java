package vivian.com.synui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static vivian.com.synui.Content.ifChange;
import static vivian.com.synui.Content.resigter;

public class Main2Activity extends AppCompatActivity implements Controller,View.OnClickListener{
        TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = (TextView)findViewById(R.id.main2);
        button = (Button)findViewById(R.id.tomain3);
        button.setOnClickListener(this);
        resigter(this);
        if(ifChange){
            change();
        }
    }

    @Override
    public void change() {
        textView.setText("main 2 change");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,Main3Activity.class);
        startActivity(intent);
    }
}
