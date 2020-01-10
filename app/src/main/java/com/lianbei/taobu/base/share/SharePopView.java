package com.lianbei.taobu.base.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestOnClick;


public class SharePopView implements View.OnClickListener{
    private ShareViewManager shareViewManager;
    private  PopupWindow pw_share;
    private Activity mContext;
    private String   earningsStr;
    int TAG;//自定义 根据不同分享页面展示不同进行自定义
    private boolean outsideTouchable = true;
    private boolean cancleVisibility =true;
    private RequestOnClick requestOnClick;



    public SharePopView(Activity context ,int TAG){
        this.TAG = TAG;
        mContext = context;
    }
    public void setRequestOnClick(RequestOnClick requestOnClick){
        this.requestOnClick = requestOnClick;
    }

    public void newsearningsStr(String earningsStr){
        this.earningsStr = earningsStr;
    }

    public void shareViewManager(Activity activity ,ShareViewManager shareViewManager){
        this.shareViewManager = shareViewManager;
    }
    public void shareViewManager(ShareViewManager shareViewManager){
        this.shareViewManager = shareViewManager;
    }
    public void showSharePop(View view){
        shwoSharePw(view);
    }
    public void setOutsideTouchable(boolean touchable){
        outsideTouchable =touchable;
    }
    public void setCancleVisibility(boolean gone){
        cancleVisibility = gone;
    }
    public  void shwoSharePw(View view) {
        View view_share = LayoutInflater.from(mContext).inflate( R.layout.common_share, null);
        pw_share = new PopupWindow (mContext);
        pw_share.setContentView(view_share);
        pw_share.setOutsideTouchable(outsideTouchable);
        pw_share.setWidth( LinearLayout.LayoutParams.MATCH_PARENT);
        pw_share.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_share.setTouchable(true);
        pw_share.setFocusable(outsideTouchable);
        pw_share.setBackgroundDrawable(new BitmapDrawable ());
        pw_share.setAnimationStyle( R.style.AnimBottom);
        pw_share.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        // 设置pw弹出时候的背景颜色的变化
        backgroundAlpha(1f);
        LinearLayout share_explain = (LinearLayout)view_share.findViewById ( R.id.share_explain);
        LinearLayout share_friendCircle = (LinearLayout)view_share.findViewById ( R.id.share_friendCircle);
        LinearLayout share_wechat = (LinearLayout)view_share.findViewById ( R.id.share_wechat);
        LinearLayout share_QQ = (LinearLayout)view_share.findViewById ( R.id.share_QQ);
        LinearLayout share_weibo = (LinearLayout)view_share.findViewById ( R.id.share_weibo);
        LinearLayout share_miniObject = (LinearLayout)view_share.findViewById ( R.id.share_miniObject);
        LinearLayout ly_cancle = (LinearLayout)view_share.findViewById ( R.id.ly_cancle);

        TextView share_news_earnings = (TextView)view_share.findViewById ( R.id.share_news_earnings);
        share_news_earnings.setText ( earningsStr+"" );
        if(cancleVisibility){
            ly_cancle.setVisibility(View.VISIBLE);
        }else{
            ly_cancle.setVisibility(View.GONE);
        }
        pw_share.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        share_friendCircle.setOnClickListener(this);
        share_wechat.setOnClickListener(this);
        share_QQ.setOnClickListener(this);
        share_weibo.setOnClickListener(this);
        ly_cancle.setOnClickListener ( this );
        share_miniObject.setOnClickListener ( this );
        if(TAG == 0){
            APIs.SHARE_TAG="newsShare"; //新闻分享
            share_explain.setVisibility ( View.VISIBLE );
        }else if(TAG == 1){
            APIs.SHARE_TAG="dailyShare";//每日分享
        }else if(TAG == 2){
            APIs.SHARE_TAG="";//
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.share_friendCircle:
                if(shareViewManager != null){
                    if(requestOnClick != null){
                        requestOnClick.onClickShare.friendCircleClick(new RequestOnClick.ResultOK() {
                            @Override
                            public void resultok() {
                                shareViewManager.mScene ( ShareViewManager.WXSceneTimeline );
                                shareViewManager.sendReq();
                                if(pw_share != null && outsideTouchable)
                                    pw_share.dismiss ();
                            }
                        });
                    }
                }
                break;
            case R.id.share_wechat:
                if(shareViewManager != null){
                    if(requestOnClick != null){
                        requestOnClick.onClickShare.wechatClick(new RequestOnClick.ResultOK() {
                            @Override
                            public void resultok() {
                                shareViewManager.mScene ( ShareViewManager.WXSceneSession );
                                shareViewManager.sendReq();
                                if(pw_share != null && outsideTouchable)
                                    pw_share.dismiss ();
                            }
                        });
                    }
                }
                break;
            case R.id.share_miniObject:
                if(shareViewManager != null){
                    shareViewManager.mScene ( ShareViewManager.WXSceneSession );
                    shareViewManager.ShareMiniObject();
                    if(pw_share != null && outsideTouchable)
                        pw_share.dismiss ();
                }
                break;
            case R.id.share_QQ:
                if(shareViewManager != null){
                    shareViewManager.sendReqtoQQ();
                    if(pw_share != null && outsideTouchable)
                        pw_share.dismiss ();
                }
                break;
            case R.id.share_weibo:
                if(shareViewManager != null){
                shareViewManager.shareWeiBo();
                    if(pw_share != null && outsideTouchable)
                        pw_share.dismiss ();
                }
                break;
            case R.id.ly_cancle:
                if(pw_share != null && outsideTouchable)
                    pw_share.dismiss ();
                break;
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param
     */
    public   void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp =mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mContext.getWindow().setAttributes(lp);
    }
}
