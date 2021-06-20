package com.example.weatherdetecte.logic.network

import android.provider.Contacts.SettingsColumns.KEY
import com.example.weatherdetecte.WeatherApplication
import com.example.weatherdetecte.logic.AppServer
import com.example.weatherdetecte.logic.model.WeatherCollection
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerCreator {
    lateinit var weather: AppServer

    init {
        var retrofit = Retrofit.Builder()
                .baseUrl(WeatherApplication.TOKEN)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        weather = retrofit.create(AppServer::class.java)

    }


}