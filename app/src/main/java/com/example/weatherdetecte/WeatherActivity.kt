package com.example.weatherdetecte

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.weatherdetecte.UI.place.SetImage
import com.example.weatherdetecte.databinding.ActivityWeatherBinding
import com.example.weatherdetecte.databinding.NowBinding
import com.example.weatherdetecte.logic.AppServer
import com.example.weatherdetecte.logic.model.Location
import com.example.weatherdetecte.logic.model.WeatherCollection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    lateinit var Bind:ActivityWeatherBinding
    lateinit var PlaceInfo:Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Bind = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(Bind.root)
        PlaceInfo = intent.getSerializableExtra("PlaceObj") as Location
        setImage()
    }

    fun setImage(){
        Log.d("weatherActivity",PlaceInfo.locationName)
        Bind.weatherLayout.setBackgroundResource(setBackGround(PlaceInfo.weatherElement[0].time[0].parameter.parameterValue))
        Bind.nowLayout.placeName.text = PlaceInfo.locationName  //地名
        Bind.nowLayout.currentTemp.text = "${PlaceInfo.weatherElement[2].time[0].parameter.parameterName} - ${PlaceInfo.weatherElement[1].time[0].parameter.parameterName} ℃"   //溫度

        Bind.liftIndexLayout.weatherSituationText.text = PlaceInfo.weatherElement[4].time[0].parameter.parameterName    //天氣現況
        Bind.liftIndexLayout.posbilityRainImageText.text = PlaceInfo.weatherElement[0].time[0].parameter.parameterName    //降雨率
        Bind.liftIndexLayout.confortableImageText.text = PlaceInfo.weatherElement[3].time[0].parameter.parameterName    //舒適度
        //第一行資料
        Bind.forecastLayout.dateColumn1Text.text = "${dateToDDHHMM(PlaceInfo.weatherElement[0].time[1].startTime)} - ${dateToDDHHMM(PlaceInfo.weatherElement[0].time[1].endTime)}"    //時間
        Bind.forecastLayout.dateColumn1Image.setImageResource(SetImage.setImageIcon(PlaceInfo.weatherElement[0].time[1].parameter.parameterValue)) //天氣icon
        Bind.forecastLayout.forecastColumn1ProbilityRainText.text = "${PlaceInfo.weatherElement[4].time[1].parameter.parameterName} %" //降雨率
        Bind.forecastLayout.forcastColumn1WeatherTempText.text = "${PlaceInfo.weatherElement[2].time[1].parameter.parameterName} - ${PlaceInfo.weatherElement[1].time[1].parameter.parameterName} ℃"
        //第二行資料
        Bind.forecastLayout.dateColumn2Text.text = "${dateToDDHHMM(PlaceInfo.weatherElement[0].time[2].startTime)} - ${dateToDDHHMM(PlaceInfo.weatherElement[0].time[2].endTime)}"    //時間
        Bind.forecastLayout.dateColumn2Image.setImageResource(SetImage.setImageIcon(PlaceInfo.weatherElement[0].time[2].parameter.parameterValue)) //天氣icon
        Bind.forecastLayout.forecastColumn2ProbilityRainText.text = "${PlaceInfo.weatherElement[4].time[2].parameter.parameterName} %" //降雨率
        Bind.forecastLayout.forcastColumn2WeatherTempText.text = "${PlaceInfo.weatherElement[2].time[2].parameter.parameterName} - ${PlaceInfo.weatherElement[1].time[1].parameter.parameterName} ℃"
    }

    fun setBackGround(param:String) : Int{
        var returnvalue = 0
        when(param){
            "1" ->returnvalue = R.drawable.bg_clear_day
            "3","6","7","19" ->returnvalue = R.drawable.bg_cloudy
            "24" ->returnvalue = R.drawable.bg_fog
            "2","25","26","27","28" ->returnvalue = R.drawable.bg_partly_cloudy_day
            "4","5" ->returnvalue = R.drawable.bg_partly_cloudy_night
            "8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","29","30","31","32","33","34","35","36","37","38","39","41" -> returnvalue = R.drawable.bg_rain
            "42" -> returnvalue = R.drawable.bg_snow
        }
        return returnvalue
    }

    fun dateToDDHHMM(orginaldate:String):String{
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSZ")
        val date = format.parse(orginaldate)
        val afterformate = SimpleDateFormat("MM/dd HH:mm")
        val dateString = afterformate.format(date)
        return dateString
    }
}