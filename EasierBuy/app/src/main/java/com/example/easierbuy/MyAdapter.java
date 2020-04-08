package com.example.easierbuy;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;


public class MyAdapter extends BaseAdapter {

    private LinkedList<Block> mData;
    private Context mContext;
    //private int mLayoutRes;


    public MyAdapter(LinkedList<Block> data,Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.favourite_list_item,parent,false);
            holder = new ViewHolder();
            holder.uTextView = (TextView)convertView.findViewById(R.id.fitemurl);
            holder.nTextView = (TextView)convertView.findViewById(R.id.ftext_spname);
            holder.pTextView = (TextView)convertView.findViewById(R.id.ftext_spprice);
            holder.bTextView = (TextView)convertView.findViewById(R.id.ftext_spbrand);
            holder.sTextView = (TextView)convertView.findViewById(R.id.ftext_spsite);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.uTextView.setText(mData.get(position).getburl());
        holder.nTextView.setText(mData.get(position).getbname());
        holder.pTextView.setText(mData.get(position).getbprice());
        holder.bTextView.setText(mData.get(position).getbbrand());
        holder.sTextView.setText(mData.get(position).getbsite());

        return convertView;


    }
    static class ViewHolder{
        TextView uTextView;
        TextView nTextView;
        TextView pTextView;
        TextView bTextView;
        TextView sTextView;
    }




}


