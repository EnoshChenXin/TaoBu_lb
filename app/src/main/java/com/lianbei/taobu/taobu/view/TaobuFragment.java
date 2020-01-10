package com.lianbei.taobu.taobu.view;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.ShareInvitation.ShareStepsActivity;
import com.lianbei.taobu.circle.view.CircleFragment;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodListAdatper;
import com.lianbei.taobu.shop.model.BannerImgInfo;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.shop.view.ActivityGoodsDetail;
import com.lianbei.taobu.shop.view.ActivityGoodsMain;
import com.lianbei.taobu.shop.view.HelpGoodsDetail;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.taobu.adapter.GridViewAdapter;
import com.lianbei.taobu.taobu.model.CommodityBean;
import com.lianbei.taobu.taobu.view.viewutils.LinearGradientUtil;
import com.lianbei.taobu.taobu.view.viewutils.MastGridView;
import com.lianbei.taobu.utils.ThreadUtil;
import com.lianbei.taobu.utils.TimeUtils;
import com.lianbei.taobu.views.CircleBarView;
import com.lianbei.taobu.views.bannerview.bean.IBanner;
import com.lianbei.taobu.views.bannerview.lib.CycleViewPager;
import com.lianbei.taobu.views.bannerview.ui.ADInfo;
import com.lianbei.taobu.views.h5.H5PublicActivity;

import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class TaobuFragment extends BaseFragment implements View.OnClickListener,RequestCompletion, BGARefreshLayout.BGARefreshLayoutDelegate  {
    @BindView(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.btn_restart)
    Button btnRestart;
    @BindView ( R.id.circle_view)
    CircleBarView circleBarView;

    @BindView ( R.id.text_progress)
    TextView textProgress;

    private int num;
    private float progressNum;
    /*@BindView(R.id.sc)
    public SmartScrollView sc;*/
    @BindView ( R.id.tv_title_bar )
    TextView tv_title_bar;
    @BindView ( R.id.upload_btn )
    LinearLayout upload_btn;
    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    @BindView(R.id.mastgridview)
    MastGridView mastgridview;

    @BindView ( R.id.daka_btn )//打卡
    ImageView daka_btn;

    @BindView ( R.id.walk_btn )//走呗
    ImageView walk_btn;
    @BindView ( R.id.huanwu_btn ) //糖豆换物
    ImageView huanwu_btn;

    @BindView ( R.id.more_activity )
    TextView more_activity;

    @BindView ( R.id.group_game_lin ) //群组
    LinearLayout groupGame;
    @BindView ( R.id.btn_title_bar_right ) //群组
    ImageButton btn_title_bar_right;

    private String mChannelCode = "0";
    protected BaseQuickAdapter commodityAdapter;
    private List <CommodityBean> commodityBeans = new ArrayList <> ( );
    private ShopManager shopManager = null;
    private CycleViewPager cycleViewPager;
    private List <BannerImgInfo> imgBannerArray;
    private ADInfo info;
    private List<ADInfo> infos = new ArrayList <ADInfo> ();
    private GridViewAdapter adapter;
    private List<TopGoodsBean> helplist = new ArrayList <> (  );
    private List<TopGoodsBean> toplist = new ArrayList <> (  );
    ArrayList<String> arrayList;
    private int offset = 0;
    private int limit = 30;
    @Override
    public int getContentViewId() {
        return R.layout.fragment_taobu;
    }

    @Override
    public void initViews() {
        //注册事件
        arrayList = new ArrayList();
        arrayList.add("54680882186");
        arrayList.add("7440289");
        arrayList.add("2338511");
        arrayList.add("50226994697");
        arrayList.add("3870377813");

        tv_title_bar.setText ( TimeUtils.getTime ()+"" );
        circleBarView.setOnAnimationListener(new CircleBarView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float updateNum, float maxNum) {
                //
                DecimalFormat decimalFormat=new DecimalFormat("0");
               // String s = decimalFormat.format(interpolatedTime * updateNum / maxNum * 100)+"%";
                String s = decimalFormat.format(interpolatedTime * updateNum);
                return s;
            }
            @Override
            public void howTiChangeProgressColor(Paint paint, float interpolatedTime, float updateNum, float maxNum) {
                LinearGradientUtil linearGradientUtil = new LinearGradientUtil ( Color.YELLOW,Color.RED);
                paint.setColor(linearGradientUtil.getColor(interpolatedTime));
            }
        });
        num = 0;
       // setProgressNumInThread();
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgressNumInThread();
            }
        });
        setListView();
       // erewe();
        //msThread.start ();
        ThreadUtil.runInUIThread(new Runnable() {
            @Override
            public void run() {
                try {
                    circleBarView.setMaxNum ( 6000 );
                    progressNum = 4586;
                    circleBarView.setTextView(textProgress);
                    circleBarView.setProgressNum(progressNum,20000);
                    //  Thread.sleep(2000);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        Handler handler = new Handler();
//        handler.post(new Runnable() {
//                         @Override
//                         public void run() {
//                             try {
//                                 //延迟两秒更新
//                                 Thread.sleep ( 2000 );
//                             } catch (InterruptedException e) {
//                                 e.printStackTrace ( );
//                             }
//                             circleBarView.setMaxNum ( 6000 );
//                             progressNum = 4586;
//                             circleBarView.setTextView(textProgress);
//                             circleBarView.setProgressNum(progressNum,20000);
//                         }
//
//        });
       new CHen().execute (  );
    }
    @Override
    public void initData() {
        ShopManager.getInstance(this.getContext()).top_goods ( helpequest,1,10,"");
        //糖豆换物
        //  TaoBuManager.getInstance().GetCommodityContent (  this,"");
        ShopManager.getInstance(this.getContext()).basic_info_get(this, arrayList, "0");

        //请求....
        //推荐banner 数据
        // initialize();
        imgBannerArray= new ArrayList <> (  );
        BannerImgInfo bannerImgInfo =new BannerImgInfo ();
        bannerImgInfo.setUrl ( "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577276257965&di=9ae951a1a3d9d779eff81abe50657cf5&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F0060lm7Tly1foa0lnqnk5j30ku07nq3g.jpg" );
        bannerImgInfo.setUrlType ( "2" );
        imgBannerArray.add ( bannerImgInfo );
        BannerImgInfo bannerImgInfo2 =new BannerImgInfo ();
        bannerImgInfo2.setUrl ( "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2936949376,4133554344&fm=26&gp=0.jpg" );
        bannerImgInfo2.setClickUrl ("http://nx.lbeizxw.com/bdjy2/1011.html");
        bannerImgInfo2.setUrlType ( "0" );
        bannerImgInfo2.setClickUrlTitle ( "春季新款 地至7折" );
        imgBannerArray.add ( bannerImgInfo2 );
        initialize();
    }
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        offset = 0;
        ShopManager.getInstance(this.getContext()).basic_info_get(this, arrayList, "0");
        ShopManager.getInstance(this.getContext()).top_goods ( helpequest,0,10,"");

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        ThreadUtil.runInUIThread(new Runnable() {
            @Override
            public void run() {
                refreshLayout.endLoadingMore();

            }
        },2000);

        return true;
    }


    class CHen extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute ( s );

        }
    }

    Thread  msThread;
    private static final int JUMP_TO_MAIN = 1000;
    private void erewe(){
          Handler mHandler = new Handler ( new Handler.Callback ( ) {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == JUMP_TO_MAIN){
                    circleBarView.setMaxNum ( 6000 );
                    progressNum = 4586;
                    circleBarView.setTextView(textProgress);
                    circleBarView.setProgressNum(progressNum,2000);
                }
                return false;
            }
        } );

        msThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Message msg = new Message();
                msg.what = JUMP_TO_MAIN;
                mHandler.sendMessage(msg);
            }
        };
    }


    private void setListView(){
        mRvNews.setLayoutManager ( new GridLayoutManager ( mActivity, 1 ) );
        mRvNews.setNestedScrollingEnabled ( false );//解决滑动卡顿
    }

    private void setProgressNumInThread(){
        new Thread ( new Runnable ( ) {
            @Override
            public void run() {
                try {
                    circleBarView.setProgressNum(progressNum,2000);
                    for (int i=1;i<=100;i++){
                        num = i;
                        //handler.obtainMessage(0).sendToTarget();
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ).start ();


    }

    @Override
    public void fetchData() {

    }

    @Override
    protected void initDataObserver() {
        super.initDataObserver();
//
        mRefreshLayout.setRefreshViewHolder ( new BGANormalRefreshViewHolder ( this.getContext(), true ) );
    }



    @Override
    public void initListener() {
        mRefreshLayout.setDelegate ( this );
        commodityAdapter = new BaseGoodListAdatper(mChannelCode, BaseGoodListAdatper.HUAN_GOOD, toplist);
        mRvNews.setAdapter(commodityAdapter);
        commodityAdapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(TaobuFragment.this.getContext(), HelpGoodsDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("goods_id", toplist.get(i).getGoods_id());
                startActivity(intent);
            }
        } );

        upload_btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                circleBarView.setProgressNum(progressNum,2000);
                setProgressNumInThread();

//                String appId = APIs.APP_ID; // 填应用AppId
//                IWXAPI api = WXAPIFactory.createWXAPI(TaobuFragment.this.getContext (), appId);
//
//                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
//                req.userName = "gh_b2d20c4a9dd7"; // 填小程序原始id
//                req.path = "";                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
//                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
//                api.sendReq(req);
            }
        } );
        //限时免费抢
        mastgridview.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                Intent intent = new Intent (TaobuFragment.this.getContext (), ActivityGoodsDetail.class  );
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra ( "goods_id",toplist.get ( position ).getGoods_id ());
                startActivity ( intent );
            }
        } );
    }
    @OnClick({R.id.daka_btn,R.id.walk_btn,R.id.huanwu_btn,R.id.group_game_lin,R.id.btn_title_bar_right,R.id.bim_lin,R.id.upload_btn,R.id.more_activity})
    @Override
    public void onClick(View view) {
        switch (view.getId ( )) {
            case  R.id.daka_btn:
                Intent intent = new Intent (  this.getActivity (), SignActivity.class);
                startActivity (intent );
                break;
            case  R.id.walk_btn:
                Intent intents = new Intent (  this.getActivity (),WalkActivity.class);
                startActivity (intents );
                break;

            case  R.id.huanwu_btn:
                Intent intentss = new Intent (  this.getActivity (),HuanWuActivity.class);
                startActivity (intentss );
                break;
            case  R.id.group_game_lin:
                CircleFragment.TabTtNum =1;
                Intent intentread = new Intent ( );
                intentread.setAction ( "com.action.circle" );
                this.getContext ().sendBroadcast ( intentread );
                break;
            case  R.id.bim_lin:
                Intent intentbim = new Intent (  this.getActivity (),BMICalcAct.class);
                startActivity (intentbim );
                break;
            case  R.id.btn_title_bar_right:
                Intent intentSteps = new Intent (  this.getActivity (), ShareStepsActivity.class);
                startActivity (intentSteps );
                break;
            case  R.id.upload_btn:
               // circleBarView.setProgressNum(progressNum,2000);
               // setProgressNumInThread();
                circleBarView.setProgressNum(progressNum,2000);
                //progressNum =3333;
                break;
            case R.id.more_activity:
                Intent intentActivityGoods = new Intent (  this.getActivity (), ActivityGoodsMain.class);
                intentActivityGoods.putExtra("param1", Constant.ZHULI);
                startActivity (intentActivityGoods );
                break;
        }
    }
    @Override
    public void Success(Object value, String tag) {
        if (value != null) {
            ThreadUtil.runInUIThread(new Runnable() {
                @Override
                public void run() {
                    if (tag.equals("0")) {
                        toplist.clear();
                        toplist.addAll((List<TopGoodsBean>) value);
                        mRefreshLayout.endRefreshing ( );
                    } else {
                        toplist.addAll((List<TopGoodsBean>) value);
                         mRefreshLayout.endLoadingMore ( );
                    }
                    commodityAdapter.notifyDataSetChanged();
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
    @SuppressLint("NewApi")
    private void initialize() {
        cycleViewPager = (CycleViewPager)getChildFragmentManager ()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        for (int i = 0; i <imgBannerArray.size (); i++) {
            info = new ADInfo ();
            info.url=imgBannerArray.get ( i ).getUrl ();
            info.photoDesc="图片-->" + i;
            info.type ="banner";
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
                if(info.getUrlType ().equals ( "1" )){
                    Intent intents = new Intent ( TaobuFragment.this.getContext (),WalkActivity.class);
                    startActivity (intents );
                }else if(info.getUrlType ().equals ( "0" )){
                    Intent intent= new Intent(TaobuFragment.this.getContext (), H5PublicActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url", info.getClickUrl ()+"");
                    intent.putExtra("title", info.getClickUrlTitle ()+"");
                    startActivity(intent);
                }else if(info.getUrlType ().equals ( "2" )){
                    Intent intentActivityGoods = new Intent ( TaobuFragment.this.getActivity (), ActivityGoodsMain.class);
                    intentActivityGoods.putExtra("param1", Constant.MIANDAN);
                    startActivity (intentActivityGoods );
                }
            }

        }
    };
   RequestCompletion helpequest = new RequestCompletion ( ) {
       @Override
       public void Success(Object value, String tag) {
           try {
               if(value != null){
                   new Thread ( new Runnable ( ) {
                       @Override
                       public void run() {
                           (TaobuFragment.this.getActivity ()).runOnUiThread ( new Runnable ( ) {
                               @Override
                               public void run() {
                                   List<TopGoodsBean> topGoodsBean = (List<TopGoodsBean>)value;
                                   if(topGoodsBean != null && topGoodsBean != null )
                                       helplist.addAll ( topGoodsBean);

                                   DisplayMetrics dm = new DisplayMetrics();
                                   TaobuFragment.this.getActivity ().getWindowManager().getDefaultDisplay().getMetrics(dm);
                                   float density = dm.density;

                                   int gridviewWidth = (int) (toplist.size () * (140 + 4) * density);
                                   int itemWidth = (int) (120 * density);
                                   adapter = new GridViewAdapter(TaobuFragment.this.getContext (),helplist);
                                   mastgridview.setDataSize ( toplist.size (), gridviewWidth,itemWidth);
                                   mastgridview.setAdapter(adapter);
                               }
                           } );
                       }
                   } ).start ();

               }
           } catch (Exception e) {
               e.printStackTrace ( );
           }
       }

       @Override
       public void Fail(Object value) {

       }

       @Override
       public void Error(Object... values) {

       }
   };

}
