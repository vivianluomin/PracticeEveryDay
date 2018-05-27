package vivian.com.musicplayer;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static vivian.com.musicplayer.MainActivity.nowMusic;
import static vivian.com.musicplayer.playMusic.mediaPlayer;
public class geciActivity extends AppCompatActivity {


Toolbar toolbar;
    WordView view;
    LrcHandle handle ;
    List<Integer> times ;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geci);
        toolbar = (Toolbar)findViewById(R.id.geci_tool);
        toolbar.setNavigationIcon(R.drawable.fanhui);
        toolbar.setTitle(nowMusic.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle(nowMusic.getArtist());
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        view = (WordView)findViewById(R.id.text);
        textView = (TextView)findViewById(R.id.lrc_xiazai);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        String p = nowMusic.getLrc();
        Log.d("pppp",p);
        if(!p.equals(" ")){
            Log.d("pppp",p);
            //lrcs(p);
        }else {
            //view.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
       }




    }

    private void lrcs (String path){
        handle = new LrcHandle();
        times = new ArrayList<>();
        handle.ReadLRC(path);
        times = handle.getTime();
    }




}
