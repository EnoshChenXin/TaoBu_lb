package com.lianbei.taobu.base;

import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbei.taobu.MainActivity;
import com.lianbei.taobu.MainActivity1;
import com.lianbei.taobu.R;
import com.lianbei.taobu.utils.SPUtils;
import com.lianbei.taobu.utils.StatusBarUtil;
import com.lianbei.taobu.utils.flyn.Eyes;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class SplashCommonActivity extends BaseActivity {

    @BindView( R.id.splash_container )
    ViewGroup container;
    @BindView ( R.id.skip_view )
    TextView skipView;
    @BindView ( R.id.splash_holder )
    ImageView splashHolder;
    @BindView ( R.id.app_logo )
    ImageView  applogo;
    @Override
    public int getContentViewId() {
        return R.layout.activity_splash_common ;
    }
    @Override
    public boolean enableSlideClose() {
        return false;
    }
    @Override
    public void initBeforeView() {
        Eyes.translucentStatusBar(this,false);//
        super.initBeforeView ( );

    }

    @Override
    public void initViews() {
        startAnim();
    }

    private void startAnim() {
        // 动画集合
        AnimationSet set = new AnimationSet ( false );
        // 渐变动画
        AlphaAnimation alpha =  new AlphaAnimation ( 1, 0);
        alpha.setDuration ( 1000 );// 动画时间
        alpha.setFillAfter ( true );// 保持动画状态
        alpha.setRepeatCount ( 0 );
       /* set.addAnimation(rotate);
        set.addAnimation(scale);*/
        set.addAnimation ( alpha );
        set.setRepeatCount ( 0 );
        set.setAnimationListener ( new Animation.AnimationListener ( ) {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {
                        //其他地方处理
                mHandler.sendEmptyMessage ( 0 );
            }
        } );
        applogo.startAnimation ( set );
    }
    private final NotLeakHandler mHandler = new NotLeakHandler ( this );
    private class NotLeakHandler extends Handler {
        private WeakReference <SplashCommonActivity> mWeakReference;

        public NotLeakHandler(SplashCommonActivity reference) {
            mWeakReference = new WeakReference <SplashCommonActivity> ( reference );
        }

        @Override
        public void handleMessage(Message msg) {
            SplashCommonActivity reference = (SplashCommonActivity) mWeakReference.get ( );
            if (reference == null) { // the referenced object has been cleared
                return;
            }
            if (msg.what == 0) {
                SPUtils.put ( "isShell", false );
                //jumpActivity(GDTSplashActivity.this,WelcomeActiity.class);
                // 判断之前有没有显示过新手引导
                boolean isFirst = (boolean) SPUtils.get ( "isFirst", true );
                if (isFirst) {
                    jumpActivity (SplashCommonActivity.this, WelcomeActiity.class );
                } else {
                    jumpActivity (SplashCommonActivity.this, MainActivity1.class );
                    // ARouter.getInstance().build(Constant.ACTIVITY_URL_MAINACTIVITY).navigation();
                }
                finish ();
            } else if(msg.what == 1){
                //majia001
                // jumpActivity ( GDTSplashActivity.this, WeatherMainActivity.class );//WeatherMainActivity
            }else if( msg.what == 2){
                new Handler ( ).postDelayed ( new Runnable ( ) {
                    @Override
                    public void run() {
                        if(applogo != null){
                            applogo.clearAnimation();
                        }
                    }
                }, 1000);

            }{

            }
        }
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
