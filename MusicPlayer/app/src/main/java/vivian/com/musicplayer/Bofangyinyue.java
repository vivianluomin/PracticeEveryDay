package vivian.com.musicplayer;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.util.List;

import static vivian.com.musicplayer.MainActivity.*;
import static vivian.com.musicplayer.playMusic.*;
import static vivian.com.musicplayer.Content.*;
public class Bofangyinyue extends AppCompatActivity implements Controller,View.OnClickListener,SeekBar.OnSeekBarChangeListener{


    SeekBar seekBar;
    ImageButton zuo,you,zanting;
    Bitmap bitmap;
    ImageView imageView;
    int songid,albumid;
    int duration;
    LooperThread play_Thread;
    List<music> musics;
    Toolbar toolbar;
    RelativeLayout bofang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bofangyinyue);
        musics = getMusic.getList(getContentResolver());
        toolbar = (Toolbar)findViewById(R.id.bofangyinyue_tool);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String artist = intent.getStringExtra("artist");
        toolbar.setTitle(title);
        toolbar.setSubtitle(artist);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.fanhui);
        setSupportActionBar(toolbar);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        zuo = (ImageButton)findViewById(R.id.zuo);
        you = (ImageButton)findViewById(R.id.you);
        zanting = (ImageButton)findViewById(R.id.zanting);
        zuo.setOnClickListener(this);
        you.setOnClickListener(this);
        zanting.setOnClickListener(this);
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            zanting.setImageResource(R.drawable.bofang2);
        }
        seekBar.setOnSeekBarChangeListener(this);
        bofang = (RelativeLayout)findViewById(R.id.bofang_relative);
        bofang.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.bofangfengmian);
        songid = intent.getIntExtra("songid",-1);
        albumid = intent.getIntExtra("albumid",-1);
        duration = intent.getIntExtra("duration",0);
        seekBar.setMax(duration);
        Log.d("issss",String.valueOf(albumid));
        bitmap = getMusic.getArtwork(getContentResolver(),songid,albumid,false,false);
        if(bitmap != null){

            imageView.setImageBitmap(bitmap);
        }else {
            imageView.setImageResource(R.drawable.largefengmian);
        }

        play_Thread = new LooperThread();

        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            play_Thread.start();
        }

        resigter(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zuo:
                nowMusic = getUpSong(songid);
                if (fristPlay) {
                    if (!connect) {

                        statMyService();

                        this.up();

                    }
                }else {
                    for (int i = 0; i < controllerList.size(); i++) {
                        controllerList.get(i).up();
                    }
                }

                break;
            case R.id.zanting:
                if (fristPlay) {
                    if (!connect) {
                        statMyService();
                        zanting.setImageResource(R.drawable.bofang2);

                    }
                }else {
                    for (int i = 0; i < controllerList.size(); i++) {
                        controllerList.get(i).play();
                    }
                }


                break;
            case R.id.you:
                nowMusic = getNextSong(songid);
                if (fristPlay) {
                    if (!connect) {

                        statMyService();
                        this.next();


                    }
                }else {
                    for (int i = 0; i < controllerList.size(); i++) {
                        controllerList.get(i).up();
                    }
                }
                break;
            case R.id.bofang_relative :
                Intent intent = new Intent(this,geciActivity.class);
                startActivity(intent);

                break;
        }

    }

    private void  statMyService(){
        Intent intent = new Intent(this,playMusic.class);
        String action;
        if(fristPlay) {
            action = "bofanglingyige";


            if (nowMusic != null) {

                intent.putExtra("songid", nowMusic.getId());
                intent.putExtra("albumid", nowMusic.getAlbum_id());
                intent.putExtra("title", nowMusic.getTitle());
                intent.putExtra("artist", nowMusic.getArtist());
                intent.putExtra("uri", nowMusic.getUrl());
                intent.putExtra("flag", 1);
                intent.putExtra("from","Main");
                intent.setAction(action);


            }
            if (nowMusic == null) {

                intent.putExtra("songid", -1);
                intent.putExtra("albumid", -1);
                intent.putExtra("title", "未知音乐");
                intent.putExtra("artist", "未知歌手");
                intent.putExtra("flag", 1);
                intent.setAction(action);
            }
            startService(intent);
            Log.d("start", "dddddddddddddddddd");

            connect = true;


        }

    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
           if(fromUser){
               if(mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                   mediaPlayer.seekTo(progress);

               }


           }
    }

    @Override
    public void play() {
        if(mediaPlayer.isPlaying()){
            zanting.setImageResource(R.drawable.bofang2);
            if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                Log.d("duration","sfsfsadfsadfsdaf");
                if(!play_Thread.isAlive())
                play_Thread.start();
            }
        }else {
            zanting.setImageResource(R.drawable.zanting2);


        }

    }

    @Override
    public void up() {
        bitmap = getMusic.getArtwork(getContentResolver(),nowMusic.getId(),nowMusic.getAlbum_id(),false,false);
        if(bitmap != null){

            imageView.setImageBitmap(bitmap);
            if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                Log.d("duration","sfsfsadfsadfsdaf");
                play_Thread.start();
            }
        }else {
            imageView.setImageResource(R.drawable.largefengmian);
        }
        zanting.setImageResource(R.drawable.bofang2);
        toolbar.setTitle(nowMusic.getTitle());
        toolbar.setSubtitle(nowMusic.getArtist());
    }

    @Override
    public void next() {
        bitmap = getMusic.getArtwork(getContentResolver(),nowMusic.getId(),nowMusic.getAlbum_id(),false,false);
        if(bitmap != null){

            imageView.setImageBitmap(bitmap);
            if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                Log.d("duration","sfsfsadfsadfsdaf");
                play_Thread.start();
            }
        }else {
            imageView.setImageResource(R.drawable.largefengmian);
        }
        zanting.setImageResource(R.drawable.bofang2);
        toolbar.setTitle(nowMusic.getTitle());
        toolbar.setSubtitle(nowMusic.getArtist());

    }

    class LooperThread extends Thread{
      int i = 0;
        @Override
        public void run() {
            while(true){

                i = mediaPlayer.getCurrentPosition();

                if(i<duration){

                    seekBar.setProgress(i);

                    try{
                        sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }


                }else {
                    break;
                }



            }
        }
    }

    private music getNextSong(int songid){
        int i;
        music m=null;
        if(songid!=-1){
            for(i = 0; i<musics.size()&&musics.get(i).getId()!=songid; i++);
            if(i == 0){
                m = musics.get(musics.size()-1);
            }else if(i == musics.size()){
                m = musics.get(0);
            }else {
                m = musics.get(i+1);
            }
        }



        return m;
    }
    private music getUpSong(int songid){
        int i;
        music m=null;
        if(songid != -1){
            for(i = 0; i<musics.size()&&musics.get(i).getId()!=songid; i++);
            if(i == 0){
                m = musics.get(musics.size()-1);
            }else if(i == musics.size()){
                m = musics.get(0);
            }else {
                m = musics.get(i-1);
            }


        }

        return m;
    }

}
