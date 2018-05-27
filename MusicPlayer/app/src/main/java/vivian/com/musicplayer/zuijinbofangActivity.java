package vivian.com.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class zuijinbofangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuijinbofang);
        Toolbar toolbar = (Toolbar)findViewById(R.id.zuijinboofang_tool);
        toolbar.setNavigationIcon(R.drawable.jiantou);
        toolbar.setTitle("最近播放");
        setSupportActionBar(toolbar);
    }
}
