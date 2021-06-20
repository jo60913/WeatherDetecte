package com.example.weatherdetecte.logic

import com.example.weatherdetecte.BuildConfig
import com.example.weatherdetecte.logic.model.WeatherCollection
import retrofit2.Call
import retrofit2.http.GET

interface AppServer {

    @GET("${BuildConfig.ACCESS_TOKEN}")
    fun getAllPlaceWeather(): Call<WeatherCollection>
}