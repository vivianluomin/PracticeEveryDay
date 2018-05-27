package vivian.com.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar)findViewById(R.id.search_tool);
        toolbar.setNavigationIcon(R.drawable.jiantou);
        toolbar.setTitle("搜索音乐");
        setSupportActionBar(toolbar);
    }
}
