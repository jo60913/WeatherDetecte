package com.example.jo.weatherdetecte;

import android.app.DownloadManager;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherShow extends AppCompatActivity {
    private String url;
    private ImageView cityPhotoImg;
    private TextView cityTemperatureText;
    private TextView cityHumidityText;
    private TextView cityRaindropsText;
    private TextView cityWindsText;
    private List<Dayinfo> DayinfoList;
    private View day1;
    private View day2;
    private View day3;
    private Dayinfo D1;
    private Dayinfo D2;
    private Dayinfo D3;
    private TextView cityConfortableContent;
    private TextView cityUVContent;
    private TextView citySportContent;
    private TextView citySuitContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_show);
        url = getIntent().getStringExtra("CityUrl");
        Log.d("cityname","url : "+ url);
        int cityphotoimg = getIntent().getIntExtra("CityImg",0);
        cityPhotoImg = (ImageView)findViewById(R.id.CityImg);
        Glide.with(this).load(cityphotoimg).override(240,160).into(cityPhotoImg);
        cityTemperatureText = (TextView)findViewById(R.id.CitytempText);
        cityHumidityText = (TextView)findViewById(R.id.CityhumidityText);
        cityRaindropsText = (TextView)findViewById(R.id.CityRaindropsText);
        cityWindsText = (TextView)findViewById(R.id.CityWindText);

        DayinfoList = new ArrayList<Dayinfo>();
        day1 = (View)findViewById(R.id.day1);
        D1 = new Dayinfo((ImageView)day1.findViewById(R.id.dayweather),(TextView)day1.findViewById(R.id.daydate),(TextView)day1.findViewById(R.id.daytempmin),(TextView)day1.findViewById(R.id.daytempmax),(TextView)day1.findViewById(R.id.dayraindrop),this);
        DayinfoList.add(D1);
        day2 = (View)findViewById(R.id.day2);

        D2 = new Dayinfo((ImageView)day2.findViewById(R.id.dayweather),(TextView)day2.findViewById(R.id.daydate),(TextView)day2.findViewById(R.id.daytempmin),(TextView)day2.findViewById(R.id.daytempmax),(TextView)day2.findViewById(R.id.dayraindrop),this);
        DayinfoList.add(D2);
        day3 = (View)findViewById(R.id.day3);
        D3 = new Dayinfo((ImageView)day3.findViewById(R.id.dayweather),(TextView)day3.findViewById(R.id.daydate),(TextView)day3.findViewById(R.id.daytempmin),(TextView)day3.findViewById(R.id.daytempmax),(TextView)day3.findViewById(R.id.dayraindrop),this);
        DayinfoList.add(D3);

        cityConfortableContent = (TextView) findViewById(R.id.CityConfortableContentText);
        cityUVContent = (TextView)findViewById(R.id.CityUVContentText);
        citySportContent = (TextView)findViewById(R.id.CitySportContentText);
        citySuitContent = (TextView)findViewById(R.id.CitySuitContent);

        new catchJson(this).execute();
    }

    class catchJson extends AsyncTask<Void,Void,String> {
        private Context mContext;
        public catchJson(Context context){
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
            try {
                int k = 0;      //每個縣市的Json結構不一樣 有的有JsonObject1有的沒有
                jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
                String kContent = jsonArray.getJSONObject(k).getString("status");
                if(kContent.equals("permission denied")){
                    k = 1;
                }
                Log.d("cityname","tmp : "+jsonArray.getJSONObject(k).getJSONArray("hourly_forecast").getJSONObject(0).getString("tmp"));
                cityTemperatureText.setText(jsonArray.getJSONObject(k).getJSONArray("hourly_forecast").getJSONObject(0).getString("tmp") + " °C");
                cityHumidityText.setText(jsonArray.getJSONObject(k).getJSONArray("hourly_forecast").getJSONObject(0).getString("hum")+" %");
                cityRaindropsText.setText(jsonArray.getJSONObject(k).getJSONArray("hourly_forecast").getJSONObject(0).getString("pop") + " %");
                cityWindsText.setText(jsonArray.getJSONObject(k).getJSONArray("hourly_forecast").getJSONObject(0).getJSONObject("wind").getString("sc"));
                for(int i = 0;i<DayinfoList.size();i++){
                    String jsonWeatherimg = jsonArray.getJSONObject(k).getJSONArray("daily_forecast").getJSONObject(i).getJSONObject("cond").getString("code_d");
                    int weatherimg = mContext.getResources().getIdentifier("w"+jsonWeatherimg,"drawable",mContext.getPackageName());
                    DayinfoList.get(i).setWeatherImg(weatherimg);
                    DayinfoList.get(i).setDayDate(jsonArray.getJSONObject(k).getJSONArray("daily_forecast").getJSONObject(i).getString("date"));
                    DayinfoList.get(i).setDayTempmin(jsonArray.getJSONObject(k).getJSONArray("daily_forecast").getJSONObject(i).getJSONObject("tmp").getString("min"));
                    DayinfoList.get(i).setDayTempmax(jsonArray.getJSONObject(k).getJSONArray("daily_forecast").getJSONObject(i).getJSONObject("tmp").getString("max"));
                    DayinfoList.get(i).setDayRaindrops(jsonArray.getJSONObject(k).getJSONArray("daily_forecast").getJSONObject(i).getString("pop"));
                }
                cityConfortableContent.setText(jsonArray.getJSONObject(k).getJSONObject("suggestion").getJSONObject("comf").getString("txt"));
                cityUVContent.setText(jsonArray.getJSONObject(k).getJSONObject("suggestion").getJSONObject("uv").getString("txt"));
                citySportContent.setText(jsonArray.getJSONObject(k).getJSONObject("suggestion").getJSONObject("sport").getString("txt"));
                citySuitContent.setText(jsonArray.getJSONObject(k).getJSONObject("suggestion").getJSONObject("drsg").getString("txt"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
