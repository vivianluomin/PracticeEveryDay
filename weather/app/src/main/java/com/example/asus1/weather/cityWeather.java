package com.example.asus1.weather;

/**
 * Created by asus1 on 2017/5/10.
 */

public class cityWeather {

    private String city;
    private String country;
    private String daydate;
    private  String night;
    private  String date;
    private int tempMax;
    private int tempMin;



    public cityWeather(String city, String country, String daydate, String night, String date, int tempMax, int tempMin) {
        this.city = city;
        this.country = country;
        this.daydate = daydate;
        this.night = night;
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }
    public   cityWeather(){

    }

    public int getTempMax() {
        return tempMax;
    }

    public int getTempMin() {
        return tempMin;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDate() {
        return date;
    }

    public String getDaydate() {
        return daydate;
    }

    public String getNight() {
        return night;
    }

}
