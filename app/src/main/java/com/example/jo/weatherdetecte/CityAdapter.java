package com.example.jo.weatherdetecte;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jo on 2017/5/29.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private ArrayList<CityShow_menu> CityList = new ArrayList<>();
    private Context mContext;

    public void addCity(String cityname,int cityimg,String cityurl){
        CityList.add(new CityShow_menu(cityname,cityimg,cityurl));
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_cardview,parent,false);
        final ViewHolder VH = new ViewHolder(view);
        mContext = parent.getContext();
        VH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext,WeatherShow.class);
                int position = VH.getAdapterPosition();
                it.putExtra("CityUrl",CityList.get(position).getCityUrl());
                it.putExtra("CityImg",CityList.get(position).getCityImg());
                mContext.startActivity(it);
            }
        });
        return VH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(CityList.get(position).getCityImg()).into(holder.cityimg);
        holder.cityname.setText(CityList.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        return CityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView cityimg;
        private TextView cityname;
        public ViewHolder(View itemView) {
            super(itemView);
            cityimg = (ImageView)itemView.findViewById(R.id.cardview_Image);
            cityname = (TextView)itemView.findViewById(R.id.cardview_Textview);
        }
    }
}
