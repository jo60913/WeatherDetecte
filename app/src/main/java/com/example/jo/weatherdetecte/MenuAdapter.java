package com.example.jo.weatherdetecte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

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
        return null;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {

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
