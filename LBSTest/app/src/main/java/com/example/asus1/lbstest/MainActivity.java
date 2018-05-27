package com.example.asus1.lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.LocaleDisplayNames;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public LocationClient mLocationClient;
    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mapView = (MapView)findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        Log.d("Map",baiduMap.toString());
        positionText = (TextView)findViewById(R.id.position_text_view);
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this
        ,Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            for(String per:permissions){
                Log.d("per",per+" "+permissionList.size());
            }
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
        }else {
            requestLocation();
        }
    }

    private void navigate(BDLocation location){
        if(isFirstLocate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomBy(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
      //  option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int i = 0;
        switch (requestCode){

            case 1:
                if(grantResults.length>0){
                    for(int result : grantResults){
                        i++;
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Log.d("permission",Integer.toString(result)+"  "+i);
                            Toast.makeText(this,"need permission",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度: ").append(bdLocation.getLatitude()).append("\n");
            currentPosition.append("经度: ").append(bdLocation.getLongitude()).append("\n");
            currentPosition.append("定位方式: ");
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation){
               currentPosition.append("GPS").append("\n");
            }else if(bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                currentPosition.append("网络").append("\n");
            }
            currentPosition.append("国家: ").append(bdLocation.getCountry()).append("\n");
            currentPosition.append("省: ").append(bdLocation.getProvince()).append("\n");
            currentPosition.append("市： ").append(bdLocation.getCity()).append("\n");
            currentPosition.append("区： ").append(bdLocation.getDistrict()).append("\n");
            currentPosition.append("街道: ").append(bdLocation.getStreet()).append("\n");
          //  positionText.setText(currentPosition);
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation||bdLocation.getLocType()
                    ==BDLocation.TypeNetWorkLocation){
                navigate(bdLocation);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
