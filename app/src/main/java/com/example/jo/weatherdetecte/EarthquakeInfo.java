package com.example.jo.weatherdetecte;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.TimeZone;

import static com.example.jo.weatherdetecte.R.drawable.location;

/**
 * Created by jo on 2017/7/9.
 */

public class EarthquakeInfo {
    private String mag;
    private String place;
    private String country;
    private long time;
    private double lng;
    private double lat;
    private double deep;

    public EarthquakeInfo(String _mag,String _place,long _time ,double _lng,double _lat,double _deep){
        mag = _mag;
        place = _place;
        time = _time;
        lng = _lng;
        lat = _lat;
        deep = _deep;
    }
    public String getMag(){
        return mag;
    }
    public String getPlace(){
        return place;
    }
    public String getTime(){
        Date Dtime = new Date(this.time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateString = sdf.format(Dtime);
        return dateString;
    }
    public double getLng(){
        return lng;
    }
    public double getLat(){
        return lat;
    }
    public double getDeep(){
        return deep;
    }
    public String getCountry(){
        String[] locations = place.split(",");
        if(locations.length == 2) {
            if (locations[1].length() >= 20)
                country = "high_seas";
            else
                locations[1] = locations[1].replaceAll(" ","");
                locations[1] = locations[1].toLowerCase();
                country = locations[1];
        }else if(locations.length == 1){
            locations[0] = locations[0].replaceAll(" ","");
            locations[0] = locations[0].toLowerCase();
            country = locations[0];
        }
        return country;
    }

}
