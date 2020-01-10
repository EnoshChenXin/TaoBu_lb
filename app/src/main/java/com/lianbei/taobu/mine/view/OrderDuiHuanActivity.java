package com.lianbei.taobu.mine.view;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;

import butterknife.BindView;

public class OrderDuiHuanActivity extends BaseActivity {

    @BindView(R.id.et_input_order)
    EditText et_input_order;
    @BindView(R.id.clean_search_tv)
    ImageView clean_search_tv;
    @Override
    public int getContentViewId() {
        return R.layout.activity_order_dui_huan;
    }

    @Override
    public void initViews() {
        createNavigationView(R.id.navigation_id);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        et_input_order.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("") || s.toString().length() > 0) {
                    clean_search_tv.setVisibility(View.VISIBLE);
                } else {
                    clean_search_tv.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("") || s.toString().length() > 0) {
                    clean_search_tv.setVisibility(View.VISIBLE);
                } else {
                    clean_search_tv.setVisibility(View.GONE);
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
}
