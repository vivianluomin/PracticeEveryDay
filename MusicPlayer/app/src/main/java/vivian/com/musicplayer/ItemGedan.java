package vivian.com.musicplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemGedan extends AppCompatActivity {
    Toolbar toolbar;
    String url;
    ListView listView;
    ImageView imageView;
    TextView textView;
    List<String> titles;
    List<String> hrefs;
    String image;
    String tit;
    itemgedanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_gedan);
        toolbar = (Toolbar)findViewById(R.id.geciitem_tool);
        toolbar.setTitle("歌单");
        Intent intent = getIntent();
        url = intent.getStringExtra("href");
        toolbar.setNavigationIcon(R.drawable.jiantou);
        listView = (ListView)findViewById(R.id.gedanitem_list);
        imageView = (ImageView)findViewById(R.id.itemgedan_image);
        textView = (TextView)findViewById(R.id.gefanitem_title);
        titles = new ArrayList<>();
        titles.add("wwwwwww");
        hrefs = new ArrayList<>();
        setSupportActionBar(toolbar);
       new gedanItem().execute();
        adapter = new itemgedanAdapter(this,R.layout.gedan_item,titles);

    }

    class gedanItem extends AsyncTask<String,String,Bitmap>{


        @Override
        protected Bitmap doInBackground(String... params) {
            try{
                titles.clear();
                Document document = Jsoup.connect(url).get();
                Elements title = document.getElementsByClass("f-ff2 f-brk");
                tit = title.html();
                Elements img = document.getElementsByClass("cover u-cover u-cover-dj");
                String ht = img.html();
                image = ht.split("<span class=\"msk\"></span>")[0];
                image = image.substring(image.indexOf("src=")+5, image.indexOf("\" class="));
                Elements musics = document.getElementsByClass("f-hide");

                String lis = musics.get(1).html().trim();
                String[] li = lis.split("<li>");
                for(int i =1 ;i<li.length;i++){
                    String temp = li[i];
                    String t = temp.substring(temp.indexOf("\">")+2,temp.indexOf("</") );
                    titles.add(t);
                    String h = temp.substring(temp.indexOf("<a")+9,temp.indexOf("\">"));
                    hrefs.add("http://music.163.com"+h);
                }


            }catch (IOException e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            adapter.notifyDataSetChanged();
        }
    }
}
