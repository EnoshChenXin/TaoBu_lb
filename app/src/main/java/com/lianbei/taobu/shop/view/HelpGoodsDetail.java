package com.lianbei.taobu.shop.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.EventMessage;
import com.lianbei.taobu.base.MessageEvent;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.model.AddressBean;
import com.lianbei.taobu.shop.model.BannerImgInfo;
import com.lianbei.taobu.shop.model.GoodDetailBean;
import com.lianbei.taobu.shop.model.PromotionUrlInfo;
import com.lianbei.taobu.shop.view.pop.OrderSelectPopView;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.taobu.view.viewutils.TransAvtivity;
import com.lianbei.taobu.utils.AmountUtils;
import com.lianbei.taobu.utils.AppUtils;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.StatusBarUtil;
import com.lianbei.taobu.views.PriceView;
import com.lianbei.taobu.views.bannerview.bean.IBanner;
import com.lianbei.taobu.views.bannerview.lib.CycleViewPager;
import com.lianbei.taobu.views.bannerview.ui.ADInfo;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpGoodsDetail extends BaseActivity implements View.OnClickListener {
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

    @BindView(R.id.more_goods)
    TextView more_goods; //
    @BindView(R.id.sales_tip1)
    TextView sales_tip1; //
    @BindView(R.id.goods_name)
    TextView goods_name; //
    @BindView(R.id.goods_desc)
    TextView goods_desc; //
    @BindView(R.id.coupon_remain_quantity) //优惠券剩余数量
            TextView tv_coupon_remain_quantity; //


    @BindView(R.id.image_list)
    LinearLayout image_list; //

    @BindView(R.id.rl_share)
    RelativeLayout rl_share; //

    @BindView(R.id.promotion_btn)
    Button promotion_btn; //
    List<String> goods_ids = null;
    OrderSelectPopView orderSelectPopView;
    private long coupon_remain_quantity;//优惠券剩余数量
    private long coupon_total_quantity;//优惠券总数量
    private long coupon_discount;//优惠券面额
    private long coupon_start_time; // 优惠券生效时间，UNIX时间戳

    private long coupon_end_time;  // 优惠券失效时间，UNIX时间戳

    @Override
    public int getContentViewId() {
        return R.layout.help_goods_detail;
    }

    @Override
    public void initBeforeView() {
        //StatusBarUtil.setTranslucentStatus ( this );
        StatusBarUtil.setStatusBarMode(this, true, R.color.transparent);
        super.initBeforeView();

    }

    @Override
    protected void onStart() {
        super.onStart();
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

                sales_tip1.setText(goodDetailBean.getSales_tip() + "人");
                goods_name.setText(goodDetailBean.getGoods_name());
                goods_desc.setText(goodDetailBean.getGoods_desc());
                tv_coupon_remain_quantity.setText(goodDetailBean.getCoupon_remain_quantity() + "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.back, R.id.promotion_btn, R.id.more_goods})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                EventBus.getDefault().post(new MessageEvent(""));
                break;
            case R.id.promotion_btn://购买
                payorder();
                break;
            case R.id.more_goods://购买
                finish();
                break;
        }
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
                info.type = "act";
                info.urlType = imgBannerArray.get(i).getUrlType();
                info.clickUrlTitle = imgBannerArray.get(i).getClickUrlTitle();
                info.clickUrl = imgBannerArray.get(i).getClickUrl();
                infos.add(info);

                //详情start
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
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

    private void payorder() {
        orderSelectPopView = new OrderSelectPopView(this, 0);
        orderSelectPopView.setData(goodDetailBean);
        orderSelectPopView.showSharePop(rl_share);
        orderSelectPopView.addOnClick(o);
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(IBanner info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                Intent intent = new Intent(HelpGoodsDetail.this, TransAvtivity.class);
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
            Intent intent = new Intent(HelpGoodsDetail.this, TransAvtivity.class);
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
    //彈窗提交訂單
    OrderSelectPopView.onClickLintener o = new OrderSelectPopView.onClickLintener() {
        @Override
        public void commit() {
            Intent intent = new Intent(HelpGoodsDetail.this, HelpGoodsDetailProgress.class);
            intent.putExtra("goodDetailBean", goodDetailBean);
            startActivity(intent);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 77:
                    AddressBean addressBean= (AddressBean)data.getSerializableExtra("addressBeans");
                    if (orderSelectPopView != null) {
                        orderSelectPopView.setAddress(addressBean);
                    }
                    break;
            }
        }
    }


    RequestCompletion pddurlgenerateCompletion = new RequestCompletion() {
        @Override
        public void Success(Object value, String tag) {
            PromotionUrlInfo promotionUrlInfo = (PromotionUrlInfo) value;
            String url = "";
            if (AppUtils.isInstallApp(HelpGoodsDetail.this)) {
                url = promotionUrlInfo.getMobile_url();
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(it);
            } else {
                url = promotionUrlInfo.getUrl();
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                startActivity(it);
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
