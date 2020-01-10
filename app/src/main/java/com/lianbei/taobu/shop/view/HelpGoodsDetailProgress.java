package com.lianbei.taobu.shop.view;


import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.share.SharePopView;
import com.lianbei.taobu.base.share.ShareViewManager;
import com.lianbei.taobu.listener.ShareRequestCompletion;
import com.lianbei.taobu.shop.model.GoodDetailBean;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.views.CustomRoundAngleImageView;
import com.lianbei.taobu.views.TextFromHtml;
import com.lianbei.taobu.views.TextViewUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpGoodsDetailProgress extends BaseActivity implements View.OnClickListener {

    GoodDetailBean goodDetailBean;
    @BindView(R.id.iv_img)
    CustomRoundAngleImageView iv_img;

    @BindView(R.id.goods_name)
    TextView goods_name;

    @BindView(R.id.helptag)
    TextView helptag;
    @BindView(R.id.ll_share)
    LinearLayout ll_share;
    @BindView(R.id.btn_stare)
    Button btn_stare;
    ShareViewManager svm;
    @BindView(R.id.progress_bar_h)
    ProgressBar progress_bar;
    @Override
    public int getContentViewId() {
        return R.layout.activity_help_goods_progress;
    }
    @Override
    public void initViews() {
        createNavigationView(R.id.navigation_id);

    }

    @Override
    public void initData() {
        Intent intent = this.getIntent();
        goodDetailBean = (GoodDetailBean) intent.getSerializableExtra("goodDetailBean");
        if (goodDetailBean != null) {
            GlideUtils.getInstance().loadthumbnail(this, goodDetailBean.getGoods_image_url(), iv_img);
            goods_name.setText(goodDetailBean.getGoods_name()+"");
        }
        double allPepper =7;
        double nownumber =4;
        int mProgressBar   =new Double((nownumber/allPepper)*100).intValue();
        setAnimation(progress_bar, 100,mProgressBar);
        String str="已有4人助力，还差3人";
        SpannableString helptext=TextViewUtil.getSizeSpanSpToPx2(this,str,2,str.indexOf("人"),str.indexOf("差")+1,str.length()-1,18,R.color.main_color);
        helptag.setText(helptext);
        showNewPeopleGift(helptext,mProgressBar);
    }
    //微信//QQ//微博分享
    private void showShare() {
        SharePopView shareView = new SharePopView ( this, 1 );
        shareView.showSharePop ( ll_share );
        svm= new ShareViewManager( this,shareCompletionListener );
        svm.isWXShareURLSessionAndTimeLine ( true,false );
        svm.title ( this.getResources ().getString (R.string.share_common_title ) );//
        svm.description ( goodDetailBean.getGoods_desc()+"");
        //svm.Resourceimgurl (R.mipmap.plan_preview_header_bg); //本地图片
        svm.imgurl ( goodDetailBean.getGoods_image_url() );//网络图片
        svm.webpageUrl ( APIs.INCOME_SHOW);
        shareView.shareViewManager ( svm );
    }
    private ShareRequestCompletion shareCompletionListener = new ShareRequestCompletion ( ) {
        @Override
        public void QQShareSuccess(Object value) {
           // shareAfter();
        }

        @Override
        public void WXShareSuccess(Object value) {
          //  shareAfter();
        }

        @Override
        public void WBShareSuccess(Object... values) {
          //  shareAfter();
        }
    };
    @Override
    public void initListener() {

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
@OnClick({R.id.btn_stare})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_stare:
                showShare();
        }
    }
    private void setAnimation(final ProgressBar view, int max,final int mProgressBar) {
        view.setMax(max);
        ValueAnimator animator = ValueAnimator.ofInt(0, mProgressBar).setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setProgress((int) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }
    /**
     *   //注册送糖豆
     */
    private void showNewPeopleGift(SpannableString helptag_text,int mProgressBar) {
        ShareViewManager svm;
        svm=new ShareViewManager ( this,shareCompletionListener);
        svm.isWXShareURLSessionAndTimeLine ( true,false );
        svm.title ( this.getResources ().getString (R.string.share_common_title ) );//
        svm.description ( goodDetailBean.getGoods_desc()+"");
        //svm.Resourceimgurl (R.mipmap.plan_preview_header_bg); //本地图片
        svm.imgurl ( goodDetailBean.getGoods_image_url() );//网络图片
        svm.webpageUrl ( APIs.INCOME_SHOW);

        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View contentView = LayoutInflater.from(this).inflate(R.layout.lay_help_dialog,null);
        View delete_v =contentView.findViewById(R.id.v_delete);
        TextView helptag_dialog = contentView.findViewById(R.id.helptag_dialog);
        helptag_dialog.setText(helptag_text);
        ProgressBar progress_bar_h_dialog = contentView.findViewById(R.id.progress_bar_h_dialog);
        setAnimation(progress_bar_h_dialog, 100,mProgressBar);
        ImageView btn_wx_share = (ImageView)contentView.findViewById(R.id.btn_wx_share);
        ImageView btn_qq_share = (ImageView)contentView.findViewById(R.id.btn_qq_share);
        dialog.setContentView(contentView);
        delete_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        btn_wx_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svm.mScene ( ShareViewManager.WXSceneSession );
                svm.sendReq ( );
            }
        });
        btn_qq_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svm.sendReqtoQQ ( );
            }
        });
    }
}
