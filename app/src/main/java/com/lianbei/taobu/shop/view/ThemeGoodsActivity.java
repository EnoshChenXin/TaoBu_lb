package com.lianbei.taobu.shop.view;


import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.model.GoodsThemeBean;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.taobu.adapter.GridViewAdapter;
import com.lianbei.taobu.taobu.view.viewutils.MastGridView;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.views.CustomRoundAngleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ThemeGoodsActivity extends BaseActivity {
    private GridViewAdapter adapter;
    MastGridView mastgridview;
    private List<List<TopGoodsBean>> themegoodslist = new ArrayList<>();
    private List<GoodsThemeBean> themelist = new ArrayList<>();
    private List<GoodsThemeBean> requestgoodslist = new ArrayList<>();
    CustomRoundAngleImageView theme_image;
    LinearLayout imageView_lin;

    TextView theme_title;
    TextView more;


    @Override
    public int getContentViewId() {
        return R.layout.activity_theme_goods;
    }

    @Override
    public void initViews() {
        createNavigationView(R.id.navigation_id);

    }

    @Override
    public void initData() {
        ShopManager.getInstance(this).cats_get(themelistd, "0");

    }

    private void myListener() {
        //限时免费抢
        mastgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ThemeGoodsActivity.this, ActivityGoodsDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("goods_id", themegoodslist.get(parent.getId()).get(position).getGoods_id());
                startActivity(intent);
            }
        });
        imageView_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsybd = new Intent(ThemeGoodsActivity.this, ActivityGoodsList.class);
                intentsybd.putExtra("param1", themelist.get(Integer.parseInt(v.getTag().toString())).getId());
                intentsybd.putExtra("pagertype", Constant.ZHUTI);
                startActivity(intentsybd);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsybd = new Intent(ThemeGoodsActivity.this, ActivityGoodsList.class);
                intentsybd.putExtra("param1", themelist.get(Integer.parseInt(v.getTag().toString())).getId());
                intentsybd.putExtra("pagertype", Constant.ZHUTI);
                startActivity(intentsybd);
            }
        });
    }

    @Override
    public void initListener() {

    }


    private void setlistValue() {


//        DisplayMetrics dm = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;
//        int gridviewWidth = (int) (list6.size() * (140 + 4) * density);
//        int itemWidth = (int) (120 * density);
//        adapter = new GridViewAdapter(this, list6);
//        mastgridview.setDataSize(list6.size(), gridviewWidth, itemWidth);
//        mastgridview.setAdapter(adapter);
        try {
            creatView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void creatView() {

        final LinearLayout lin = (LinearLayout) findViewById(R.id.mainLinearLayout);
        // 获取需要添加的布局
//       LinearLayout layout = (LinearLayout)getLayoutInflater().inflate(
//               R.layout.goods_add_zhuti, null).findViewById(R.id.goods_add_zhuti_lin);
//       mastgridview =layout.findViewById(R.id.mastgridview);
//       theme_image = layout.findViewById(R.id.theme_image);
//       theme_title = (TextView) layout.findViewById(R.id.theme_title);
//       more = layout.findViewById(R.id.more);

        try {
            for (int i = 0; i < themelist.size(); i++) {
                List<TopGoodsBean> list6 = new ArrayList<>();
                if (themegoodslist != null && themegoodslist.size() == 0)
                    return;
                if (themegoodslist.size() >= 6) {
                    list6.addAll(themegoodslist.get(i).subList(0, 6));
                } else {
                    list6.addAll(themegoodslist.get(i));
                }

                LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(
                        R.layout.goods_add_zhuti, null).findViewById(R.id.goods_add_zhuti_lin);
                FrameLayout.LayoutParams params=
                        new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,0,0,15);
                layout.setLayoutParams(params);
                mastgridview = layout.findViewById(R.id.mastgridview);
                theme_image = layout.findViewById(R.id.theme_image);
                imageView_lin = layout.findViewById(R.id.imageView_lin);
                theme_title = (TextView) layout.findViewById(R.id.theme_title);
                more = layout.findViewById(R.id.more);
                GlideUtils.getInstance().load(this, themelist.get(i).getImage_url(), theme_image);
                theme_title.setText(themelist.get(i).getName());

                DisplayMetrics dm = new DisplayMetrics();
                this.getWindowManager().getDefaultDisplay().getMetrics(dm);
                float density = dm.density;
                int gridviewWidth = (int) (list6.size() * (140 + 4) * density);
                int itemWidth = (int) (120 * density);
                adapter = new GridViewAdapter(this, list6);
                mastgridview.setDataSize(list6.size(), gridviewWidth, itemWidth);
                mastgridview.setAdapter(adapter);
                mastgridview.setId(i);
                imageView_lin.setTag(i);
                more.setTag(i);
                layout.setTag(i);
                myListener();
                lin.addView(layout);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


// 将布局加入到当前布局中

    }
//重写activity的onDestroy（）方法，停止该页面的glide的加载请求


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //GlideUtils.getInstance().loadpauseRequests(this);
    }

    private void setThemeMessage(String id) {

        ShopManager.getInstance(ThemeGoodsActivity.this).Theme_Goods_list(id, themelistd, "1");
    }

    RequestCompletion themelistd = new RequestCompletion() {
        @Override
        public void Success(Object value, String tag) {
            if (tag.equals("0")) {
                if (value != null) {
                    themelist.addAll((List<GoodsThemeBean>) value);
                    requestgoodslist.addAll(themelist);
                    setThemeMessage(requestgoodslist.get(0).getId());
                }
            } else {
                requestgoodslist.remove(0);
                if(requestgoodslist.size()>0){
                    setThemeMessage(requestgoodslist.get(0).getId());
                    themegoodslist.add((List<TopGoodsBean>) value);
                }else{
                    themegoodslist.add((List<TopGoodsBean>) value);
                    setlistValue();
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
    public void onLeftClick() {
        finish();

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }
}
