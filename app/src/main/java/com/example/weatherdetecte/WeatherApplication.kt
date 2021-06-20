package com.example.weatherdetecte

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WeatherApplication : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        const val TOKEN = "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}