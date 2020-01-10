package com.lianbei.taobu.utils.dbhelper.dao;

import com.lianbei.taobu.shop.model.GoodsOptBean;

import net.sqlcipher.Cursor;

import java.util.List;

public interface OptDAO {

    public long insertOperationArea(GoodsOptBean goodsOptBean);

    public List<GoodsOptBean> query();
    public Cursor rawQuery();
    public Cursor getSerchStationDB(String search_address);
}
