package com.example.jo.weatherdetecte;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by jo on 2017/7/3.
 */

public class Dayinfo {
    private ImageView weatherImg;
    private TextView dayDate;
    private TextView dayTempmin;
    private TextView dayTempmax;
    private TextView dayRaindrops;
    private Context mContext;
    public Dayinfo(ImageView _weatherImg, TextView _dayDate, TextView _dayTempmin, TextView _dayTempmax, TextView _dayRaindrops, Context context){
        weatherImg = _weatherImg;
        dayDate = _dayDate;
        dayTempmin = _dayTempmin;
        dayTempmax = _dayTempmax;
        dayRaindrops = _dayRaindrops;
        mContext = context;
    }
    public void setWeatherImg(int img){
        Glide.with(mContext).load(img).into(weatherImg);
    }
    public void setDayDate(String date){
        dayDate.setText(date);
    }
    public void setDayTempmin(String tempmin){
        dayTempmin.setText(tempmin+" °C");
    }
    public void setDayTempmax(String tempmax){
        dayTempmax.setText(tempmax+" °C");
    }
    public void setDayRaindrops(String raindrops){
        dayRaindrops.setText(raindrops+" %");
    }
}
