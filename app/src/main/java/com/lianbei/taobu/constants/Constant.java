package com.lianbei.taobu.constants;


import android.content.Context;
import android.telephony.TelephonyManager;

import com.lianbei.taobu.application.GlobalApplication;
import com.lianbei.taobu.utils.GLog;

/**
 * 全局常量配置
 */
public class Constant {
    /**
     * 动态图片服务器路径
     */
    public static final String DYNAMIC_IMG = "img/dynamic";
    /**
     * 组队头像
     */
    public static final String ITEMICON_IMG = "img/itemicon";

    /**
     * 佣金比例 千分比
     */
    public static final double MAKE_MONEY_RATE = 0.5;
    /**
     * 糖豆兌換比率  千分比 1元 = 10 糖豆
     */
    public static final double MAKE_TANDOU_RATE = 10;
    /**
     *
     */
    public static final String PDD_PID_HOT = "8691118_118079522";

    public static final String SEARCH_GROUP = "search_group";
    public static final String My_GROUP = "my_group";


    /**
     * 发版注意事项
     *  市场版本：
     * 1，注意修改 IS_STORE   发布市场：true 市场 ，   false：不属于市场包 不显示
     * 2，注意修改统计的市场渠道在 清单文件中 <meta-data android:value="Wandoujia" android:name="UMENG_CHANNEL"/>
     *     qq 360    ali   baidu
     * 3. 注意修改   APP_STORE 发布市场名称 本地不需要关注
     * 4.注意修改  GlobalApplication extends Application
     *
     * 线下版本：
     * 1.ISMARKET = false
     * 2.统计渠道写： lianbei
     * 3.清除掉依赖包：【majiawearher】      注释掉的  搜索到放开或者注释掉   //majia001
     * 4，如果发布线下版本（本地服务器）,则不需要 依赖包 majiawearher  ，去掉【majiawearher】重新编译发版
     * 5.注意修改  GlobalApplication extends Application
     */
    /**
     * 发版修改内容start
     */

    /**
     * 已选中频道的json
     */
    public static final String SELECTED_CHANNEL_JSON = "selectedChannelJson";
    /**
     * w未选频道的json
     */
    public static final String UNSELECTED_CHANNEL_JSON = "unselectChannelJson";

    public static final String client_id = "f865f2475ebf495c8f29cc3b4435a68d";
    public static final String client_secret = "08646a5a9e1fdfd994265f6d5fe3ca5bcbbabe86";


    /**
     * 频道对应的请求参数
     */
    public static final String CHANNEL_CODE = "channelCode";
    public static final String CHANNEL_TYPE = "channelType";
    public static final String IS_RECOMMEND_LIST = "isRecommendList";
    public static final String CHANNEL_KEYWORD = "channel_Keyword";
    public static int ONPAGESELECTED = -1;


    public static final String JIUBAOYOU = "jiubaoyou";//9.9包邮
    public static final String HAOHUO = "haohuo";//优品好货
    public static final String YONGJIN = "yongjin";//佣金
    public static final String BAOKUAN = "baokuan";//爆款
    public static final String ZHUTI = "zhuti_list";//主题列表

    public static final String MIANDAN = "miandan";//免单
    public static final String ZHULI = "zhuli";//助力



    public  static final String SHOP_BANNER_URL_1 = "http://t16img.yangkeduo.com/pdd_oms/2019-12-23/32273f51e3a063d52a6cf0762a83573d.jpg";
    public  static final String SHOP_BANNER_URL_2 = "https://t16img.yangkeduo.com/mms_static/2019-12-01/8ea08537-5eea-4785-ae5b-9191db3fa2fc.jpg";
    public  static final String SHOP_BANNER_URL_3 = "http://t16img.yangkeduo.com/pdd_oms/2019-12-24/5d617ed69eb93335c2b11b78ccbaef5d.jpg";
    public  static final String SHOP_BANNER_URL_4 = "https://t16img.yangkeduo.com/mms_static/2019-12-01/d9fa8958-4709-48ec-9a83-134dc54d10b1.jpeg";

    public static final String ARTICLE_GENRE_VIDEO = "video";
    public static final String ARTICLE_GENRE_AD = "ad";

    public static final String TAG_MOVIE = "video_movie";

    public static final String URL_VIDEO = "/video/urls/v/1/toutiao/mp4/%s?r=%s";


    //是否显示加壳  true 市场 ，false 非市场app,本地服务器版本
    public static final boolean IS_STORE = true;
    //发布不同应用市场   old_version_qq      old_version_360        old_version_ali    old_version_baidu  old_version_test
    public static final String APP_STORE = "old_version_qq";
    /**
     * //发版修改内容end
     */

    //apk下载地址
    //public static final String APK_URL = "http://meirisubao.cqlianbei.com/install/news.apk";
    //apk名称
    public static final String APK_NAME = "mrsb.apk";

    //当前版本
    public static final int lOCAL_VERSION = 0;//

    //版本更新
    public static final String MUST_UPDATE = "0"; //强制更新
    public static final String NEXT_UPDATE = "1";//稍后再说
    public static final String NO_UPDATE = "2";//不再提醒

    //集成的广告平台
    public static final String GDT = "GDT";
    public static final String SOGOU = "SOGOU";
    public static final String BAIDU = "BAIDU";
    public static final String BAIDUFEED = "BAIDUFEED";
    public static final String NOADV = "0";
    //新闻页面广告类型
    public static String ADVType = "GDT";
    /**
     * 广点通广告参数
     */
    public static final String ADAPPID = "1107798688";   //11011525701107798688
    public static final String BannerPosID = "8080834901034337";
    public static final String InterteristalPosID = "8575134060152130849";
    public static final String SplashPosID = "8080934910214784";  //8863364436303842593
    public static final String NativePosID = "5010320697302671";
    public static final String NativeVideoPosID = "5090421627704602";
    public static final String NativeExpressPosID = "4090034990510859"; //如果选择支持视频的模版样式，请使用NativeExpressSupportVideoPosID测试广告位拉取
    public static final String NativeExpressSupportVideoPosID = "2000629911207832"; //支持视频模版样式的广告位
    public static final String ContentADPosID = "5060323935699523";
    public static final String NativeExpressZuoWen = "1030648211674732";//信息流/左文右图

    /**
     * # 视频：推荐channel_id : 20001
     * # 视频：体育channel_id : 20003
     * # 视频：娱乐channel_id : 20004
     * # 视频：新闻channel_id : 20005
     * # 视频：科技channel_id : 20006
     * # 视频：时尚channel_id : 20007
     * # 视频：搞笑channel_id : 20008
     * # 视频：文化channel_id : 20009
     * # 视频：汽车channel_id : 20010
     * # 视频：教育channel_id : 20011
     * # 视频：少儿channel_id : 20012
     * # 视频：军事channel_id : 20013
     * # 视频：动漫channel_id : 20014
     * # 视频：游戏channel_id : 20015
     * # 视频：数码channel_id : 20016
     * # 视频：旅游channel_id : 20017
     * # 视频：生活channel_id : 20018
     * # 视频：电影channel_id : 20019
     * # 视频：电视剧channel_id : 20020
     * # 视频：科学channel_id : 20021
     * # 视频：纪录片channel_id : 20022
     * # 视频：综艺channel_id : 20023
     * # 视频：美食channel_id : 20024
     * # 视频：育儿channel_id : 20025
     * # 视频：舞蹈channel_id : 20026
     * # 视频：财经channel_id : 20027
     * # 视频：音乐channel_id : 20028
     * # 视频：其他channel_id : 20029
     * # 视频：广场舞channel_id : 20030
     * 广点通视频
     */
    public static final String TUIJIAN_VIDEO = "20001";//信息流/左文右图
    public static final String TIYU_VIDEO = "20003";//信息流/左文右图

    public static String getImei(){
      //  String imei = ((TelephonyManager) GlobalApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
     //   GLog.d("getImei::"+imei);
        return "123";
    };

    /**
     * 广点通
     */
    public static final int MAX_ITEMS = 50;
    //新闻列表每次获取广告条数
    public static final int AD_LOADCOUNT = 6;    // 加载广告的条数，取值范围为[1, 10]
    public static final int AD_COUNT = 4;     //无意义 每页新闻加四次广告
    public static int FIRST_AD_POSITION = 2; // 第一条广告的位置
    public static int ITEMS_PER_AD = 4;     // 每间隔10个条目插入一条广告
    //新闻详情每次获取广告条数
    public static final int AD_NEWDETAIL_COUNT = 4;    //


    //新闻详情页
    public static final int NEWSDETAIL_COUNT = 3;    // 加载广告的条数，取值范围为[1, 10]
    public static final int NEWSDETAIL_AD_COUNT = 4;     //无意义 每页新闻加四次广告
    public static final int FIRST_NEWSDETAIL_AD_POSITION = 1; // 第一条广告的位置
    public static final int NEWSDETAIL_ITEMS_PER_AD = 6;     // 每间隔10个条目插入一条广告
    /**
     * sogou adv
     */

    public static final int REQ_AD_NUM_6 = 6;//每次获取广告个数
    public static final int REQ_AD_NUM_3 = 3;//每次获取广告个数
    public static final int REQ_AD_NUM_2 = 2;//每次获取广告个数
    public static final int REQ_AD_NUM_1 = 1;//每次获取广告个数

    public static String HOME_ADV = "GDT"; //GDT  :  SOGOU //开屏
    public static String NEWSLIST_ADV = "BAIDU"; //新闻列表广告
    public static String NEWSDETAIL_ADV = "GDT"; //新闻详情广告
    public static String BANNER_ADV = "1"; // banner广告 邀请页面 ： 0：展示广告 1：不展示广告
    public static String NEWPERSION_ADV = "1"; // 新手是否提醒： 0:提醒 1，不提醒
    public static String NEWSDETAIL_BANNER_ADV = "GDT"; //详情页面banner平台


    /**
     * 本地广告 （相关推荐里面的广告）
     */
    public static final int LOCATION_ADV_FIRST = 3; //本地第一条广告
    public static final int LOCATION_ADV_NUM = 8;//总共可添加的广告条数,
    public static final int LOCATION_ADN_INTERVAL = 3; //添加广告间隔  ,间隔可自定义。

    ///赚钱中心
    public static String MINE_MAKE_MONEY = "";//赚钱中心  0：展示   1：隐藏


    //
    public static boolean getADVIsGDT(String str) {
        if (GDT.equals(str)) {
            return true;
        }
        return false;
    }

    //广告类型
    public static String ADV_TYPE_LIST = "LIST_ADV";//消息流广告
    public static String ADV_TYPE_DETAIL = "DETAIL_ADV"; //详情广告

    public enum YPPE {
        LIST_ADV, DETAIL_ADV,
    }

    /**
     * 百度广告 账号01
     */
    //恋北/bnlianbei  百度广告appid
    public static final String BAIDU_AD_APP_IDBA = "be6b8bd9"; //返回元素广告。

    public static final String BAIDU_AD_PLACE_ID = "5966884"; //返回元素广告。
    public static final String BAIDU_BIG_AD_PLACE_ID = "5999651"; //返回元素广告。
    public static final String BAIDU_BANNER_AD_PLACE_ID = "6002260"; //新信息流模板轮播
    public static final String BAIDU_THREE_AD_PLACE_ID = "5999658"; //返回元素广告。
    public static final String BAIDU_AD_THREE_ID = "5935056"; //添加广告间隔  ,间隔可自定义。
    public static final String BAIDU_AD_onepicture_ID = "5935246"; //左文右图
    public static final String BAIDU_AD_TASK_ID = "5966888"; //返回元素广告。

    public static final String BAIDU_AD_INVITATION_ID = "6002275"; //返回元素广告。
    public static final String BAIDU_AD_NEWS_ID = "6006794"; //返回元素广告。
    public static final String BAIDU_AD_SPLASH_ID = "6011100"; //开屏广告

    /**
     * 百度广告 账号02   再战/cqlianbei
     */
    public static final String BAIDU_AD_APP_IDCQ = "e4f4d86e"; //返回元素广告。


    //用户获取金币配置
    public static int CurrentGetCoinNumber = 0;  //用户已读次数
    //app开放阅读获取金币的新闻数量
    public static int TotleCount = 0;  //提供总阅读数量


    public static float MINE_CURRENT_GOLD = 0;  //用户当前获取到的金币
    public static float MINE_CURRENT_MONEY = 0;  //用户当前现金余额
    public static float MINE_TOTAL_MONEY = 0; //累计收益

    //引导页
    public static int Gilde_num = 0;  //提供总阅读数量

    //  消息推送传递数据ID ，随意定义
    public static final String JPUSH_KEY = "zaq1xsw2cde3vfr4bgt5nhy6mju7Kki8";

    public static String JPUSH_TYPE = "";
    public static String JPUSH_ID = "";
    public static String JPUSH_URL = "";
    public static String JPUSH_DES = "";
    public static String JPUSH_REDMONEY = "";
    public static String JPUSH_ISRED = "";
    public static String JPUSH_IFREAD = "0";

    public static boolean ISSHOWJPUSH = false;

    //初始化页面
    public static int FirstSelectedPosition = 0;
    //活动弹窗
    public static String WINDOWPOP = "0";
    public static String NEWS_WINDOWPOP = "1";
    public static String VIDEO_WINDOWPOP = "2";
    public static String INVITATION_WINDOWPOP = "3";
    public static String MINE_WINDOWPOP = "4";


    //微信分享
    public static final String WXSHARE_APP_ID = "wxdfa50b4a09706648";

    //任务模块
    public static int TASK_BAIDUAD_NUM = 0;//广告任务列表每次获取广告个数
    public static int TASK_SOGOUAD_NUM = 0;//广告任务列表每次获取广告个数


    public static String REC_LOGON_OK = "com.action.login.success";//登录成功
    public static String REC_QUIT_OK = "com.action.quit";   //退出成功
    public static String REC_WITHDRAWAL_OK = "com.action.withdrawal.success";//邀请成功
    public static String REC_BINDWX_OK = "com.action.bindWx.success"; //绑定微信成功
    public static String REC_FORGETPWD_OK = "com.action.updatapwd.success"; //绑定微信成功


    public static String INITDATA = "initData";
    public static String REFRESH = "Refresh";
    public static String MOREDATA = "moreData";

    public static String PAGER_SIZE = "10";

    public static final String ACTIVITY_URL_SETTING = "/mine/user/SettingsActivity"; //设置
    public static final String ACTIVITY_URL_WRITEINVITATION = "/mine/view/WriteInvitationActivity"; //填写邀请码
    public static final String ACTIVITY_URL_H5PUBLIC = "/mine/view/h5/H5PublicActivity"; //填写邀请码
    public static final String ACTIVITY_URL_BAIDUSPLASHACTIVITY = "/mine/view/BaiduSplashActivity"; //填写邀请码
    public static final String ACTIVITY_URL_GDTSPLASHACTIVITY = "/mine/view/GDTSplashActivity"; //填写邀请码
    public static final String ACTIVITY_URL_WELCOMEACTIITY = "/mine/view/WelcomeActiity"; //填写邀请码
    public static final String ACTIVITY_URL_MAINACTIVITY = "com/qianyi/dailynews/MainActivity"; //填写邀请码

}
