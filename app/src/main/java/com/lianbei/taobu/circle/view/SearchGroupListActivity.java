package com.lianbei.taobu.circle.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.adapter.GroupGameListAdapter;
import com.lianbei.taobu.circle.model.GameBean;
import com.lianbei.taobu.circle.viewmanager.GameManager;
import com.lianbei.taobu.constants.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchGroupListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rv_list)
    PowerfulRecyclerView mRvList;
    @BindView ( R.id.cancel_btn )
    TextView cancel_btn;

    @BindView ( R.id.iv_pager )
    TextView iv_pager;

    @BindView ( R.id.mSearchEdit )
    EditText mSearchEdit;
    protected BaseQuickAdapter groupGameListAdapter;
    private GameBean gameBean = new GameBean();
    private List <GameBean.Gamelist> gamelist = new ArrayList <> (  );
    private String mChannelCode = Constant.SEARCH_GROUP;
    @Override
    public int getContentViewId() {
        return R.layout.activity_search_group_list;
    }

    @Override
    public void initViews() {
        setListView();
    }

    private void setListView(){
        mRvList.setLayoutManager ( new GridLayoutManager ( this, 1 ) );
        mRvList.setNestedScrollingEnabled ( false );//解决滑动卡顿
    }
    @OnClick(R.id.cancel_btn)
    @Override
    public void onClick(View view) {
        switch (view.getId ()){
            case R.id.cancel_btn:
                finish ();
                break;
        }

    }
    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mSearchEdit.setOnEditorActionListener ( new TextView.OnEditorActionListener ( ) {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    GameManager.getInstance (SearchGroupListActivity.this).searchgroup ( textView.getText ( ) + "", 0, 10, requestCompletion);
                    Toast.makeText(SearchGroupListActivity.this, textView.getText ()+"", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        } );
        groupGameListAdapter = new GroupGameListAdapter ( mChannelCode, gamelist);
        mRvList.setAdapter ( groupGameListAdapter );
        groupGameListAdapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent (SearchGroupListActivity.this, GroupGameDetailActivity.class );
                intent.putExtra ( "gamebean", gameBean.getList ().get ( i ));
                startActivity (intent);
            }
        } );
    }

    GameManager.RequestCompletion requestCompletion = new GameManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            if(value != null){
                iv_pager.setText ( "" );
                gameBean = null;
                gamelist.clear ();
                gameBean = (GameBean)value;
                gamelist.addAll (gameBean.getList ());
                groupGameListAdapter.notifyDataSetChanged ();
            }
            if(gamelist == null || gamelist.isEmpty () || gamelist.size ()==0){
                iv_pager.setText ( "暂无数据" );
            }else{
                iv_pager.setText ( "" );
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
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

}
