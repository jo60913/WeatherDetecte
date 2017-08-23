package com.example.jo.weatherdetecte;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jo on 2017/7/10.
 */

public class EarthquakeInfoAdapter extends RecyclerView.Adapter<EarthquakeInfoAdapter.ViewHolder> {
    private List<EarthquakeInfo> EarthquakeInfoList;
    private Context mContext;
    private int clickItemCount;
    public EarthquakeInfoAdapter(Context _mContext, List<EarthquakeInfo> _EarthquakeInfoList){
        mContext = _mContext;
        EarthquakeInfoList = _EarthquakeInfoList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.earthquack_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof EarthquakeShow) {
                    int position = holder.getAdapterPosition();
                    clickItemCount = position;
                    EarthquakeInfo EI = EarthquakeInfoList.get(position);
                    ((EarthquakeShow)mContext).locationChange(EI);
                    notifyDataSetChanged();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EarthquakeInfo EI = EarthquakeInfoList.get(position);
        int CountryIcon = mContext.getResources().getIdentifier(EI.getCountry(),"drawable",mContext.getPackageName());
        if(CountryIcon == 0){
            CountryIcon = mContext.getResources().getIdentifier("high_seas","drawable",mContext.getPackageName());
        }
        holder.CountryImg.setImageResource(CountryIcon);
        holder.EpicenterText.setText("規模 : "+EI.getMag() + " 深度 : "+EI.getDeep()+" 公里");
        holder.LocationText.setText(EI.getTime());
        if(clickItemCount == position)
            holder.view.setBackgroundColor(Color.parseColor("#FF8000"));
         else
            holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.background_material_dark));
    }

    @Override
    public int getItemCount() {
        return EarthquakeInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView CountryImg;
        private TextView EpicenterText;
        private TextView LocationText;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            CountryImg = (ImageView)itemView.findViewById(R.id.earthquack_countryFlag);
            EpicenterText = (TextView)itemView.findViewById(R.id.earthquack_epicenterText);
            LocationText = (TextView)itemView.findViewById(R.id.earthquack_location);
        }
    }
}
