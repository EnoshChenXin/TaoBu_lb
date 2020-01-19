package com.lianbei.taobu.circle.view;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.adapter.GroupGameDatailAdapter;
import com.lianbei.taobu.circle.model.GameBean;
import com.lianbei.taobu.circle.model.ItmeInfo;
import com.lianbei.taobu.circle.viewmanager.GameManager;
import com.lianbei.taobu.mine.model.MemberInfo;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.mine.viewmanager.UserUtils;
import com.lianbei.taobu.taobu.view.AddDynamicActivity;
import com.lianbei.taobu.taobu.view.viewutils.KeyMapDailog;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class GroupGameDetailActivity extends BaseActivity implements View.OnClickListener {

    GameBean.Gamelist gameBean;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.lin_button)
    LinearLayout lin_button;

    @BindView(R.id.re_comm)
    LinearLayout re_comm;

    @BindView(R.id.share_group)
    LinearLayout share_group;
    @BindView(R.id.join_group)
    LinearLayout join_group;

    @BindView(R.id.group_tag)
    TextView group_tag;

    GroupGameDatailAdapter groupGameDatailAdapter;

    ArrayList <Fragment> listfragment;
    List <Drawable> icons;
    List <String> tab_titles;
    String itmeId;
    KeyMapDailog dialog;
    ItmeInfo itmeInfo = new ItmeInfo ( );
    @BindView(R.id.headImageView)
    CircleImageView headImageView;
    UserInfo userInfo;
    ListDiscussionFragment listDiscussionFragment;

    @Override
    public int getContentViewId() {
        return R.layout.activity_group_game_detail_main;
    }

    @Override
    public void initViews() {
        Intent intent = getIntent ( );
        gameBean = (GameBean.Gamelist) intent.getSerializableExtra ( "gamebean" );
        createNavigationView ( R.id.navigation_view );
        navigationView.setTitleText ( gameBean.getTitle ( ) + "" );
        navigationView.setRightBtnHidden ( false );
        navigationView.setStrBtnRight ( "详情" );
        navigationView.setBtnRightTextColor ( getResources ( ).getColor ( R.color.color_515051 ) );
        tabs.setTabMode ( TabLayout.GRAVITY_CENTER );//设置tab模式，当前为系统默认模式
        listfragment = new ArrayList <Fragment> ( );
        listDiscussionFragment = new ListDiscussionFragment ( );
        listfragment.add ( listDiscussionFragment );
        listfragment.add ( new ListTodayFragment ( ) );
        listfragment.add ( new ListYesterdayFragment ( ) );
        listfragment.add ( new ListMonthFragment ( ) );
        initView ( );
    }

    private void initView() {
        if(Validator.isNotNull (itmeInfo) && Validator.isNotNull ( itmeInfo.getUserTeam () )){
            if((!UserInfo.UserInfoIsEmpty ( this )
                    && itmeInfo.getUserTeam ().getUser_id () == UserInfo.getUserInfo ( this ).getUser_id ())
                    || itmeInfo.isInTeam () ){
                share_group.setVisibility ( View.VISIBLE );
                join_group.setVisibility ( View.GONE );
              }
        }else{
            join_group.setVisibility ( View.VISIBLE );
            share_group.setVisibility ( View.GONE );
        }
    }

    @Override
    public void initData() {
        GlideUtils.getInstance ( ).load ( this, gameBean.getIcon ( ), headImageView );
        init ( );
        setTabFragment ( );
        initRequest ( );
    }

    private void initRequest() {
        GameManager.getInstance (this).getItemInfo ( gameBean.getId ( ) + "", requestCompletion );
    }

    GameManager.RequestCompletion requestCompletion = new GameManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            if (value != null) {
                itmeInfo = (ItmeInfo) value;
                listDiscussionFragment.setItmeInfo ( itmeInfo );
                setViewState ( );
                initView();
            }
        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };

    private void setViewState() {
        if (itmeInfo.getUserTeam ( ).getIspass ( ) == 1) {
            group_tag.setText ( "公开" );
        } else {
            group_tag.setText ( "私密" );
        }

    }

    private void setTabFragment() {
        for (int i = 0; i < tab_titles.size ( ); i++) {
            TabLayout.Tab tab = tabs.newTab ( );
            if (tab != null) {
                tab.setCustomView ( getTabView ( i ) );
            }
            if (i == 1) {
                TextView tv_tab = tab.getCustomView ( ).findViewById ( R.id.tv_title );
                tv_tab.setTextColor ( getResources ( ).getColor ( R.color.main_red ) );
                tv_tab.setBackgroundColor ( getResources ( ).getColor ( R.color.dark_background ) );
                tabs.addTab ( tab, true );//默认  第一项 回调
            } else {
                tabs.addTab ( tab );
            }
        }
        groupGameDatailAdapter = new GroupGameDatailAdapter ( this, getSupportFragmentManager ( ), listfragment, tab_titles );
        viewPager.setAdapter ( groupGameDatailAdapter );
        viewPager.setOffscreenPageLimit ( 2 );//预加载管理,除去当前显示页面以外需要被预加载的页面数。
        //通过调用方法,对直接tabLayout和viewPager关联
        //Tablayout自定义view绑定ViewPager 自定义view时使用 tabLayout.setupWithViewPager(viewPager);方法关联无效，通过以下方法进行viewpager和tablayout的关联
        // tabs.setupWithViewPager(viewPager); //标题无法正常显示,需要复写RecommandPagerAdapter对标题处理的方法就可以了
        //使用自定义布局，默认选中
        //TabLayout与viewpager配合使用时  viewpager设置选中不起作用。
        //需要两个同时设置才可以。
        viewPager.setCurrentItem ( 1 );
        tabs.getTabAt ( 1 ).getCustomView ( ).setSelected ( true );
        //联动   tabs.getTabAt(i)..select();
        viewPager.addOnPageChangeListener ( new TabLayout.TabLayoutOnPageChangeListener ( tabs ) );
        tabs.addOnTabSelectedListener ( new TabLayout.ViewPagerOnTabSelectedListener ( viewPager ) );
    }

    @Override
    public void initListener() {
        tabs.addOnTabSelectedListener ( new TabLayout.OnTabSelectedListener ( ) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中的相关操作
                TextView tv_tab = tab.getCustomView ( ).findViewById ( R.id.tv_title );
                tv_tab.setTextColor ( getResources ( ).getColor ( R.color.main_red ) );
                tv_tab.setBackgroundColor ( getResources ( ).getColor ( R.color.dark_background ) );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中的相关操作
                TextView tv_tab = tab.getCustomView ( ).findViewById ( R.id.tv_title );
                tv_tab.setTextColor ( getResources ( ).getColor ( R.color.gary ) );
                tv_tab.setBackgroundColor ( getResources ( ).getColor ( R.color.white ) );
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );
    }

    @Override
    public void onLeftClick() {
        finish ( );

    }

    @Override
    public void onRightClick() {
        Intent intent = new Intent ( this, GroupManagerActivity.class );
        intent.putExtra ( "itmeInfo", itmeInfo);
        startActivity ( intent );
    }

    @Override
    public void onRefreshClick() {

    }

    private void init() {
        tab_titles = new ArrayList <> ( );
        tab_titles.add ( getString ( R.string.TAB_TEXT_DISCUSSION ) );
        tab_titles.add ( getString ( R.string.TAB_TEXT_TODAY_LIST ) );
        tab_titles.add ( getString ( R.string.TAB_TEXT_YESTERDAY_LIST ) );
        tab_titles.add ( getString ( R.string.TAB_TEXT_MONTH_LIST ) );

        icons = new ArrayList <> ( );
        icons.add ( getResources ( ).getDrawable ( R.mipmap.discuss ) );
        icons.add ( getResources ( ).getDrawable ( R.mipmap.today_icon ) );
        icons.add ( getResources ( ).getDrawable ( R.mipmap.yesterday_icon ) );
        icons.add ( getResources ( ).getDrawable ( R.mipmap.month_icon ) );
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from ( this ).inflate ( R.layout.item_group_tab_layout, null );
        TextView tv = (TextView) v.findViewById ( R.id.tv_title );
        tv.setText ( tab_titles.get ( position ) );
        tv.setCompoundDrawablesWithIntrinsicBounds ( null, icons.get ( position ), null, null );
        return v;
    }


    @OnClick({R.id.lin_button, R.id.re_comm, R.id.share_group, R.id.join_group})
    @Override
    public void onClick(View view) {
        switch (view.getId ( )) {
            case R.id.lin_button:
                Intent intent = new Intent ( this, GroupManagerActivity.class );
                intent.putExtra ( "itmeInfo", itmeInfo);
                startActivity ( intent );
                break;
            case R.id.re_comm:
                if (UserUtils.isNotLogin ( this ))
                    return;
                PublishComm ( );
                break;
            case R.id.share_group://分享

                break;
            case R.id.join_group: //加入队伍
                if (UserUtils.isNotLogin ( this ))
                    return;
                addGroup ( );
                break;
        }
    }

    private void PublishComn() {
        int math_id = itmeInfo.getUserTeam ( ).getId ( );
        Intent intent = new Intent ( GroupGameDetailActivity.this, AddDynamicActivity.class );
        intent.putExtra ( "cate", "1" );
        intent.putExtra ( "math_id", math_id + "" );
        startActivity ( intent );
    }

    private void PublishComm() {
        dialog = new KeyMapDailog ( "发表留言", new KeyMapDailog.SendBackListener ( ) {
            @Override
            public void sendBack(String inputText) {
                new Handler ( ).postDelayed ( new Runnable ( ) {
                    @Override
                    public void run() {
                        int match_id = itmeInfo.getUserTeam ( ).getId ( );
                        GameManager.getInstance ( GroupGameDetailActivity.this).ItemSendMessage ( GroupGameDetailActivity.this, inputText, match_id + "", itemsendRequest );
                    }
                }, 250 );

                // gameManager.ItemSendMessage(inputText,math_id+"",user_id+"",itemsendRequest);
            }
        } );
        dialog.show ( getSupportFragmentManager ( ), "dialog" );
    }

    GameManager.RequestCompletion itemsendRequest = new GameManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            if (value != null) {
                if (value.equals ( "0" )) {
                    dialog.hideProgressdialog ( );
                    dialog.dismiss ( );
                    //动态发布成功
                }
            }
        }

        @Override
        public void Fail(Object value) {
            dialog.hideProgressdialog ( );
        }

        @Override
        public void Error(Object... values) {
            dialog.hideProgressdialog ( );
        }
    };

    /**
     * 加入群组
     */
    private void addGroup() {
        GameManager.getInstance (GroupGameDetailActivity.this).additem_apply ( itmeInfo.getUserTeam ( ).getId ( ) + "", new GameManager.RequestCompletion ( ) {
            @Override
            public void Success(Object value, String tag) {
                MemberInfo m = (MemberInfo) value;
                ToastUtil.showShort ( GroupGameDetailActivity.this, m.getMsg ( ) + "" );
                if (m.getCode ( ) == 0) {
                    initRequest ( );

                }
            }

            @Override
            public void Fail(Object value) {

            }

            @Override
            public void Error(Object... values) {

            }
        } );

    }
}
