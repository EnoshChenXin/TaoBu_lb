package com.lianbei.taobu.views.bannerview.lib;

import android.annotation.SuppressLint;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.views.bannerview.bean.IBanner;
import com.lianbei.taobu.views.bannerview.utils.ViewFactory;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;


/**
 * 实现可循环，可轮播的viewpager
 */
@SuppressLint("NewApi")
public class CycleViewPager extends BaseFragment implements ViewPager.OnPageChangeListener {

    private List<ImageView> imageViews = new ArrayList<ImageView>();
    private List<ImageView> imageViews2 = new ArrayList<ImageView>();
    private ImageView[] indicators;


    private BaseViewPager parentViewPager;
    private ViewPagerAdapter adapter;
    private CycleViewPagerHandler handler;
    private int time = 5000; // 默认轮播时间
    private int mDuration = 1500; // 设置轮播速度
    private int currentPosition = 0; // 轮播当前位置
    private boolean isScrolling = false; // 滚动框是否滚动着
    /**
     * // 是否循环,默认为true
     */
    private boolean isCycle = true;
    /**
     * // 是否轮播 ,默认值为true
     */
    private boolean isWheel = true;
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private int WHEEL = 100; // 转动
    private int WHEEL_WAIT = 101; // 等待
    private ImageCycleViewListener mImageCycleViewListener;
    private List<? extends IBanner> imagePaths;
    @BindView ( R.id.viewPager )
     BaseViewPager viewPager;
    @BindView ( R.id.layout_viewpager_indicator )
    LinearLayout indicatorLayout;// 指示器
    @BindView (R.id.layout_viewager_content)
     FrameLayout viewPagerFragmentLayout;


    @Override
    public int getContentViewId() {
        return  R.layout.view_cycle_viewpager_contet;
    }
    public  List<ImageView> getImageViews(){
        return imageViews;
    }

    @Override
    public void initViews() {
        handler = new CycleViewPagerHandler (getActivity()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == WHEEL && imageViews.size() != 0) {
                    if (!isScrolling) {
                        int max = imageViews.size() + 1;
                        int position = (currentPosition + 1) % imageViews.size();
                        viewPager.setCurrentItem(position, true);
                        if (position == max) { // 最后一页时回到第一页
                            viewPager.setCurrentItem(1, false);
                        }
                    }

                    releaseTime = System.currentTimeMillis();
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, time);
                    return;
                }
                if (msg.what == WHEEL_WAIT && imageViews.size() != 0) {
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, time);
                }
            }
        };
       // configImageLoader();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }


    public void setData(List<? extends IBanner> list, ImageCycleViewListener listener) {
        setData(list, listener, 0);
    }

    /**
     * 初始化viewpager
     *
     * @param showPosition 默认显示位置
     */
    public void setData(List<? extends IBanner> list, ImageCycleViewListener listener, int showPosition) {
        mImageCycleViewListener = listener;
        imagePaths = list;

        if (imageViews == null) {
            imageViews = new ArrayList<>();
        }
        this.imageViews.clear();

        if (imageViews2 == null) {
            imageViews2 = new ArrayList<>();
        }
        this.imageViews2.clear();
        if (list != null) {
            if (list.size() > 1) {// banner图不止一张
                // 将最后一个ImageView添加进来
                imageViews.add( ViewFactory.getImageView(getActivity(), list.get (list.size ()-1).getUrl (),list.get (list.size ()-1).getType (),list.get (list.size ()-1).getView ()));
                for (int i = 0; i < list.size(); i++) {
                    imageViews.add(ViewFactory.getImageView(getActivity(), list.get(i).getUrl(),list.get(i).getType (), list.get(i).getView ()));
                   // imageViews2.add(ViewFactory.getImageView(getActivity(), list.get(i).getUrl(),list.get(i).getType (), list.get(i).getView ()));
                }
                // 将第一个ImageView添加进来
                imageViews.add(ViewFactory.getImageView(getActivity(), list.get(0).getUrl(), list.get(0).getType (), list.get(0).getView ()));
            } else {// banner图有一张或者没有，这种情况下是不需要轮播的
                for (int i = 0; i < list.size(); i++) {
                    imageViews.add(ViewFactory.getImageView(getActivity(), list.get(i).getUrl(), list.get(i).getType (), list.get(i).getView ()));
                }
            }
        }

        if (imageViews.size() == 0) {// 数据为空的时候，不显示banner图的位置
            viewPagerFragmentLayout.setVisibility(View.GONE);
            return;
        } else if (imageViews.size() == 1) { //当数据为1的时候，不显示小圆点
            indicatorLayout.setVisibility(View.GONE);
            setWheel(false);
        } else {//　当数据的个数大于1的时候，才显示小圆点，并执行轮播操作
            setWheel(true);
            indicatorLayout.setVisibility(View.VISIBLE);
        }
        int ivSize = imageViews.size();

        // 设置指示器
        indicators = new ImageView[ivSize];
        if (isCycle)
            indicators = new ImageView[ivSize - 2];
        indicatorLayout.removeAllViews();
        for (int i = 0; i < indicators.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(
                    R.layout.view_cycle_viewpager_indicator, null);
            indicators[i] = (ImageView) view.findViewById(R.id.image_indicator);
            indicatorLayout.addView(view);
        }

        adapter = new ViewPagerAdapter();

        // 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向
        setIndicator(0);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(this);
        viewPager.setAdapter(adapter);
        if (showPosition < 0 || showPosition >= imageViews.size())
            showPosition = 0;
        if (isCycle) {
            showPosition = showPosition + 1;
        }
        setViewPagerScrollSpeed();
        viewPager.setPageMargin(50);
        viewPager.setPageMarginDrawable(R.color.color_transparent);
       // viewPager.setScrollBarFadeDuration(4000);
        viewPager.setCurrentItem(showPosition);

    }


    /**
     * 设置ViewPager切换速度
     */
    private void setViewPagerScrollSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
          //  mScroller.setmDuration(2* 1000);
          //  mScroller.d
          //  scroller.setmDuration(mDuration);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

    /**
     * 设置指示器居中，默认指示器在右方
     * ----setData之后调用
     */
    public void setIndicatorCenter() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicatorLayout.setLayoutParams(params);
    }

    /**
     * 是否循环，默认不开启，开启前，请将views的最前面与最后面各加入一个视图，用于循环....
     * 在调用setData方法前调用
     *
     * @param isCycle 是否循环
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * 是否处于循环状态
     *
     * @return
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * 设置是否轮播，默认轮播,轮播一定是循环的，当list的size<2的时候，不轮播，不循环，
     * 在setData数据之后调用
     * @param isWheel
     */
    public void setWheel(boolean isWheel) {
        this.isWheel = isWheel;
        isCycle = isWheel;

        if (imageViews.size() < 2) {
            this.isWheel = false;
            isCycle = false;
        }

        if (isWheel) {
            handler.postDelayed(runnable, time);
        }
    }

    /**
     * 是否处于轮播状态
     *
     * @return
     */
    public boolean isWheel() {
        return isWheel;
    }

    final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (getActivity() != null && !getActivity().isFinishing()
                    && isWheel) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > time - 500) {
                    handler.sendEmptyMessage(WHEEL);
                    mDuration =500;
                    setViewPagerScrollSpeed();
                } else {
                   mDuration =1500;
                    setViewPagerScrollSpeed();
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    /**
     * 释放指示器高度，可能由于之前指示器被限制了高度，此处释放
     */
    public void releaseHeight() {
        getView().getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        refreshData();
    }

    /**
     * 设置轮播暂停时间，即没多少秒切换到下一张视图.默认5000ms，
     *---setData之后调用
     * @param time 毫秒为单位
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    /**
     * 隐藏CycleViewPager
     */
    public void hide() {
        viewPagerFragmentLayout.setVisibility(View.GONE);
    }

    /**
     * 返回内置的viewpager
     *
     * @return viewPager
     */
    public BaseViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void fetchData() {

    }


    /**
     * 页面适配器 返回对应的view
     *
     * @author lym
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            View view = imageViews.get(position);
            if (mImageCycleViewListener != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(imagePaths.size()>1){
                            mImageCycleViewListener.onImageClick(imagePaths.get(currentPosition - 1), currentPosition, v);
                        }else{
                            mImageCycleViewListener.onImageClick(imagePaths.get(currentPosition), currentPosition, v);
                        }

                    }
                });
            }
            if(view instanceof RelativeLayout){
                if (view.getParent ( ) != null) {
                    ((ViewGroup)view.getParent ( )).removeView ( view );
                }
            }
             container.addView(view);
            return view;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (arg0 == 1) { // viewPager在滚动
            isScrolling = true;
            return;
        } else if (arg0 == 0) { // viewPager滚动结束
            if (parentViewPager != null)
                parentViewPager.setScrollable(true);

            releaseTime = System.currentTimeMillis();

            viewPager.setCurrentItem(currentPosition, false);

        }
        isScrolling = false;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        int max = imageViews.size() - 1;
        int position = arg0;
        currentPosition = arg0;
        if (isCycle) {
            if (arg0 == 0) {
                currentPosition = max - 1;
            } else if (arg0 == max) {
                currentPosition = 1;
            }
            position = currentPosition - 1;
        }
        setIndicator(position);
    }

    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable
     */
    public void setScrollable(boolean enable) {
        viewPager.setScrollable(enable);
    }

    /**
     * 返回当前位置,循环时需要注意返回的position包含之前在views最前方与最后方加入的视图，即当前页面试图在views集合的位置
     *
     * @return
     */
    public int getCurrentPostion() {
        return currentPosition;
    }

    /**
     * 设置指示器
     *
     * @param selectedPosition 默认指示器位置
     */
    private void setIndicator(int selectedPosition) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i]
                    .setBackgroundResource(R.drawable.icon_point);
        }
        if (indicators.length > selectedPosition)
            indicators[selectedPosition]
                    .setBackgroundResource(R.drawable.icon_point_pre);
    }

    /**
     * 如果当前页面嵌套在另一个viewPager中，为了在进行滚动时阻断父ViewPager滚动，可以 阻止父ViewPager滑动事件
     * 父ViewPager需要实现ParentViewPager中的setScrollable方法
     */
    public void disableParentViewPagerTouchEvent(BaseViewPager parentViewPager) {
        if (parentViewPager != null)
            parentViewPager.setScrollable(false);
    }


    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public static interface ImageCycleViewListener {

        /**
         * 单击图片事件
         *
         * @param banner
         * @param postion
         * @param imageView
         */
        public void onImageClick(IBanner banner, int postion, View imageView);
    }


    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
      //  GlideUtils.getInstance ().load ( getContext (), );
        // 初始化ImageLoader
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator ()).tasksProcessingOrder( QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

}