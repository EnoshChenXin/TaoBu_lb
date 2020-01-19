package com.lianbei.taobu.mine.view;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.adapter.OrderListAdapter;
import com.lianbei.taobu.shop.model.OrderBean;
import com.lianbei.taobu.shop.viewmanager.ShopManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class OrderListFragment extends BaseFragment {
    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    private String mChannelCode = "0";
    protected BaseQuickAdapter orderBeanListadapter;

    List<OrderBean> orderBeanList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {
        if (orderBeanList != null && orderBeanList.size() > 0)
            return;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 30秒以前
        calendar.add(Calendar.SECOND, -30);
        // 格式化显示
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        String time = sdf.format(calendar.getTime());
        ShopManager.getInstance(this.getContext()).getOrderListRange("2019-11-01 00:00:00", "", "", time, requestCompletion);

    }

    @Override
    public void initListener() {
        orderBeanListadapter = new OrderListAdapter(mChannelCode, orderBeanList);
        mRvNews.setAdapter(orderBeanListadapter);
    }

    RequestCompletion requestCompletion = new RequestCompletion() {
        @Override
        public void Success(Object value, String tag) {
            if (value != null) {
                List<OrderBean> orderlist = (List<OrderBean>) value;
                if (orderBeanList != null) {
                    orderBeanList.clear();
                }
                orderBeanList.addAll(orderlist);
                orderBeanListadapter.notifyDataSetChanged();
            }

        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };

    @Override
    public void fetchData() {

    }
}
