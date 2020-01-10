package com.lianbei.taobu.circle.adapter.itemprovider;


import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.circle.model.GroupPoperBean;
import com.lianbei.taobu.circle.model.lineListBean;

public  class GroupManagerItemProvider extends BaseGroupManagerItemProvider {
    public GroupManagerItemProvider(String channelCode) {
        super ( channelCode );
    }

    @Override
    public int viewType() {
        return 0;
    }

    @Override
    public int layout() {
        return R.layout.item_group_manager_layout;
    }

    @Override
    protected void setData(BaseViewHolder helper, lineListBean.ListBean groupPoperBean) {
        helper.setText ( R.id.tv_title,groupPoperBean.getNickname ()+"" );
        helper.setText ( R.id.tv_addTime,groupPoperBean.getUpdate_time ()+"" );
        helper.setBackgroundColor ( R.id.bg_lin , mContext.getResources ().getColor ( R.color.green500 ));
        helper.setText ( R.id.state_tv,"查看" );
        helper.addOnClickListener ( R.id.bg_lin );

//        switch (groupPoperBean.getState ()){
//            case "0":
//                helper.setBackgroundColor ( R.id.bg_lin , mContext.getResources ().getColor ( R.color.green500 ));
//                helper.setText ( R.id.state_tv,"查看" );
//                helper.addOnClickListener ( R.id.bg_lin );
//                break;
//            case "1":
//                helper.setBackgroundColor ( R.id.bg_lin , mContext.getResources ().getColor ( R.color.transparent ));
//                helper.setText ( R.id.state_tv,"已添加" );
//                helper.setTextColor ( R.id.state_tv, mContext.getResources ().getColor ( R.color.green450 ));
//                break;
//            case "2":
//                helper.setBackgroundColor ( R.id.bg_lin , mContext.getResources ().getColor ( R.color.transparent ));
//                helper.setText ( R.id.state_tv,"已拒绝" );
//                helper.setTextColor ( R.id.state_tv, mContext.getResources ().getColor ( R.color.theme_gray  ));
//                break;

        }
    }