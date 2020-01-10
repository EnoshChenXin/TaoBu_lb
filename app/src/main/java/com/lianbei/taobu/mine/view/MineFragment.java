package com.lianbei.taobu.mine.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lianbei.taobu.R;
import com.lianbei.taobu.action.AdDialogManager;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.login.WXLoginActivity;
import com.lianbei.taobu.mine.model.WXUserInfo;
import com.lianbei.taobu.mine.viewmanager.UserInfoManager;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.StatusBarUtil;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.CircleImageView;
import com.lianbei.taobu.views.dialog.CustomLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    UserInfoManager userInfoManager;
    @BindView ( R.id.headImageView )
    CircleImageView headImageView;
    @BindView ( R.id.nickName )
    TextView nickName;
    @BindView ( R.id.set_Btn )
    ImageView set_Btn;

    @BindView ( R.id.message_Btn )
    ImageView message_Btn;

    @BindView(R.id.img_bt_address)
    ImageView img_bt_address;
    @BindView(R.id.img_duihuan)
    ImageView img_duihuan;

    @BindView ( R.id.invitationCode )
    TextView invitationCode;


    WXUserInfo wxUserInfo;

    private MyReceiver myReceiver;
    private String category[] = null;
    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews() {
        myReceiver=new MyReceiver();
        IntentFilter intentFilterLogin=new IntentFilter();
        intentFilterLogin.addAction(Constant.REC_LOGON_OK);
        getActivity().registerReceiver(myReceiver,intentFilterLogin);
        IntentFilter intentFilterQuit=new IntentFilter();
        intentFilterQuit.addAction(Constant.REC_QUIT_OK);
        getActivity().registerReceiver(myReceiver,intentFilterQuit);
        setValue();

    }

    @Override
    public void initBeforeView() {
        super.initBeforeView();
    }

    private void setValue(){
        wxUserInfo = WXUserInfo.getWXUserInfo ( this.getContext () );
        if(wxUserInfo.WxUserInfoIsEmpty (  this.getContext () )){
            nickName.setText ("请登录");
            invitationCode.setVisibility ( View.GONE);
        }else{
            nickName.setText (wxUserInfo.getNickname ()+"" );
            invitationCode.setVisibility ( View.VISIBLE);
        }
        GlideUtils.getInstance ().load (getActivity(),wxUserInfo.getHeadimgurl (),headImageView);

    }
    @Override
    public void initData() {
        userInfoManager = new UserInfoManager (this.getActivity ());

    }

    @Override
    public void initListener() {

    }
    @OnClick({R.id.headImageView,R.id.set_Btn, R.id.nickName,R.id.message_Btn,R.id.order ,R.id.order1,R.id.img_bt_address,R.id.img_duihuan})
    @Override
    public void onClick(View view) {
        if(wxUserInfo.WxUserInfoIsEmpty (  this.getContext () )){
            Intent intent = new Intent ( this.getActivity (), WXLoginActivity.class );
            startActivity ( intent );
            return;
        }
        switch (view.getId ()){
            case R.id.nickName:
                if(!wxUserInfo.WxUserInfoIsEmpty (  this.getContext () )){
                    return;
                }
                break;
            case R.id.set_Btn:
                Intent intentSet = new Intent ( this.getActivity (),SetActivity.class );
                startActivity ( intentSet );
                break;
            case R.id.message_Btn:
                break;
            case R.id.order:
                toIntentOrder();
                break;
            case R.id.order1:
                toIntentOrder();
                break;
            case R.id.img_bt_address:
                toIntentAddress();
                break;
            case R.id.img_duihuan:
                toIntentDuiHuan();
                break;
        }
    }
    private void toIntentOrder(){
        Intent intentSet = new Intent ( this.getActivity (),OrderActivity.class );
        startActivity ( intentSet );
    }

    private void toIntentAddress(){
        Intent intentSet = new Intent ( this.getActivity (),AddressManagerActivity.class );
        intentSet.putExtra("choose",-1);
        startActivity ( intentSet );
    }
    private void toIntentDuiHuan(){
        Intent intentSet = new Intent ( this.getActivity (),OrderDuiHuanActivity.class );
        startActivity ( intentSet );
    }

    @Override
    public void fetchData() {

    }


    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e ("action:" ,action+"");
            if(Validator.isStrNotEmpty ( action )){
                if(action.equals ( Constant.REC_LOGON_OK )){
                    setValue();
                    getRegCoin();
                }else if (action.equals ( Constant.REC_QUIT_OK )){
                    setValue();
                }
            }

        }
    }

    /**
     * 获取奖励
     */
    private void getRegCoin(){
        userInfoManager.getRegCoin ( new RequestCompletion ( ) {
            @Override
            public void Success(Object value, String tag) {
                showNewPeopleGift(value.toString ());
            }

            @Override
            public void Fail(Object value) {

            }

            @Override
            public void Error(Object... values) {

            }
        } );
    }
    /**
     *   //注册送糖豆
     */
    private void showNewPeopleGift(String beanNum) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.lay_new_register,null);
        View delete_v =contentView.findViewById(R.id.v_delete);
        TextView tv_register_bean = contentView.findViewById(R.id.tv_register_bean);
        dialog.setContentView(contentView);
        tv_register_bean.setText (beanNum);
        delete_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
