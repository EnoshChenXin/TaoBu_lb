package com.lianbei.taobu.taobu.view;

import android.net.Uri;
import android.os.Bundle;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;

public class SignHotkFragment extends BaseFragment {
    public static SignHotkFragment newInstance(String param1, String param2) {
        SignHotkFragment fragment = new SignHotkFragment ( );
        Bundle args = new Bundle ( );
        fragment.setArguments ( args );
        return fragment;
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_hot_signk;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void fetchData() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
