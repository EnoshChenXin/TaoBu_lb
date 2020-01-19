package com.lianbei.taobu.mine.view.shareimage;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.utils.GlideUtils;

import butterknife.BindView;

public class InvitationImageView extends BaseActivity {
    private static final String URL = "url";

    @BindView(R.id.image)
    ImageView imageView;
    @Override
    public int getContentViewId() {
        return R.layout.activity_invitation_image_view;
    }

    @Override
    public void initViews() {
      //Glide.with(this).load(getIntent().getStringExtra(URL)).into(imageView);
        GlideUtils.getInstance().load(this,URL,imageView);
    }
    public static void start(Context context, String url) {
        Intent intent = new Intent(context, InvitationImageView.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }
    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

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
}
