package vivian.com.musicplayer;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static vivian.com.musicplayer.Content.controllerList;

public class bendiyinyueActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView listView;
    List<music> musics;
    bendiyinyueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bendiyinyue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.bendiyinyue_tool);
        toolbar.setNavigationIcon(R.drawable.jiantou);
        toolbar.setTitle("本地音乐");
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.bendiyinyue_list);
        listView.setOnItemClickListener(this);
        musics = getMusic.getList(getContentResolver());
        adapter = new bendiyinyueAdapter(this, R.layout.bendi_item, musics);
        listView.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        music m = (music) parent.getItemAtPosition(position);
        MainActivity.nowMusic = m;

        Intent intent = new Intent(this, playMusic.class);
        intent.putExtra("songid", m.getId());
        intent.putExtra("albumid", m.getAlbum_id());
        intent.putExtra("title", m.getTitle());
        intent.putExtra("artist", m.getArtist());
        Log.d("id", String.valueOf(m.getId()) + "    " + String.valueOf(m.getAlbum_id()));
        intent.putExtra("uri", m.getUrl());
        intent.putExtra("flag", 1);
        intent.putExtra("from", "frombendi");
        intent.setAction("bofanglingyige");
        startService(intent);
        MainActivity.connect = true;

    }
}
