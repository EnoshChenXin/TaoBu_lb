package com.lianbei.taobu.shop.view;


import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.adapter.GroupGameListAdapter;
import com.lianbei.taobu.shop.adapter.SecordRecordListAdapter;
import com.lianbei.taobu.shop.model.SearchRecord;
import com.lianbei.taobu.utils.CacheHelp;
import com.lianbei.taobu.utils.DirectoryHelp;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.SoftInputUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class SearchGoodsActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_SAVE_IMG = 10;
    @BindView(R.id.listframeLayout)
    FrameLayout listframeLayout;

    @BindView(R.id.lin_serarch)
    LinearLayout lin_serarch;

    @BindView(R.id.search)
    Button search;
    @BindView(R.id.et_input_keyword)
    EditText et_input_keyword;
    @BindView(R.id.clean_search_tv)
    ImageView clean_search_tv;

    @BindView(R.id.bt_clean)
    Button bt_clean;
    @BindView(R.id.list_record)
    PowerfulRecyclerView list_record;
    protected BaseQuickAdapter recordListAdapter;
    ShopListFragment shopListFragment;
    String keyword;
    HashSet<SearchRecord> searchRecords = new HashSet<SearchRecord>();
    List<SearchRecord> recordList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search_goods;
    }

    @Override
    public void initViews() {
        list_record.setLayoutManager(new GridLayoutManager(this, 4));
        list_record.setNestedScrollingEnabled(false);//解决滑动卡顿
    }

    @Override
    public void initData() {
        requestPermission(0);
    }

    public void SearchContent() {
        //listframeLayout.setVisibility(View.VISIBLE);
        //必需继承FragmentActivity,嵌套fragment只需要这行代码
        shopListFragment = ShopListFragment.newInstance(keyword);
        shopListFragment.setNeedRequestSuccessCallback(new ShopListFragment.NeedRequestSuccessCallback() {
            @Override
            public void success(Object str) {
                listframeLayout.setVisibility(View.VISIBLE);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.listframeLayout, shopListFragment).commitAllowingStateLoss();
    }

    @Override
    public void initListener() {
        et_input_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //do something
                    keyword = et_input_keyword.getText().toString().trim();
                    doSearch(keyword);
                    return true;
                }
                return false;
            }
        });
        et_input_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("") || s.toString().length() > 0) {
                    clean_search_tv.setVisibility(View.VISIBLE);
                } else {
                    clean_search_tv.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("") || s.toString().length() > 0) {
                    clean_search_tv.setVisibility(View.VISIBLE);
                } else {
                    clean_search_tv.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    public void hideSoftkeyboard() {
        try {
            ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {

        }
    }

    @OnClick({R.id.search, R.id.bt_back, R.id.bt_clean, R.id.clean_search_tv})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                keyword = et_input_keyword.getText().toString().trim();
                doSearch(keyword);
                break;
            case R.id.bt_back:
                finish();
                break;
            case R.id.bt_clean:
                recordList.clear();
                recordListAdapter.notifyDataSetChanged();
                CacheHelp.remove("t.list");
                break;
            case R.id.clean_search_tv:
                et_input_keyword.setText("");
                hideSearchRecordView(false);
                break;
        }
    }

        private void doSearch(String keyword) {
        if (Validator.isStrNotEmpty(keyword)) {
            et_input_keyword.setText(keyword + "");
            SoftInputUtil.hideSoftInput(this, listframeLayout);
            hideSearchRecordView(true);
            SearchContent();
            requestPermission(1);
        } else {
            ToastUtil.showShort(this, "请输入关键词后再搜索");
        }
    }

    private void hideSearchRecordView(boolean hide) {
        if (hide) {
            lin_serarch.setVisibility(View.GONE);
        } else {
            lin_serarch.setVisibility(View.VISIBLE);
            listframeLayout.setVisibility(View.INVISIBLE);
        }

    }

    private void saveRecord() {
        SearchRecord searchRecord = new SearchRecord();
        searchRecord.setKeywordRecord(keyword);
        searchRecords.add(searchRecord);
        CacheHelp.saveObjList(this, searchRecords, "t");
    }

    private void CheckRecord() {
        recordList = convertToSiteList(CacheHelp.getStrList("t"));
        if (!recordList.isEmpty()) {
//            StringBuffer sre = new StringBuffer();
//            for (int i = 0; i <recordList.size() ; i++) {
//              String name =   recordList.get(i).getKeywordRecord();
//                Log.e("name:",name+"");
//                sre.append(name+" ,");
//            }
            recordListAdapter = new SecordRecordListAdapter(recordList);
            list_record.setAdapter(recordListAdapter);
            recordListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    SearchRecord se = recordList.get(i);
                    keyword = se.getKeywordRecord();
                    doSearch(keyword);
                }
            });
        }
    }

    public List<SearchRecord> convertToSiteList(
            HashSet<? extends Object> strList) {
        searchRecords = (HashSet<SearchRecord>) strList;
        List<SearchRecord> list = new ArrayList<SearchRecord>();
        if (strList != null) {
            for (Object str : strList) {
                SearchRecord site = new SearchRecord();
                site.setKeywordRecord(((SearchRecord) str).getKeywordRecord());
                list.add(site);
            }
        }
        return list;
    }

    /**
     * 请求读取sd卡的权限
     */
    private void requestPermission(int type) {
        if (Build.VERSION.SDK_INT >= 23) {
            //读取sd卡的权限
            String[] mPermissionList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, mPermissionList)) {
                //已经同意过
                if (type == 0) {
                    CheckRecord();
                } else {
                    saveRecord();
                }

            } else {
                //未同意过,或者说是拒绝了，再次申请权限
                EasyPermissions.requestPermissions(
                        this,  //上下文
                        "保存图片需要读取sd卡的权限", //提示文言
                        REQUEST_CODE_SAVE_IMG, //请求码
                        mPermissionList //权限列表
                );
            }
        } else {
            if (type == 0) {
                CheckRecord();
            } else {
                saveRecord();
            }
        }
    }
}
