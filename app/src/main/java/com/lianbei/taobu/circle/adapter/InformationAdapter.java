package com.lianbei.taobu.circle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.circle.model.InformationBean;

import java.util.List;

public class InformationAdapter extends BaseAdapter {
    Context context;
    List <InformationBean> list;
    public InformationAdapter(Context context, List<InformationBean> _list) {
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
        convertView = layoutInflater.inflate( R.layout.item_information, null);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.title);
        TextView tvDescribe = (TextView) convertView.findViewById(R.id.describe);
        InformationBean InforBean = list.get(position);
        tvTitle.setText(InforBean.getTitle ());
        tvDescribe.setText(InforBean.getDescribe ());
        return convertView;
    }
}
