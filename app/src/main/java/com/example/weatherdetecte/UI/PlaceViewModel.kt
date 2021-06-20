package com.example.weatherdetecte.UI

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.weatherdetecte.WeatherApplication
import com.example.weatherdetecte.logic.model.Cwbopendata
import com.example.weatherdetecte.logic.model.Location
import com.example.weatherdetecte.logic.model.WeatherCollection
import com.example.weatherdetecte.logic.network.JsonCallback
import com.example.weatherdetecte.logic.network.Repository
import com.google.gson.Gson
import okhttp3.Response
import java.lang.Exception
import java.util.*

class PlaceViewModel : ViewModel() {
    var weatherCollection: MutableLiveData<WeatherCollection> = MutableLiveData()
    var searchPlaceWeather = MutableLiveData<List<Location>>()

    init {
        getAllWeather()
    }

    fun getAllWeather() {
        Repository.getWeather(object : JsonCallback {
            override fun response(weatherCollection: WeatherCollection) {
                this@PlaceViewModel.weatherCollection.value = weatherCollection
            }
        })
    }

    fun searchPlace(placename: String) {
        searchPlaceWeather.value = weatherCollection.value!!.cwbopendata.dataset.location.filter {
            it.locationName.substring(0, placename.length) == placename
        }
    }
}