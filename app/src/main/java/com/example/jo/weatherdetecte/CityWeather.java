package com.example.jo.weatherdetecte;

/**
 * Created by jo on 2017/5/28.
 */

public class CityWeather {
    private String siteName;
    private String temperature;
    private String weather;

    public CityWeather(String _sitename , String _temperature,String _weather){
        siteName = _sitename;
        temperature = _temperature;
        weather = _weather;
    }

    public String getSiteName(){
        return this.siteName;
    }
    public String getTemperature(){
        return this.temperature;
    }
    public String getWeather(){
        return this.weather;
    }
}
