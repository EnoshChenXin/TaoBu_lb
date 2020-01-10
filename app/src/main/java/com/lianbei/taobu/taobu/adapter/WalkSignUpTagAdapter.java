package com.lianbei.taobu.taobu.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.taobu.model.WalkApplyBean;

import java.util.ArrayList;
import java.util.List;

public class WalkSignUpTagAdapter extends RecyclerView.Adapter<WalkSignUpTagAdapter.ViewHolder> {
    //private List<WithdrawalAllBean> tagList;
    private List<WalkApplyBean> singBeanList;

    private boolean isSelected = false;



    private List<RelativeLayout> RelViewselectList = new ArrayList <> (  );
    private List<TextView> mTextViewlist = new ArrayList <> (  );
    private List<TextView> tagContent_tvlist = new ArrayList <> (  );
    public WalkSignUpTagAdapter(List<WalkApplyBean> singBeanList) {
        this.singBeanList = singBeanList;
    }

    @Override
    public WalkSignUpTagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.tag_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final WalkSignUpTagAdapter.ViewHolder holder, final int position) {
        WalkApplyBean bean= singBeanList.get(position);
        if(bean.getTeamtype () == 0){
            holder.mTextView.setText(bean.getSugarBean ()+"糖豆");
            holder.tagContent_tv.setText ( bean.getStepnumber ()+"步");
        }else{
            holder.hot_img.setVisibility ( View.VISIBLE );
            String mTextView =  (bean.getSugarBean () != 0 ? bean.getSugarBean ()+"糖豆":"") +
                    (bean.getInvitation () != 0 ? "好友助力(0/"+bean.getInvitation ()+")":"");
             holder.mTextView.setText ( mTextView );
            holder.tagContent_tv.setText ( bean.getStepnumber ()+"步");
        }

        holder.itemView.setTag(bean);

        holder.lin_tag.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < RelViewselectList.size (); i++) {
                        RelViewselectList.get ( i ).setBackgroundResource( R.drawable.bg_gridview2);
                        RelViewselectList.get ( i ).setSelected(false);
                        mTextViewlist.get ( i ).setTextColor ( Color.parseColor("#F18623"));
                        tagContent_tvlist.get ( i ).setTextColor ( Color.parseColor("#F18623"));
                }
                isSelected = !holder.mTextView.isSelected();
                if (isSelected) {
                    holder.lin_tag.setSelected(true);
                        holder.lin_tag.setBackgroundResource( R.drawable.bg_gridview3);
                        holder.mTextView.setTextColor ( Color.parseColor("#ffffff"));
                        holder.tagContent_tv.setTextColor ( Color.parseColor("#ffffff"));
                        RelViewselectList.add ( holder.lin_tag );
                        mTextViewlist.add ( holder.mTextView );
                        tagContent_tvlist.add ( holder.tagContent_tv );
                    if(mListener != null){
                        mListener.mClick ( singBeanList.get ( position ) );
                    }
                }
            }
        } );

    }

    @Override
    public int getItemCount() {
        return singBeanList.size ();
    }

    public List<WalkApplyBean> getTagList() {
        return singBeanList;
    }

    public void setTagList(List<WalkApplyBean> tagList) {
        this.singBeanList = tagList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView tagContent_tv;
        public RelativeLayout lin_tag;
        public ImageView hot_img;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById( R.id.sugarBean);
            tagContent_tv =(TextView) view.findViewById( R.id.step_number);
            lin_tag =(RelativeLayout) view.findViewById( R.id.lin_tag);
            hot_img =(ImageView) view.findViewById( R.id.hot_img);
        }
    }

    private onClickListener mListener = null;

    public interface onClickListener {
        void mClick(WalkApplyBean bean);
    }

    public onClickListener getmListener() {
        return mListener;
    }

    public void setmListener(onClickListener mListener) {
        this.mListener = mListener;
    }
}
