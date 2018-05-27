package com.example.asus1.weather;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    URL weatherUrl;
    HttpURLConnection urlConnection;
    InputStream in=null;
    BufferedReader reader = null;
    StringBuilder responde =null;
    TextView arear;
    StringBuilder city_p = new StringBuilder();
    List<cityWeather> cityWeatherList = new ArrayList<cityWeather>();
    EditText search ;
    TextView date;
    TextView weatherdate;
    ImageView weatherImageD;
    ImageView weatherImageN;
    LinearLayout linearLayout;

    Toolbar toolbar;
    ImageButton imageButton;
    cityWeather citywether ;

    TextView weatherNight;
    TextView temp;
    Button cha ;
    public LocationClient mLocationClient = null;
    public  MyLocationListener myListenner = new MyLocationListener();
    public boolean ifNetwork=true;
  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (EditText)findViewById(R.id.search_weather);
        arear=(TextView)findViewById(R.id.weizhi_text);
        cha = (Button)findViewById(R.id.cha);
        cha.setOnClickListener(this);



        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListenner);



        ImageView weizhi_image = (ImageView)findViewById(R.id.weizhi);
        weizhi_image.setImageResource(R.drawable.weizhi);
        ifNetwork=isNetworkAvailable(MainActivity.this);
        getPermission();



    }
private  boolean isNetworkAvailable(Activity activity){
    Context context = activity.getApplicationContext();
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if(connectivityManager == null){
        return false;
    }else {
       NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isAvailable()){
            return true;
        }

    }
   return false;
}

  private Handler handler = new Handler(){
      @Override
      public void handleMessage(Message msg) {
          switch (msg.what){
              case 1:
                  Toast.makeText(MainActivity.this,"您所查询的城市不存在,请重新输入",Toast.LENGTH_SHORT).show();
              case 2:
                  Bundle bundle =msg.getData();
                  city_p.replace(0,city_p.length(),bundle.getString("s"));
          }
      }
  };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_item:
                DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
                drawerLayout.openDrawer(GravityCompat.END);
        }
        return false;
    }

    private void requestLocation(){

        if(ifNetwork){
            initLocation();
            mLocationClient.start();
        }else {
            getDatabade();
        }

    }


    private  void getDatabade(){
        if(helper!=null){
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor=database.query("Weather",null,null,null,null,null,null);
        List<cityWeather> cityWeatherList2 = new ArrayList<>();
        if(cursor.moveToFirst()) {
            for(int i=0;i<3;i++){

                String city = cursor.getString(cursor.getColumnIndex("city"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String day = cursor.getString(cursor.getColumnIndex("day"));
                String night = cursor.getString(cursor.getColumnIndex("night"));
                int tempMax = cursor.getInt(cursor.getColumnIndex("tempMax"));
                int tempMin = cursor.getInt(cursor.getColumnIndex("tempMin"));
                Log.d("database",city+"  "+date+"   "+day+"   "+night);
                cityWeather cityWeather = new cityWeather(city, "中国", day, night, date, tempMax, tempMin);
                city_p.replace(0, city.length(), city);
                cityWeatherList2.add(cityWeather);
                cursor.moveToNext();


            }

            arear.setText(city_p.toString()+"  "+"中国");
            display(R.id.date1,R.id.weather_day1,R.id.weather_night1,R.id.temp1,cityWeatherList2.get(0),R.id.weather_imageday1,R.id.weather_imagenight1);
            display(R.id.date2,R.id.weather_day2,R.id.weather_night2,R.id.temp2,cityWeatherList2.get(1),R.id.weather_imageday2,R.id.weather_imagenight2);
            display(R.id.date3,R.id.weather_day3,R.id.weather_night3,R.id.temp3,cityWeatherList2.get(2),R.id.weather_imageday3,R.id.weather_imagenight3);
            database.close();
            cursor.close();
        }
    }else {
        helper = new weatherDatabase(MainActivity.this,"Weather.db",null,1);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        for(int i=0;i<3;i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("city", "北京");
            contentValues.put("date", "无");
            contentValues.put("day", "无");
            contentValues.put("night", "无");
            contentValues.put("tempMax", 0);
            contentValues.put("tempMin", 0);
            sqLiteDatabase.insert("Weather",null,contentValues);
        }
            getDatabade();

    }
    }



    private void getPermission(){
        final List<String> permissionsList = new ArrayList<>();

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionsList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        String[] permissions = permissionsList.toArray(new String[permissionsList.size()]);

        if(!permissionsList.isEmpty()){
            ActivityCompat.requestPermissions(this,permissions,1);
        }else {
           requestLocation();
            Log.d("getpermission",city_p.toString());

            getData(city_p.toString());

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cha:
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{"android.permission.INTERNET"},1);
                }else {
                    ifNetwork=isNetworkAvailable(MainActivity.this);
                    if(ifNetwork){
                    city_p.replace(0,city_p.length(),search.getText().toString());
                    arear.setText(city_p.toString());
                    search.setText("");
                    if(city_p.length()>0){
                        getData(city_p.toString());
                    }else {
                        Toast.makeText(this,"城市不能为空",Toast.LENGTH_SHORT).show();
                    }
                }else {
                getDatabade();
            }}
                break;


        }



    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        int span = 1000;
        option.setScanSpan(0);

        option.setIsNeedAddress(true);

        mLocationClient.setLocOption(option);


    }

    private  void  getData(final String city){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                try {

                    Log.d("getData", city_p.toString());
                        weatherUrl = new URL("https://free-api.heweather.com/v5/forecast?" + "city=" + city_p.toString() + "&key=f4b64ca6f3734318b7ac8134e54c2cd0");
                        urlConnection = (HttpURLConnection) weatherUrl.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setConnectTimeout(8000);
                        urlConnection.setReadTimeout(8000);
                        in = urlConnection.getInputStream();
                        if(urlConnection != null) {
                            // Log.d("Main",in.toString());
                            reader = new BufferedReader(new InputStreamReader(in));
                            responde = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                responde.append(line);
                                responde.append('\n');
                            }
                            String response = responde.toString();
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("HeWeather5");

                            String country;
                            String daydate;
                            String night;
                            String date;
                            int tempMax;
                            int tempMin;
                            JSONObject t = jsonArray.getJSONObject(0);
                            JSONObject jsonObject_basic = t.getJSONObject("basic");

                            country = jsonObject_basic.getString("cnty");

                            JSONArray jsonArray_dayily_forecast = t.getJSONArray("daily_forecast");
                            List<JSONObject> jsonArrayList = new ArrayList<JSONObject>();

                            for (int i = 0; i < 3; i++) {
                                jsonArrayList.add(jsonArray_dayily_forecast.getJSONObject(i));
                                JSONObject jsonObject_temp = jsonArrayList.get(i);
                                JSONObject cnd = jsonObject_temp.getJSONObject("cond");
                                daydate = cnd.getString("txt_d");
                                night = cnd.getString("txt_n");
                                date = jsonObject_temp.getString("date");
                                JSONObject temp = jsonObject_temp.getJSONObject("tmp");
                                tempMax = temp.getInt("max");
                                tempMin = temp.getInt("min");
                                cityWeather weather = new cityWeather(city_p.toString(), country, daydate, night, date, tempMax, tempMin);
                                citywether = weather;
                                cityWeatherList.add(0, weather);

                            }
                            showData();
                        }else {
                           Message message = new Message();
                            message.what=1;
                            handler.sendMessage(message);

                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }catch(JSONException E){
                        E.printStackTrace();
                    }finally{
                        if (urlConnection != null)
                            urlConnection.disconnect();
                    }

            }
        }).start();

    }


    private int count=2;
    private  SQLiteOpenHelper helper;
    SQLiteDatabase database=null;
    private  void showData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String arer_s;
                cityWeather cityWeather_temp = cityWeatherList.get(2);
                arer_s = cityWeather_temp.getCountry();
                arear.setText(city_p.toString()+"  "+arer_s);
                linearLayout=(LinearLayout)findViewById(R.id.bg_L);
                setBG(cityWeather_temp.getDaydate());
                helper=new weatherDatabase(MainActivity.this,"Weather.db",null,1);

                database= helper.getWritableDatabase();


                display(R.id.date1,R.id.weather_day1,R.id.weather_night1,R.id.temp1,cityWeatherList.get(2),R.id.weather_imageday1,R.id.weather_imagenight1);
                display(R.id.date2,R.id.weather_day2,R.id.weather_night2,R.id.temp2,cityWeatherList.get(1),R.id.weather_imageday2,R.id.weather_imagenight2);
                display(R.id.date3,R.id.weather_day3,R.id.weather_night3,R.id.temp3,cityWeatherList.get(0),R.id.weather_imageday3,R.id.weather_imagenight3);

            }
        });
    }

    private  void display(int id1,int id2,int id3,int id4,cityWeather weather,int Dimage,int Nimage){

        date =(TextView)findViewById(id1);
        date.setText(weather.getDate());
        weatherdate = (TextView)findViewById(id2);
        weatherNight=(TextView)findViewById(id3);
        weatherImageD=(ImageView)findViewById(Dimage);
        weatherImageN = (ImageView)findViewById(Nimage);
        temp=(TextView)findViewById(id4);
        weatherdate.setText(weather.getDaydate());
        weatherNight.setText(weather.getNight());
        temp.setText(weather.getTempMax()+"°-----"+weather.getTempMin()+"°");
        weatherImageD.setImageResource(getImage(weather.getDaydate()));
        weatherImageN.setImageResource(getImage(weather.getNight()));
        Log.d("display",weather.getDaydate()+"  "+weather.getCity());
        if(ifNetwork) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("city", city_p.toString());
            contentValues.put("date", weather.getDate());
            contentValues.put("day", weather.getDaydate());
            contentValues.put("night", weather.getNight());
            contentValues.put("tempMax", weather.getTempMax());
            contentValues.put("tempMin", weather.getTempMin());

            database.insert("Weather", null, contentValues);
        }


    }

    private void setBG(String weather){
        if(weather.contains("晴")){
            linearLayout.setBackgroundResource(R.drawable.bg_qing);

        }else if(weather.contains("雨")){
            linearLayout.setBackgroundResource(R.drawable.bg_yu);

        }else if(weather.contains("云")||weather.contains("阴")){
            linearLayout.setBackgroundResource(R.drawable.bg_duoyun);

        }
    }

    private  int getImage(String weather){
        int id = R.drawable.d00;
        if(weather.contains("晴")){
            return R.drawable.d00;
        }else if(weather.contains("雨")){

            return R.drawable.d09;
        }else if(weather.contains("云")||weather.contains("阴")){

            return R.drawable.d02;
        }

        return id;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                for(int i = 0;i<grantResults.length;i++){
                    if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"你拒绝了定位",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                requestLocation();
                Log.d("onReueat",city_p.toString());

                    getData(city_p.toString());


        }
    }

    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            String s=bdLocation.getCity();
            String[] sp=s.split("市");
            s=sp[0];
            Message message = new Message();
            message.what=2;
            Bundle bundle = new Bundle();
            bundle.putString("s",s);
            message.setData(bundle);
            handler.sendMessage(message);


        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }


}
