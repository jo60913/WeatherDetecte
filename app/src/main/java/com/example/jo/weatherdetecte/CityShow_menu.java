package com.example.jo.weatherdetecte;

import android.widget.ImageView;

/**
 * Created by jo on 2017/5/29.
 */

public class CityShow_menu {
    private String CityName;
    private int CityImg;
    private String CityUrl;
    public CityShow_menu(String _CityName,int _CityImg,String _CityUrl){
        CityName = _CityName;
        CityImg = _CityImg;
        CityUrl = _CityUrl;
    }

    public String getCityName(){
        return CityName;
    }

    public int getCityImg(){
        return CityImg;
    }
    public String getCityUrl(){
        return CityUrl;
    }
}
