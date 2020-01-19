package com.lianbei.taobu.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lianbei.httplbrary.utils.ApiRequestParamInterface;
import com.lianbei.taobu.constants.GlobalRequestManage;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;


/**
 * Created by SilenceDut layout_toolbaron 16/10/15.
 */

public abstract class BaseFragment extends LazyLoadFragment implements BaseViewInit , ApiRequestParamInterface {
    protected final String TAG = this.getClass().getSimpleName();
    protected FragmentActivity mActivity;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible = false;
    /**
     * Fragment的view是否已创建
     */
    protected boolean mIsViewCreated = false;


    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //Router.instance().register(this);
        initBeforeView();
    }

    @Override
    public void initBeforeView() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
       // Router.instance().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        initData ();
        initListener ();
        initDataObserver();
        mIsViewCreated = true;

        isViewInitiated = true;
        prepareFetchData();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ( );
        mIsViewCreated = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint ( isVisibleToUser );
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
        if (!mIsViewCreated)//view没有创建的时候不进行操作
            return;

        if (getUserVisibleHint()) {
            if (!isVisible) {//确保在一个可见周期中只调用一次onVisible()
                isVisible = true;
                onVisible();
            }
        } else {
            if (isVisible) {
                isVisible = false;
                onHidden();
            }
        }
    }
    /**
     * 可见
     */
    protected void onVisible() {

    }

    /**
     * fragment不可见的时候操作,onPause的时候,以及不可见的时候调用
     */
    protected void onHidden() {

    }
    //在这个方法中写网络请求
    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
    @Override
    public void onResume() {
        super.onResume ( );
        if (isAdded() && !isHidden()) {//用isVisible此时为false，因为mView.getWindowToken为null
            onVisible();
            isVisible = true;
        }
    }

    @Override
    public void onPause() {
        if (isVisible()||isVisible) {
            onHidden();
            isVisible = false;
        }
        super.onPause ( );
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged ( hidden );
        System.out.println(TAG + "--Base onHiddenChanged:" + hidden);
        if (!hidden) {
            onVisible();
            isVisible = true;
        } else {
            onHidden();
            isVisible = false;
        }
    }

    protected void initDataObserver() {

    }
    @Override
    public String RequestParam() {
        return null;
    }

    @Override
    public Map<String, String> parammap() {
        return null;
    }

    @Override
    public void result(Object object) {
        GlobalRequestManage.getInstance(this.getActivity ()).apiResult(object);
    }

}
