package com.lianbei.taobu.circle.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.circle.adapter.InformationAdapter;
import com.lianbei.taobu.circle.model.InformationBean;
import com.lianbei.taobu.circle.viewmanager.GameManager;
import com.lianbei.taobu.taobu.adapter.GridViewAdapter;
import com.lianbei.taobu.taobu.model.CommodityBean;
import com.lianbei.taobu.taobu.view.SignActivity;
import com.lianbei.taobu.taobu.view.WalkActivity;
import com.lianbei.taobu.taobu.view.viewutils.MastGridView;
import com.lianbei.taobu.views.h5.H5PublicActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 赛事
 */
public class GameEventFragment extends BaseFragment implements GameManager.RequestCompletion , View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private List <InformationBean> informationBeanList = new ArrayList <> ( );
    private OnFragmentInteractionListener mListener;
    GameManager gameManager;
    @BindView(R.id.mastgridview)
    MastGridView mastgridview;

    @BindView ( R.id.zaoqi_LinBtn )
    LinearLayout  zaoqi_LinBtn ;

    @BindView ( R.id.walk_relBtn )
    RelativeLayout walk_relBtn ;

    InformationAdapter adapter;
    public GameEventFragment() {
        // Required empty public constructor
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_game_event;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {
        gameManager = new GameManager ( this.getActivity () );
        gameManager.GetInformationList (this, "" );
    }

    @Override
    public void initListener() {
        mastgridview.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(GameEventFragment.this.getContext (), H5PublicActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", APIs.BASE_URL );
                intent.putExtra("title", informationBeanList.get ( i ).getTitle ()+"");
                startActivity(intent);
            }
        } );
    }

    @Override
    public void Success(Object value, String tag) {
        if(value != null){
            informationBeanList = (List<InformationBean>) value;
            DisplayMetrics dm = new DisplayMetrics();
            this.getActivity ().getWindowManager().getDefaultDisplay().getMetrics(dm);
            float density = dm.density;

            int gridviewWidth = (int) (informationBeanList.size () * (170 + 4) * density);
            int itemWidth = (int) (160 * density);
            adapter = new InformationAdapter(this.getContext (),
                    informationBeanList);
            mastgridview.setDataSize ( informationBeanList.size (), gridviewWidth,itemWidth);
            mastgridview.setAdapter(adapter);
        }

    }

    @Override
    public void Fail(Object value) {

    }

    @Override
    public void Error(Object... values) {

    }
     @OnClick({R.id.walk_relBtn,R.id.zaoqi_LinBtn})
    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.walk_relBtn://走呗
                Intent intents = new Intent (  this.getActivity (), WalkActivity.class);
                startActivity (intents );
                break;
            case R.id.zaoqi_LinBtn://早起
                Intent intent = new Intent (  this.getActivity (), SignActivity.class);
                startActivity (intent );
                break;
        }

    }

    @Override
    public void fetchData() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
