package com.example.jo.weatherdetecte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo on 2017/7/20.
 */

public class WeatherInfo implements Serializable{
    private String nowTemp;
    private String nowHumid;
    private String nowRaindrop;
    private String nowWind;
    public List<daysdetail> daysdetailList = new ArrayList<>();
    private String comfortableContent;
    private String UVContent;
    private String sportContent;
    private String suitContent;

    public WeatherInfo(String _nowTemp,String _nowHumid,String _nowRaindrop,String _nowWind,String _comfortableContent,String _UVContent,String _sportContent,String _suitContent){
        nowTemp = _nowTemp;
        nowHumid = _nowHumid;
        nowRaindrop = _nowRaindrop;
        nowWind = _nowWind;
        comfortableContent = _comfortableContent;
        UVContent = _UVContent;
        sportContent = _sportContent;
        suitContent = _suitContent;
    }

    public String getNowTemp(){return nowTemp;}
    public String getNowRaindrop(){return nowRaindrop;}
    public String getNowHumid(){return nowHumid;}
    public String getNowWind(){return nowWind;}
    public String getComfortableContent(){return comfortableContent;}
    public String getUVContent(){return UVContent;}
    public String getSportContent(){return sportContent;}
    public String getSuitContent(){return suitContent;}

    public void addDayDetail(String _date,String _weather,String _tempMax,String _tempMin,String _raindrop){
        daysdetail dd = new daysdetail(_date, _weather, _tempMax, _tempMin, _raindrop);
        daysdetailList.add(dd);
    }


    public class daysdetail implements Serializable{
        private String date;
        private String weather;
        private String tempMax;
        private String tempMin;
        private String raindrop;
        public daysdetail(String _date,String _weather,String _tempMax,String _tempMin,String _raindrop){
            this.date = _date;
            this.weather = _weather;
            this.tempMax = _tempMax;
            this.tempMin = _tempMin;
            this.raindrop = _raindrop;
        }
        public String getDate(){
            return this.date;
        }

        public String getWeather(){
            return this.weather;
        }

        public String getTempMax(){
            return this.tempMax;
        }

        public String getTempMin(){
            return this.tempMin;
        }

        public String getRaindrop(){
            return this.raindrop;
        }
    }
}
