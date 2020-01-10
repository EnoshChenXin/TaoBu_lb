package com.lianbei.taobu.taobu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.views.CustomRoundAngleImageView;
import com.lianbei.taobu.views.PriceView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    List <TopGoodsBean> list;
    public GridViewAdapter(Context context, List<TopGoodsBean> _list) {
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
        convertView = layoutInflater.inflate( R.layout.item_gridview, null);
        TextView goods_name = (TextView) convertView.findViewById(R.id.goods_name);
        CustomRoundAngleImageView goods_thumbnail = (CustomRoundAngleImageView) convertView.findViewById(R.id.goods_thumbnail);
        PriceView nowprice = (PriceView) convertView.findViewById(R.id.nowprice);
       // nowprice.setText ( "￥0.00" );
        PriceView oldprice = (PriceView) convertView.findViewById(R.id.oldprice);
       // oldprice.setText ( "￥2.30" );
        GlideUtils.getInstance ().load (context,  list.get(position).getGoods_thumbnail_url (),goods_thumbnail);
        goods_name.setText(list.get(position).getGoods_name ());
        return convertView;
    }
}
