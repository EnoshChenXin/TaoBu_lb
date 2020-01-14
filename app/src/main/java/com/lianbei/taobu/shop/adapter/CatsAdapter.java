package com.lianbei.taobu.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.circle.model.InformationBean;
import com.lianbei.taobu.shop.model.CatsBean;
import com.lianbei.taobu.shop.model.GoodsOptBean;

import java.util.List;

public class CatsAdapter extends BaseAdapter {
    Context context;
    List <GoodsOptBean> list;
    public CatsAdapter(Context context, List<GoodsOptBean> _list) {
        this.list = _list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size ();
    }

    @Override
    public Object getItem(int position) {
        return list.get ( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate( R.layout.item_cats, null);
        TextView cat_name = (TextView) convertView.findViewById(R.id.cat_name);
        RelativeLayout rel_bg =(RelativeLayout)convertView.findViewById(R.id.rel_bg) ;
        LinearLayout lin_bottom = (LinearLayout)convertView.findViewById(R.id.lin_bottom);
        GoodsOptBean goodsOptBean = list.get(position);
        cat_name.setText(goodsOptBean.getOpt_name());
        if(goodsOptBean.isSelect()){
            rel_bg.setBackgroundResource(R.drawable.bg_gridview5);
            lin_bottom.setBackgroundResource(R.drawable.bg_carts_bottom_select);
        }else{
            rel_bg.setBackgroundResource(R.drawable.bg_gridview0);
            lin_bottom.setBackgroundResource(R.drawable.bg_carts_bottom);
        }
        return convertView;
    }
}
