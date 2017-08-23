package com.example.jo.weatherdetecte;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by jo on 2017/7/23.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private String[] menuItemName;
    private int[] menuItemIcon;
    private Context mContext;
    public MenuAdapter(Context _context,String[] _menuItemName,int[] _menuItemIcon) {
        super();
        mContext = _context;
        menuItemIcon = _menuItemIcon;
        menuItemName = _menuItemName;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.menuItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent It = new Intent();
                switch (position){
                    case 0:
                        It.setClass(mContext,MainActivity.class);
                        break;
                    case 1:
                        It.setClass(mContext,EarthquakeShow.class);
                        break;
                    default:
                        break;
                }
                mContext.startActivity(It);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        holder.menuTex.setText(menuItemName[position]);
        Glide.with(mContext).load(menuItemIcon[position]).into(holder.menuItemImg);
    }

    @Override
    public int getItemCount() {
        return menuItemIcon.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView menuItemImg;
        public TextView menuTex;
        public ViewHolder(View itemView) {
            super(itemView);
            menuItemImg = (ImageView)itemView.findViewById(R.id.menu_icon);
            menuTex = (TextView)itemView.findViewById(R.id.menu_text);
        }
    }
}
