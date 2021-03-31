package com.example.jo.weatherdetecte;

import android.app.Application;
import android.content.Context;

/**
 * Created by jo on 2017/7/3.
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    public static Context getmContext(){
        return mContext;
    }
}
