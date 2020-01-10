package com.lianbei.taobu.circle.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.lianbei.taobu.MainActivity;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.viewmanager.GameManager;
import com.lianbei.taobu.mine.model.MemberInfo;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.utils.clip.CircularImagesCutUtils;
import com.lianbei.taobu.views.MButton;
import com.lianbei.taobu.views.mMaterialEditText;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.OnClick;

public class CreatOrAddGroupActivity extends  BaseActivity implements View.OnClickListener {
    CircularImagesCutUtils circularImagesCutUtils;
    @BindView ( R.id.creat_btn )
    MButton creat_btn;
    @BindView ( R.id.groupName )
    mMaterialEditText groupNameEditText;
    @BindView ( R.id.switch_btn )
    Switch switch_btn;
    @BindView ( R.id.edit_text )
    EditText edit_text;
    @BindView ( R.id.lin_search )
    LinearLayout lin_search;
    @BindView ( R.id.rel_upload_photo )
    RelativeLayout upload_photo;
    @BindView ( R.id.headImageView )
    ImageView headImageView;
    boolean  makePhoto =false ;
    boolean PickAlbum =false;
    GameManager gameManager;
    String title="";
    String ispass = "";
    String remark = "";
    File iconfile= null;
    @Override
    public int getContentViewId() {
        return R.layout.activity_creat_or_add_group;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
        creat_btn.setButtonEditTextType ( new mMaterialEditText[]{groupNameEditText} );
        circularImagesCutUtils = CircularImagesCutUtils.getSingleton();

    }
    @OnClick( {R.id.creat_btn,R.id.switch_btn,R.id.lin_search ,R.id.rel_upload_photo } )
    @Override
    public void onClick(View view) {
        switch (view.getId ()){
            case R.id.creat_btn:
                if(creat_btn.isNotEmpty ()){
                    title = groupNameEditText.getText ().toString ().trim ();
                    remark =edit_text.getText ().toString ().trim ();
                    if(iconfile == null){
                        Toast.makeText(this, "为你的群组设置个头像吧", Toast.LENGTH_SHORT).show();
                    }else if(!Validator.isStrNotEmpty ( title )){
                        Toast.makeText(this, "还未设置队伍名称", Toast.LENGTH_SHORT).show();
                    }
                    else if(!Validator.isStrNotEmpty (remark)){
                        Toast.makeText(this, "你还没有为你的群组填写介绍呢", Toast.LENGTH_SHORT).show();
                    }else{
                        CommitContent();
                    }

                }
                break;
            case R.id.lin_search:
                Intent intent = new Intent ( this,SearchGroupListActivity.class );
                startActivity ( intent );
                break;
            case R.id.rel_upload_photo :
                circularImagesCutUtils.setshowPopupWindow(this,headImageView);
                circularImagesCutUtils.setMakePhotoOrpickAlbumClick ( new CircularImagesCutUtils.MakePhotoOrPickAlbum ( ) {
                    @Override
                    public boolean isMakePhoto() {
                        requestPermissions ( CreatOrAddGroupActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE} , new RequestPermissionCallBack ( ) {
                            @Override
                            public void granted() {
                                makePhoto = true;
                            }

                            @Override
                            public void denied() {
                                makePhoto = false;
                            }
                        } );

                        return makePhoto;
                    }

                    @Override
                    public boolean ispickAlbumPhoto() {
                        requestPermissions ( CreatOrAddGroupActivity.this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE} , new RequestPermissionCallBack ( ) {
                            @Override
                            public void granted() {
                                PickAlbum = true;
                            }

                            @Override
                            public void denied() {
                                PickAlbum = false;
                            }
                        } );

                        return PickAlbum;
                    }

                } );
                break;
        }

    }
    @Override
    public void initData() {
        gameManager = new GameManager ( this );

    }

    /**
     * 创建队伍
     */
    private void CommitContent(){
        gameManager.uploadItemImage ( iconfile, title, remark, ispass, new GameManager.RequestCompletion ( ) {
            @Override
            public void Success(Object value, String tag) {
                MemberInfo memberInfo = (MemberInfo)value;
                if(memberInfo.getCode () == 0){
                    // 可以用putExtra()的方法，也可以用setXXX()的方法
                    Intent intent = new Intent();
                    intent.putExtra("code", memberInfo.getCode ());
                    // 设置返回码和返回携带的数据
                    setResult( Activity.RESULT_OK, intent);
                    // RESULT_OK就是一个默认值，=-1，它说OK就OK吧
                    ToastUtil.showShort (CreatOrAddGroupActivity.this, memberInfo.getMsg ()+"" );
                    finish ();
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

    @Override
    public void initListener() {
        boolean isChecked = switch_btn.isChecked();
        ispass = isChecked?"2":"1";
//        if(isChecked){
//            Toast.makeText( this, "开启", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this, "关闭", Toast.LENGTH_SHORT).show();
//        }
        switch_btn.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener ( ) {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                ispass = isChecked?"2":"1";
                if (isChecked){
                    Toast.makeText(CreatOrAddGroupActivity. this, "开启", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CreatOrAddGroupActivity. this, "关闭", Toast.LENGTH_SHORT).show();
                }
            }
        } );
    }

    @Override
    public void onLeftClick() {
        finish ();

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        circularImagesCutUtils.onActivityResult(requestCode,resultCode,data);
        circularImagesCutUtils.OnCularImageListener(new CircularImagesCutUtils.CularImageInterface() {
            @Override
            public void CularImageInterface(Uri uri) {
                if(uri != null){
                    try {
                        iconfile = new File(new URI(uri.toString()));
                        Log.e("path---------:",uri+"");
                    } catch (URISyntaxException e) {
                        e.printStackTrace ( );
                    }
                    // updataUserHeadImage(file);
                }
            }

            @Override
            public void CularImageInterface(Bitmap bitmap) {
                headImageView.setImageBitmap(null);
                headImageView.setImageBitmap(bitmap);
                Log.e("path2---------:",bitmap+"");
               // updataUserHeadImage(file);
            }
        });
    }
    /**
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        Bitmap bt = BitmapFactory.decodeFile(url);
        return bt;
    }
    /*更新用头像*/
    private void updataUserHeadImage(File file) {

    }

}
