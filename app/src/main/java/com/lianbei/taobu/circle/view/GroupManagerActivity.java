package com.lianbei.taobu.circle.view;


import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.adapter.GroupManagerAdapter;
import com.lianbei.taobu.circle.model.GroupPoperBean;
import com.lianbei.taobu.circle.model.ItmeInfo;
import com.lianbei.taobu.circle.model.lineListBean;
import com.lianbei.taobu.circle.view.commondialog.GlobalDialogModel;
import com.lianbei.taobu.circle.viewmanager.GameManager;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.Validator;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;

public class GroupManagerActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;

    @BindView(R.id.rel_exit)
    RelativeLayout rel_exit;
    @BindView(R.id.btn_exit)
    Button btn_exit;

    @BindView(R.id.rel_dissolve)
    RelativeLayout rel_dissolve;
    @BindView(R.id.btn_dissolve)
    Button btn_dissolve;
    ItmeInfo itmeInfo = new ItmeInfo ( );

    List <lineListBean.ListBean> gameBeanList = new ArrayList <> ( );


    protected BaseQuickAdapter groupmanageradapter;
    private String mChannelCode;

    @Override
    public int getContentViewId() {
        return R.layout.activity_group_manager;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
        itmeInfo  =(ItmeInfo)getIntent ().getSerializableExtra ( "itmeInfo" );
        navigationView.setTitleText ( getResources ( ).getString ( R.string.TAB_TEXT_GROUP_MANAGER ) );
        navigationView.setRightBtnHidden ( false );
        navigationView.setStrBtnRight ( "邀请" );
        setExitGroupVisible();
    }

  private void   setExitGroupVisible(){
        if(itmeInfo != null){
            if(!UserInfo.UserInfoIsEmpty ( this ) && (itmeInfo.getUserTeam ().getUser_id () == UserInfo.getUserInfo ( this ).getUser_id ())){
                rel_dissolve.setVisibility ( View.VISIBLE );
            }else{
                if(itmeInfo.getUserTeam ().isInTeam ()) {
                    btn_exit.setText ( "退出队伍" );
                }else{
                    btn_exit.setText ( "加入队伍" );
                }
                rel_exit.setVisibility ( View.VISIBLE );
            }
        }
    }

    @Override
    public void initData() {
        setListView ( );
        GameManager.getInstance ( this ).GetGroupManagerList ( itmeInfo.getUserTeam ().getId ()+"", "1", "10", requestCompletion);
    }

    private void setListView() {
        mRvNews.setLayoutManager ( new GridLayoutManager( this, 1 ) );
        mRvNews.setNestedScrollingEnabled ( false );//解决滑动卡顿
    }

    @Override
    public void initListener() {
        groupmanageradapter = new GroupManagerAdapter ( mChannelCode, gameBeanList );
        mRvNews.setAdapter ( groupmanageradapter );
        groupmanageradapter.setOnItemChildClickListener ( new BaseQuickAdapter.OnItemChildClickListener ( ) {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if((view.getId() == R.id.bg_lin)){
                    GlobalDialogModel.getInstance ( ).commonDialog ( GroupManagerActivity.this, "对方请求加入群组，希望与你一决高下", new GlobalDialogModel.DialogCallBack ( ) {
                        @Override
                        public void oklBtn() {
                            GameManager.getInstance ( GroupManagerActivity.this ).intHasPass(itmeInfo.getUserTeam ().getId ()+"", UserInfo.getUserInfo ( GroupManagerActivity.this ).getUser_id ()+"" ,"1",hasPass);

                        }
                    } );
                }
            }
        } );
    }

    @Override
    public void onLeftClick() {
        finish ( );

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @OnClick({R.id.btn_dissolve,R.id.btn_exit})
    @Override
    public void onClick(View view) {
        switch (view.getId ()){
            case R.id.btn_dissolve:
                GlobalDialogModel.getInstance ( ).commonDialog ( this, "确认要解散群组嘛？", new GlobalDialogModel.DialogCallBack ( ) {
                    @Override
                    public void oklBtn() {
                        ToastUtil.showShort ( GroupManagerActivity.this,"暂不支持此功能" );
                    }
                } );
                break;
            case R.id.btn_exit:
                GlobalDialogModel.getInstance ( ).commonDialog ( this, "确定离开？", new GlobalDialogModel.DialogCallBack ( ) {
                    @Override
                    public void oklBtn() {

                    }
                } );
                break;
        }
    }

    GameManager.RequestCompletion  requestCompletion =new GameManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            if (value != null) {
                lineListBean value1= (lineListBean)value;
                gameBeanList.addAll ( value1.getList () );
                groupmanageradapter.notifyDataSetChanged ();
            }



        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };

    GameManager.RequestCompletion  hasPass =new GameManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            //同意或者拒絕则刷新数据
           finish ();

        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };
}
