package com.lianbei.taobu.utils.dbhelper.dao;

import com.lianbei.taobu.shop.model.GoodsOptBean;
import com.lianbei.taobu.shop.model.SearchRecord;

import net.sqlcipher.Cursor;

import java.util.List;

public interface OptDAO {

    public long insertGoodsOpt(GoodsOptBean goodsOptBean);
    public long insertSearchRecord(SearchRecord searchRecord);

    public List<GoodsOptBean> queryOptBean();
    public List<SearchRecord> querySearchRecord();


    public Cursor rawQuery();
    public Cursor getSerchGoodsOpt(String search_address);
}
