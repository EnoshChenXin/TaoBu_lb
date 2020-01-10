package com.lianbei.taobu.base;

import com.lianbei.httplbrary.utils.NetworkAvailableUtils;
import com.lianbei.taobu.R;
import com.lianbei.taobu.application.GlobalApplication;


/**
 * 配置空页面文件，在页面中设置图片和文字或者自定义空页面样式
 * Created by HASEE on 2017/4/10.
 */
public class NavigationViewUtil {
    public NavigationView navigationView;

    /**
     * 页面无内容显示
     *
     * @param navigationView NavigationView
     */
    public static void setNotMessageView(NavigationView navigationView) {
        navigationView.setMessageViewVisibility(true);
        navigationView.isErrorButtonVisibility(false);
        String msg = "页面暂无内容";
        String titleMsg = navigationView.getTitleText();
        int drawable = R.mipmap.wuneirongyemian;
        if (titleMsg.equals(getString(R.string.NAVIGATION_TITLE_NEWS))) { //车辆列表
            msg = "车辆列表数据为空";
        } else if (titleMsg.equals(getString(R.string.NAVIGATION_TITLE_CIRCLE))) { //当前订单页面
            msg = "您暂时还没有订单哦";
        } else if (titleMsg.equals(getString(R.string.NAVIGATION_TITLE_INVITATION))) {  //历史订单页面
            /**
             * 设置自定义xml布局文件......
             navigationView.setCustomViewVisibility(true);
             navigationView.setNaigationLayoutView(R.layout.activity_cjedit_person);
             drawable = R.drawable.meiyouneirong_icon;
             **/
            msg = "您暂时还没有订单哦";
        } else if (titleMsg.equals(getString(R.string.NAVIGATION_TITLE_MINE))) { //押金明细
            msg = "您当前没有缴费记录哦";
        } else {
            msg = "页面暂无内容";
        }
        navigationView.setMessageViewText(msg);
        navigationView.setCenter_drawable(drawable);
    }

    private static String getString(int path) {
        return (GlobalApplication.getApplication ().getString(path));
    }

    /**
     * 网络或请求服务异常
     *
     * @param navigationView NavigationView
     */
    public static void setErrorView(NavigationView navigationView) {
        navigationView.setMessageViewVisibility(true);
        navigationView.isErrorButtonVisibility(true);
        navigationView.setMessageViewText( NetworkAvailableUtils.NETWORK_TIMEOUT);
        navigationView.setCenter_drawable(R.mipmap.queshengye_wuwangluo);
    }

}
