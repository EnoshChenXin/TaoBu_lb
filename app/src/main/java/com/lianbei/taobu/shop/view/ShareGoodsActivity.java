package com.lianbei.taobu.shop.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BaseTransientBottomBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lianbei.taobu.MainActivity;
import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.share.SharePopView;
import com.lianbei.taobu.base.share.ShareViewManager;
import com.lianbei.taobu.listener.RequestOnClick;
import com.lianbei.taobu.listener.ShareRequestCompletion;
import com.lianbei.taobu.shop.model.GoodDetailBean;
import com.lianbei.taobu.shop.model.PromotionUrlInfo;
import com.lianbei.taobu.taobu.view.viewutils.DpOrPxUtils;
import com.lianbei.taobu.taobu.view.viewutils.TransAvtivity;
import com.lianbei.taobu.utils.AmountUtils;
import com.lianbei.taobu.utils.DisplayUtil;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.ThreadUtil;
import com.lianbei.taobu.utils.ViewGenerationImage;
import com.lianbei.taobu.utils.ZXingUtils;
import com.lianbei.taobu.views.ImageUtils;
import com.lianbei.taobu.views.PriceView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShareGoodsActivity extends BaseActivity {
    @BindView(R.id.share_image_list)
    LinearLayout share_image_list;
    @BindView(R.id.lin_share)
    LinearLayout lin_share;
    @BindView(R.id.goods_name)
    TextView goods_name;
    @BindView(R.id.min_group_price)
    TextView min_group_price;
    @BindView(R.id.coupon_total_price)
    TextView coupon_total_price;
    @BindView(R.id.short_url)
    TextView short_url;


    GoodDetailBean goodDetailBean;
    PromotionUrlInfo promotionUrlInfo;
    ShareViewManager svm;
    Bitmap bitmap;
    LinearLayout goods_bg_share;
    LinearLayout image_list;
    List<Drawable> drawableList = new ArrayList<>();
    String groupPriceStr=""; //原价
    String CoupondiscountStr = "";//折后价

    @Override
    public int getContentViewId() {
        return R.layout.activity_share_goods;
    }

    @Override
    public void initBeforeView() {
        super.initBeforeView();

    }

    @Override
    protected void initDataObserver() {
        super.initDataObserver();
    }

    @Override
    public void initViews() {
        createNavigationView(R.id.navigation_id);
        goodDetailBean = (GoodDetailBean) this.getIntent().getSerializableExtra("goodDetailBean");
        promotionUrlInfo = (PromotionUrlInfo) this.getIntent().getSerializableExtra("promotionUrlInfo");
        share_image_list.post(new Runnable() {
            @Override
            public void run() {
                showShare();
            }
        });

    }

    @Override
    public void initData() {
        try {
            if (goodDetailBean == null || promotionUrlInfo == null)
                return;
            for (int i = 0; i < goodDetailBean.getGoods_gallery_urls().size(); i++) {
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setAdjustViewBounds(true);
                imageView.setId(i);
                imageView.setPadding(10, 0, 10, 0);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setAdjustViewBounds(true);
                share_image_list.addView(imageView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);
                //imageView.setLayoutParams(new ViewGroup.LayoutParams ( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));  //设置图片宽高
                //imageView.setImageBitmap(ToolsMet.readBitMap(CouponActivity.this, imgs[j]));
                GlideUtils.getInstance().load(this, goodDetailBean.getGoods_gallery_urls().get(i), imageView);

                imageView.setOnClickListener(imageOnClickListener);
            }
            goods_name.setText(goodDetailBean.getGoods_name());
            groupPriceStr = AmountUtils.changeF2Y2(goodDetailBean.getMin_group_price());
            long nowprice = goodDetailBean.getMin_group_price() - (goodDetailBean.getCoupon_discount());
            CoupondiscountStr =  AmountUtils.changeF2Y2(nowprice);

            min_group_price.setText("拼团活动价:￥" + groupPriceStr);
            coupon_total_price.setText("优惠卷后价:￥" +  CoupondiscountStr);
            short_url.setText("优惠链接:" + promotionUrlInfo.getShort_url() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    View.OnClickListener imageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("View:", v.getId() + "");
            Intent intent = new Intent(ShareGoodsActivity.this, TransAvtivity.class);
            intent.putExtra(TransAvtivity.EXTRA_PHOTOS, goodDetailBean.getGoods_gallery_urls());
            intent.putExtra(TransAvtivity.EXTRA_CURRENT_ITEM, v.getId());
            startActivity(intent);
        }
    };


    //微信//QQ//微博分享
    private void showShare() {
        SharePopView shareView = new SharePopView(this, 1);
        shareView.setCancleVisibility(false);
        shareView.setOutsideTouchable(false);
        shareView.showSharePop(lin_share);
        svm = new ShareViewManager(this, shareCompletionListener);
        svm.isWXShareURLSessionAndTimeLine(false, false);
        svm.title(this.getResources().getString(R.string.share_common_title));//
        svm.description(goodDetailBean.getGoods_desc() + "");
        // svm.ResourceBitmap ( bitmap );
        //svm.Resourceimgurl (R.mipmap.plan_preview_header_bg); //本地图片
        svm.setResourceBitmaponReqest(resourceBitmaponReqest);
        svm.imgurl(goodDetailBean.getGoods_image_url());//网络图片
        svm.webpageUrl(APIs.INCOME_SHOW);
        shareView.shareViewManager(svm);
        RequestOnClick requestOnClick = new RequestOnClick();
        shareView.setRequestOnClick(requestOnClick);
        requestOnClick.setOnClickShare(onClickShare);
    }

    RequestOnClick.onClickShare onClickShare = new RequestOnClick.onClickShare() {
        @Override
        public void wechatClick(RequestOnClick.ResultOK resultOK) {
            if (goodDetailBean == null || goodDetailBean.getGoods_gallery_urls().size() == 0)
                return;
            //View view = getLayoutInflater().inflate(R.layout.view_share_goods, null);
            View view_share = LayoutInflater.from(ShareGoodsActivity.this).inflate(R.layout.view_share_goods, null);
            goods_bg_share = view_share.findViewById(R.id.goods_bg_share);
            image_list = view_share.findViewById(R.id.image_list);
            ImageView goods_image = view_share.findViewById(R.id.goods_image);
            TextView  goods_name =view_share.findViewById(R.id. goods_name);
            PriceView oldprice =view_share.findViewById(R.id. oldprice);
            PriceView  nowprice =view_share.findViewById(R.id. nowprice);
            ImageView ewm_image = view_share.findViewById(R.id.ewm);
            goods_image.setAdjustViewBounds(true);
            LinearLayout  lin_image_parent =  view_share.findViewById(R.id.lin_image_parent);

            //setTextContnet
            goods_name.setText("\u3000\u3000\u0020"+goodDetailBean.getGoods_name());
            oldprice.setText(groupPriceStr);
            nowprice.setTypeface(Typeface.DEFAULT_BOLD);
            nowprice.setText(CoupondiscountStr);

           // goods_image.setImageResource(ShareGoodsActivity.this.getResources(R.mipmap.add_picture));
           // goods_image.setImageResource(R.mipmap.add_picture);
            DisplayMetrics outMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            int widthPixels = outMetrics.widthPixels;
            Log.e("widthPixels:",widthPixels+"--");
            int heightPixels = outMetrics.heightPixels;

            Bitmap ewm =   ZXingUtils.createQRImage(promotionUrlInfo.getShort_url()
                   ,800,800);
            ewm_image.setScaleType(ImageView.ScaleType.FIT_XY);
            ewm_image.setAdjustViewBounds(true);
            ewm_image.setImageBitmap(ewm);


            GlideUtils.getInstance().load(ShareGoodsActivity.this, goodDetailBean.getGoods_gallery_urls().get(0), new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    ThreadUtil.runInUIThread(new Runnable() {
                        @Override
                        public void run() {

//                            DisplayMetrics dm = new DisplayMetrics();
//                            ShareGoodsActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//                            int width = dm.widthPixels;

                            //获取屏幕宽度
                            WindowManager m = (WindowManager)ShareGoodsActivity.this.getSystemService(Context.WINDOW_SERVICE);
                            DisplayMetrics outMetrics = new DisplayMetrics();
                            m.getDefaultDisplay().getMetrics(outMetrics);
                            int width = outMetrics.widthPixels;
                            Log.e("width:",width+"--");
                         //   int height = width * mainImage.getHeight() / mainImage.getWidth();
                            ViewGroup.LayoutParams lp;
                            lp= lin_image_parent.getLayoutParams();
                            lp.width=width;
                            lp.height=width;
                            lin_image_parent.setLayoutParams(lp);



                           // goods_image.setMinimumHeight(goods_image.getWidth());
                           int image_W = resource.getMinimumWidth();
                           int image_H = resource.getMinimumHeight();
                           int goods_imageW = goods_image.getWidth();
                           int view_shareW = lin_share.getWidth();
                           int totalPadding = com.lianbei.commomview.adlibrary.utils.DisplayUtil.dip2px(ShareGoodsActivity.this, view_shareW );
                           Log.e("image_W:","image_W:"+image_W+"---image_H:"+image_H+"==goods_imageW:=="+goods_imageW+"view_shareW:=="+view_shareW);
                           Log.e("totalPadding:",totalPadding+"");

                            lin_image_parent.setMinimumWidth(image_W);
                            lin_image_parent.setMinimumHeight(image_H);
//                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                    width);
//                            lin_image_parent.setLayoutParams(params);
//                            ViewGroup.LayoutParams params = goods_image.getLayoutParams();
//                            params.width = width;
//                            params.height= width;
                             goods_image.setImageDrawable(resource);
                             goods_image.setMinimumWidth(image_H);
                           //  goods_image.setMinimumHeight(image_H);
                             LinearLayout.LayoutParams params_1= (LinearLayout.LayoutParams) goods_image.getLayoutParams();
                            // params_1.width = width*2;
                             params_1.height= width;
                             goods_image.setLayoutParams(params_1);


                           // goods_image.setImageBitmap(ImageUtils.drawableToBitmap(resource));

                            Log.e("image_W:","image_W:"+image_W+"---image_H:"+image_H+"==goods_imageW:=="+goods_imageW);
                            goods_bg_share.destroyDrawingCache();
                            goods_bg_share.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                            goods_bg_share.setBackgroundColor(Color.WHITE);
                            goods_bg_share.layout(0, 0, goods_bg_share.getMeasuredWidth(), goods_bg_share.getMeasuredHeight());
                            goods_bg_share.setDrawingCacheEnabled(true);
                            bitmap =  goods_bg_share.getDrawingCache(true);
                          //  goods_image.setMinimumHeight(widthPixels*2);
                          //  goods_image.setImageDrawable(resource);
                         //   bitmap = new ViewGenerationImage().getViewBitmap(goods_bg_share);
                            svm.ResourceBitmap(bitmap);
                            resultOK.resultok();
                        }
                    });
                }
            });
        }



        private void shareImage(){

        }


        @Override
        public void friendCircleClick(RequestOnClick.ResultOK resultOK) {
            if (goodDetailBean == null || goodDetailBean.getGoods_gallery_urls().size() == 0)
                return;
            //View view = getLayoutInflater().inflate(R.layout.view_share_goods, null);
            View view_share = LayoutInflater.from(ShareGoodsActivity.this).inflate(R.layout.view_share_goods, null);
            goods_bg_share = view_share.findViewById(R.id.goods_bg_share);
            image_list = view_share.findViewById(R.id.image_list);
            LinearLayout ll_share = view_share.findViewById(R.id.ll_share);

            DisplayMetrics outMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            int widthPixels = outMetrics.widthPixels;
            int heightPixels = outMetrics.heightPixels;


            ImageView goods_image = view_share.findViewById(R.id.goods_image);
            GlideUtils.getInstance().load(ShareGoodsActivity.this, goodDetailBean.getGoods_gallery_urls().get(0), new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    ThreadUtil.runInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            goods_image.setImageDrawable(resource);
                            bitmap = new ViewGenerationImage().getViewBitmap(goods_bg_share);
                            svm.ResourceBitmap(bitmap);
                            resultOK.resultok();
                        }
                    });

                }
            });
        }


    };


    ShareViewManager.ResourceBitmaponReqest resourceBitmaponReqest = new ShareViewManager.ResourceBitmaponReqest() {
        @Override
        public Bitmap bitmap() {
            return null;
        }
    };

    private ShareRequestCompletion shareCompletionListener = new ShareRequestCompletion() {
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

}
