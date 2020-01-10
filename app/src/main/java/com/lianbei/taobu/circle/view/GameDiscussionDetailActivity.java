package com.lianbei.taobu.circle.view;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.adapter.GroupDiscussListAdapter;
import com.lianbei.taobu.circle.model.ItemInfoMsg;
import com.lianbei.taobu.circle.viewmanager.GameManager;
import com.lianbei.taobu.taobu.view.viewutils.KeyMapDailog;
import com.lianbei.taobu.taobu.view.viewutils.TransAvtivity;
import com.lianbei.taobu.taobu.viewmanager.TaoBuManager;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 留言详情
 */

public class GameDiscussionDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    private String mChannelCode = "0";
    protected BaseQuickAdapter groupListAdapter;
    @BindView(R.id.image_list)
    LinearLayout image_list;
    @BindView(R.id.re_comm)
    LinearLayout re_comm;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.creatTime)
    TextView creatTime;

    @BindView(R.id.headImageView)
    CircleImageView headImageView;
    @BindView(R.id.shafa)
    LinearLayout shafa;
    ArrayList <String> list4 = null;
    public KeyMapDailog dialog;
    private List <ItemInfoMsg.msglist.childMsgList> childMsgListList = new ArrayList <> ( );
    private int match_id;
    private ItemInfoMsg.msglist msglist;
    @Override
    public int getContentViewId() {
        return R.layout.activity_discussion_detail;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
        msglist = (ItemInfoMsg.msglist) getIntent ( ).getSerializableExtra ( "msglists" );
        childMsgListList.addAll ( msglist.getList () );
        if(childMsgListList !=null && childMsgListList.size ()>0){
            shafa.setVisibility ( View.GONE );
        }else{
            shafa.setVisibility ( View.VISIBLE );
        }
        match_id = msglist.getMatch_id ();
        if (msglist != null) {
            content.setText ( msglist.getContent ( ) + "" );
            creatTime.setText ( msglist.getCreate_time ( ) + "" );
            GlideUtils.getInstance ( ).load ( this, msglist.getHeadimgurl ( ), headImageView );
        }
    }

    @Override
    public void initData() {


//        ArrayList<String> imageList = new ArrayList<String>( Arrays.asList(dynamicBeanList.getList ( ).split(",")));
//        if (Validator.isStrNotEmpty ( dynamicBeanList.getList ( ) ) && imageList != null && imageList.size () > 0) {
//            for (int i = 0; i < imageList.size ( ); i++) {
//                list4 = new ArrayList <> ( );
//                list4.add ( imageList.get ( i ) );
//            }
//        }
        //在addView之前加入如下代码
//        ViewGroup parentViewGroup = (ViewGroup) image_list.getParent();
//        if (parentViewGroup != null) {
//            parentViewGroup.removeAllViews ( );
//        }
//        ImageView imageView = new ImageView(this);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setAdjustViewBounds ( true );
//        //imageView.setImageBitmap(ToolsMet.readBitMap(CouponActivity.this, imgs[j]));
//        GlideUtils.load(this,"http://nx.lbeizxw.com/image/wap/jieti8.jpg", imageView);
//        image_list.addView(imageView);
        if(Validator.isNotNull ( msglist ))
            return;
    }

    @Override
    public void initListener() {
        groupListAdapter = new GroupDiscussListAdapter ( mChannelCode, childMsgListList );
        mRvNews.setAdapter ( groupListAdapter );
        groupListAdapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                Intent intent = new Intent (ListDiscussionFragment.this.getContext (),GameDiscussionDetailActivity.class  );
//                intent.putExtra ( "msglists",msglists.get ( i ) );
//                intent.putExtra ( "match_id",msglists.get ( i ).getMatch_id ());
//                startActivity ( intent );
            }
        } );
        groupListAdapter.setOnItemChildClickListener ( new BaseQuickAdapter.OnItemChildClickListener ( ) {
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
            Intent intent = new Intent ( GameDiscussionDetailActivity.this, TransAvtivity.class );
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
                TaoBuManager.getInstance ().item_msgreply ( GameDiscussionDetailActivity.this, match_id+"",msglist.getId ()+"",inputText,requestCompletion);
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
}
