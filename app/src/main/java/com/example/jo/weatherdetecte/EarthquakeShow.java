package com.example.jo.weatherdetecte;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EarthquakeShow extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    private String url = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";
    private List<EarthquakeInfo> EarthquakeList = new ArrayList<>();
    private RecyclerView EarthquakeItem;
    private EarthquakeInfoAdapter EarthquakeinfoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_show);
        SupportMapFragment mapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.earthquake_fragment));
        mapFragment.getMapAsync(this);

        SharedPreferences saveInfo = getSharedPreferences("saveInfo",MODE_PRIVATE);
        String earthquakeString = saveInfo.getString("earthquake","");
        Type type =new TypeToken<ArrayList<EarthquakeInfo>>() { }.getType();
        EarthquakeList = new Gson().fromJson(earthquakeString,type);

        EarthquakeItem = (RecyclerView)findViewById(R.id.earthquake_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        EarthquakeItem.setLayoutManager(layoutManager);
        EarthquakeinfoAdapter = new EarthquakeInfoAdapter(this,EarthquakeList);
        EarthquakeItem.setAdapter(EarthquakeinfoAdapter);
        EarthquakeItem.addItemDecoration(new RecyclerviewItemDivider());

    }

    public void locationChange(EarthquakeInfo EI){
        double lat = EI.getLat();
        double lng = EI.getLng();
        LatLng location = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(location).title(EI.getCountry()).snippet(EI.getPlace()+"\n"+EI.getTime())).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {       //mMap是在這裡初始化的 所以再onCreate做任何mMap的動作都會失敗
        mMap = googleMap;
        EarthquakeInfo EI = EarthquakeList.get(0);
        LatLng location = new LatLng(EI.getLat(),EI.getLng());
        mMap.addMarker(new MarkerOptions().position(location).title(EI.getCountry()).snippet(EI.getPlace()+"\n"+EI.getTime())).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
