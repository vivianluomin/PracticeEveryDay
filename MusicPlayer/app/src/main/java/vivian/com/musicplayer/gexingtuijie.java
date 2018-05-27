package vivian.com.musicplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by asus1 on 2017/7/28.
 */

public class gexingtuijie extends Fragment {

        RecyclerView recyclerView,xindie;
        RemenAdapter adapter,xindieAdapter;
    List<String> imgs;
    List<String> herfs;
    List<String> titles;
    List<String> album_imags;
    List<String> album_herfs;
    List<String> album_titles;

     final String URL = "http://music.163.com/discover";
    GridLayoutManager manager,manager1;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgs =new ArrayList<>();
        herfs= new ArrayList<>();
        titles = new ArrayList<>();
        album_imags = new ArrayList<>();
        album_herfs  = new ArrayList<>();
        album_titles = new ArrayList<>();

        if(recyclerView != null){
            adapter = new RemenAdapter(titles,herfs,imgs,getContext());
            xindieAdapter = new RemenAdapter(album_titles,album_herfs,album_imags,getContext());
            recyclerView.setAdapter(adapter);
            xindie.setAdapter(xindieAdapter);
        }
        new LoadImage().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gexingtuijie,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rementuijian);
        xindie = (RecyclerView)view.findViewById(R.id.xindieshangjia);
       manager =  new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(manager);
        manager1 =  new GridLayoutManager(getContext(),4);
        xindie.setLayoutManager(manager1);

        adapter = new RemenAdapter(titles,herfs,imgs,getContext());
        xindieAdapter = new RemenAdapter(album_titles,album_herfs,album_imags,getContext());
        recyclerView.setAdapter(adapter);
        xindie.setAdapter(xindieAdapter);
        return view;
    }


    class LoadImage extends AsyncTask<String,String,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            try{



            Document doc = Jsoup.connect(URL)
                    .get();

            Elements ele = doc.select("div");
            Elements ee = new Elements();
                Elements albums = new Elements();

            StringBuilder htmls = new StringBuilder();



            for(Element e :ele){
                if(e.className().equals("u-cover u-cover-1")){
                    ee.add(e);
                }
                if(e.className().equals("u-cover u-cover-alb1")){
                    albums.add(e);
                }
            }


            for(int i = 0;i<8;i++){
                htmls.append(ee.get(i).html());
            }
            String[] ss =htmls.toString().split("</div>");


            for(int i =0;i<ss.length;i++){
                String temp = ss[i];
                String imge = (String) temp.subSequence(temp.indexOf("src=")+5, temp.indexOf(">")-1);

                imgs.add(imge);
                String title= temp.substring(temp.indexOf("<a"), temp.indexOf("</a>"));
                String t = title.substring(title.indexOf("title=")+6,title.indexOf("href"));
                titles.add(t);
                String href = title.substring(title.indexOf("href=")+5,title.indexOf("class"));
                herfs.add("http://music.163.com"+href.trim());

            }



                ss =albums.toString().split("</div>");
                for(int i = 0;i<ss.length;i++){
                    String temp = ss[i];
                    String[] tt = temp.split("\">");


                    String href = tt[2].substring(tt[2].indexOf("href=\"")+6,tt[2].indexOf("\" class") );
                    if(!album_herfs.contains(href)){
                        String img = tt[1].substring(tt[1].indexOf("data-src")+10, tt[1].length());
                        album_imags.add(img);
                        String title = tt[2].substring(tt[2].indexOf("title")+7, tt[2].indexOf("\" href"));
                        album_titles.add(title);

                        album_herfs.add("http://music.163.com"+href);

                    }

                }





            } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            adapter.notifyDataSetChanged();
            xindieAdapter.notifyDataSetChanged();
        }
    }






}



