package com.lianbei.taobu.mine.view;


import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.EventMessage;
import com.lianbei.taobu.mine.model.AddressBean;
import com.lianbei.taobu.mine.view.addresspickerview.AddressDistrictBean;
import com.lianbei.taobu.mine.view.addresspickerview.AreaPickerView;
import com.lianbei.taobu.utils.ThreadUtil;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.views.MButton;
import com.lianbei.taobu.views.SoftInputUtil;
import com.lianbei.taobu.views.mMaterialEditText;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditAddressDialog extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_name)
    mMaterialEditText et_name;

    @BindView(R.id.et_phone)
    mMaterialEditText et_phone;

    @BindView(R.id.et_address)
    mMaterialEditText et_address;

    @BindView(R.id.tv_district)
    TextView tv_district;//选择地区
    @BindView(R.id.close_dialog)
    ImageView close_dialog;
    private AreaPickerView areaPickerView;
    private List<AddressDistrictBean> addressDistrictBeans;
    @BindView(R.id.mybutton)
    public MButton mButton;
    AddressBean addressBean = new AddressBean();
    private int position;

    private int[] i;

    @Override
    public int getContentViewId() {
        return R.layout.activity_edit_address_dialog;
    }

    @Override
    public void initViews() {
        //隐藏标题栏
        position = this.getIntent().getIntExtra("position", -2);
        if (position != -1) {
            addressBean = (AddressBean) this.getIntent().getSerializableExtra("addressBeans");
            if (addressBean != null) {
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        et_name.setText(addressBean.getName() + "");
                        et_phone.setText(addressBean.getPhone() + "");
                        tv_district.setText(addressBean.getDistrict() + "");
                        et_address.setText(addressBean.getAddress() + "");
                    }
                });

            }
        }


    }

    @Override
    public void initData() {


        Gson gson = new Gson();
        addressDistrictBeans = gson.fromJson(getCityJson(), new TypeToken<List<AddressDistrictBean>>() {
        }.getType());

        areaPickerView = new AreaPickerView(this, R.style.Dialog, addressDistrictBeans);
        areaPickerView.setAreaPickerViewCallback(new AreaPickerView.AreaPickerViewCallback() {
            @Override
            public void callback(int... value) {
                i = value;
                if (value.length == 3)
                    tv_district.setText(addressDistrictBeans.get(value[0]).getLabel() + "" + addressDistrictBeans.get(value[0]).getChildren().get(value[1]).getLabel() + " " + addressDistrictBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel());
                else
                    tv_district.setText(addressDistrictBeans.get(value[0]).getLabel() + " " + addressDistrictBeans.get(value[0]).getChildren().get(value[1]).getLabel());
                if (!tv_district.getText().equals(EditAddressDialog.this.getResources().getString(R.string.HINT_INPUT_DISTRICT))) {
                    mButton.setButtonType1(false);
                }
            }
        });
    }

    private String getCityJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("region.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void initListener() {
        mButton.post(new Runnable() {
            @Override
            public void run() {
                mButton.setButtonEditTextType(new mMaterialEditText[]{et_name, et_phone, et_address});
                if(!tv_district.getText().equals(EditAddressDialog.this.getResources().getString(R.string.HINT_INPUT_DISTRICT))){
                    mButton.setButtonType1(false);
                }else{
                    mButton.setButtonType1(true);
                }
            }
        });


    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @OnClick({R.id.close_dialog, R.id.rl_address_picker, R.id.mybutton})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_dialog:
                SoftInputUtil.hideSoftInput(this, close_dialog);
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1);
                break;
            case R.id.rl_address_picker:
                areaPickerView.setSelect(i);
                areaPickerView.show();
                break;
            case R.id.mybutton:
                Log.e("mybutton:", mButton.checkInputValid() + "sss");
                if (mButton.checkInputValid()) {
                    if (tv_district.getText().equals(this.getResources().getString(R.string.HINT_INPUT_DISTRICT))) {
                        ToastUtil.showShort(this, R.string.HINT_INPUT_DISTRICT_TOAST);
                        areaPickerView.setSelect(i);
                        areaPickerView.show();
                    } else {
                        //保存操作
                        addressBean.setName(et_name.getText().toString().trim());
                        addressBean.setPhone(et_phone.getText().toString().trim());
                        addressBean.setDistrict(tv_district.getText().toString().trim());
                        addressBean.setAddress(et_address.getText().toString().trim());
                        EventMessage msg = new EventMessage();
                        msg.setObj(addressBean);
                        msg.setType(position);
                        EventBus.getDefault().post(msg);
                        finish();
                    }

                } else {

                }
                break;
        }

    }

}
