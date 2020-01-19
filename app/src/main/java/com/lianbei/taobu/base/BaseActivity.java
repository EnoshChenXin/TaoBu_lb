package com.lianbei.taobu.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chaychan.lib.SlidingLayout;
import com.lianbei.httplbrary.utils.ApiRequestParamInterface;
import com.lianbei.taobu.MainActivity;
import com.lianbei.taobu.MainActivity1;
import com.lianbei.taobu.utils.ActivityUtils;
import com.lianbei.taobu.constants.GlobalRequestManage;
import com.lianbei.taobu.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by SilenceDut on 16/10/15.
 */

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.onNavigationBarClickListener,BaseViewInit , ApiRequestParamInterface {
    private boolean isExit=false;
    public NavigationView navigationView;
    private boolean DEBUG = true;
    private RequestPermissionCallBack mRequestPermissionCallBack;
    private final int mRequestCode = 1024;
    private static Activity mCurrentActivity;// 对所有activity进行管理
    public static List <Activity> mActivities = new LinkedList <Activity> ();
    @Override
    protected void attachBaseContext(Context newBase) {
       super.attachBaseContext( CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Router.instance().register(this);
        //添加Activity到堆栈
        ActivityUtils.getAppManager().addActivity(this);
        initBeforeView();
        if (enableSlideClose()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
        setContentView(getContentViewId());
        ActivityManagerUtil.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initViews();
        initData();
        initListener();
        initDataObserver();
        ActivityUtils.getAppManager().checkAllActivity();
    }
    //跳转的方法
    public void jumpActivity(Context context, Class <?> targetActivity) {
        Intent intent = new Intent ( context, targetActivity );
        startActivity ( intent );
    }
    private void initARouter() {
        //ARouter
        if (DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this.getApplication()); // 尽可能早，推荐在Application中初始化
    }
    protected void initDataObserver() {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();
        initARouter();
        mCurrentActivity = this;
        //友盟统计分析——session统计
        Log.e("缓存：", GlideUtils.getInstance ().getCacheSize ( this ));
    }


    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从堆栈中移除
        ActivityUtils.getAppManager().finishActivity(this);
       // ActivityUtils.getAppManager().checkAllActivity();
        //Router.instance().unregister(this);

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
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity1){
            //如果是主页面
            exitBy2Click ( );
        }
        super.onBackPressed();// finish()
    }



    @Override
    public void initBeforeView() {
        initARouter();
    }
    public void createNavigationView(@IdRes int id){
        navigationView = (NavigationView)findViewById(id);
        navigationView.setNavigationBarClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
    public boolean enableSlideClose() {
        return true;
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
        GlobalRequestManage.getInstance(this).apiResult(object);
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
                //System.exit(0);
                // moveTaskToBack(true);
               // ListActivity.closeAll ( );
                ActivityUtils.getAppManager().AppExit(this);
                finish ( );
            }
        } else {
            finish ( );
        }
    }


    /**
     * 发起权限请求
     *
     * @param context
     * @param permissions
     * @param callback
     */
    public void
    requestPermissions(final Context context, final String[] permissions,
                                   RequestPermissionCallBack callback) {
        this.mRequestPermissionCallBack = callback;
        StringBuilder permissionNames = new StringBuilder ( );
        for (String s : permissions) {
            permissionNames = permissionNames.append ( s + "\r\n" );
        }
        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
        boolean isAllGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission ( context, permission ) == PackageManager.PERMISSION_DENIED) {
                isAllGranted = false;
                if (ActivityCompat.shouldShowRequestPermissionRationale ( (Activity) context, permission )) {
                    new AlertDialog.Builder ( BaseActivity.this ).setTitle ( "PermissionTest" )//设置对话框标题
                            .setMessage ( "【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】" +
                                    "您好，需要如下权限：" + permissionNames +
                                    " 请允许，否则将影响部分功能的正常使用。" )//设置显示的内容
                            .setPositiveButton ( "确定", new DialogInterface.OnClickListener ( ) {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    //TODO Auto-generated method stub
                                    ActivityCompat.requestPermissions ( ((Activity) context), permissions, mRequestCode );
                                }
                            } ).show ( );//在按键响应事件中显示此对话框
                } else {
                    ActivityCompat.requestPermissions ( ((Activity) context), permissions, mRequestCode );
                }
                break;
            }
        }
        if (isAllGranted) {
            mRequestPermissionCallBack.granted ( );
            return;
        }
    }
    /**
     * 权限请求结果回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
        boolean hasAllGranted = true;
        StringBuilder permissionName = new StringBuilder ( );
        for (String s : permissions) {
            permissionName = permissionName.append ( s + "\r\n" );
        }
        switch (requestCode) {
            case mRequestCode: {
                for (int i = 0; i < grantResults.length; ++i) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        hasAllGranted = false;
                        //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                        // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                        if (!ActivityCompat.shouldShowRequestPermissionRationale ( this, permissions[i] )) {
                            new AlertDialog.Builder ( BaseActivity.this ).setTitle ( "PermissionTest" )//设置对话框标题
                                    .setMessage ( "【用户选择了不再提示按钮，或者系统默认不在提示（如MIUI）。" +
                                            "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】" +
                                            "获取相关权限失败:" + permissionName +
                                            "将导致部分功能无法正常使用，需要到设置页面手动授权" )//设置显示的内容
                                    .setPositiveButton ( "去授权", new DialogInterface.OnClickListener ( ) {//添加确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                            //TODO Auto-generated method stub
                                            Intent intent = new Intent ( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
                                            Uri uri = Uri.fromParts ( "package", getApplicationContext ( ).getPackageName ( ), null );
                                            intent.setData ( uri );
                                            startActivity ( intent );
                                            dialog.dismiss ( );
                                        }
                                    } ).setNegativeButton ( "取消", new DialogInterface.OnClickListener ( ) {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//响应事件
                                    // TODO Auto-generated method stub
                                    dialog.dismiss ( );
                                }
                            } ).setOnCancelListener ( new DialogInterface.OnCancelListener ( ) {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    mRequestPermissionCallBack.denied ( );
                                }
                            } ).show ( );//在按键响应事件中显示此对话框
                        } else {
                            //用户拒绝权限请求，但未选中“不再提示”选项
                            mRequestPermissionCallBack.denied ( );
                        }
                        break;
                    }
                }
                if (hasAllGranted) {
                    mRequestPermissionCallBack.granted ( );
                }
            }
        }
    }


    /**
     * 权限请求结果回调接口
     */
    public interface RequestPermissionCallBack {
        /**
         * 同意授权
         */
        void granted();

        /**
         * 取消授权
         */
        void denied();
    }
}
