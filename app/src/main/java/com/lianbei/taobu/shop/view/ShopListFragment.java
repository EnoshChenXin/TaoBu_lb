package com.lianbei.taobu.shop.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.adapter.CatsAdapter;
import com.lianbei.taobu.shop.model.BannerImgInfo;
import com.lianbei.taobu.shop.model.GoodsOptBean;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.taobu.view.viewutils.MastGridView;
import com.lianbei.taobu.utils.TabLayoutAddOnClickHelper;
import com.lianbei.taobu.utils.ThreadUtil;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.bannerview.bean.IBanner;
import com.lianbei.taobu.views.bannerview.lib.CycleViewPager;
import com.lianbei.taobu.views.bannerview.ui.ADInfo;
import com.lianbei.taobu.views.h5.H5PublicActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickyNavLayout;


public class ShopListFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, RequestCompletion, View.OnClickListener {

    @BindView(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;

    @BindView(R.id.bgastickynavlayout)
    BGAStickyNavLayout bgastickynavlayout;
//    @BindView(R.id.frlg_search_shop_list)
//    FrameLayout frlg_search_shop_list;


//    @BindView(R.id.rv_news)
//    PowerfulRecyclerView mRvNews;

    @BindView(R.id.recommend_view)
    LinearLayout recommend_view;

    @BindView(R.id.cats_view)
    LinearLayout cats_view;

//    @BindView(R.id.general_view)
//    LinearLayout general_view;

    @BindView(R.id.new_pesion_icon)
    LinearLayout new_pesion_icon;
    //    banner
//    @BindView(R.id.indicator)
//    BGAFixedIndicator mIndicator;

    @BindView(R.id.tabs)
    TabLayout tabs;
    //  @BindView(R.id.view_pager)
    //  ViewPager mContentVp;
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

    @BindView(R.id.mastgridview)
    MastGridView mastgridview;
    CatsAdapter catsAdapter;

    private List<GoodsOptBean> goodsOptBeanList = new ArrayList<>();
    private GoodsOptBean goodsOptBean;
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
    private String keyword = "";
    private String tagStr = ""; //排序序号/ 对饮接口参数
    private String opt_id;
    private View SelectTabView = null;
    private int tabIndex = 0;
    private int SoftpNum = 0; //当前页面排序
    private boolean switchtable = false; //false 没有切换
    SearchGoodsListFragment searchGoodsListFragment;
    // private CatsBean catsBean = new CatsBean();
    private volatile static ShopListFragment categoryFragment = null;
    /**
     * 是否是推荐频道tabs
     */
    private boolean isRecommendChannel;

    private FragmentManager childfragmentmanager;

    public ShopListFragment() {
        // Required empty public constructor
    }

    public static ShopListFragment newInstance(String keyword) {
        if (categoryFragment == null) {
            synchronized (ShopListFragment.class) {
                if (categoryFragment == null) {
                    categoryFragment = new ShopListFragment();
                }
            }
        }
        // ShopListFragment categoryFragment = new ShopListFragment();
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
        //  fetchData();
    }

    @Override
    public void initViews() {
        //取出传过来的值
        Bundle bundle = getArguments();
        keyword = bundle.getString(Constant.CHANNEL_KEYWORD, "");
        mChannelCode = getArguments().getString(Constant.CHANNEL_CODE, "");
        isrecommendlist = getArguments().getBoolean(Constant.IS_RECOMMEND_LIST, false);
    }

    public void seatchData(String keyword) {
        this.keyword = keyword;
        ThreadUtil.runInUIThread(new Runnable() {
            @Override
            public void run() {
                intViwePager();
            }
        }, 500);
    }


    @Override
    public void fetchData() {
        ThreadUtil.runInUIThread(new Runnable() {
            @Override
            public void run() {
                if (isrecommendlist) {
                    //推荐banner 数据
                    recommend_view.setVisibility(View.VISIBLE);
                    // initialize();
                    imgBannerArray = new ArrayList<>();
                    BannerImgInfo bannerImgInfo = new BannerImgInfo();
                    bannerImgInfo.setImgUrl(Constant.SHOP_BANNER_URL_1);
                    imgBannerArray.add(bannerImgInfo);
                    BannerImgInfo bannerImgInfo2 = new BannerImgInfo();
                    bannerImgInfo2.setImgUrl(Constant.SHOP_BANNER_URL_2);
                    imgBannerArray.add(bannerImgInfo2);
                    BannerImgInfo bannerImgInfo3 = new BannerImgInfo();
                    bannerImgInfo3.setImgUrl(Constant.SHOP_BANNER_URL_3);
                    imgBannerArray.add(bannerImgInfo3);
                    BannerImgInfo bannerImgInfo4 = new BannerImgInfo();
                    bannerImgInfo4.setImgUrl(Constant.SHOP_BANNER_URL_4);
                    imgBannerArray.add(bannerImgInfo4);
                    ThreadUtil.runInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            initialize();
                        }
                    });
                    //如果是推荐列表
                    //mShopListAdapter = new ShopRecommendListAdapter (mShopList);
                    label_yhh.setImageResource(R.mipmap.label_yhh);
                    labe_sybd.setImageResource(R.mipmap.labe_sybd);
                    labe_dzg.setImageResource(R.mipmap.labe_dzg);
                    label_yym.setImageResource(R.mipmap.label_yym);
                } else {
                    if (!Validator.isStrNotEmpty(keyword)) {
                        cats_view.setVisibility(View.VISIBLE);
                        ShopManager.getInstance(ShopListFragment.this.getContext()).opt_get(requestCompletion, mChannelCode);//11687
                    }

                }
                intViwePager();
            }
        }, 500);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        DisplayMetrics dm = new DisplayMetrics();
                        ShopListFragment.this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                        float density = dm.density;

                        int gridviewWidth = (int) (goodsOptBeanList.size() * (100 + 4) * density);
                        int itemWidth = (int) (80 * density);
                        catsAdapter = new CatsAdapter(ShopListFragment.this.getContext(),
                                goodsOptBeanList);
                        mastgridview.setDataSize(goodsOptBeanList.size(), gridviewWidth, itemWidth);
                        mastgridview.setAdapter(catsAdapter);
                    }
                }, 0);

            }


            return false;
        }
    });

    @Override
    protected void initDataObserver() {
        super.initDataObserver();
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(ShopListFragment.this.getActivity(), true));

    }

    private void intViwePager() {
        if (tabs == null || tabs.getTabCount() == 0) {
            for (int channel : TAB_TITLES) {
                tabs.addTab(tabs.newTab());

            }
            tabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
            tabs.setTabIndicatorFullWidth(false);
            TabLayoutAddOnClickHelper.AddOnClick(this.getContext(), tabs, tabOnClickListener, onClickListener);
        }
        searchGoodsListFragment = SearchGoodsListFragment.getInstance();
        //获取Fragmen管理器
        childfragmentmanager = getChildFragmentManager();
        searchGoodsListFragment = (SearchGoodsListFragment) getChildFragmentManager()
                .findFragmentById(R.id.frag_SearchGoodsFag);
        searchGoodsListFragment.setRequestSuccessCallback(requestSuccessCallback);
        searchGoodsListFragment.setDatas(keyword, mChannelCode, "", "0");
    }

    @Override
    public void initBeforeView() {
        super.initBeforeView();
    }

    @Override
    public void initData() {
        Log.e("initData", mChannelCode + "");
    }

    @Override
    public void initListener() {
        mRefreshLayout.setDelegate(this);
        if (isrecommendlist) {
            //如果是视频列表，监听滚动
        }
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

        mastgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (searchGoodsListFragment != null) {
                    goodsOptBean = goodsOptBeanList.get(i);
                    opt_id = goodsOptBean.getOpt_id();
                    for (int j = 0; j < goodsOptBeanList.size(); j++) {
                        goodsOptBeanList.get(j).setSelect(false);
                    }
                    goodsOptBeanList.get(i).setSelect(true);
                    catsAdapter.notifyDataSetChanged();
                    TabLayoutAddOnClickHelper.seTTablestate(ShopListFragment.this.getContext(), tabs);
                    tabs.getTabAt(0).select();
                    searchGoodsListFragment.setDatas(keyword, opt_id, "", "0");
                }
            }
        });
    }

    View newview = null;
    View oleview = null;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TabLayoutAddOnClickHelper.seTTablestate(ShopListFragment.this.getContext(), tabs);
            ThreadUtil.runInUIThread(new Runnable() {
                @Override
                public void run() {
                    newview = v;
                    if (newview != oleview) {
                        switchtable = true;
                        oleview = newview;
                    } else {
                        switchtable = false;
                    }
                    getGoodSoft(tabIndex);
                    // bgastickynavlayout.scrollToBottom();
                    if (tabIndex == 0) {
                        searchGoodsListFragment.setDatas(keyword, mChannelCode, "", "0");
                    } else {
                        searchGoodsListFragment.setDatas(keyword, mChannelCode, "", SoftpNum + "");
                    }
                }
            }, 300);
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
                    if (pos == 0) {
                        //    mContentVp.setCurrentItem(0);
                        tab.setText(getResources().getString(TAB_TITLES[0]));
                        imageButton.setVisibility(View.GONE);
// 拦截第一个item点击添加自定义逻辑
                        return false;
                    }
                    if (pos == 1) {
                        //   mContentVp.setCurrentItem(1);
                        tab.setText(getResources().getString(TAB_TITLES[1]));
                        Log.e("tabpos888:", view.getTag() + "");
// 拦截第二个item点击

                        return false;
                    }
                    if (pos == 2) {
                        //    mContentVp.setCurrentItem(2);
                        tab.setText(getResources().getString(TAB_TITLES[2]));
                        Log.e("tabpos888:", view.getTag() + "");
// 拦截第二个item点击
                        return false;
                    }
                    if (pos == 3) {
                        //    mContentVp.setCurrentItem(3);
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


    private void getGoodSoft(int post) {
        switch (post) {
            case 0:
                SoftpNum = 0;
                break;
            case 1:
                if (SoftpNum != 13 && SoftpNum != 14) {
                    SoftpNum = 13;
                } else {
                    SoftpNum = SoftpNum == 13 ? 14 : 13;
                }
                break;
            case 2:
                if (SoftpNum != 9 && SoftpNum != 10) {
                    SoftpNum = 9;
                } else {
                    SoftpNum = SoftpNum == 9 ? 10 : 9;
                }
                break;
            case 3:
                if (SoftpNum != 5 && SoftpNum != 6) {
                    SoftpNum = 6;
                } else {
                    SoftpNum = SoftpNum == 5 ? 6 : 5;
                }
                break;
        }

    }


    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (goodsOptBeanList != null && goodsOptBean != null) {
            for (int j = 0; j < goodsOptBeanList.size(); j++) {
                goodsOptBeanList.get(j).setSelect(false);
            }
            catsAdapter.notifyDataSetChanged();
            searchGoodsListFragment.setKeyword("", mChannelCode, "0");
            keyword = "";
            TabLayoutAddOnClickHelper.seTTablestate(ShopListFragment.this.getContext(), tabs);
            tabs.getTabAt(0).select();
            goodsOptBean = null;
        }
        searchGoodsListFragment.onBGARefreshLayoutBeginRefreshing(refreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return searchGoodsListFragment.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
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

    @OnClick({R.id.new_pesion_icon, R.id.labe_dzg, R.id.label_yhh, R.id.labe_sybd, R.id.label_yym, R.id.youhuijuan_icon, R.id.zhuti_icon, R.id.baokuan_icon})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_pesion_icon:
                // ShopManager.getInstance (this.getContext()).getGoodsSearch (keyword, mChannelCode,"11687" ,1 + "", 50 + "", "", "",false,false,requestCompletion, "-1:" );
                //  ShopManager.getInstance(this.getContext()).cats_get(requestCompletion, "0");
                ShopManager.getInstance(this.getContext()).opt_get(requestCompletion, "14");//11687
                //ShopManager.getInstance(this.getContext()).get_cats_url("0",requestCompletion,"0");
                break;
            case R.id.labe_dzg:
                Intent intent = new Intent(ShopListFragment.this.getContext(), ActivityGoodsList.class);
                intent.putExtra("pagertype", Constant.JIUBAOYOU);
                startActivity(intent);
                break;
            case R.id.label_yhh:
                Intent intentyhh = new Intent(ShopListFragment.this.getContext(), ActivityGoodsList.class);
                intentyhh.putExtra("pagertype", Constant.HAOHUO);
                startActivity(intentyhh);
                break;
            case R.id.labe_sybd:
                Intent intentsybd = new Intent(ShopListFragment.this.getContext(), ActivityGoodsList.class);
                intentsybd.putExtra("pagertype", Constant.YONGJIN);
                startActivity(intentsybd);
                break;
            case R.id.label_yym:
                Intent intentActivityGoods = new Intent(this.getActivity(), ActivityGoodsMain.class);
                intentActivityGoods.putExtra("param1", Constant.ZHULI);
                startActivity(intentActivityGoods);
                break;
            case R.id.baokuan_icon:
                Intent intentbaokuan = new Intent(ShopListFragment.this.getContext(), ActivityGoodsList.class);
                intentbaokuan.putExtra("pagertype", Constant.BAOKUAN);
                startActivity(intentbaokuan);
                break;
            case R.id.zhuti_icon:
                Intent intentTheme = new Intent(ShopListFragment.this.getContext(), ThemeGoodsActivity.class);
                startActivity(intentTheme);
                break;
            case R.id.youhuijuan_icon:

                break;

        }
    }


    RequestCompletion requestCompletion = new RequestCompletion() {
        @Override
        public void Success(Object value, String tag) {
            if (value != null) {
                goodsOptBeanList.addAll(((List<GoodsOptBean>) value));
                handler.sendEmptyMessage(0);


//                GoodsUtils.disposeopt((List<GoodsOptBean>) value, new GoodsUtils.myInterface() {
//                    @Override
//                    public void result(Object o) {
//                        goodsOptBeanList.addAll(((List<GoodsOptBean>) o));
//                        ThreadUtil.runInThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                OptDAPImpl optDAP = new OptDAPImpl(ShopFragment.this.getContext());
//                                for (int i = 0; i <goodsOptBeanList.size() ; i++) {
//                                    long rr =   optDAP.insertGoodsOpt(goodsOptBeanList.get(i));
//                                    Log.e("rr：",rr+"");
//                                }
//                            }
//                        });
//                        // ShareUtils.setDataList(ShopFragment.this.getContext(), ShareUtils.OPT_INFO, goodsOptBeanList);
//                        // handler.sendEmptyMessage ( 0 );
//                    }
//                });
//                ThreadUtil.runInThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        //   goodsOptBeanList.addAll ( GoodsUtils.disposeopt ( (List<GoodsOptBean>)value ))  ;
//                        // handler.sendEmptyMessage ( 0 );
//                    }
//                });

            }
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
            info.type = "banner2";
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
        cycleViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (viewpageChange != null) {
                    viewpageChange.onPageScrolled(i, v, i);
                    viewpageChange.pagerViewImageView(cycleViewPager.getImageViews());
                }
            }

            @Override
            public void onPageSelected(int i) {
                if (viewpageChange != null) {
                    viewpageChange.onPageSelected(i);
                    viewpageChange.pagerViewImageView(cycleViewPager.getImageViews());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (viewpageChange != null) {
                    viewpageChange.onPageScrollStateChanged(i);
                    viewpageChange.pagerViewImageView(cycleViewPager.getImageViews());
                }
            }
        });
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(IBanner info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                Intent intentTheme = new Intent(ShopListFragment.this.getContext(), ThemeGoodsActivity.class);
                startActivity(intentTheme);
//                position = position - 1;
//                Toast.makeText(ShopListFragment.this.getContext(),
//                        "position-->" + info.getPhotoDesc(), Toast.LENGTH_SHORT).show();
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
                if (tagStr.equals("13") || tagStr.equals("9") || tagStr.equals("5")) {
                    // imageButton.setBackground(null);
                    imageButton.setBackground(drawableSoftTop);
                    // setSelectorDrawable(imageButton,drawableNormal,drawableSoftTop);
                } else {
                    //  setSelectorDrawable(imageButton,drawableNormal,drawableSoftbottom);
                    // imageButton.setBackground(null);
                    imageButton.setBackground(drawableSoftbottom);
                }
                //其他接口可调用

            }
            if (needRequestSuccessCallback != null) {
                needRequestSuccessCallback.success(tag);
            }
        }
    };

    public void setSelectorDrawable(ImageButton imageButton, Drawable drawableNormal, Drawable drawableSelect) {

        StateListDrawable drawable = new StateListDrawable();

        //选中
        drawable.addState(new int[]{android.R.attr.state_checked}, drawableSelect);

        //未选中
        drawable.addState(new int[]{-android.R.attr.state_checked}, drawableSelect);

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


    public ViewpageChange viewpageChange = null;

    public interface ViewpageChange {
        void onPageScrolled(int i, float v, int i1);

        void onPageSelected(int i);

        void onPageScrollStateChanged(int i);

        void pagerViewImageView(List<ImageView> imageviewlist);
    }

    public void setViewpageChange(ViewpageChange Callback) {
        viewpageChange = Callback;

    }

}
