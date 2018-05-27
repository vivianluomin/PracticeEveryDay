package vivian.com.musicplayer;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IntentMusicActivity extends AppCompatActivity implements View.OnClickListener,ItemClick{
    ImageButton bendi ;
    ImageButton wangluo;
    ImageButton sousuo;

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_music);

        bendi = (ImageButton)findViewById(R.id.bendiyinyue);
        wangluo = (ImageButton)findViewById(R.id.wangluosousuo);
        sousuo = (ImageButton)findViewById(R.id.sousuo);
        bendi.setOnClickListener(this);
        wangluo.setOnClickListener(this);
        sousuo.setOnClickListener(this);
        viewPager = (ViewPager)findViewById(R.id.pager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bendiyinyue:
                Intent i = new Intent(this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.sousuo:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onItemClick(String href) {
        Intent intent = new Intent(this,ItemGedan.class);
        intent.putExtra("href",href);
        startActivity(intent);


    }
}
