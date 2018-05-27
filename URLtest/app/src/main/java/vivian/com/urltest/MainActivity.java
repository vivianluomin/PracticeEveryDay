package vivian.com.urltest;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    String url = "https://api.seniverse.com/v3/weather/now.json?key=5ekxy0g1gzc7nfl5&location=beijing&language=zh-Hans&unit=c";
    NetworkImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (NetworkImageView) findViewById(R.id.image);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                StringRequest request = new StringRequest(Request.Method.GET,"https://www.baidu.com/",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Log.d("A", response);
//                            }
//                        }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("AAA",error.getMessage(),error);
//                    }
//                });
//                queue.add(request);
//            }
//        }).start();
       // testJsonRequest();
        //testImageRequest();
        testImageLoader();
        //testXmlRequest();
        //testPost();


    }

    public void testJsonRequest(){
        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(
               Request.Method.GET,  url,null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try{
                    JSONArray response = jsonObject.getJSONArray("results");
                    JSONObject loc = response.getJSONObject(0);
                    JSONObject locc = loc.getJSONObject("location");
                    String city = locc.getString("name");
                    String country  = locc.getString("country");
                    Log.d("city",city+"  "+country);
                    //Log.d("ccc",loc.toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("cccc",volleyError.getMessage());
            }
        }
        );

        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }

    public void testImageRequest(){
        String url = "http://img0.imgtn.bdimg.com/it/u=1430510032,2634676844&fm=26&gp=0.jpg";

        final ImageRequest imageRequest = new ImageRequest(
                url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
        }
        );

        Volley.newRequestQueue(this).add(imageRequest);

    }

    public  void testImageLoader(){
        String url = "http://p1.music.126.net/b2v1j9bLcWGBRDLpQElvJA==/18909400974694216.jpg?param=140y140";
        RequestQueue queue = Volley.newRequestQueue(this);
        ImageLoader loader = new ImageLoader(queue,new BitmapCache());

//        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
//                R.mipmap.ic_launcher,R.mipmap.ic_launcher);
//        loader.get(url,listener);
        imageView .setDefaultImageResId(R.mipmap.ic_launcher);
        imageView.setErrorImageResId(R.mipmap.ic_launcher);
        imageView.setImageUrl(url,loader);
    }


    class BitmapCache implements ImageLoader.ImageCache{

        private LruCache<String,Bitmap> mCache;

        public BitmapCache(){
            int maxSize = 10*1024*1024;
            mCache = new LruCache<String, Bitmap>(maxSize){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes()*value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url,bitmap);
        }
    }

    public  void testXmlRequest(){
        String url = "http://flash.weather.com.cn/wmaps/xml/china.xml";
        XMLRequest request = new XMLRequest(url, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                try{
                    int eventype = response.getEventType();
                    while(eventype != XmlPullParser.END_DOCUMENT){
                        switch (eventype){
                            case XmlPullParser.START_TAG:
                                String nodeName = response.getName();
                                if("city".equals(nodeName)){
                                    String  pName = response.getAttributeValue(0);
                                    Log.d("aaaaa",pName);
                                }
                                break;
                        }
                        eventype = response.next();

                    }
                }catch (XmlPullParserException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("aaa",volleyError.getMessage(),volleyError);
            }
        });
        Volley.newRequestQueue(this).add(request);
    }


    public void testPost(){
        String url = "http://222.24.19.201/default2.aspx";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {



//                            JSONObject jsonObject = new JSONObject(s);
//                            JSONArray response = jsonObject.getJSONArray("results");
//                            JSONObject loc = response.getJSONObject(0);
//                            JSONObject locc = loc.getJSONObject("location");
//                            String city = locc.getString("name");
//                            String country  = locc.getString("country");
                           Log.d("city",s);


                    }
                }, new Response.ErrorListener() {
                     @Override
                        public void onErrorResponse(VolleyError volleyError) {
                         Log.d("qq",volleyError.getMessage(),volleyError);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("txtUserName","05158091");
                params.put("TextBox2","abc@+1234...");
                //params.put("txtSecretCode","fp6b");

                return super.getParams();
            }
        };

        Volley.newRequestQueue(this).add(request);
    }



}
