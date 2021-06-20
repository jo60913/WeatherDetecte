package com.example.weatherdetecte.logic.network

import android.provider.Contacts.SettingsColumns.KEY
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.liveData
import com.example.weatherdetecte.BuildConfig
import com.example.weatherdetecte.WeatherApplication
import com.example.weatherdetecte.logic.AppServer
import com.example.weatherdetecte.logic.model.Cwbopendata
import com.example.weatherdetecte.logic.model.Location
import com.example.weatherdetecte.logic.model.WeatherCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException
import java.net.URL
import java.util.*

object Repository {


    fun getWeather(jsonCallback: JsonCallback){

        ServerCreator.weather.getAllPlaceWeather().enqueue(object : Callback<WeatherCollection>{
            override fun onResponse(call: Call<WeatherCollection>, response: Response<WeatherCollection>) {
                if(response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        jsonCallback.response(body)
                    }else{
                        throw RuntimeException("沒成功取得jsonCallBack 請回到WeatherNetwork做檢查")
                    }
                }

            }

            override fun onFailure(call: Call<WeatherCollection>, t: Throwable) {
                print("getWeather失敗 $t")
            }
        })
    }
}


interface JsonCallback {
    fun response(weatherCollection: WeatherCollection)
}
