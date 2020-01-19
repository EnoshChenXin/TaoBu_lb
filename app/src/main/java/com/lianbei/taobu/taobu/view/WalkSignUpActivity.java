package com.lianbei.taobu.taobu.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.view.commondialog.GlobalDialogModel;
import com.lianbei.taobu.taobu.model.WalkApplyBean;
import com.lianbei.taobu.taobu.viewmanager.WalkSignUpManager;
import com.lianbei.taobu.utils.ToastUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 【跑步报名
 */
public class WalkSignUpActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.writhdrawls_tag_rv)
    RecyclerView tagRecyclerView;
    WalkSignUpManager walkSignUpManager;
    @BindView ( R.id.pay_btn )
    Button pay_btn;
    @BindView ( R.id.sugarBean_num )
    TextView sugarBean_num;

    WalkApplyBean walkBean;


    @Override
    public int getContentViewId() {
        return R.layout.activity_walk_sign_up;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
    }

    @Override
    public void initData() {
        walkSignUpManager  =new WalkSignUpManager (this);
        walkSignUpManager.getWithdrawalAllNeed ( this, tagRecyclerView, new WalkSignUpManager.onClickListener ( ) {
            @Override
            public void mClick(WalkApplyBean bean) {
                walkBean = bean;
                sugarBean_num.setText ( "糖豆：" +bean.getSugarBean ());

            }

            @Override
            public void beanlist(List <WalkApplyBean> list) {

            }
        } );

    }
    @OnClick({R.id.pay_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId ()){
            case R.id.pay_btn:
                if(walkBean != null){
                    GlobalDialogModel.getInstance ().commonDialog ( WalkSignUpActivity.this, "从淘步账户余额中扣除", walkBean.getSugarBean ()+"糖豆", new GlobalDialogModel.DialogCallBack ( ) {
                        @Override
                        public void oklBtn() {
                            Intent intent = new Intent ( WalkSignUpActivity.this,SignUpSuccess.class );
                            WalkSignUpActivity.this.startActivity ( intent);
                            finish ();
                        }
                    } );
                }else{
                    ToastUtil.showShort ( this,"请选择挑战组" );
                }
                break;
        }

    }
    @Override
    public void initListener() {

    }

    @Override
    public void onLeftClick() {
       finish ();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

}
