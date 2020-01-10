package com.lianbei.taobu.base;

import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.lianbei.taobu.MainActivity;
import com.lianbei.taobu.MainActivity1;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.adapter.MyPagerAdapter;
import com.lianbei.taobu.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/9.
 */
public class WelcomeActiity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private MyPagerAdapter myPagerAdapter;
    private int[] res={R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3,R.mipmap.guide_4};
    private List<ImageView> imageViews;
    private boolean isExit = false;

    @Override
    public int getContentViewId() {
        return  R.layout.activity_welcome;
    }

    @Override
    public void initBeforeView() {
        super.initBeforeView ( );
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StatusBarUtil.setStatusBarModeNoTitle ( this );
    }
    @Override
    public boolean enableSlideClose() {
        return false;
    }
    @Override
    public void initViews() {
        imageViews=new ArrayList<>();
        for (int i = 0; i <res.length; i++) {
            ImageView imageView=new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(res[i]);
            imageViews.add(imageView);
        }
        myPagerAdapter=new MyPagerAdapter(this,imageViews);
        viewpager.setAdapter(myPagerAdapter);
    }
    @Override
    public void initData() {

    }
    @Override
    public void initListener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click ( );
            return true;
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (getClass ( ).getName ( ).equals ( MainActivity1.class.getName ( ) )) {
            if (isExit == false) {
                isExit = true; // 准备退出
                Toast.makeText ( this, "再按一次，退出程序", Toast.LENGTH_SHORT ).show ( );
                tExit = new Timer ( );
                tExit.schedule ( new TimerTask ( ) {
                    @Override
                    public void run() {
                        isExit = false; // 取消退出
                    }
                }, 2000 ); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            } else {
               // ListActivity.closeAll ( );
                finish ( );
            }
        } else {
            finish ( );
        }
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
