package com.lianbei.taobu.shop.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodListAdatper;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SearchGoodsListFragment2 extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, RequestCompletion {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ShopManager shopManager = null;
    private String mChannelCode ="";
    private String channel_type = "0";
    private String keyword ="";
    @BindView(R.id.m_RvNews)
    PowerfulRecyclerView mRvNews;
    private ArrayList <TopGoodsBean> goodsSearchBeanList = new ArrayList <TopGoodsBean> ( );
    protected BaseGoodListAdatper mShopListAdapter;
    private int page = 1;
    private int pageSize = 20;

    private volatile static SearchGoodsListFragment2 singleton = null;

    public static SearchGoodsListFragment2 getInstance()   {

        if (singleton== null)  {
            synchronized (SearchGoodsListFragment2.class) {
                if (singleton== null)  {
                    singleton= new SearchGoodsListFragment2();
                }
            }
        }
        return singleton;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_search_goods_list;

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {
       // mChannelCode = getArguments ( ).getString ( Constant.CHANNEL_CODE );
      //  channel_type = getArguments ( ).getString ( Constant.CHANNEL_TYPE );
      // keyword = getArguments ( ).getString ( Constant.CHANNEL_KEYWORD );
    }

//    Intent intent = new Intent (ActivityGoodsList.this,ActivityGoodsDetail.class  );
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.putExtra ( "goods_id",toplist.get ( position ).getGoods_id ());
//    startActivity ( intent );

    @Override
    public void fetchData() {
      //  mChannelCode = getArguments ( ).getString ( Constant.CHANNEL_CODE );
      //  channel_type = getArguments ( ).getString ( Constant.CHANNEL_TYPE );
      //  keyword = getArguments ( ).getString ( Constant.CHANNEL_KEYWORD );
        if(channel_type.equals("0")){
            Log.e ( "initDatagoodsSeat:" + goodsSearchBeanList.size ( ), mChannelCode + "==" + mShopListAdapter.getItemCount ( ) );
            //  if (mShopListAdapter != null && mShopListAdapter.getData ( ).size ( ) == 0) {
       //     ShopManager.getInstance (mActivity).getGoodsSearch (keyword,mChannelCode, page + "", pageSize + "", channel_type, "",false,false,requestCompletion, "-1:"+channel_type  );
            //      Log.e ( "initData请求", mChannelCode + "--" + goodsSearchBeanList.size ( ) );
            //  }
            newinitListener();
        }
//
    }
    public void setData(String mChannelCode,String channel_type){

    }
    public void setDatas(String mChannelCode,String channel_type){
        this.mChannelCode = mChannelCode;
        this.channel_type = channel_type;
        ShopManager.getInstance (mActivity).SearchGoods (keyword, mChannelCode, "",page + "", pageSize + "", channel_type, "",false,false,this, "0:"+channel_type );
        Log.e ( "initData请求", mChannelCode + "--" + goodsSearchBeanList.size ( ) );
    }

    @Override
    protected void initDataObserver() {
        super.initDataObserver ( );
       // mChannelCode = getArguments ( ).getString ( Constant.CHANNEL_CODE );
//       // channel_type = getArguments ( ).getString ( Constant.CHANNEL_TYPE );
//        Log.e ( "initDatagoodsSeat:" + goodsSearchBeanList.size ( ), mChannelCode + "==" + mShopListAdapter.getItemCount ( ) );
//        if (mShopListAdapter != null && mShopListAdapter.getData ( ).size ( ) == 0) {
//            ShopManager.getInstance ( this.getContext ( ) ).getGoodsSearch ( mChannelCode, page + "", pageSize + "", channel_type, this, "0" );
//            Log.e ( "initData请求", mChannelCode + "--" + goodsSearchBeanList.size ( ) );
//        }
    }

    public void GoodsSoft(String type) {
        channel_type = type;
//        Log.e ( "initDatagoodsSeat:" + goodsSearchBeanList.size ( ), mChannelCode + "==" + mShopListAdapter.getItemCount ( ) );
        //  if (mShopListAdapter != null && mShopListAdapter.getData ( ).size ( ) == 0) {
        ShopManager.getInstance (mActivity).SearchGoods (keyword,mChannelCode, "",page + "", pageSize + "", channel_type, "",false,false,requestCompletion, "-1:"+channel_type);
        //      Log.e ( "initData请求", mChannelCode + "--" + goodsSearchBeanList.size ( ) );
        //  }
        newinitListener();
    }
    public String getSoftNum(){
       return channel_type;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        Context m = mActivity;
        ShopManager.getInstance ( mActivity).SearchGoods (keyword, mChannelCode, "",page + "", pageSize + "", channel_type, "",false,false,requestCompletion, "0:"+channel_type );
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (mShopListAdapter != null) {
            page++;
            Context m = SearchGoodsListFragment2.this.getActivity();
            ShopManager.getInstance (mActivity).SearchGoods (keyword, mChannelCode, "",page + "", pageSize + "", channel_type,"",false,false, requestCompletion, "1:"+channel_type );
        }
        return true;
    }
    public void  newinitListener(){

    }

    @Override
    public void initListener() {
        mRvNews.setLayoutManager ( new GridLayoutManager( this.getContext ( ), 1 ) );
        mShopListAdapter = new BaseGoodListAdatper ( mChannelCode, BaseGoodListAdatper.COMMON_GOOD, goodsSearchBeanList );
        mRvNews.setAdapter ( mShopListAdapter );
        mShopListAdapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent ( SearchGoodsListFragment2.this.getContext ( ), ActivityGoodsDetail.class );
                intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                intent.putExtra ( "goods_id", goodsSearchBeanList.get ( i ).getGoods_id ( ) );
                startActivity ( intent );
            }
        } );
    }
    RequestCompletion requestCompletion = new RequestCompletion() {
        @Override
        public void Success(Object value, String tag) {
            try {
                if (value != null) {
                    if(requestSuccess != null){
                        requestSuccess.success(tag);
                    }
                    ThreadUtil.runInUIThread ( new Runnable ( ) {
                        @Override
                        public void run() {
                            String a[] = tag.split(":");
                            String tagStr = a[0];
                            if (tagStr.equals ( "0" )) {
                                if (goodsSearchBeanList != null) {
                                    goodsSearchBeanList.clear ( );
                                    goodsSearchBeanList.addAll ( (List <TopGoodsBean>) value );
                                    ((ShopListFragment) (SearchGoodsListFragment2.this.getParentFragment ( ))).endRefreshing ( );
                                }
                            } else if (tagStr.equals ( "1" )) {
                                goodsSearchBeanList.addAll ( (List <TopGoodsBean>) value );
                                ((ShopListFragment) (SearchGoodsListFragment2.this.getParentFragment ( ))).endLoadingMore ( );
                            }else if(tagStr.equals ( "-1" )){
                                if (goodsSearchBeanList != null) {
                                    goodsSearchBeanList.clear ( );
                                    goodsSearchBeanList.addAll ( (List <TopGoodsBean>) value );
                                }
                            }
                            Log.e("mShopListAdapter:",mShopListAdapter.getClass()+"");
                            mShopListAdapter.notifyDataSetChanged ( );
                            //
                        }
                    } );
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


    @Override
    public void Success(Object value, String tag) {

    }

    @Override
    public void Fail(Object value) {
        if (value.equals ( "0" )) {
                ((ShopListFragment) (SearchGoodsListFragment2.this.getParentFragment ( ))).endRefreshing ( );
        } else if (value.equals ( "1" )) {
            ((ShopListFragment) (SearchGoodsListFragment2.this.getParentFragment ( ))).endLoadingMore ( );
        }
    }

    @Override
    public void Error(Object... values) {
        if (values.equals ( "0" )) {
            ((ShopListFragment) (SearchGoodsListFragment2.this.getParentFragment ( ))).endRefreshing ( );
        } else if (values.equals ( "1" )) {
            ((ShopListFragment) (SearchGoodsListFragment2.this.getParentFragment ( ))).endLoadingMore ( );
        }
    }

    public String getmChannelCode() {
        return mChannelCode;
    }

    public void setmChannelCode(String mChannelCode) {
        this.mChannelCode = mChannelCode;
    }

    public void setRequestSuccessCallback(RequestSuccessCallback requestSuccessCallback){
        requestSuccess = requestSuccessCallback;

    }

    public RequestSuccessCallback  requestSuccess = null;

    public interface  RequestSuccessCallback{
        void success(Object tag);
    }
}
