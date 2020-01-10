package com.lianbei.taobu.mine.view;


import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.EventMessage;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.adapter.AddressListAdapter;
import com.lianbei.taobu.mine.model.AddressBean;
import com.lianbei.taobu.mine.viewmanager.UserInfoManager;
import com.lianbei.taobu.views.MButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressManagerActivity extends BaseActivity implements RequestCompletion, View.OnClickListener {
    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;

    @BindView(R.id.MButton)
    MButton MButton;

    private String mChannelCode = "0";
    protected BaseQuickAdapter orderBeanListadapter;
    private int choose = -2;

    List<AddressBean> addressBeans = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_address_manager;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initViews() {
        createNavigationView(R.id.navigation_id);
        choose = this.getIntent().getIntExtra("choose", -2);

    }

    @Override
    public void initBeforeView() {
        super.initBeforeView();
    }

    @Override
    public void initData() {
        UserInfoManager.getInstance(this).modifyAddress(this);
    }

    @Override
    public void initListener() {
        MButton.post(new Runnable() {
            @Override
            public void run() {
                MButton.setButtonAvailable(true);
            }
        });
        orderBeanListadapter = new AddressListAdapter(mChannelCode, addressBeans);
        mRvNews.setAdapter(orderBeanListadapter);

        orderBeanListadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                for (int j = 0; j < addressBeans.size(); j++) {
                    addressBeans.get(j).setIschoose(false);
                }
                addressBeans.get(i).setIschoose(true);
                orderBeanListadapter.notifyDataSetChanged();
                if (choose != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("addressBeans", addressBeans.get(i));
                    setResult(RESULT_OK, intent);
//                            EventMessage msg = new EventMessage();
//                            msg.setType(0);
//                            msg.setObj(addressBeans.get(i));
//                            EventBus.getDefault().post(msg);
                    finish();
                }

            }

        });
        orderBeanListadapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                int viewId = view.getId();
                if (viewId == R.id.edit_address) {
                    Intent intent = new Intent(AddressManagerActivity.this, EditAddressDialog.class);
                    intent.putExtra("position", i);
                    intent.putExtra("addressBeans", addressBeans.get(i));
                    startActivity(intent);

                }
            }
        });


    }

    @Override
    public void onLeftClick() {
        finish();

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @Override
    public void Success(Object value, String tag) {
        try {
            addressBeans.addAll((List<AddressBean>) (value));
            orderBeanListadapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Fail(Object value) {

    }

    @Override
    public void Error(Object... values) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(EventMessage message) {
        if (message != null) {
            AddressBean addressBean = (AddressBean) message.getObj();
            if (message.getType() != -1) {
                addressBeans.set(message.getType(), addressBean);
            } else {
                addressBeans.add(addressBean);
            }
            orderBeanListadapter.notifyDataSetChanged();
        }

    }

    @OnClick({R.id.MButton})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MButton:
                Intent intent = new Intent(AddressManagerActivity.this, EditAddressDialog.class);
                intent.putExtra("position", -1);
                startActivity(intent);
                break;
        }

    }
}
