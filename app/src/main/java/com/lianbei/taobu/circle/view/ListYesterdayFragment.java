package com.lianbei.taobu.circle.view;

import android.net.Uri;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;

public class ListYesterdayFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    public ListYesterdayFragment() {
        // Required empty public constructor
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_yesterday;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
