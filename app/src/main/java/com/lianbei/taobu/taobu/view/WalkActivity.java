package com.lianbei.taobu.taobu.view;


import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.taobu.model.WalkBean;
import com.lianbei.taobu.views.h5.ProgressWebView;

import butterknife.BindView;
import butterknife.OnClick;

public class WalkActivity extends BaseActivity  implements View.OnClickListener {

    @BindView ( R.id.sign_up_btn )
    LinearLayout signup_btn;

//    未报名
    @BindView ( R.id.lin_No_registration ) //总布局
    LinearLayout lin_No_registration;

    //    已报名/比赛总页面
    @BindView ( R.id.lin_registration ) //总布局
    LinearLayout lin_registration;

    //提交成绩
    @BindView ( R.id.rel_upload ) //总布局
    RelativeLayout rel_upload;

    //名次
    @BindView ( R.id.rel_ranking ) //总布局
    RelativeLayout rel_ranking;

    // <!--比赛金额/报名人数-->
    @BindView ( R.id.rel_amountNumber ) //总布局
     RelativeLayout rel_amountNumber;

    WalkBean walkBean;

    @BindView ( R.id.web )
    ProgressWebView webView;
    String url = "http://nx.lbeizxw.com/tbyx/run_rules.html";

    @Override
    public int getContentViewId() {
        return R.layout.activity_run;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
    }

    @Override
    public void initData() {
        walkBean = new WalkBean ();
        walkBean.setGamestate (0 );
        //0：未参加比赛状态 1：已报名明日比赛状态  2：比赛进行中......
        if(walkBean.getGamestate () == 0){
            lin_No_registration.setVisibility ( View.VISIBLE );
            lin_registration.setVisibility ( View.GONE );
            rel_ranking.setVisibility ( View.GONE );
            rel_amountNumber.setVisibility ( View.GONE );
        }else if(walkBean.getGamestate () == 1){
            lin_No_registration.setVisibility ( View.GONE );
            lin_registration.setVisibility ( View.VISIBLE );
            rel_upload.setVisibility ( View.GONE  );
            rel_ranking.setVisibility ( View.GONE );
            rel_amountNumber.setVisibility ( View.VISIBLE );
        }else if(walkBean.getGamestate () == 2){
            lin_No_registration.setVisibility ( View.GONE );
            lin_registration.setVisibility ( View.VISIBLE );
            rel_upload.setVisibility ( View.VISIBLE  );
            rel_ranking.setVisibility ( View.VISIBLE );
            rel_amountNumber.setVisibility ( View.GONE );
        }
        loadUrl (url);

    }

   @OnClick(R.id.sign_up_btn)
   @Override
   public void onClick(View view) {
       switch (view.getId ()){
           case R.id.sign_up_btn:
               Intent intent = new Intent ( this,WalkSignUpActivity.class );
               startActivity ( intent );
               break;
       }

   }
    /**
     * 加载url
     * @param url
     */
    private void loadUrl(String url) {
        webView.loadUrl(url.trim());
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
