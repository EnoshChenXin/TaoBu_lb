package com.lianbei.taobu.shop.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.api.PddParam;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.MessageEvent;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.shop.model.BannerImgInfo;
import com.lianbei.taobu.shop.model.GoodDetailBean;
import com.lianbei.taobu.shop.model.PromotionUrlInfo;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.taobu.view.viewutils.TransAvtivity;
import com.lianbei.taobu.utils.AmountUtils;
import com.lianbei.taobu.utils.AppUtils;
import com.lianbei.taobu.utils.DisplayUtil;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.StatusBarUtil;
import com.lianbei.taobu.utils.TimeUtils;
import com.lianbei.taobu.views.PriceView;
import com.lianbei.taobu.views.bannerview.bean.IBanner;
import com.lianbei.taobu.views.bannerview.lib.CycleViewPager;
import com.lianbei.taobu.views.bannerview.ui.ADInfo;
import com.lianbei.taobu.views.h5.H5PublicActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.OnClick;

public class ActivityGoodsDetail extends BaseActivity implements View.OnClickListener {
    private CycleViewPager cycleViewPager;
    private List<BannerImgInfo> imgBannerArray;
    private ADInfo info;
    private List<ADInfo> infos = new ArrayList<ADInfo>();

    private String goods_id;
    GoodDetailBean goodDetailBean;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.afterprice)
    PriceView afterprice;

    @BindView(R.id.oldprice)
    PriceView oldprice;

    @BindView(R.id.sales_tip)
    TextView sales_tip; //
    @BindView(R.id.goods_name)
    TextView goods_name; //
    @BindView(R.id.goods_desc)
    TextView goods_desc; //
    @BindView(R.id.coupon_remain_quantity) //优惠券剩余数量
            TextView tv_coupon_remain_quantity; //

    @BindView(R.id.starttime_endtime) //开始结束时间
            TextView starttime_endtime; //
    @BindView(R.id.tv_coupon_count) //优惠券数量
            TextView tv_coupon_count; //
    @BindView(R.id.tv_coupon_discount) //优惠券面额
            TextView tv_coupon_discount; //

    @BindView(R.id.promotion_btn2) //卷已抢光
            TextView tv_promotion_btn2; //

    @BindView(R.id.buy_price) //自购反
            PriceView buy_price; //

    @BindView(R.id.share_price) //分享赚
            PriceView share_price; //

    @BindView(R.id.image_list)
    LinearLayout image_list; //

    @BindView(R.id.title_rel)
    RelativeLayout title_rel;

    @BindView(R.id.rel_banner)
    RelativeLayout rel_banner; //banner 高度

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @BindView(R.id.title_tv)
    TextView title_tv;

    List<String> goods_ids = null;

    private long coupon_remain_quantity;//优惠券剩余数量
    private long coupon_total_quantity;//优惠券总数量
    private long coupon_discount;//优惠券面额
    private long coupon_start_time; // 优惠券生效时间，UNIX时间戳

    private long coupon_end_time;  // 优惠券失效时间，UNIX时间戳

    @Override
    public int getContentViewId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initBeforeView() {
        //StatusBarUtil.setTranslucentStatus ( this );
        StatusBarUtil.setStatusBarMode(this, true, R.color.transparent);
        super.initBeforeView();

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {
        goods_id = this.getIntent().getStringExtra("goods_id");
        goods_ids = new ArrayList<>();
        goods_ids.add(goods_id);
        ShopManager.getInstance(this).goods_Detail(requestgoods_Detail, goods_ids.toString());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("热门1");
        //shopManager.Pid_Generate(requestgoods_Detail,"1",arrayList.toString ());
    }

    private void setView() {
        setDatailView();
        tet();
        if (imgBannerArray == null) {
            imgBannerArray = new ArrayList<>();
        }
        imgBannerArray.clear();
        image_list.removeAllViews();
        BannerImgInfo b;
        for (int i = 0; i < goodDetailBean.getGoods_gallery_urls().size(); i++) {
            b = new BannerImgInfo();
            b.setUrl(goodDetailBean.getGoods_gallery_urls().get(i));
            b.setUrlType("1");
            imgBannerArray.add(b);
        }
        initialize();
        // new test ( ).execute ( "" );
    }

    private void tet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long nowprice = goodDetailBean.getMin_group_price() - (goodDetailBean.getCoupon_discount());
                            afterprice.setText(AmountUtils.changeF2Y(nowprice));
                            //自购反
                            buy_price.setText(AmountUtils.changeF2Y2(PddParam.getCommission(nowprice, goodDetailBean.getPromotion_rate())) + "");
                            //分享赚
                            share_price.setText(AmountUtils.changeF2Y2(PddParam.getCommission(nowprice, goodDetailBean.getPromotion_rate())) + "");
                            oldprice.setText(AmountUtils.changeF2Y(goodDetailBean.getMin_group_price()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();

    }

    private void setDatailView() {
        try {
            if (goodDetailBean != null) {
                coupon_remain_quantity = goodDetailBean.getCoupon_remain_quantity();
                coupon_total_quantity = goodDetailBean.getCoupon_total_quantity();
                coupon_discount = goodDetailBean.getCoupon_discount();
                coupon_discount = goodDetailBean.getCoupon_discount();
                coupon_start_time = goodDetailBean.getCoupon_start_time();
                coupon_end_time = goodDetailBean.getCoupon_end_time();

                sales_tip.setText("已售" + goodDetailBean.getSales_tip() + "单");
                goods_name.setText(goodDetailBean.getGoods_name());
                goods_desc.setText(goodDetailBean.getGoods_desc());
                tv_coupon_remain_quantity.setText(goodDetailBean.getCoupon_remain_quantity() + "");
                //开始结束时间
                starttime_endtime.setText(TimeUtils.getUnixTransferTime(goodDetailBean.getCoupon_start_time(),"yyyy-MM-dd") + " ~ " + TimeUtils.getUnixTransferTime(goodDetailBean.getCoupon_end_time(),"yyyy-MM-dd") + "");

                //优惠券剩余数量
                tv_coupon_count.setText(coupon_remain_quantity + "/" + coupon_total_quantity + "张");
                tv_coupon_discount.setText(AmountUtils.changeF2Y2(coupon_discount));

                if (coupon_start_time == 0 || coupon_end_time == 0) {
                    tv_promotion_btn2.setText("卷已抢光");
                    tv_promotion_btn2.setTextColor(getResources().getColor(R.color.transparent90_white));
                    // promotion_btn.setText ( "购买" );
                } else {
                    tv_promotion_btn2.setText("抢先领劵");
                    // promotion_btn.setText ("领劵购买" );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.back, R.id.promotion_lin, R.id.promotion_btn2, R.id.share_promotion, R.id.home, R.id.collect})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                EventBus.getDefault().post(new MessageEvent(""));
                break;
            case R.id.promotion_lin:
                promotionUrl(true,0);
                break;
            case R.id.promotion_btn2:
                if (coupon_start_time != 0 && coupon_end_time != 0) {
                    promotionUrl(true,0);
                }
                break;
            case R.id.share_promotion: //分享赚
                if (goodDetailBean != null) {
                    promotionUrl(false,1);

                }

//                if(coupon_start_time != 0 && coupon_end_time !=0){
//                    promotionUrl ( );
//                }
                break;
            case R.id.home: //首页
                finish();
                break;
            case R.id.collect: //收藏

                break;
        }
    }

    /**
     * 多多进宝推广链接生成
     */
    private void promotionUrl(boolean isbuy,int tag) {
//        if(UserUtils.isNotLogin ( this )){
//            return;
//        }
        UserInfo userInfo = UserInfo.getUserInfo(this);
        int userid = userInfo.getUser_id();
        ShopManager.getInstance(this).PDD_Url_Generate(goods_ids.toString(), isbuy, pddurlgenerateCompletion, tag + "");
    }


    RequestCompletion requestgoods_Detail = new RequestCompletion() {
        @Override
        public void Success(Object value, String tag) {
            goodDetailBean = (GoodDetailBean) value;
            setView();
        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };

    @Override
    public void initListener() {
        title_rel.post(new Runnable() {
            @Override
            public void run() {
                title_rel.getBackground().mutate().setAlpha(0);
                title_tv.setTextColor(Color.argb(0, 61, 61, 61));
            }
        });


        rel_banner.post(new Runnable() {
            @Override
            public void run() {
                //rel_banner.getMeasuredHeight();
                Log.e("rel_banner:",rel_banner.getMeasuredHeight()- DisplayUtil.px2dip(ActivityGoodsDetail.this,70)+"");
            }
        });
       nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                Log.e("initListener:","i:"+i+"  i1:"+i1+"  i2:"+i2+"  i3:"+i3);

                if(0<= i1  &&  i1<= 620){
                    Long in = Math.round((double)i1/2.43);
                    int b=in.intValue();
                    title_rel.getBackground().mutate().setAlpha(b);//(alpha 的取值范围0~255)；
                    title_tv.setTextColor(Color.argb(b, 61, 61, 61));
                }else{
                    title_tv.setTextColor(Color.argb(255, 61, 61, 61));
                    title_rel.getBackground().mutate().setAlpha(255);//(alpha 的取值范围0~255)；
                }
            }
        });
    }

    @SuppressLint("NewApi")
    private void initialize() {
        cycleViewPager = (CycleViewPager) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        if (imgBannerArray != null && imgBannerArray.size() > 0) {
            for (int i = 0; i < imgBannerArray.size(); i++) {
                info = new ADInfo();
                info.url = imgBannerArray.get(i).getUrl();
                info.photoDesc = "图片-->" + i;
                info.type = "banner";
                info.urlType = imgBannerArray.get(i).getUrlType();
                info.clickUrlTitle = imgBannerArray.get(i).getClickUrlTitle();
                info.clickUrl = imgBannerArray.get(i).getClickUrl();
                infos.add(info);
                //详情start
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setAdjustViewBounds(true);
                imageView.setId(i);
                imageView.setPadding(20, 20, 20, 0);
                //imageView.setLayoutParams(new ViewGroup.LayoutParams ( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));  //设置图片宽高
                //imageView.setImageBitmap(ToolsMet.readBitMap(CouponActivity.this, imgs[j]));
                GlideUtils.getInstance().load(this, imgBannerArray.get(i).getUrl(), imageView);
                image_list.addView(imageView);
                imageView.setOnClickListener(imageOnClickListener);
                //详情end
            }
        }
        cycleViewPager.setData(infos, mAdCycleViewListener);
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(4000);
//		cycleViewPager.setWheel(true);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();

//		// 将最后一个ImageView添加进来
//		views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
//		for (int i = 0; i < infos.size(); i++) {
//			views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
//		}
//		// 将第一个ImageView添加进来
//		views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));
//
//		// 设置循环，在调用setData方法前调用
//		cycleViewPager.setCycle(true);
//
//		// 在加载数据前设置是否循环
//
//		//设置轮播
//		cycleViewPager.setWheel(true);
//
//	    // 设置轮播时间，默认5000ms
//		cycleViewPager.setTime(2000);
//		//设置圆点指示图标组居中显示，默认靠右
//		cycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(IBanner info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                Intent intent = new Intent(ActivityGoodsDetail.this, TransAvtivity.class);
                intent.putExtra(TransAvtivity.EXTRA_PHOTOS, goodDetailBean.getGoods_gallery_urls());
                intent.putExtra(TransAvtivity.EXTRA_CURRENT_ITEM, imageView.getId());
                startActivity(intent);
            }
        }
    };
    View.OnClickListener imageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//         PhotoPreviewIntent intent = new PhotoPreviewIntent( SignDynamicDetailActivity.this);
//        intent.setCurrentItem(v.getId ());
//        intent.setCurrentType (-1);
//        intent.setPhotoPaths(list4);
//        startActivity ( intent );
            Log.e("View:", v.getId() + "");
            Intent intent = new Intent(ActivityGoodsDetail.this, TransAvtivity.class);
            intent.putExtra(TransAvtivity.EXTRA_PHOTOS, goodDetailBean.getGoods_gallery_urls());
            intent.putExtra(TransAvtivity.EXTRA_CURRENT_ITEM, v.getId());
            startActivity(intent);

//            Intent intent = new Intent( SignDynamicDetailActivity.this, PicDetailActivity.class);
//            int location[] = new int[2];
//            v.getLocationOnScreen(location);
//            intent.putExtra("x", location[0]);
//            intent.putExtra("y", location[1]);
//            startActivity(intent);
//            overridePendingTransition(0, 0);
        }
    };
    RequestCompletion pddurlgenerateCompletion = new RequestCompletion() {
        @Override
        public void Success(Object value, String tag) {
            PromotionUrlInfo promotionUrlInfo = (PromotionUrlInfo) value;
            if (tag.equals("0")) {
                if(AppUtils.isInstallApp(ActivityGoodsDetail.this)){
                    String content =promotionUrlInfo.getSchema_url();
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(content));
                    startActivity(intent);
                }else{
                    Intent pdd= new Intent(ActivityGoodsDetail.this, H5PublicActivity.class);
                    pdd.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    pdd.putExtra("url",promotionUrlInfo.getUrl());
                    pdd.putExtra("title", "拼多多");
                    startActivity(pdd);
//                    Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//                    startActivity(it);
                }
            } else {
                Intent intent = new Intent(ActivityGoodsDetail.this, ShareGoodsActivity.class);
                intent.putExtra("goodDetailBean", goodDetailBean);
                intent.putExtra("promotionUrlInfo", promotionUrlInfo);
                startActivity(intent);


            }
        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
