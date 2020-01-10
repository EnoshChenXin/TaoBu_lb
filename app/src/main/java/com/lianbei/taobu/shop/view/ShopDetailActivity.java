package com.lianbei.taobu.shop.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.shop.model.BannerImgInfo;
import com.lianbei.taobu.taobu.view.TaobuFragment;
import com.lianbei.taobu.taobu.view.WalkActivity;
import com.lianbei.taobu.views.bannerview.bean.IBanner;
import com.lianbei.taobu.views.bannerview.lib.CycleViewPager;
import com.lianbei.taobu.views.bannerview.ui.ADInfo;
import com.lianbei.taobu.views.h5.H5PublicActivity;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailActivity extends BaseActivity {
    private CycleViewPager cycleViewPager;
    private List <BannerImgInfo> imgBannerArray;
    private ADInfo info;
    private List<ADInfo> infos = new ArrayList <ADInfo> ();
    @Override
    public int getContentViewId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
    }

    @Override
    public void initData() {
        //请求....
        //推荐banner 数据
        // initialize();
        imgBannerArray= new ArrayList <> (  );
        BannerImgInfo bannerImgInfo =new BannerImgInfo ();
        bannerImgInfo.setUrl ( "http://nx.lbeizxw.com/image/mainbanner.png" );
        bannerImgInfo.setUrlType ( "1" );
        imgBannerArray.add ( bannerImgInfo );
        BannerImgInfo bannerImgInfo2 =new BannerImgInfo ();
        bannerImgInfo2.setUrl ( "http://nx.lbeizxw.com/image/banner.png" );
        bannerImgInfo2.setClickUrl ("http://nx.lbeizxw.com/bdjy2/1011.html");
        bannerImgInfo2.setUrlType ( "0" );
        bannerImgInfo2.setClickUrlTitle ( "春季新款 地至7折" );
        imgBannerArray.add ( bannerImgInfo2 );
        initialize();
    }

    @SuppressLint("NewApi")
    private void initialize() {
        cycleViewPager = (CycleViewPager)getSupportFragmentManager ()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        for (int i = 0; i <imgBannerArray.size (); i++) {
            info = new ADInfo ();
            info.url=imgBannerArray.get ( i ).getUrl ();
            info.photoDesc="图片-->" + i;
            info.type ="act";
            info.urlType =imgBannerArray.get ( i ).getUrlType ();
            info.clickUrlTitle =imgBannerArray.get ( i ).getClickUrlTitle ();
            info.clickUrl = imgBannerArray.get ( i ).getClickUrl ();
            infos.add(info);
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
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener () {

        @Override
        public void onImageClick(IBanner info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                if(info.getUrlType ().equals ( "1" )){
                    Intent intents = new Intent ( ShopDetailActivity.this, WalkActivity.class);
                    startActivity (intents );
                }else if(info.getUrlType ().equals ( "0" )){
                    Intent intent= new Intent(ShopDetailActivity.this, H5PublicActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url", info.getClickUrl ()+"");
                    intent.putExtra("title", info.getClickUrlTitle ()+"");
                    startActivity(intent);

                }
            }

        }
    };
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
