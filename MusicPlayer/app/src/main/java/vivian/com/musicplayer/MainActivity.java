package vivian.com.musicplayer;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentUris.*;
import static vivian.com.musicplayer.Content.*;

public class MainActivity extends AppCompatActivity implements Controller,View.OnClickListener{



    ImageButton bendi ,wangluo,sousuo,bofang_button;
    TextView bendiyinyue,zuijinbofang,xiazaiguanli;
    List<music> musicList;
    ImageView fengmian;
    TextView bofang_name,bofang_artist;
    RelativeLayout relativeLayout;
    public  static  music nowMusic;

    static  boolean fristPlay = true;
    static  boolean connect = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bendi = (ImageButton) findViewById(R.id.bendiyinyue);
        wangluo = (ImageButton) findViewById(R.id.wangluosousuo);
        sousuo = (ImageButton) findViewById(R.id.sousuo);
        bendi.setOnClickListener(this);
        wangluo.setOnClickListener(this);
        sousuo.setOnClickListener(this);
        bendiyinyue = (TextView)findViewById(R.id.bendiyinyue_text);
        zuijinbofang = (TextView)findViewById(R.id.zuijinpofang_text);
        xiazaiguanli = (TextView)findViewById(R.id.xiazaiguanli_text);
        bendiyinyue.setOnClickListener(this);
        zuijinbofang.setOnClickListener(this);
        xiazaiguanli.setOnClickListener(this);
        fengmian = (ImageView)findViewById(R.id.fengmian);
        bofang_name=(TextView)findViewById(R.id.bofang_name);
        bofang_artist = (TextView)findViewById(R.id.bofang_artist);
        relativeLayout = (RelativeLayout)findViewById(R.id.bofang_layout);
        relativeLayout.setOnClickListener(this);
        bofang_button = (ImageButton)findViewById(R.id.bofang_button);

        bofang_button.setOnClickListener(this);
        checkMedia();
        getPermissions(this);

        Content.resigter(this);





    }

    private  void checkMedia(){
        if(playMusic.mediaPlayer !=null){
            if(playMusic.mediaPlayer.isPlaying()){
                bofang_button.setImageResource(R.drawable.bofang2);
            }else {
                bofang_button.setImageResource(R.drawable.zanting2);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wangluosousuo:
                Intent intent = new Intent(this,IntentMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.sousuo:
                Intent intent1   = new Intent(this,SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.bendiyinyue_text:
                Intent intent2 = new Intent(this,bendiyinyueActivity.class);
                startActivity(intent2);
                break;
            case R.id.zuijinpofang_text:
                Intent intent3 = new Intent(this,zuijinbofangActivity.class);
                startActivity(intent3);
                break;
            case R.id.xiazaiguanli_text:
                Intent intent4 = new Intent(this,xiazaiguanliActivity.class);
                startActivity(intent4);
                break;
            case R.id.bofang_layout:
                Intent intent5 = new Intent(this,Bofangyinyue.class);

                intent5.putExtra("title",bofang_name.getText());
                intent5.putExtra("artist",bofang_artist.getText());
                intent5.putExtra("duration",nowMusic.getDuration());
                intent5.putExtra("songid",nowMusic.getId());
                intent5.putExtra("albumid",nowMusic.getAlbum_id());

                startActivity(intent5);
                break;
            case R.id.bofang_button:
                if(fristPlay){
                    if(!connect){
                        statMyService();


                    }



                }else {
                    for(int j =0 ;j<controllerList.size();j++){
                  controllerList.get(j).play();
                  Log.d("con",controllerList.get(j).toString());
                    }
                }


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





    public  void getPermissions(Context context){

        List<String> permissions = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.INTERNET);
        }
        if(ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        }
        if(ContextCompat.checkSelfPermission(context,Manifest.permission.WAKE_LOCK)!=PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.WAKE_LOCK);
        }
        if(ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_NOTIFICATION_POLICY)!=PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.ACCESS_NOTIFICATION_POLICY);
        }
        if(permissions.size()>0) {
            String[] strings = permissions.toArray(new String[permissions.size()]);

            for (String s : strings) {
                Log.d("permission", s);
            }

            Log.d("size",String.valueOf(strings.length));
            ActivityCompat.requestPermissions(this,strings,1);
        }else {
            musicList = getMusic.getList(getContentResolver());
            music m = musicList.get(0);
            if(nowMusic == null){
                nowMusic = m;
            }

            Bitmap bitmap = getMusic.getArtwork(getContentResolver(), nowMusic.getId(),nowMusic.getAlbum_id(), false, true);
            Log.d("id", String.valueOf(m.getAlbum_id()) + "    " + permissions.size());
            if (bitmap != null) {
                fengmian.setImageBitmap(bitmap);
            } else {
                fengmian.setImageResource(R.drawable.fengmian);
            }
            bofang_artist.setText(nowMusic.getArtist());
            bofang_name.setText(nowMusic.getTitle());
        }


        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                for(int i : grantResults){
                    if(i!=PackageManager.PERMISSION_GRANTED){
                        fengmian.setImageResource(R.drawable.fengmian);
                        bofang_artist.setText("未知歌手");
                        bofang_name.setText("未知音乐");
                        return;
                    }

                }

                music m = musicList.get(0);
                if(nowMusic ==null)
                nowMusic = m;
                Bitmap bitmap  = getMusic.getArtwork(getContentResolver(),nowMusic.getId(),nowMusic.getAlbum_id(),false,true);
                Log.d("id",String.valueOf(m.getAlbum_id()));
                if(bitmap != null){
                    fengmian.setImageBitmap(bitmap);
                }else {
                    fengmian.setImageResource(R.drawable.fengmian);
                }
                bofang_artist.setText(nowMusic.getArtist());
                bofang_name.setText(nowMusic.getTitle());

                }
        }



    @Override
    public void play() {
        if(fristPlay){
            bofang_button.setImageResource(R.drawable.bofang2);
            Log.d("frist","frist");
            fristPlay = false;

        }else {
            if(playMusic.mediaPlayer.isPlaying()){

                bofang_button.setImageResource(R.drawable.bofang2);
                Log.d("bofang","bofang");

            }else {

                bofang_button.setImageResource(R.drawable.zanting2);
                Log.d("zanting","zanting");
            }


        }

        Log.d("plat_main",nowMusic.getArtist()+"   "+nowMusic.getTitle());
        bofang_artist.setText(nowMusic.getArtist());
        bofang_name.setText(nowMusic.getTitle());

    }

    @Override
    public void up() {
        Bitmap bitmap  = getMusic.getArtwork(getContentResolver(),nowMusic.getId(),nowMusic.getAlbum_id(),false,true);

        if(bitmap != null){
            fengmian.setImageBitmap(bitmap);
        }else {
            fengmian.setImageResource(R.drawable.fengmian);
        }
        bofang_artist.setText(nowMusic.getArtist());
        bofang_name.setText(nowMusic.getTitle());
        bofang_button.setImageResource(R.drawable.bofang2);

    }

    @Override
    public void next() {
        Bitmap bitmap  = getMusic.getArtwork(getContentResolver(),nowMusic.getId(),nowMusic.getAlbum_id(),false,true);

        if(bitmap != null){
            fengmian.setImageBitmap(bitmap);
        }else {
            fengmian.setImageResource(R.drawable.fengmian);
        }
        bofang_artist.setText(nowMusic.getArtist());
        bofang_name.setText(nowMusic.getTitle());
        bofang_button.setImageResource(R.drawable.bofang2);

    }



}

