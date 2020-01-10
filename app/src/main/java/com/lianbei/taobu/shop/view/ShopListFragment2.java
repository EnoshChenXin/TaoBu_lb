package com.lianbei.taobu.shop.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.model.BannerImgInfo;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.taobu.adapter.SectionsPagerAdapter;
import com.lianbei.taobu.taobu.view.GoodsUtils;
import com.lianbei.taobu.utils.TabLayoutAddOnClickHelper;
import com.lianbei.taobu.utils.ThreadUtil;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.bannerview.bean.IBanner;
import com.lianbei.taobu.views.bannerview.lib.CycleViewPager;
import com.lianbei.taobu.views.bannerview.ui.ADInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Fragmeng + Viewpager
 */

public class ShopListFragment2 extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, RequestCompletion, View.OnClickListener {

    @BindView(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;


//    @BindView(R.id.rv_news)
//    PowerfulRecyclerView mRvNews;

    @BindView(R.id.recommend_view)
    LinearLayout recommend_view;

//    @BindView(R.id.general_view)
//    LinearLayout general_view;

    @BindView(R.id.new_pesion_icon)
    LinearLayout new_pesion_icon;
    //    banner
//    @BindView(R.id.indicator)
//    BGAFixedIndicator mIndicator;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager mContentVp;
    ArrayList<Fragment> list = new ArrayList<Fragment>();
    ;

    @BindView(R.id.label_yhh)
    ImageView label_yhh;
    @BindView(R.id.labe_sybd)
    ImageView labe_sybd;
    @BindView(R.id.labe_dzg)
    ImageView labe_dzg;
    @BindView(R.id.label_yym)
    ImageView label_yym;
    public static int[] TAB_TITLES = new int[]{R.string.TAB_TEXT_COMPREHENSIVE, R.string.TAB_TEXT_COMMISSION, R.string.TAB_TEXT_PRICE, R.string.TAB_TEXT_SALES};


    private CycleViewPager cycleViewPager;
    private List<BannerImgInfo> imgBannerArray;
    private ADInfo info;
    private List<ADInfo> infos = new ArrayList<ADInfo>();

    protected BaseQuickAdapter mShopListAdapter;
    private OnFragmentInteractionListener mListener;
    private String mChannelCode = "";
    private boolean isrecommendlist;
    private boolean isSearch = false;
    private List<TopGoodsBean> goodsSearchBeanList = new ArrayList<>();
    SectionsPagerAdapter sectionsPagerAdapter;
    private String keyword = "";
    private String tagStr = ""; //排序序号/ 对饮接口参数
    private View SelectTabView = null;
    private int tabIndex = 0;
    private boolean switchtable =false; //false 没有切换
    /**
     * 是否是推荐频道tabs
     */
    private boolean isRecommendChannel;

    public ShopListFragment2() {
        // Required empty public constructor
    }

    public static ShopListFragment2 newInstance(String keyword) {
        ShopListFragment2 categoryFragment = new ShopListFragment2();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CHANNEL_KEYWORD, keyword);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_shop_list;
    }

    public void setkeyword(String keyword) {
        isrecommendlist = false;
        isSearch = true;
        this.keyword = keyword;
        fetchData();
    }

    public void cleandata() {

    }


    @Override
    public void initViews() {
        //取出传过来的值
        Bundle bundle = getArguments();
        keyword = bundle.getString(Constant.CHANNEL_KEYWORD, "");
        mChannelCode = getArguments().getString(Constant.CHANNEL_CODE, "");
        isrecommendlist = getArguments().getBoolean(Constant.IS_RECOMMEND_LIST, false);

    }

    @Override
    public void fetchData() {
        if (isrecommendlist) {
            //推荐banner 数据
            recommend_view.setVisibility(View.VISIBLE);
            // initialize();
            imgBannerArray = new ArrayList<>();
            BannerImgInfo bannerImgInfo = new BannerImgInfo();
            bannerImgInfo.setImgUrl("http://t16img.yangkeduo.com/pdd_oms/2019-10-31/d325173978ce5606b6fe3a2a5fcd97d8.jpg");
            imgBannerArray.add(bannerImgInfo);
            BannerImgInfo bannerImgInfo2 = new BannerImgInfo();
            bannerImgInfo2.setImgUrl("http://t16img.yangkeduo.com/pdd_oms/2019-10-11/7a56294ab6a221aa85727d35afeccfdf.png");
            imgBannerArray.add(bannerImgInfo2);
            initialize();
            //如果是推荐列表
            //mShopListAdapter = new ShopRecommendListAdapter (mShopList);

//            GlideUtils.getInstance ().load ( this.getContext (), APIs.MYBASESET+"label_yhh.png", label_yhh);
//            GlideUtils.getInstance ().load ( this.getContext (), APIs.MYBASESET+"labe_sybd.png", labe_sybd);
//            GlideUtils.getInstance ().load ( this.getContext (), APIs.MYBASESET+"labe_dzg.png", labe_dzg);
//            GlideUtils.getInstance ().load ( this.getContext (), APIs.MYBASESET+"label_yym.png", label_yym);
            label_yhh.setImageResource(R.mipmap.label_yhh);
            labe_sybd.setImageResource(R.mipmap.labe_sybd);
            labe_dzg.setImageResource(R.mipmap.labe_dzg);
            label_yym.setImageResource(R.mipmap.label_yym);
        }
        intViwePager();

    }

    @Override
    protected void initDataObserver() {
        super.initDataObserver();
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(ShopListFragment2.this.getActivity(), true));

    }

    private void intViwePager() {
        if (list != null && list.size() == 0) {
            for (int channel : TAB_TITLES) {
                SearchGoodsListFragment seatchfoodsFragment = new SearchGoodsListFragment();
                seatchfoodsFragment.setRequestSuccessCallback(requestSuccessCallback);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.CHANNEL_CODE, mChannelCode);
                bundle.putString(Constant.CHANNEL_TYPE, GoodsUtils.GoodsSortType(getString(channel)));
                bundle.putString(Constant.CHANNEL_KEYWORD, keyword);
                seatchfoodsFragment.setArguments(bundle);
//           Bundle bundle = new Bundle();
//           bundle.putString(Constant.CHANNEL_CODE,TAB_TITLES[0]);
                list.add(seatchfoodsFragment);
                Log.e(channel + "------intViwePager---:" + list.size(), mChannelCode + "");
            }
        }
        tabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        if (list == null || list.size() == 0)
            return;

        //sectionsPagerAdapter.setFragments(list);
        mContentVp.setAdapter(sectionsPagerAdapter);
        mContentVp.setOffscreenPageLimit(1);
        // tv_item_one.setBackgroundColor( Color.RED);//被选中就为红色
        //  mIndicator.initData(0, mContentVp);
        tabs.setupWithViewPager(mContentVp);
        tabs.setTabIndicatorFullWidth(false);
        mContentVp.setCurrentItem(0);  //初始化显示第一个页面
        // tabs.getTabAt(0).select();
        // View tabView = LayoutInflater.from ( this.getContext () ).inflate (R.layout.text,null  );
        //  tabs.getTabAt ( 1 ).setCustomView (LayoutInflater.from ( this.getContext () ).inflate (R.layout.text,null  ) );
        //  tabs.getTabAt ( 2 ).setCustomView (LayoutInflater.from ( this.getContext () ).inflate (R.layout.text,null  ));
        //  tabs.getTabAt ( 3 ).setCustomView (LayoutInflater.from ( this.getContext () ).inflate (R.layout.text,null  ));
        TabLayoutAddOnClickHelper.AddOnClick(this.getContext(), tabs, tabOnClickListener, onClickListener);
    }

    @Override
    public void initBeforeView() {
        super.initBeforeView();
        sectionsPagerAdapter = new SectionsPagerAdapter(this.getContext(), getChildFragmentManager(), list, TAB_TITLES);
    }

    @Override
    public void initData() {
        Log.e("initData", mChannelCode + "");
    }

    @Override
    public void initListener() {
        mRefreshLayout.setDelegate(this);
        //   mShopListAdapter = new BaseGoodListAdatper (mChannelCode,BaseGoodListAdatper.COMMON_GOOD,goodsSearchBeanList);
        //   mRvNews.setAdapter(mShopListAdapter);
//        mShopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent (ShopListFragment.this.getContext (),ActivityGoodsDetail.class  );
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.putExtra ( "goods_id",goodsSearchBeanList.get ( position ).getGoods_id ());
//                startActivity ( intent );
//            }
//        });
//        mShopListAdapter.setEnableLoadMore(true);
//        mShopListAdapter.setOnLoadMoreListener(this, mRvNews);
        if (isrecommendlist) {
            //如果是视频列表，监听滚动
        }
        mContentVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.e("onPageScrolled:", i+"");
                switch (i) {

//                    case 0:
//                        ((SearchGoodsListFragment) list.get ( 0 )).GoodsSoft ( "0" );
//                        break;
//                    case 1:
//                        ((SearchGoodsListFragment) list.get ( 1 )).GoodsSoft ( "2" );
//                        break;
//                    case 2:
//                        ((SearchGoodsListFragment) list.get ( 2 )).GoodsSoft ( "4" );
//                        break;
//                    case 3:
//                        ((SearchGoodsListFragment) list.get ( 3 )).GoodsSoft ( "6" );
//                        break;
                }
            }

            @Override
            public void onPageSelected(int i) {
                Log.e("onPageSelected:", i+"");
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.e("onPageScroChanged:", i+"");
            }
        });


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabIndex = tab.getPosition();
                SelectTabView = tab.getCustomView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e("onTabUnselected:", tab.getPosition() + "");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("onTabReselected:", tab.getPosition() + "");

            }
        });
    }

    View newview = null;
    View oleview = null;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TabLayoutAddOnClickHelper.seTTablestate(ShopListFragment2.this.getContext(), tabs);
            ThreadUtil.runInUIThread(new Runnable() {
                @Override
                public void run() {
                    newview = v;
                    if(newview != oleview){
                        switchtable =true;
                        oleview =newview;
                    }else{
                        switchtable =false;
                    }
                    String Softparam = getGoodSoft(tabIndex);
                    switch (tabIndex) {
                        case 0:
                            ((SearchGoodsListFragment) list.get ( 0 )).GoodsSoft ( "0" );
                            break;
                        case 1:
                            ((SearchGoodsListFragment) list.get(1)).GoodsSoft(Softparam);
                            break;
                        case 2:
                            ((SearchGoodsListFragment) list.get(2)).GoodsSoft(Softparam);
                            break;
                        case 3:
                            ((SearchGoodsListFragment) list.get(3)).GoodsSoft(Softparam);
                            break;
                    }
                }
            },500);
        }
    };
    private View tabView = null;
    /* 拦截tablayout点击事件 */
    View.OnTouchListener tabOnClickListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.e("ACTION_DOWN", "ACTION_DOWN");

                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e("ACTION_MOVE", "ACTION_MOVE");
                    break;
                case MotionEvent.ACTION_UP:
                    int pos = (int) view.getTag();
                    TextView tab = view.findViewById(R.id.tab_tv);
                    ImageButton imageButton = view.findViewById(R.id.rb_tab);
                    Drawable drawableNormal = ContextCompat.getDrawable(getContext(), R.drawable.nomal);
                    //  setSelectorDrawable(imageButton,drawableNormal,drawableNormal);
                    if (pos == 0) {
                        mContentVp.setCurrentItem(0);
                        tab.setText(getResources().getString(TAB_TITLES[0]));
                        imageButton.setVisibility(View.GONE);
                        Log.e("tabpos777:", view.getTag() + "");
// 拦截第一个item点击添加自定义逻辑
                        return false;
                    }
                    if (pos == 1) {
                        mContentVp.setCurrentItem(1);
                        tab.setText(getResources().getString(TAB_TITLES[1]));
                        Log.e("tabpos888:", view.getTag() + "");
// 拦截第二个item点击

                        return false;
                    }
                    if (pos == 2) {
                        mContentVp.setCurrentItem(2);
                        tab.setText(getResources().getString(TAB_TITLES[2]));
                        Log.e("tabpos888:", view.getTag() + "");
// 拦截第二个item点击
                        return false;
                    }
                    if (pos == 3) {
                        mContentVp.setCurrentItem(3);
                        tab.setText(getResources().getString(TAB_TITLES[3]));
                        Log.e("tabpos888:", view.getTag() + "");
// 拦截第二个item点击
                        return false;
                    }
                    break;
            }

            return false;
        }
    };


    private String getGoodSoft(int post) {
        tagStr = ((SearchGoodsListFragment) list.get(post)).getSoftNum();
        if(switchtable){
            if (Validator.isStrNotEmpty(tagStr)) {
                if (tagStr.equals("1")) {
                    return "1";
                } else if (tagStr.equals("2")) {
                    return "1";
                } else if (tagStr.equals("3")) {
                    return "3";
                } else if (tagStr.equals("4")) {
                    return "3";
                } else if (tagStr.equals("5")) {
                    return "6";
                } else if (tagStr.equals("6")) {
                    return "6";
                } else {
                    return "";
                }
            }
        }else{
            if (Validator.isStrNotEmpty(tagStr)) {
                if (tagStr.equals("1")) {
                    return "2";
                } else if (tagStr.equals("2")) {
                    return "1";
                } else if (tagStr.equals("3")) {
                    return "4";
                } else if (tagStr.equals("4")) {
                    return "3";
                } else if (tagStr.equals("5")) {
                    return "6";
                } else if (tagStr.equals("6")) {
                    return "5";
                } else {
                    return "";
                }

            }
        }

        return "";
    }


    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        int CurrentItem = mContentVp.getCurrentItem();
        ((SearchGoodsListFragment) list.get(CurrentItem)).onBGARefreshLayoutBeginRefreshing(refreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        int CurrentItem = mContentVp.getCurrentItem();
        return ((SearchGoodsListFragment) list.get(CurrentItem)).onBGARefreshLayoutBeginLoadingMore(refreshLayout);
    }

    public void endRefreshing() {
        mRefreshLayout.endRefreshing();
    }

    public void endLoadingMore() {
        mRefreshLayout.endLoadingMore();
    }

    @Override
    public void Success(Object value, String tag) {
        try {
            if (value != null) {
                goodsSearchBeanList.addAll((List<TopGoodsBean>) value);
                mShopListAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Fail(Object value) {

    }

    @Override
    public void Error(Object... values) {

    }

    @OnClick({R.id.new_pesion_icon, R.id.labe_dzg, R.id.label_yhh, R.id.labe_sybd, R.id.label_yym})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_pesion_icon:
                ShopManager.getInstance(this.getContext()).cats_get(requestCompletion, "0");
                break;
            case R.id.labe_dzg:
                Intent intent = new Intent(ShopListFragment2.this.getContext(), ActivityGoodsList.class);
                intent.putExtra("pagertype", Constant.JIUBAOYOU);
                startActivity(intent);
                break;
            case R.id.label_yhh:
                Intent intentyhh = new Intent(ShopListFragment2.this.getContext(), ActivityGoodsList.class);
                intentyhh.putExtra("pagertype", Constant.HAOHUO);
                startActivity(intentyhh);
                break;
            case R.id.labe_sybd:
                Intent intentsybd = new Intent(ShopListFragment2.this.getContext(), ActivityGoodsList.class);
                intentsybd.putExtra("pagertype", Constant.YONGJIN);
                startActivity(intentsybd);
                break;
            case R.id.label_yym:
                Intent intentActivityGoods = new Intent(this.getActivity(), ActivityGoodsMain.class);
                startActivity(intentActivityGoods);
                break;

        }
    }

    RequestCompletion requestCompletion = new RequestCompletion() {
        @Override
        public void Success(Object value, String tag) {

        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @SuppressLint("NewApi")
    private void initialize() {
        cycleViewPager = (CycleViewPager) getChildFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        for (int i = 0; i < imgBannerArray.size(); i++) {
            info = new ADInfo();
            info.url = imgBannerArray.get(i).getImgUrl();
            info.photoDesc = "图片-->" + i;
            info.type = "act";
            infos.add(info);
        }

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

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(IBanner info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(ShopListFragment2.this.getContext(),
                        "position-->" + info.getPhotoDesc(), Toast.LENGTH_SHORT).show();
            }
        }
    };
    SearchGoodsListFragment.RequestSuccessCallback requestSuccessCallback = new SearchGoodsListFragment.RequestSuccessCallback() {
        @Override
        public void success(Object tag) {
            String a[] = tag.toString().split(":");
            tagStr = a[1];
            if (SelectTabView != null) {
                ImageButton imageButton = SelectTabView.findViewById(R.id.rb_tab);

//                Drawable backgroundDrawable = getResources().getDrawable(R.drawable.selector_special_tab_check);
                 int mNormalId = R.drawable.nomal;
                 int mSelectIdtop = R.drawable.nomal_top;
                 int mSelectIdbottom = R.drawable.nomal_bottom;


                Drawable drawableNormal = ContextCompat.getDrawable(getContext(), mNormalId);
                Drawable drawableSoftTop = ContextCompat.getDrawable(getContext(), mSelectIdtop);
                Drawable drawableSoftbottom = ContextCompat.getDrawable(getContext(), mSelectIdbottom);
//
//                StateListDrawable sld = (StateListDrawable) backgroundDrawable;
//                Drawable.ConstantState cs = sld.getConstantState();

                //SelectorUtils.changeViewColor(ShopListFragment.this.getContext(),sld,new int[]{R.color.main_color,R.color.yellow});



                if (tagStr.equals("1") || tagStr.equals("3") || tagStr.equals("5")) {
                   // imageButton.setBackground(null);
                    imageButton.setBackground(drawableSoftTop);
                   // setSelectorDrawable(imageButton,drawableNormal,drawableSoftTop);
                } else {
                  //  setSelectorDrawable(imageButton,drawableNormal,drawableSoftbottom);
                   // imageButton.setBackground(null);
                    imageButton.setBackground(drawableSoftbottom);
                }
                //其他接口可调用
                if (needRequestSuccessCallback != null) {
                    needRequestSuccessCallback.success(tag);
                }
            }

        }
    };

    public void setSelectorDrawable(ImageButton imageButton,Drawable drawableNormal, Drawable drawableSelect){

        StateListDrawable drawable =new StateListDrawable();

        //选中
        drawable.addState(new int[]{android.R.attr.state_checked},drawableSelect);

        //未选中
        drawable.addState(new int[]{-android.R.attr.state_checked},drawableSelect);

       /// imageButton.setBackground();
       imageButton.setBackground(drawable);


    }

    public void setNeedRequestSuccessCallback(NeedRequestSuccessCallback Callback) {
        needRequestSuccessCallback = Callback;

    }

    public NeedRequestSuccessCallback needRequestSuccessCallback = null;

    public interface NeedRequestSuccessCallback {
        void success(Object str);
    }

}
