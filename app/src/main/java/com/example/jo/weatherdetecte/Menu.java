package com.example.jo.weatherdetecte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class Menu extends AppCompatActivity {
    private boolean isLoad = false;
    private final int LOAD_ACTIVITY = 100;
    private ArrayList<EarthquakeInfo> EarthquakeAList = new ArrayList<>();
    private ArrayList<WeatherInfo> WeatherAList = new ArrayList<>();
    private RecyclerView MenuRecyclerView;
    private String[] menuItemName = new String[]{"天氣預報","地震報告"};
    private int[] menuItemIcon = new int[]{R.drawable.weather_icon,R.drawable.news_icon};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle("選單");
        MenuRecyclerView = (RecyclerView)findViewById(R.id.menu_Recyclerview);
        if(isLoad == false){
            Intent It = new Intent(this,LoadActivity.class);
            startActivityForResult(It,LOAD_ACTIVITY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOAD_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                EarthquakeAList = (ArrayList<EarthquakeInfo>)data.getSerializableExtra("EarthquakeList");
                WeatherAList = (ArrayList<WeatherInfo>)data.getSerializableExtra("weatherAList");
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }
}
