package vivian.com.musicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import static vivian.com.musicplayer.Content.*;
/**
 * Created by asus1 on 2017/7/18.
 */

public class playMusic extends Service implements Controller,MediaPlayer.OnPreparedListener {
    private static final String ACTION_ZUO= "vivian.com.musicplayer.zuo";
    private static final String ACTION_PAUSE= "vivian.com.musicplayer.pause";
    private static final String ACTION_YOU= "vivian.com.musicplayer.you";
   static MediaPlayer mediaPlayer;
    static Notification notification;
    String title;
    String artist;
    static String from="";
    int songid;
    int albumid;
    int size;
    List<music> musics;




    @Override
    public void play() {
        try {

            music m = getSong(songid);
            MainActivity.nowMusic = m;
            if(m !=null){
                int up = m.getId();
                songid = up;
                title = m.getTitle();
                artist = m.getArtist();
                albumid = m.getAlbum_id();
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }
                if(!from.equals("frombendi")){
                    if(mediaPlayer.isPlaying()){
                        setNotification(up, albumid, true);
                        mediaPlayer.pause();


                    }else {
                        setNotification(up, albumid, false);
                        mediaPlayer.start();
                    }
                }else {
                    Log.d("from",from);
                    from = "";
                }






            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void up() {
        try {

            Log.d("DDDDD",String.valueOf(songid));
            music m = getUpSong(songid);
            MainActivity.nowMusic = m;
            if(m !=null){
                int up = m.getId();
                songid=up;
                title = m.getTitle();
                artist=m.getArtist();
                albumid = m.getAlbum_id();
                if(mediaPlayer ==null){
                    mediaPlayer = new MediaPlayer();
                }
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, Uri.parse("content://media/external/audio/media/"+up));
                setNotification(up,albumid,false);
                mediaPlayer.prepareAsync();
            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void next() {
        try {

            music m = getNextSong(songid);
            MainActivity.nowMusic = m;
            if(m != null){
                int up = m.getId();
                songid=up;
                title = m.getTitle();
                artist=m.getArtist();
                albumid = m.getAlbum_id();
                if(mediaPlayer ==null){
                    mediaPlayer = new MediaPlayer();
                }
                mediaPlayer .reset();
                mediaPlayer.setDataSource(this, Uri.parse("content://media/external/audio/media/"+up));
                setNotification(up,albumid,false);
                mediaPlayer.prepareAsync();
            }


        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public  class myIBind extends Binder{

        public Service getService(){
            return playMusic.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musics = getMusic.getList(getContentResolver());
        Log.d("ser_onCreate","aaaaaaaaaa");
        Content.resigter(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        String action = intent.getAction();
        songid = intent.getIntExtra("songid", -1);
        Log.d("song", String.valueOf(songid));
        albumid = intent.getIntExtra("albumid", -1);
        int flag = intent.getIntExtra("flag", -1);
        title = intent.getStringExtra("title");
        artist = intent.getStringExtra("artist");
        size = intent.getIntExtra("size", -1);

        if (action.equals("bofanglingyige")) {
            try {
                from = intent.getStringExtra("from");

                mediaPlayer.reset();
                String uri = intent.getStringExtra("uri");

                if (flag == 1) {
                    if(songid != -1){
                        mediaPlayer.setDataSource(this, Uri.parse("content://media/external/audio/media/" + songid));
                        
                    }else {
                        setNotification(songid,albumid,true);
                        return super.onStartCommand(intent, flags, startId);
                    }


                } else if (flag == 2) {
                    mediaPlayer.setDataSource(uri);
                } else if (flag == -1) {
                    Toast.makeText(this, "音乐不存在", Toast.LENGTH_SHORT).show();
                    return START_STICKY;
                }

                    mediaPlayer.prepareAsync();
                    setNotification(songid, albumid, false);
                for(int j =0 ;j<controllerList.size();j++) {
                    controllerList.get(j).play();
                }


            } catch (IOException e) {
                Toast.makeText(this, "音乐不存在", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        } else if (action.equals(ACTION_PAUSE)) {

            for(int j =0 ;j<controllerList.size();j++) {
                controllerList.get(j).play();
            }

        }else if (action.equals(ACTION_ZUO)){
            for(int j =0 ;j<controllerList.size();j++) {
                controllerList.get(j).up();
            }

        }else if(action.equals(ACTION_YOU)){
            for(int j =0 ;j<controllerList.size();j++) {
                controllerList.get(j).next();
            }

        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new myIBind();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

            mp.start();
    }

    public void setNotification(int songid,int albumid,boolean puase){
        Bitmap bitmap = getMusic.getArtwork(getContentResolver(),songid,albumid,false,false);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        RemoteViews contentView = new RemoteViews(getPackageName(),R.layout.notification);
        contentView.setTextViewText(R.id.tubiao_title,title);
        contentView.setTextViewText(R.id.tubiao_artist,artist);
        if(bitmap !=null){

            contentView.setImageViewBitmap(R.id.tubiao_bitmap,bitmap);
        }else {
            contentView.setImageViewResource(R.id.tubiao_bitmap,R.drawable.fengmian);
        }

        Intent intent = new Intent(this,geciActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);

        Intent zuo = new Intent(this,playMusic.class);
        zuo.putExtra("songid",songid);
        zuo.setAction(ACTION_ZUO);
        PendingIntent pendingIntent_zuo = PendingIntent.getService(this,1,zuo,PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.tubiao_zuo,pendingIntent_zuo);

        Intent pase = new Intent(this,playMusic.class);
        pase.setAction(ACTION_PAUSE);
        pase.putExtra("songid",songid);
        if(!puase) {
            contentView.setImageViewResource(R.id.tubiao_zanting, R.drawable.bofang2);
        }else {
            contentView.setImageViewResource(R.id.tubiao_zanting, R.drawable.zanting2);
        }
        PendingIntent pendingIntent_pause = PendingIntent.getService(this,2,pase,PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.tubiao_zanting,pendingIntent_pause);

        Intent you = new Intent(this,playMusic.class);
        you.putExtra("songid",songid);
        Log.d("songididid",String.valueOf(songid));
        you.setAction(ACTION_YOU);
        PendingIntent pendingIntent_you = PendingIntent.getService(this,3,you,PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.tubiao_you,pendingIntent_you);

        Intent delet_intent = new Intent(this,playMusic.class);
        delet_intent.setAction("stop");


        builder.setSmallIcon(R.drawable.tubiao)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setContent(contentView);

        notification  = builder.build();

        notification.flags  = Notification.FLAG_NO_CLEAR;
        notification.priority = Notification.PRIORITY_DEFAULT;
        startForeground(10,notification);






    }


    private music getSong(int songid){

        int i;
        music m;
        for(i = 0; i<musics.size()&&musics.get(i).getId()!=songid; i++);
        if(i<musics.size()){
            m = musics.get(i);
        }else {
            m=musics.get(0);
        }




        return m;

    }

    private music getNextSong(int songid){
        int i;
        music m;
        for(i = 0; i<musics.size()&&musics.get(i).getId()!=songid; i++);
        if(i == 0){
            m = musics.get(musics.size()-1);
        }else if(i == musics.size()){
            m = musics.get(0);
        }else {
            m = musics.get(i+1);
        }


        return m;
    }
    private music getUpSong(int songid){
        int i;
        music m;
        for(i = 0; i<musics.size()&&musics.get(i).getId()!=songid; i++);
        if(i == 0){
            m = musics.get(musics.size()-1);
        }else if(i == musics.size()){
            m = musics.get(0);
        }else {
            m = musics.get(i-1);
        }


        return m;
    }


}
