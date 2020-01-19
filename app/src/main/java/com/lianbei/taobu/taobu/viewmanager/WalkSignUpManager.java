package com.lianbei.taobu.taobu.viewmanager;

import android.content.Context;
import android.util.Log;

import com.lianbei.taobu.taobu.adapter.WalkSignUpTagAdapter;
import com.lianbei.taobu.taobu.model.WalkAllNeedBean;
import com.lianbei.taobu.taobu.model.WalkApplyBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WalkSignUpManager {
    private Context mContext;
    private WalkSignUpTagAdapter walkSignUpTagAdapter;
    private  onClickListener mListener = null;
    private volatile static WalkSignUpManager singWithdrawalsManager;
    private WalkApplyBean walkBean;
    private WalkAllNeedBean walkAllNeedBean = new WalkAllNeedBean ( );
    private RecyclerView recyclerView = null;

    public WalkSignUpManager(){
    }
    public WalkSignUpManager(Context context){
        this.mContext = context;
    }

    public void getWithdrawalAllNeed(Context context, RecyclerView tagRecyclerView, onClickListener ClickListener) {
        this.mContext = context;
        this.mListener = ClickListener;
        this.recyclerView = tagRecyclerView;
        initData();
    }

    private void initData() {

    List<WalkApplyBean> walkBeanList = new ArrayList <> (  );

        walkBean = new WalkApplyBean ();
        walkBean.setSugarBean (0 );
        walkBean.setStepnumber (8000);
        walkBean.setInvitation ( 3 );
        walkBean.setTeamtype ( 1 );

        walkBeanList.add ( walkBean );
        walkBean = new WalkApplyBean ();
        walkBean.setSugarBean ( 5);
        walkBean.setStepnumber (3000);
        walkBean.setTeamtype ( 2 );
        walkBeanList.add ( walkBean );


        walkBean = new WalkApplyBean ();
        walkBean.setSugarBean (0);
        walkBean.setStepnumber (3000 );
        walkBean.setTeamtype ( 0 );
        walkBeanList.add ( walkBean );

        walkBean = new WalkApplyBean ();
        walkBean.setSugarBean ( 10 );
        walkBean.setStepnumber (3000 );
        walkBean.setTeamtype ( 0 );
        walkBeanList.add ( walkBean );

        walkBean = new WalkApplyBean ();
        walkBean.setSugarBean ( 20 );
        walkBean.setStepnumber (5000 );
        walkBean.setTeamtype ( 0 );
        walkBeanList.add ( walkBean );

        walkBean = new WalkApplyBean ();
        walkBean.setSugarBean ( 50 );
        walkBean.setStepnumber ( 8000);
        walkBean.setTeamtype ( 0 );
        walkBeanList.add ( walkBean );

        walkBean = new WalkApplyBean ();
        walkBean.setSugarBean ( 100 );
        walkBean.setStepnumber ( 12000);
        walkBean.setTeamtype ( 0 );
        walkBeanList.add ( walkBean );

        walkBean = new WalkApplyBean ();
        walkBean.setSugarBean ( 150 );
        walkBean.setStepnumber ( 20000 );
        walkBean.setTeamtype ( 0 );
        walkBeanList.add ( walkBean );

        walkAllNeedBean.setData (walkBeanList);
        setRecyclerView();
    }


    private  void setRecyclerView() {
        try {
            Log.e ( "recyclerView666",recyclerView+"" );
            GridLayoutManager layoutManage = new GridLayoutManager( mContext, 3 );
            recyclerView.setLayoutManager ( layoutManage );
            walkSignUpTagAdapter = new WalkSignUpTagAdapter ( walkAllNeedBean.getData () );
            walkSignUpTagAdapter.setmListener ( new WalkSignUpTagAdapter.onClickListener ( ) {
                @Override
                public void mClick(WalkApplyBean bean) {
                    if(mListener != null){
                        mListener.mClick ( bean );
                    }
                }
            } );

            recyclerView.setAdapter ( walkSignUpTagAdapter );
        } catch (Exception e) {
            e.printStackTrace ( );
        }

    }

    //获取1元提现任务情况
    public interface WithdrawalsRequestListener{
        void listener(String model);
    }



    public onClickListener getmListener() {
        return mListener;
    }
    public void setmListener(onClickListener mListener) {
        this.mListener = mListener;
    }
    public interface onClickListener {
        void mClick(WalkApplyBean bean);
        void beanlist(List <WalkApplyBean> list);
    }


}
