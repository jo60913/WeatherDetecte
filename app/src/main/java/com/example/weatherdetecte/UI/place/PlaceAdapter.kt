package com.example.weatherdetecte.UI.place

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherdetecte.R
import com.example.weatherdetecte.UI.PlaceViewModel
import com.example.weatherdetecte.WeatherActivity
import com.example.weatherdetecte.logic.model.Location
import com.example.weatherdetecte.logic.model.WeatherCollection
import java.text.SimpleDateFormat
import java.util.*

class PlaceAdapter(private val list: List<Location>) :
        RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    private var weatherList: List<Location>? = list

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placetemperature: TextView = view.findViewById(R.id.placetemperature)
        val weatherIcon: ImageView = view.findViewById(R.id.weather_icon)
        val item:ConstraintLayout = view.findViewById(R.id.itemLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.item.setOnClickListener {
            val position = holder.adapterPosition
            val place = weatherList?.get(position)
            val intent = Intent(parent.context,WeatherActivity::class.java)
            intent.apply {
                putExtra("PlaceObj",place)
            }
            parent.context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = weatherList?.get(position)
        holder.placeName.text = place?.locationName
        val minTempture = place!!.weatherElement[2].time[0].parameter.parameterName
        val maxnTempture = place!!.weatherElement[1].time[0].parameter.parameterName
        holder.placetemperature.text = "$minTempture ~ $maxnTempture °C"
        val weatherparam = place.weatherElement[0].time[0].parameter.parameterValue
        holder.weatherIcon.setImageResource(SetImage.setImageIcon(weatherparam))

    }

    override fun getItemCount(): Int = weatherList!!.size

    fun setList(list:List<Location>){
        weatherList = list
    }
}

class SetImage(){
    companion object{
        fun setImageIcon(param:String) :Int{
            var returnvalue = 1
            when(param){
                "1" -> returnvalue = R.drawable.ic__1
                "2" -> returnvalue = R.drawable.ic__2
                "3" -> returnvalue = R.drawable.ic__3
                "4" -> returnvalue = R.drawable.ic__4
                "5" -> returnvalue = R.drawable.ic__5
                "6" -> returnvalue = R.drawable.ic__6
                "7" -> returnvalue = R.drawable.ic__7
                "8" -> returnvalue = R.drawable.ic__8
                "9" -> returnvalue = R.drawable.ic__9
                "10" -> returnvalue = R.drawable.ic__10
                "11" -> returnvalue = R.drawable.ic__11
                "12" -> returnvalue = R.drawable.ic__12
                "13" -> returnvalue = R.drawable.ic__13
                "14" -> returnvalue = R.drawable.ic__14
                "15" -> returnvalue = R.drawable.ic__15
                "16" -> returnvalue = R.drawable.ic__16
                "17" -> returnvalue = R.drawable.ic__17
                "18" -> returnvalue = R.drawable.ic__18
                "19" -> returnvalue = R.drawable.ic__19
                "20" -> returnvalue = R.drawable.ic__20
                "21" -> returnvalue = R.drawable.ic__21
                "22" -> returnvalue = R.drawable.ic__22
                "23" -> returnvalue = R.drawable.ic__23
                "24" -> returnvalue = R.drawable.ic__24
                "25" -> returnvalue = R.drawable.ic__25
                "26" -> returnvalue = R.drawable.ic_26
                "27" -> returnvalue = R.drawable.ic__27
                "28" -> returnvalue = R.drawable.ic__28
                "29" -> returnvalue = R.drawable.ic__29
                "30" -> returnvalue = R.drawable.ic__30
                "31" -> returnvalue = R.drawable.ic__31
                "32" -> returnvalue = R.drawable.ic__32
                "33" -> returnvalue = R.drawable.ic__33
                "34" -> returnvalue = R.drawable.ic__34
                "35" -> returnvalue = R.drawable.ic__35
                "36" -> returnvalue = R.drawable.ic__36
                "37" -> returnvalue = R.drawable.ic__37
                "38" -> returnvalue = R.drawable.ic__38
                "39" -> returnvalue = R.drawable.ic__39
                "41" -> returnvalue = R.drawable.ic__41
                "42" -> returnvalue = R.drawable.ic__42
            }
            return returnvalue
        }
    }
}