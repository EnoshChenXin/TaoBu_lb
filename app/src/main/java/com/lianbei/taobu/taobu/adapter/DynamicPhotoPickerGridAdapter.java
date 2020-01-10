package com.lianbei.taobu.taobu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.utils.DisplayUtil;
import com.lianbei.taobu.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class DynamicPhotoPickerGridAdapter extends BaseAdapter {

    private ArrayList <String> listUrls;
    private LayoutInflater inflater;
    Context mContext;


    public DynamicPhotoPickerGridAdapter(ArrayList <String> listUrls, Context activity) {
        this.mContext =activity;
        this.listUrls = listUrls;
        if(listUrls.size() == 10){
            listUrls.remove(listUrls.size()-1);
        }
        inflater = LayoutInflater.from( mContext);
    }
    @Override
    public int getCount() {
        return listUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate( R.layout.dynamic_ppg_item_image, parent,false);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            ViewGroup.LayoutParams params = holder.image.getLayoutParams();
            int widthPixels =  DisplayUtil.getScreenWidth(mContext);
            if(listUrls.size () ==1) {
                params.width =(int)Math.round(widthPixels/2.34);
                params.height=params.width;
               // holder.image.setScaleType ( ImageView.ScaleType.FIT_CENTER );
            }else if(listUrls.size () ==2 || listUrls.size () ==4){
                params.width =(int)Math.round(widthPixels/2.34);
                params.height=params.width;
            }else if(listUrls.size () ==3 || listUrls.size () > 4){
                params.width =(int)Math.round(widthPixels/3.57);
                params.height= params.width;

            }
            holder.image.setLayoutParams(params);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final String path=listUrls.get(position);
        if (path.equals("000000")){
            holder.image.setImageResource(R.mipmap.add_picture);
        }else {
            GlideUtils.getInstance ().load (mContext,path,holder.image);
        }
        return convertView;
    }
    class ViewHolder {
        ImageView image;
    }
}
