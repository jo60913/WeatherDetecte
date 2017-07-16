package com.example.jo.weatherdetecte;

import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONObject;

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

        new catchEarthquakeJson(this).execute();
        EarthquakeItem = (RecyclerView)findViewById(R.id.earthquake_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        EarthquakeItem.setLayoutManager(layoutManager);
        Thread T = new Thread(new Runnable() {
            @Override
            public void run() {
                SupportMapFragment mapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment));
                mapFragment.getMapAsync(EarthquakeShow.this);
            }
        });
        T.start();

    }

    public void locationChange(EarthquakeInfo EI){
        double lat = EI.getLat();
        double lng = EI.getLng();
        LatLng location = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(location).title(EI.getCountry()).snippet(EI.getPlace()+"\n"+EI.getTime())).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private class catchEarthquakeJson extends AsyncTask<Void,Void,String> {
        private Context mContext;
        public catchEarthquakeJson(Context context) {
            super();
            mContext = context;
        }
        @Override
        protected String doInBackground(Void... params) {
            String responseData = null;
            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                responseData = response.body().string();
            }catch(Exception e){
                e.printStackTrace();
            }
            return responseData;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jsonObject = null;
            try{
                jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("features");
                int earthquakeCount = jsonArray.length();
                Log.d("earthquake","size of features : " + String.valueOf(earthquakeCount));
                Log.d("earthquake","json : "+jsonArray.getJSONObject(0));
                for(int i = 0;i<earthquakeCount;i++){
                    String mag = jsonArray.getJSONObject(i).getJSONObject("properties").getString("mag");
                    String place = jsonArray.getJSONObject(i).getJSONObject("properties").getString("place");
                    long time = jsonArray.getJSONObject(i).getJSONObject("properties").getLong("time");
                    double lng = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getDouble(0);
                    double lat = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getDouble(1);
                    double deep = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getDouble(2);
                    EarthquakeInfo EI = new EarthquakeInfo(mag,place,time,lng,lat,deep);
                    EarthquakeList.add(EI);
                    Log.d("earthquake","EI : "+EarthquakeList.get(i).getMag() +" , "+EarthquakeList.get(i).getPlace()+" , "+EarthquakeList.get(i).getTime()+" , "+EarthquakeList.get(i).getLng()+" , "+EarthquakeList.get(i).getLat()+" , "+EarthquakeList.get(i).getDeep()+" ,"+EarthquakeList.get(i).getCountry());
                }
                EarthquakeinfoAdapter = new EarthquakeInfoAdapter(mContext,EarthquakeList);
                EarthquakeItem.setAdapter(EarthquakeinfoAdapter);
                EarthquakeInfo EI = EarthquakeList.get(0);
                double lat = EI.getLat();
                double lng = EI.getLng();
                LatLng location = new LatLng(lat,lng);
                mMap.addMarker(new MarkerOptions().position(location).title(EI.getCountry()).snippet(EI.getPlace()+"\n"+EI.getTime())).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
