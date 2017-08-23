package com.example.jo.weatherdetecte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class LoadActivity extends AppCompatActivity {
    private TextView LoadingText;
    private String earthquakeUrl = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";
    private String weatherUrl = "https://free-api.heweather.com/v5/weather?city=";
    private String weatherKeyUrl = "&key=38c459be3d9144878545bc41807f1e85";
    String[] cityurl = new String[]{"Yilan","xinbei","taibeixian",
            "taoyuan","xinzhu","miaoli","taizhong",
            "nantou","zhanghua","yunlin",
            "jiayi","tainan","gaoxiong",
            "pingdong","taidong","hualian","jinmen"};
    private ArrayList<EarthquakeInfo> EarthquakeList = new ArrayList<>();
    private ArrayList<WeatherInfo> weatherAList = new ArrayList<>();
    private boolean isEnd = false; //判斷資料是否跑完
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Log.d("loading","載入Activity");
        LoadingText = (TextView)findViewById(R.id.Load_loadingInfo);
        new DownloadAsync().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    class DownloadAsync extends AsyncTask<Void,String,String[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoadingText.setText("準備讀取資料");
        }

        @Override
        protected String[] doInBackground(Void... params) {
            String[] Jsons = new String[18];
            try{
                publishProgress("開始下載資料");
                OkHttpClient earthquakeClient = new OkHttpClient();
                Request earthquakeRequest = new Request.Builder().url(earthquakeUrl).build();
                Response earthquakeResponse = earthquakeClient.newCall(earthquakeRequest).execute();
                int code = earthquakeResponse.code();     //code = 200 表示正常
                if(code == 200) {
                    Jsons[0] = earthquakeResponse.body().string();
                    Log.d("earthquake","Json[0] : "+Jsons[0]);
                    publishProgress("地震資料下載完成，天氣資料開始下載");
                    for(int i = 0;i<cityurl.length;i++) {
                        String weburl = weatherUrl+cityurl[i]+weatherKeyUrl;
                        OkHttpClient weatherClient = new OkHttpClient();
                        Request weatherRequest = new Request.Builder().url(weburl).build();
                        Response weatherResponse = weatherClient.newCall(weatherRequest).execute();
                        if(weatherResponse.code() != 200){
                            publishProgress("連線問題請檢查連線狀態");
                            break;
                        }
                        Jsons[1+i] = weatherResponse.body().string();
                    }
                    publishProgress("天氣資料下載完成");
                }else{
                    publishProgress("連線問題請重複檢查連線狀態");
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            return Jsons;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            LoadingText.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            JSONObject jsonObject = null;
            LoadingText.setText("開始解析地震及天氣資料");
            try{
                for(int k = 0 ;k<=cityurl.length;k++) {
                    if (k == 0) {
                        jsonObject = new JSONObject(result[0]);
                        JSONArray jsonArray = jsonObject.getJSONArray("features");
                        int earthquakeCount = jsonArray.length();
                        Log.d("Debug", "size of features : " + String.valueOf(earthquakeCount));
                        Log.d("Debug", "json : " + jsonArray.getJSONObject(0));
                        for (int i = 0; i < earthquakeCount; i++) {
                            String mag = jsonArray.getJSONObject(i).getJSONObject("properties").getString("mag");
                            String place = jsonArray.getJSONObject(i).getJSONObject("properties").getString("place");
                            long time = jsonArray.getJSONObject(i).getJSONObject("properties").getLong("time");
                            double lng = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getDouble(0);
                            double lat = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getDouble(1);
                            double deep = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getDouble(2);
                            EarthquakeInfo EI = new EarthquakeInfo(mag, place, time, lng, lat, deep);
                            EarthquakeList.add(EI);
                            Log.d("Debug", "EI : " + EarthquakeList.get(i).getMag() + " , " + EarthquakeList.get(i).getPlace() + " , " + EarthquakeList.get(i).getTime() + " , " + EarthquakeList.get(i).getLng() + " , " + EarthquakeList.get(i).getLat() + " , " + EarthquakeList.get(i).getDeep() + " ," + EarthquakeList.get(i).getCountry());
                        }
                        getIntent().putExtra("EarthquakeList",EarthquakeList);
                    } else {
                        JSONObject weatherJsonObject = null;
                        int j = 0;      //每個縣市的Json結構不一樣 有的有JsonObject1有的沒有
                        jsonObject = new JSONObject(result[k]);
                        JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
                        String jContent = jsonArray.getJSONObject(j).getString("status");
                        if (jContent.equals("permission denied")) {
                            j = 1;
                        }
                        String nowTemp = jsonArray.getJSONObject(j).getJSONArray("hourly_forecast").getJSONObject(0).getString("tmp");
                        String nowHum = jsonArray.getJSONObject(j).getJSONArray("hourly_forecast").getJSONObject(0).getString("hum");
                        String nowRaindrop = jsonArray.getJSONObject(j).getJSONArray("hourly_forecast").getJSONObject(0).getString("pop");
                        String nowWind = jsonArray.getJSONObject(j).getJSONArray("hourly_forecast").getJSONObject(0).getJSONObject("wind").getString("sc");
                        String comfortableContent = jsonArray.getJSONObject(j).getJSONObject("suggestion").getJSONObject("comf").getString("txt");
                        String UVContent = jsonArray.getJSONObject(j).getJSONObject("suggestion").getJSONObject("uv").getString("txt");
                        String sportContent = jsonArray.getJSONObject(j).getJSONObject("suggestion").getJSONObject("sport").getString("txt");
                        String suitContent = jsonArray.getJSONObject(j).getJSONObject("suggestion").getJSONObject("drsg").getString("txt");
                        WeatherInfo WI = new WeatherInfo(nowTemp,nowHum,nowRaindrop,nowWind,comfortableContent,UVContent,sportContent,suitContent);
                        Log.d("Debug","nowTemp : "+nowTemp+" nowHum : "+nowHum+" nowRaindrop : "+nowRaindrop+" nowWind : "+nowWind+" comfortableContent : "+comfortableContent+" UVContent : "+UVContent+" sportContent : "+sportContent+" suitContent : "+suitContent);
                        for (int i = 0; i < 3; i++) {
                            String date = jsonArray.getJSONObject(j).getJSONArray("daily_forecast").getJSONObject(i).getString("date");
                            String weather = jsonArray.getJSONObject(j).getJSONArray("daily_forecast").getJSONObject(i).getJSONObject("cond").getString("code_d");
                            String tempMax = jsonArray.getJSONObject(j).getJSONArray("daily_forecast").getJSONObject(i).getJSONObject("tmp").getString("max");
                            String tempMin = jsonArray.getJSONObject(j).getJSONArray("daily_forecast").getJSONObject(i).getJSONObject("tmp").getString("min");
                            String raindrop = jsonArray.getJSONObject(j).getJSONArray("daily_forecast").getJSONObject(i).getString("pop");
                            WI.addDayDetail(date,weather,tempMax,tempMin,raindrop);
                            Log.d("Debug","第"+i+"天date : "+date+" weather"+weather+" tempMax : "+tempMax+" tempMin : "+tempMin+" raindrop : "+raindrop);
                        }
                        weatherAList.add(WI);
                    }
                }
                Gson earthquakeGaon = new Gson();
                String earthquakeString = earthquakeGaon.toJson(EarthquakeList);
                Gson weatherGson = new Gson();
                String weatherString = weatherGson.toJson(weatherAList);
                SharedPreferences saveInfo = getSharedPreferences("saveInfo",MODE_PRIVATE);
                saveInfo.edit().putString("weatherAList",weatherString)
                        .putString("earthquake",earthquakeString).commit();
                LoadingText.setText("解析完成即將進入頁面");
                setResult(RESULT_OK,getIntent());
                finish();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
