package com.lianbei.taobu.taobu.view;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import androidx.recyclerview.widget.GridLayoutManager;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.viewmanager.GameManager;
import com.lianbei.taobu.taobu.adapter.DynamicDetailLisetAdapter;
import com.lianbei.taobu.taobu.model.DynamicBean;
import com.lianbei.taobu.taobu.model.SignReplyBean;
import com.lianbei.taobu.taobu.view.viewutils.KeyMapDailog;
import com.lianbei.taobu.taobu.view.viewutils.TransAvtivity;
import com.lianbei.taobu.taobu.viewmanager.TaoBuManager;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.CircleImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SignDynamicDetailActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate  {

    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    private String mChannelCode = "0";
    protected BaseQuickAdapter dynamicdetaillisetadapter;

    @BindView(R.id.mRefreshLayout)
    BGARefreshLayout mRefreshLayout;

    @BindView(R.id.image_list)
    LinearLayout image_list;
    @BindView(R.id.shafa)
    LinearLayout shafa;


    @BindView(R.id.re_comm)
    LinearLayout re_comm;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.creatTime)
    TextView creatTime;
    @BindView(R.id.nickName)
    TextView nickName;
    @BindView(R.id.headImageView)
    CircleImageView headImageView;
    ArrayList <String> list4 = null;
    public KeyMapDailog dialog;
    DynamicBean.DynamicList dynamicBeanList;
    SignReplyBean signReplyBean;
    List<SignReplyBean.ReplyList> signReplyBeanList = new ArrayList <> (  );
    private String match_id;
    private int mNewPageNumber = 1;
    private int limit = 10;
    private boolean refreshMore = true;
    ArrayList <String> imageList=new ArrayList <> (  );
    @Override
    public int getContentViewId() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
        dynamicBeanList = (DynamicBean.DynamicList) getIntent ( ).getSerializableExtra ( "dynamicBean" );
        match_id = getIntent ().getStringExtra ( "match_id" );
        if (dynamicBeanList != null) {
            nickName.setText ( dynamicBeanList.getNickname ( ) + "" );
            content.setText ( dynamicBeanList.getContent ( ) + "" );
            creatTime.setText ( dynamicBeanList.getCreate_time ( ) + "" );
            GlideUtils.getInstance ( ).load ( this, dynamicBeanList.getHeadimgurl ( ), headImageView );
        }
    }

    @Override
    public void initData() {
        setListView();
        if(Validator.isNotNull ( dynamicBeanList ) && dynamicBeanList.getList().length ()>0){
            imageList.addAll (Arrays.asList ( dynamicBeanList.getList ( ).split ( "," ) ));
          //  imageList= new ArrayList <String> ( );
            setImageList();
        }

//            ArrayList <ImageView> imageViews = new ArrayList <> ( );
//            image_list.removeAllViews ( );
//            for (int i = 0; i < imageList.size ( ); i++) {
//                ImageView imageView = new ImageView ( this );
//                imageView.setScaleType ( ImageView.ScaleType.FIT_XY );
//                imageView.setAdjustViewBounds ( true );
//                imageView.setId ( i );
//                imageView.setPadding ( 0, 10, 0, 0 );
//                //imageView.setLayoutParams(new ViewGroup.LayoutParams ( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));  //设置图片宽高
//                //imageView.setImageBitmap(ToolsMet.readBitMap(CouponActivity.this, imgs[j]));
//                GlideUtils.getInstance ( ).load ( this, imageList.get ( i ), imageView );
//                image_list.addView ( imageView );
//                imageView.setOnClickListener ( onClickListener );
//            }
         GameManager.getInstance ( this ).getItem_Replylist (dynamicBeanList.getId (),mNewPageNumber,limit, replylisrequestCompletion,"0" );

    }
    private void setListView() {
        mRvNews.setLayoutManager ( new GridLayoutManager( this, 1 ) );
        mRvNews.setNestedScrollingEnabled ( false );//解决滑动卡顿
    }
    /**
     * 设置内容图片图片
     */
   private void  setImageList(){
       if(signReplyBeanList != null){
           signReplyBeanList.clear ();
       }
       if (Validator.isNotNull ( imageList ) && imageList.size ( ) > 0) {
           for (int i = 0; i < imageList.size ( ); i++) {
               SignReplyBean replyBeans = new SignReplyBean ();
               SignReplyBean.ReplyList myreplyList =replyBeans.new ReplyList();
               myreplyList.setContentImagelist (imageList.get ( i ));
               signReplyBeanList.add( myreplyList );
           }
       }

    }
    GameManager.RequestCompletion replylisrequestCompletion = new GameManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            if(value != null) {
                signReplyBean= (SignReplyBean) value;
                if(tag.equals ( "0" )){
                    if(signReplyBeanList!= null){
                       // signReplyBeanList.clear ();
                        setImageList();
                        signReplyBeanList.addAll ( signReplyBean.getList ( ) );
                        mRefreshLayout.endRefreshing();
                    }
                }else{
                    if(signReplyBean !=null && signReplyBean.getList ().size ()>0 ){
                        refreshMore = true;
                    }else{
                        refreshMore =false;
                    }
                    if(signReplyBeanList!= null){
                        signReplyBeanList.addAll ( signReplyBean.getList ( ) );
                    }
                    mRefreshLayout.endLoadingMore();
                }
                dynamicdetaillisetadapter.notifyDataSetChanged ( );
                if (signReplyBeanList != null && signReplyBeanList.size ( ) > 0) {
                    shafa.setVisibility ( View.GONE );
                }else{
                    shafa.setVisibility ( View.VISIBLE );
                }
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
    protected void initDataObserver() {
        super.initDataObserver ( );
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder (this, true));
    }

    @Override
    public void initListener() {
        mRefreshLayout.setDelegate(this);
        dynamicdetaillisetadapter = new DynamicDetailLisetAdapter ( mChannelCode, signReplyBeanList );
        mRvNews.setAdapter ( dynamicdetaillisetadapter );
        dynamicdetaillisetadapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                Intent intent = new Intent (ListDiscussionFragment.this.getContext (),GameDiscussionDetailActivity.class  );
//                intent.putExtra ( "msglists",msglists.get ( i ) );
//                intent.putExtra ( "match_id",msglists.get ( i ).getMatch_id ());
//                startActivity ( intent );
            }
        } );
        dynamicdetaillisetadapter.setOnItemChildClickListener ( new BaseQuickAdapter.OnItemChildClickListener ( ) {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                int viewId= view.getId ();

            }
        } );
    }

    View.OnClickListener onClickListener = new View.OnClickListener ( ) {
        @Override
        public void onClick(View v) {
//        PhotoPreviewIntent intent = new PhotoPreviewIntent( SignDynamicDetailActivity.this);
//        intent.setCurrentItem(v.getId ());
//        intent.setCurrentType (-1);
//        intent.setPhotoPaths(list4);
//        startActivity ( intent );
            Log.e ( "View:", v.getId ( ) + "" );
            Intent intent = new Intent ( SignDynamicDetailActivity.this, TransAvtivity.class );
            intent.putExtra ( TransAvtivity.EXTRA_PHOTOS, list4 );
            intent.putExtra ( TransAvtivity.EXTRA_CURRENT_ITEM, v.getId ( ) );
            startActivity ( intent );

//            Intent intent = new Intent( SignDynamicDetailActivity.this, PicDetailActivity.class);
//            int location[] = new int[2];
//            v.getLocationOnScreen(location);
//            intent.putExtra("x", location[0]);
//            intent.putExtra("y", location[1]);
//            startActivity(intent);
//            overridePendingTransition(0, 0);
        }
    };

    @Override
    public void onLeftClick() {
        finish ( );

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @OnClick({R.id.re_comm})
    @Override
    public void onClick(View v) {
        switch (v.getId ( )) {
            case R.id.re_comm:
                PublishComm ( );
                break;
        }
    }

    private void PublishComm() {
        dialog = new KeyMapDailog ( "给对方留言吧，让他感受到你......", new KeyMapDailog.SendBackListener ( ) {
            @Override
            public void sendBack(String inputText) {
                TaoBuManager.getInstance ().item_userreply ( SignDynamicDetailActivity.this, dynamicBeanList.getId ()+"","","",dynamicBeanList.getNickname (),inputText,requestCompletion);
            }
        } );
        dialog.show ( getSupportFragmentManager ( ), "dialog" );

    }
    TaoBuManager.RequestCompletion requestCompletion =new TaoBuManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            if(value.equals ( "0" )){
                dialog.hideProgressdialog ();
                dialog.dismiss ();
                mNewPageNumber =1;
                GameManager.getInstance ( SignDynamicDetailActivity.this ).getItem_Replylist (dynamicBeanList.getId (),mNewPageNumber,limit, replylisrequestCompletion,"0" );
            }else {//记载
                dialog.hideProgressdialog ();
            }
        }

        @Override
        public void Fail(Object value) {
            dialog.hideProgressdialog ();
            dialog.dismiss ();
        }

        @Override
        public void Error(Object... values) {
            dialog.hideProgressdialog ();
            dialog.dismiss ();
        }
    };

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mNewPageNumber=1;
        refreshMore =true;
        GameManager.getInstance ( SignDynamicDetailActivity.this ).getItem_Replylist (dynamicBeanList.getId (),mNewPageNumber,limit, replylisrequestCompletion,"0" );

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if(refreshMore){
            mNewPageNumber++;
            GameManager.getInstance ( SignDynamicDetailActivity.this ).getItem_Replylist (dynamicBeanList.getId (),mNewPageNumber,limit, replylisrequestCompletion,"1" );
            return true;
        }else{
            return false;
        }

    }
}
