package com.lianbei.taobu.shop.view.pop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.base.EventMessage;
import com.lianbei.taobu.base.share.ShareViewManager;
import com.lianbei.taobu.mine.model.AddressBean;
import com.lianbei.taobu.mine.view.AddressManagerActivity;
import com.lianbei.taobu.shop.model.GoodDetailBean;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.views.CustomRoundAngleImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class OrderSelectPopView implements View.OnClickListener {
    private ShareViewManager shareViewManager;
    private GoodDetailBean goodDetailBean;
    private PopupWindow pw_share;
    private Activity mContext;
    private String earningsStr;
    int TAG;//自定义 根据不同分享页面展示不同进行自定义


    private TextView tv_name;
    private TextView tv_phont;
    private TextView tv_address;


    public OrderSelectPopView(Activity context, int TAG) {
        this.TAG = TAG;
        mContext = context;

    }


    public void newsearningsStr(String earningsStr) {
        this.earningsStr = earningsStr;
    }

    public void setData(GoodDetailBean goodDetailBean) {
        this.goodDetailBean = goodDetailBean;
    }

    public void shareViewManager(Activity activity, ShareViewManager shareViewManager) {
        this.shareViewManager = shareViewManager;
    }

    public void shareViewManager(ShareViewManager shareViewManager) {
        this.shareViewManager = shareViewManager;
    }


    public void showSharePop(View view) {
        shwoSharePw(view);
    }

    public void shwoSharePw(View view) {
        View view_share = LayoutInflater.from(mContext).inflate(R.layout.order_select, null);
        pw_share = new PopupWindow(mContext);
        pw_share.setContentView(view_share);
        pw_share.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pw_share.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_share.setTouchable(true);
        pw_share.setFocusable(true);
        pw_share.setBackgroundDrawable(new BitmapDrawable());
        pw_share.setAnimationStyle(R.style.AnimBottom);
        pw_share.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        // 设置pw弹出时候的背景颜色的变化
        backgroundAlpha(0.5f);
        pw_share.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        initData(view_share);
    }

    private void initData(View view) {
        CustomRoundAngleImageView v = (CustomRoundAngleImageView) view.findViewById(R.id.iv_img);
        TextView goods_name = (TextView) view.findViewById(R.id.goods_name);
        tv_name = view.findViewById(R.id.tv_name);
        tv_phont = view.findViewById(R.id.tv_phone);
        tv_address = view.findViewById(R.id.tv_address);
        LinearLayout lin_btn = (LinearLayout) view.findViewById(R.id.lin_btn);
        RelativeLayout choose_address = view.findViewById(R.id.choose_address);
        lin_btn.setOnClickListener(this);

        if (goodDetailBean != null) {
            GlideUtils.getInstance().loadthumbnail(mContext, goodDetailBean.getGoods_image_url(), v);
            goods_name.setText(goodDetailBean.getGoods_name() + "");
        }
        choose_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSet = new Intent(mContext, AddressManagerActivity.class);
                intentSet.putExtra("choose", 0);
                mContext.startActivityForResult(intentSet,77);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_btn:
                if (clickLintener != null) {
                    clickLintener.commit();
                }
        }
    }
    public void setAddress(AddressBean addressBean) {
        if (addressBean != null) {
            if(tv_name != null && tv_phont!= null && tv_address != null) {
                tv_name.setText(addressBean.getName()+"");
                tv_phont.setText(addressBean.getPhone()+"");
                tv_address.setText(addressBean.getAddress()+"");
            }
        }

    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mContext.getWindow().setAttributes(lp);
    }

    public onClickLintener clickLintener = null;

    public void addOnClick(onClickLintener clickLintener) {
        this.clickLintener = clickLintener;

    }

public interface onClickLintener {
    void commit();
}

}
