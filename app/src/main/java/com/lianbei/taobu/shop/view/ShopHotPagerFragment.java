package com.lianbei.taobu.shop.view;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.shop.model.BannerImgInfo;
import com.lianbei.taobu.views.bannerview.bean.IBanner;
import com.lianbei.taobu.views.bannerview.lib.CycleViewPager;
import com.lianbei.taobu.views.bannerview.ui.ADInfo;

import java.util.ArrayList;
import java.util.List;

public class ShopHotPagerFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CycleViewPager cycleViewPager;
    private List <BannerImgInfo> imgBannerArray;
    private ADInfo info;
    private List<ADInfo> infos = new ArrayList <ADInfo> ();
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ShopHotPagerFragment() {
        // Required empty public constructor
    }

    public static ShopHotPagerFragment newInstance(String param1, String param2) {
        ShopHotPagerFragment fragment = new ShopHotPagerFragment ( );
        Bundle args = new Bundle ( );
        args.putString ( ARG_PARAM1, param1 );
        args.putString ( ARG_PARAM2, param2 );
        fragment.setArguments ( args );
        return fragment;
    }
    @Override
    public int getContentViewId() {
        return R.layout.fragment_shop_hot;
    }

    @Override
    public void initData() {
        initialize();
    }
    @SuppressLint("NewApi")
    private void initialize() {
        cycleViewPager = (CycleViewPager)getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        for (int i = 0; i <imgBannerArray.size (); i++) {
            info = new ADInfo();
            info.url=imgBannerArray.get (i).getImgUrl ();
            info.photoDesc="图片-->" + i;
            info.type ="act";
            infos.add(info);
        }

        /**
         for (int i = 0; i <baiduAdDataList.size () ; i++) {
         NativeResponse mNrAd =baiduAdDataList.get ( i ).getNativeResponse () ;
         if(mNrAd.getMaterialType() == NativeResponse.MaterialType.HTML){
         webView =  mNrAd.getWebView();
         info = new ADInfo();
         info.content="广告-->" ;
         info.view = webView;
         info.type ="adv";
         infos.add(info);
         }
         }
         **/

        cycleViewPager.setData(infos, mAdCycleViewListener);
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(4000);
//		cycleViewPager.setWheel(true);
        //设置圆点指示图标组居中显示，默认靠右
//		cycleViewPager.setIndicatorCenter();

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
                Toast.makeText(ShopHotPagerFragment.this.getContext (),
                        "position-->" + info.getPhotoDesc(), Toast.LENGTH_SHORT).show();
            }

        }

    };
    @Override
    public void onDetach() {
        super.onDetach ( );
        mListener = null;
    }



    @Override
    public void initViews() {

    }



    @Override
    public void initListener() {

    }

    @Override
    public void fetchData() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
