package com.example.jo.weatherdetecte;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

public class Menu extends AppCompatActivity{
    private boolean isLoad = false;  //進入
    private final int LOAD_ACTIVITY = 100;
    private String[] menuItemName = new String[]{"天氣預報","地震報告"};
    private int[] menuItemIcon = new int[]{R.drawable.weather_icon,R.drawable.news_icon};
    private RecyclerView menuRecyclerview;
    private MenuAdapter menuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle("選單");
        if(isLoad == false){
            Intent It = new Intent(this,LoadActivity.class);
            startActivityForResult(It,LOAD_ACTIVITY);
        }
        menuRecyclerview = (RecyclerView)findViewById(R.id.menu_Recyclerview);
        menuRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        menuAdapter = new MenuAdapter(this,menuItemName,menuItemIcon);
        menuRecyclerview.setAdapter(menuAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOAD_ACTIVITY) {
            if (resultCode == RESULT_OK) {

            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

}
