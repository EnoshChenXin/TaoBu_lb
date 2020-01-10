package com.lianbei.taobu.circle.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.circle.adapter.GroupGameListAdapter;
import com.lianbei.taobu.circle.model.GameBean;
import com.lianbei.taobu.circle.viewmanager.GameManager;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.utils.Validator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 群组赛
 */

public class GroupGameFragment extends BaseFragment implements GameManager.RequestCompletion,BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    @BindView(R.id.creat_group_lin)
    LinearLayout creat_group;

    private OnFragmentInteractionListener mListener;
    private String mChannelCode = Constant.My_GROUP;
    protected BaseQuickAdapter groupGameListAdapter;
    private GameBean gameBean = new GameBean();
    private List<GameBean.Gamelist> gamelist = new ArrayList <> (  );
    GameManager gameManager;
    public GroupGameFragment() {
        // Required empty public constructor
    }

//

    @Override
    public int getContentViewId() {
        return R.layout.fragment_group_game;
    }

    @Override
    public void initViews() {
        setListView();
    }
    private void setListView(){
        mRvNews.setLayoutManager ( new GridLayoutManager ( mActivity, 1 ) );
        mRvNews.setNestedScrollingEnabled ( false );//解决滑动卡顿
    }
    @Override
    public void initData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged ( hidden );
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint ( isVisibleToUser );
        if ((isVisibleToUser && isResumed())) {
            onResume();
        } else if (!isVisibleToUser) {
           // Timber.i("On Pause on %s Fragment Invisble", getClass().getSimpleName());
            onPause();
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onResume() {
        super.onResume ( );
        if (getUserVisibleHint()) {
            sendRequest();

        }
    }
    private void sendRequest(){
        if(gameManager == null){
            gameManager = new GameManager ( this.getActivity () );
        }
        if(!Validator.isNotNull ( gameBean )  || gameBean.getPages ()==0){
            gameManager.GetGroupGameContent (  this,"");
        }
    }

    @Override
    public void initListener() {
        groupGameListAdapter = new GroupGameListAdapter ( mChannelCode, gamelist);
        mRvNews.setAdapter ( groupGameListAdapter );
        groupGameListAdapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent (GroupGameFragment.this.getActivity (), GroupGameDetailActivity.class );
                intent.putExtra ( "gamebean", gameBean.getList ().get ( i ));
                startActivity (intent);
            }
        } );
        creat_group.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (GroupGameFragment.this.getActivity (), CreatOrAddGroupActivity.class );
                startActivityForResult (intent,0  );
            }
        } );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK)
            if(data.getIntExtra ( "code",-1 ) == 0){
                GameManager.getInstance (  this.getActivity () ).GetGroupGameContent (  this,"");
            }
        }
    }

    @Override
    public void Success(Object value, String tag) {
     if(value != null){
         gameBean = null;
         gamelist.clear ();
         gameBean = (GameBean)value;
         gamelist.addAll (gameBean.getList ());
         groupGameListAdapter.notifyDataSetChanged ();
     }
    }

    @Override
    public void Fail(Object value) {

    }

    @Override
    public void Error(Object... values) {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
