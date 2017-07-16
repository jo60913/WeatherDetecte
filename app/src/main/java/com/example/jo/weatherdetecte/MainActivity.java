package com.example.jo.weatherdetecte;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;

    private CityAdapter cityAdapter;
    private final String WEBURL = "https://free-api.heweather.com/v5/weather?city=";
    private final String WEBURLKEY = "&key=38c459be3d9144878545bc41807f1e85";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("天氣預報");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        cityAdapter = new CityAdapter();
        InitCity();
        recyclerView.setAdapter(cityAdapter);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent it = null;
        switch (id){
            case R.id.nav_wather:
                break;
            case R.id.nav_earthquake:
                it = new Intent(this,EarthquakeShow.class);
                startActivity(it);
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void InitCity(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String[] cityname = new String[]{"宜蘭","新北","臺北","桃園","新竹","苗栗","臺中","南投","彰化","雲林","嘉義","臺南","高雄","屏東","臺東","花蓮","金門"};

                    int [] cityimg = new int[]{R.drawable.yilan,R.drawable.newtaipei,R.drawable.taipei,
                            R.drawable.taoyuan,R.drawable.hsinchu,R.drawable.miaoli,
                            R.drawable.taichung,R.drawable.nantou,R.drawable.changhua,
                            R.drawable.yunlin,R.drawable.chiayi,R.drawable.tainan,
                            R.drawable.kaohsiung,R.drawable.pingtung,R.drawable.taitung,R.drawable.hualien,
                            R.drawable.kinmen};
                    String[] cityurl = new String[]{"Yilan","xinbei","taibeixian",
                            "taoyuan","xinzhu","miaoli","taizhong",
                            "nantou","zhanghua","yunlin",
                            "jiayi","tainan","gaoxiong",
                            "pingdong","taidong","hualian","jinmen"};
                    for(int i = 0;i<=cityname.length;i++){
                        cityAdapter.addCity(cityname[i],cityimg[i],WEBURL+cityurl[i]+WEBURLKEY);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
