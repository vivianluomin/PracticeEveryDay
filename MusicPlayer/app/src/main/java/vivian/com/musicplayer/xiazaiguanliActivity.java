package vivian.com.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class xiazaiguanliActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiazaiguanli);
        Toolbar toolbar = (Toolbar)findViewById(R.id.xaizaiguanli_tool);
        toolbar.setNavigationIcon(R.drawable.jiantou);
        toolbar.setTitle("下载管理");
        setSupportActionBar(toolbar);
    }
}
