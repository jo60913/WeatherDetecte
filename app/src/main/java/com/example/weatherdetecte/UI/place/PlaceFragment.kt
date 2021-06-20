package com.example.weatherdetecte.UI.place

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherdetecte.R
import com.example.weatherdetecte.UI.PlaceViewModel
import com.example.weatherdetecte.WeatherApplication
import com.example.weatherdetecte.databinding.ActivityMainBinding
import com.example.weatherdetecte.databinding.FragmentPlaceBinding
import com.example.weatherdetecte.logic.model.Location
import com.example.weatherdetecte.logic.network.Repository
import okhttp3.Response
import java.lang.Exception
import kotlin.system.exitProcess

class PlaceFragment : Fragment() {
    private lateinit var viewModel: PlaceViewModel

    lateinit var Bind: FragmentPlaceBinding
    private var adapter: PlaceAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Bind = FragmentPlaceBinding.inflate(inflater, container, false)
        val view = Bind.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val connManager = activity?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetwork
        if(networkInfo == null ){
            AlertDialog.Builder(activity!!)
                    .setTitle("沒有網路")
                    .setMessage("請開啟網路")
                    .setPositiveButton("重試"){_,_->
                        init()
                    }.setNegativeButton("退出"){_,_->
                        activity?.finish()
                        exitProcess(0)
                    }.setCancelable(false).show()
        }else{
            init()
        }
    }



    fun init(){
        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        Bind.bgImageView.visibility = View.GONE
        Bind.recyclerview.visibility = View.VISIBLE
        Bind.progressBar.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(activity)
        Bind.recyclerview.layoutManager = layoutManager
        //確認是否取得資料
        viewModel.weatherCollection.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter = PlaceAdapter(viewModel.weatherCollection.value!!.cwbopendata.dataset.location)
                Bind.recyclerview.adapter = adapter
                Bind.progressBar.visibility = View.GONE
            }
        })
        //輸入改變的時候
        Bind.searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if(content.isNotEmpty()){
                viewModel.searchPlace(content)
            }else{
                Bind.bgImageView.visibility = View.GONE
                Bind.recyclerview.visibility = View.VISIBLE
                adapter!!.setList(viewModel.weatherCollection?.value!!.cwbopendata.dataset.location)
                adapter!!.notifyDataSetChanged()
            }
        }
        //如果輸入後的結果
        viewModel.searchPlaceWeather.observe(viewLifecycleOwner,{
            if(viewModel.searchPlaceWeather.value!!.size > 0){
                adapter!!.setList(viewModel.searchPlaceWeather.value!!)
                adapter!!.notifyDataSetChanged()
            } else{
                Bind.bgImageView.visibility = View.VISIBLE
                Bind.recyclerview.visibility = View.GONE
            }
        })
    }
}

