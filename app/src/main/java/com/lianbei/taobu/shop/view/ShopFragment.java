package com.lianbei.taobu.shop.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.adapter.ChannelPagerAdapter;
import com.lianbei.taobu.shop.model.Channel;
import com.lianbei.taobu.shop.model.GoodsOptBean;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.taobu.view.GoodsUtils;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.ShareUtils;
import com.lianbei.taobu.utils.TabLayoutAddOnClickHelper;
import com.lianbei.taobu.utils.ThreadUtil;
import com.lianbei.taobu.utils.dbhelper.dao.OptDAPImpl;
import com.lianbei.taobu.views.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ShopFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tabs)
    TabLayout mTabChannel;
    @BindView(R.id.view_pager)
    ViewPager mVpContent;


    @BindView(R.id.iv_operation)
    ImageView ivAddChannel;

    @BindView(R.id.imageView_bg)
    ImageView imageView_bg;

    @BindView(R.id.other_pager_bg)
    ImageView other_pager_bg;

    @BindView(R.id.other_pager_bg2)
    gradual other_pager_bg2;
    //    @BindView(R.id.banner)
//    BGABanner banner;
    @BindView(R.id.shop_main_lin)
    LinearLayout shop_main_lin;

    @BindView(R.id.search_goods)
    LinearLayout search_goods;
    @BindView(R.id.im_secrch_icon)
    ImageView im_secrch_icon;

    @BindView(R.id.tv_sratch)
    TextView tv_sratch;

    private View SelectTabView = null;
    private int tabIndex = 0;
    private TabLayout.Tab SelectTabtablayout = null;
    private List<Channel> mSelectedChannels = new ArrayList<>();
    private List<Channel> mUnSelectedChannels = new ArrayList<>();
    private List<ShopListFragment> mChannelFragments = new ArrayList<>();
    private Gson mGson = new Gson();
    private ChannelPagerAdapter mChannelPagerAdapter;
    private String[] mChannelCodes;
    private ShopManager shopManager;
    private GoodsOptBean goodsOptBean = new GoodsOptBean();
    private List<GoodsOptBean> goodsOptBeanList = new ArrayList<>();


    private List<String> listurl = new ArrayList<>();
    // private ArrayList<Integer> colorint = new ArrayList<>();
    private List<ImageView> imageViewList;
    private ArrayList<String> stringLis = new ArrayList<>();
    private List<ImageView> new_bitmap_list = new ArrayList<>();
    private List<Bitmap> new_bitmap_list2 = new ArrayList<>();
    private static Integer[] colorint;
    private static Bitmap[] bitmapList;
    private int START_COLOR = 0xfff1f1f1;
    private int onPageSelected = 0;
    int[][] bitmapList111 ;

    @Override
    public void initBeforeView() {
        super.initBeforeView();
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void initViews() {
        //  banner.onPageScrolled();


    }


    @Override
    protected void onVisible() {
        super.onVisible();
        Log.e("---onVisible", "onVisible");
    }

    @Override
    protected void onHidden() {
        super.onHidden();
        Log.e("---onHidden", "onHidden");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mChannelFragments != null && mChannelFragments.size() > 0) {
                return;
            }
            OptDAPImpl optDAP = new OptDAPImpl(ShopFragment.this.getContext());
            List<GoodsOptBean> goodsOptBeans  =optDAP.queryOptBean();
          //List<GoodsOptBean> goodsOptBeans = ShareUtils.getDataList(ShopFragment.this.getContext(), ShareUtils.OPT_INFO, GoodsOptBean.class);

            if (goodsOptBeans != null && goodsOptBeans.size() > 0) {
                for (int i = 0; i <goodsOptBeans.size() ; i++) {
                    Log.e("dbname:" ,goodsOptBeans.get(i).getOpt_name()+"--"+goodsOptBeans.get(i).getOpt_id()+"");

                }
                goodsOptBeanList.addAll(goodsOptBeans);
                handler.sendEmptyMessage(0);
            } else {
                ShopManager.getInstance(ShopFragment.this.getContext()).opt_get(requestCompletion, "0");
            }
            listurl.add(Constant.SHOP_BANNER_URL_1);
            listurl.add(Constant.SHOP_BANNER_URL_2);
            listurl.add(Constant.SHOP_BANNER_URL_3);
            listurl.add(Constant.SHOP_BANNER_URL_4);
            shop_main_lin.post(new Runnable() {
                @Override
                public void run() {
                    if (bitmapList == null || bitmapList.length < listurl.size()) {
                        //  colorint = new Integer[listurl.size()];
                        bitmapList111 = new int[listurl.size()][];
                        bitmapList = new Bitmap[listurl.size()];
                        for (int i = 0; i < listurl.size(); i++) {
                            getBitMap(listurl.get(i));
                        }
                        //getBitMap(listurl.get(0));
                    }
                }
            });
        }
    }

    private void getBitMap(String url) {
        //stringLis.add(0,"我的");
        GlideUtils.getInstance().loadright_AngleCache(this.getContext(), url, new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                //   bitmapList.add(ImageUtils.drawableToBitmap(resource));
                ThreadUtil.runInThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < listurl.size(); i++) {
                            if (url.equals(listurl.get(i))) {
                                // colorint.add(BitmapUtil.getImageViewColor(ImageUtils.drawableToBitmap(resource)));
                                //   colorint[i] = BitmapUtil.getImageViewColor(ImageUtils.drawableToBitmap(resource));
                                Palette(ImageUtils.drawableToBitmap(resource), i);
                            }
                        }
                    }
                });
            }
        });
    }

    private void Palette(Bitmap resource, int i) {
        if (resource == null) {
            return;
        }
        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //记得判空
                if (palette == null) return;
                //palette取色不一定取得到某些特定的颜色，这里通过取多种颜色来避免取不到颜色的情况
                if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                    createLinearGradientBitmap(palette.getDarkVibrantColor(Color.TRANSPARENT), palette.getVibrantColor(Color.TRANSPARENT), i);
                } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                    createLinearGradientBitmap(palette.getDarkMutedColor(Color.TRANSPARENT), palette.getMutedColor(Color.TRANSPARENT), i);
                } else {
                    createLinearGradientBitmap(palette.getLightMutedColor(Color.TRANSPARENT), palette.getLightVibrantColor(Color.TRANSPARENT), i);
                }
            }
        });
    }

    Bitmap bgBitmap;
    Canvas mCanvas;
    Paint mPaint;
    //创建线性渐变背景色
    private void createLinearGradientBitmap(int darkColor, int color, int i) {
        int bgColors[] = new int[2];
        bgColors[0] = darkColor;
        bgColors[1] = color;
        bitmapList111[i] = bgColors;
        Log.e("bgColors[0]:",bgColors[0]+"bgColors[1]:"+ bgColors[1]);
        Bitmap bgBitmap = Bitmap.createBitmap(imageView_bg.getWidth(), imageView_bg.getHeight(), Bitmap.Config.ARGB_8888);

        if (mCanvas == null) {
            mCanvas = new Canvas();
        }
        if (mPaint == null) {
            mPaint = new Paint();
        }
        mCanvas.setBitmap(bgBitmap);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        LinearGradient gradient = new LinearGradient(0, 0, 0, bgBitmap.getHeight(), bgColors[0], bgColors[1], Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        RectF rectF = new RectF(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        mCanvas.drawRoundRect(rectF, 100, 100, mPaint); //这个用来绘制圆角的哈
        mCanvas.drawRect(rectF, mPaint);
        Log.e("createbgBitmap:", bgBitmap + "");
        bitmapList[i] = bgBitmap;
        //  bitmapList111[i] =bitmapList;
        // bitmapList = null;
        //imageView_bg.setImageBitmap(bgBitmap);
      //  handler.sendEmptyMessage(1);
    }

    @Override
    public void fetchData() {
        Log.e("fetchData", "fetchData");

    }

    @Override
    public void initData() {
//        TbDB tbDB = new TbDB(this.getContext());
//        tbDB.open();
//        tbDB.createOptData("0","衣服","1","99");

        Log.e("---otData", "initData");

//        if(goodsOptBeanList != null && goodsOptBeanList.size()>0){
//            handler.sendEmptyMessage ( 0 );
//            return;
//        }
//        List<GoodsOptBean> goodsOptBeans =   ShareUtils.getDataList(ShopFragment.this.getContext(),ShareUtils.OPT_INFO,GoodsOptBean.class);
//                if(goodsOptBeans != null && goodsOptBeans.size()>0){
//                    goodsOptBeanList.addAll(goodsOptBeans);
//                    handler.sendEmptyMessage ( 0 );
//                }else{
//                    ShopManager.getInstance(ShopFragment.this.getContext()).opt_get (requestCompletion,"0");
//                }


    }

    RequestCompletion requestCompletion = new RequestCompletion() {
        @Override
        public void Success(Object value, String tag) {
            if (value != null) {
                GoodsUtils.disposeopt((List<GoodsOptBean>) value, new GoodsUtils.myInterface() {
                    @Override
                    public void result(Object o) {
                        goodsOptBeanList.addAll(((List<GoodsOptBean>) o));
                        ThreadUtil.runInThread(new Runnable() {
                            @Override
                            public void run() {
                                OptDAPImpl optDAP = new OptDAPImpl(ShopFragment.this.getContext());
                                for (int i = 0; i <goodsOptBeanList.size() ; i++) {
                                    long rr =   optDAP.insertGoodsOpt(goodsOptBeanList.get(i));
                                    Log.e("rr：",rr+"");
                                }
                            }
                        });
                       // ShareUtils.setDataList(ShopFragment.this.getContext(), ShareUtils.OPT_INFO, goodsOptBeanList);
                        // handler.sendEmptyMessage ( 0 );
                        initChannelData();
                        initChannelFragments();
                    }
                });
                ThreadUtil.runInThread(new Runnable() {
                    @Override
                    public void run() {

                        //   goodsOptBeanList.addAll ( GoodsUtils.disposeopt ( (List<GoodsOptBean>)value ))  ;
                        // handler.sendEmptyMessage ( 0 );
                    }
                });

            }
        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                if (goodsOptBeanList != null && goodsOptBeanList.size() > 0) {
                    ThreadUtil.runInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            initChannelData();
                            initChannelFragments();
                           // dbdb();
                        }
                    });
                }

            } else if (msg.what == 1) {
                if(bitmapList111 != null){
                    if(bitmapList111[nextposition] != null && !bitmapList111[nextposition].equals("")){
                        setBackground2(bitmapList111[nextposition][0],bitmapList111[nextposition][1]);
                    }
                }

                if (bitmapList != null && bitmapList.length == new_bitmap_list.size()) {
                  //  imageView_bg.setImageBitmap(bitmapList[nextposition]);
                //    setBackground2(bitmapList111[nextposition][0],bitmapList111[nextposition][1]);
             //      setBackground(colorint[nextposition]);
//                    imageView_bg.setAlpha(1f);
//                    if (nextposition == 0 || nextposition == 1) {
//                        imageView_bg.setAlpha(0.0f);
//                    } else {
//                        imageView_bg.setAlpha(0.9f);
//                    }

                }

            }

            return false;
        }
    });

    private void setBackground(int endColor) {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(imageView_bg, "backgroundColor", START_COLOR, endColor);
        colorAnim.setDuration(500);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatCount(0);
        colorAnim.start();
        START_COLOR = endColor;
    }
    private void setBackground2(int ColorIntTop,int ColorIntBottom) {
        other_pager_bg2.setColorInt(ColorIntTop,ColorIntBottom);
        other_pager_bg2.requestLayout();
        other_pager_bg2.invalidate();
    }

    /**
     * 初始化已选频道和未选频道的数据
     */
    private void initChannelData() {
        //默认添加了全部频道
        for (int i = 0; i < goodsOptBeanList.size(); i++) {
            String title = goodsOptBeanList.get(i).getOpt_name();
            String code = goodsOptBeanList.get(i).getOpt_id();
            mSelectedChannels.add(new Channel(title, code));
            Log.e(i + "频道---:", goodsOptBeanList.get(i).getOpt_name() + "---" + goodsOptBeanList.get(i).getOpt_id() + "");
        }

//        String selectedChannelJson = PreUtils.getString( Constant.SELECTED_CHANNEL_JSON, "");
//      //  String unselectChannel = PreUtils.getString(Constant.UNSELECTED_CHANNEL_JSON, "");
//
//        if (TextUtils.isEmpty(selectedChannelJson) ) {
//            //本地没有title
//            String[] channels = getResources().getStringArray(R.array.channel);
//            String[] channelCodes = getResources().getStringArray(R.array.channel_code);
//            //默认添加了全部频道
//            for (int i = 0; i < channelCodes.length; i++) {
//                String title = channels[i];
//                String code = channelCodes[i];
//                mSelectedChannels.add(new Channel(title, code));
//            }
//            selectedChannelJson = mGson.toJson(mSelectedChannels);//将集合转换成json字符串
//            KLog.i("selectedChannelJson:" + selectedChannelJson);
//            PreUtils.putString(Constant.SELECTED_CHANNEL_JSON, selectedChannelJson);//保存到sp
//        } else {
//            //之前添加过
//            List<Channel> selectedChannel = mGson.fromJson(selectedChannelJson, new TypeToken <List<Channel>> () {
//            }.getType());
//           // List<Channel> unselectedChannel = mGson.fromJson(unselectChannel, new TypeToken<List<Channel>>() {
//           // }.getType());
//            mSelectedChannels.addAll(selectedChannel);
//          // mUnSelectedChannels.addAll(unselectedChannel);
//        }
    }


    /**
     * 初始化已选频道的fragment的集合
     */
    private void initChannelFragments() {
        //  KLog.e("initChannelFragments");
        // mChannelCodes = getResources().getStringArray(R.array.channel_code);
        for (Channel channel : mSelectedChannels) {
            ShopListFragment newsFragment = new ShopListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE, channel.channelCode);
            bundle.putBoolean(Constant.IS_RECOMMEND_LIST, channel.channelCode.equals("8569"));//是否是热门列表页面,根据判断频道号是否是推荐
            newsFragment.setArguments(bundle);
            newsFragment.setViewpageChange(viewpageChange);
            mChannelFragments.add(newsFragment);//添加到集合中
            //mChannelPagerAdapter.notifyDataSetChanged ();
        }
        setAdapter();
    }

    private void setAdapter() {
        mChannelPagerAdapter = new ChannelPagerAdapter(mChannelFragments, mSelectedChannels, getChildFragmentManager());
        mVpContent.setAdapter(mChannelPagerAdapter);
        mVpContent.setOffscreenPageLimit(5);

        // mTabChannel.setTabPaddingLeftAndRight( UIUtils.dip2Px(10), UIUtils.dip2Px(10));
        mTabChannel.setupWithViewPager(mVpContent);
        mTabChannel.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabChannel.post(new Runnable() {
            @Override
            public void run() {
                //设置最小宽度，使其可以在滑动一部分距离
                ViewGroup slidingTabStrip = (ViewGroup) mTabChannel.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + ivAddChannel.getMeasuredWidth());
            }
        });
        //隐藏指示器
        //  mTabChannel.setSelectedTabIndicatorHeight(0);
        mTabChannel.setTabIndicatorFullWidth(false);
        TabLayoutAddOnClickHelper.TopTabOnClick(this.getContext(), mSelectedChannels, mTabChannel, tabOnClickListener, onClickListener);
        mTabChannel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("onPageSelected111", tab.getPosition() + "");
                SelectTabtablayout = tab;
                tabIndex = tab.getPosition();
                SelectTabView = tab.getCustomView();
                TabLayoutAddOnClickHelper.setTopTablestate(SelectTabtablayout, ShopFragment.this.getContext(), mTabChannel);
//                ThreadUtil.runInThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TabLayoutAddOnClickHelper.setTopTablestate(SelectTabtablayout,ShopFragment.this.getContext(), mTabChannel);
//                    }
//                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.e("onPageScrolled", i + "v:" + v + "i1:" + i1);
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    other_pager_bg.setVisibility(View.GONE);
                    search_goods.setBackgroundResource(R.drawable.goods_view_search);
                    im_secrch_icon.setImageResource(R.mipmap.search_icon_white);
                    tv_sratch.setTextColor(0xABFFFFFF);
                } else {
                    other_pager_bg.setVisibility(View.VISIBLE);
                    search_goods.setBackgroundResource(R.drawable.goods_view_search_black);
                    im_secrch_icon.setImageResource(R.mipmap.search_icon);
                    tv_sratch.setTextColor(getResources().getColor(R.color.main_font1));
                }

                Log.e("onPageSelected222", i + "");

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.e("onPageScrollStateC", i + "");
            }
        });

    }

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

                    break;
            }

            return false;
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ThreadUtil.runInThread(new Runnable() {
                @Override
                public void run() {
                    TabLayoutAddOnClickHelper.setTopTablestate(SelectTabtablayout, ShopFragment.this.getContext(), mTabChannel);
                }
            });

        }
    };

    private boolean isClick = false;
    int lastposition = 0;
    int nextposition = 0;
    int position = 0;
    private static int preSelectedPage = 0;
    private static int scrollState;
    float Scrolled = 0;
    boolean lift = false;
    boolean right = false;

    ShopListFragment.ViewpageChange viewpageChange = new ShopListFragment.ViewpageChange() {
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            Scrolled = arg1;
            if (arg2 != 0) {
                if (scrollState == 1) {//手指按下
                    if (preSelectedPage == arg0) {//表示往左拉，相应的tab往右走
                        Log.i(TAG, "ux==--> 手指左滑 整体页面--> ");
                        lift = true;
                        right = false;
                    } else {
                        lift = false;
                        right = true;
                        Log.i(TAG, "ux==--> 手指向右 整体页面<--");
                    }
                } else if (scrollState == 2) {
//                    if (preSelectedPage == arg0) {//往左拉
//                        Log.i(TAG, "ux==--> 手指左滑 整体页面--> 页面向右");
//                    } else {//表示往右拉
//                        Log.i(TAG, "ux==--> 手指右滑 整体页面-->  页面向左");
//                    }
                }
                // setBannerbg();
            }

        }

        @Override
        public void onPageSelected(int arg0) {
            Log.e("onPageSelected:", arg0 + "--");
            int par = (arg0 - 1) > 0 ? arg0 - 1 : -(arg0 - 1);
            if (par < listurl.size()) { //2
                position = arg0 - 1;
                //  setBannerbg();
                if (position >= 0 && position < listurl.size()) {
                    Log.e("position:", position + "--");
                    setBannerbg();
                }
            } else {

            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (!isClick) {
                scrollState = arg0;
                preSelectedPage = position;
            }
        }

        @Override
        public void pagerViewImageView(List<ImageView> imageviewlist) {
            if (!isListEqual(new_bitmap_list, imageviewlist)) {
                new_bitmap_list.clear();
                new_bitmap_list.addAll(imageviewlist);
                new_bitmap_list.remove(0);
                new_bitmap_list.remove(new_bitmap_list.size() - 1);
            }
            // setBgBitmap();
        }

    };

    boolean isoJfoR = false;

    private void setBgBitmap() {
        if (bitmapList == null) {
            bitmapList = new Bitmap[new_bitmap_list.size()];
        } else {
            for (int i = 0; i < bitmapList.length; i++) {
                if (bitmapList[i] == null) {
                    isoJfoR = false;
                    break;
                } else {
                    isoJfoR = true;
                }
            }
        }
        if (isoJfoR) {
            return;
        }
        //  colorint = new Integer[listurl.size()]
        for (int i = 0; i < new_bitmap_list.size(); i++) {
            Bitmap image = null;
            try {
                if (new_bitmap_list.get(i) == null) {
                    break;
                }
                image = ((BitmapDrawable) new_bitmap_list.get(i).getDrawable()).getBitmap();
                if (image == null || image.equals("")) {
                    bitmapList[i] = null;
                    break;
                }
                try {
                    image.getWidth();
                } catch (Exception e) {
//			Log.v("LOG", "图片出错");
                    bitmapList[i] = null;
                    break;
                }

            } catch (Exception e) {
                bitmapList[i] = null;
                // e.printStackTrace();
                break;
            }
            Palette(image, i);
        }
    }


    public static boolean isListEqual(List l0, List l1) {
        if (l0 == l1)
            return true;
        if (l0 == null && l1 == null)
            return true;
        if (l0 == null || l1 == null)
            return false;
        if (l0.size() != l1.size())
            return false;
        if (l0.containsAll(l1) && l1.containsAll(l0)) {
            return true;
        }
        return false;
    }

    public void setBannerbg() {
        if (position != lastposition) {
            lastposition = position;
            nextposition = position;
            ThreadUtil.runInThread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            });

        } else {//
//            if(Scrolled>0.5){
//                if(lift){
//                    if(position == listurl.size()){
//                        nextposition = 0;
//                    }else{
//                        nextposition =position-1;
//                    }
//                    setBannerbg();
//
//                }else if(right){
//
//                }
//
//            }else{
//                nextposition = position;
//               // setBannerbg();
//            }
        }
    }


    @Override
    public void initListener() {

    }

    @OnClick({R.id.search_goods})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_goods:
                Intent intent = new Intent(this.getContext(), SearchGoodsActivity.class);
                startActivity(intent);
                break;
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
