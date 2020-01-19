package com.lianbei.taobu.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/13.
 */

public class APIs {
  public static String SHARE_TAG="";
  public static final String APP_ID="wxdfa50b4a09706648";
  public static final String APP_SECRET="a6b2b1afae65ee801106c48b801d4ab5";
  //QQ分享
  public static final String QQShare_APP_ID = "101752859"; //获取的APPID


  public static final String APP_KEY_WEIBO="2664184092"; //微博
  public static final String REDIRECT_URL = "http://www.sina.com";
  public static final String SCOPE =
          "email,direct_messages_read,direct_messages_write,"
                  + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                  + "follow_app_official_microblog," + "invitation_write";

  //资源服务ip  35
  public static final String  MYBASESET= "http://cs.lbeizxw.com/image/tb/";

  //聚推小程序平台媒体主API
  public static final String  JUTUIMP= "https://api.jutuimp.com";

  //public static final String BASE_URL="http://lbeizxw.com";
  //public static final String BASE_URL="http://lzh.free.idcfengye.com";
  public static final String BASE_URL="http://47.99.46.147:8081";
  //拼多多
  public static final String PDD_BASE_URL= "https://gw-api.pinduoduo.com/api/router";

  //微信登录
  public static final String  WECHAT_LOGIN="login/userLogin";
  //首次登录奖励
  public static final String  USER_REG_COIN= "coin/userRegCoin";
  //打卡记录
  public static final String  USER_CLOCK="clock/clockList";
  //签到
  public static final String  CLOCK_CLOCK= "clock/userclock";
  //绑定邀请码
  public static final String  SHIP_BINDUSER= "ship/bindUser";
  //上传图片新增cate字段1.跑圈 2.打开内容
  public static final String  UPLOAD_FILE= "upload/uploadFile";
  //发布动态 新增cate字段1.跑圈 2.打开内容
  public static final String  PUBLISH_MOMENTS= "moment/publishMoments";

  //moments是查询跑圈内容列表的  新增cate字段1.跑圈 2.打开内容
  public static final String  MOMENTS="moment/moments";
  //热门动态
  public static final String  PUBLISH_HOTLIST="user/hotList";
  //我的组队
  public static final String  TEAM_TEAMLIS="team/teamList";
  //发起组队
  public static final String  TEAM_COMBOTEAM= "team/comboTeam";
  //组队搜索
  public static final String  ITEM_SEARCH= "team/search";
  //组队详情
  public static final String  ITEM_TEAMINFO= "team/teamInfo";
  //组队留言
  public static final String  ITEM_SETMSG="team/setMsg";
  //组队留言列表
  public static final String  ITEM_MSGLIST="team/msgList";
  //组队留言回复列表
  public static final String  ITEM_REPLYLIST="team/replyList";

  //留言评论
  public static final String  ITEM_MSGREPLY="team/msgReply";

  //入队申请
  public static final String  ITEM_APPLY= "team/applyTeam";

  //入队申请列表
  public static final String  ITEM_LINELIST="team/lineList";

  //入队审核
  public static final String  ITEM_HASPASS="team/hasPass";

  //打卡 / 回复列表
  public static final String  REPLY_REPLYLIST="reply/replyList";

  //回复文章
  public static final String  ITEM_USERREPLY="reply/userReply";
  //删除回复
  public static final String  ITEM_DELETEREPLY="reply/deleteReply";


  /**
   * 拼多多
   */

  public static final String   PDD_OPT_GET= "pdd.goods.opt.get";    //查询商品标签列表
  public static final String   PDD_CATS_GET= "pdd.goods.cats.get";    //获取拼多多标准商品类目信息（请使用pdd.goods.authorization.cats接口获取商家可发布类目）
  public static final String   PDD_THEME_LIST_GET= "pdd.ddk.theme.list.get";//多多进宝主题列表查询
  public static final String   PDD_THEME_GOODS_SEARCH= "pdd.ddk.theme.goods.search";//多多进宝主题列表查询
  public static final String   PDD_TOP_GOODS= "pdd.ddk.top.goods.list.query";//多多客获取爆款排行商品接口
  public static final String   PDD_GOODS_DETAIL= "pdd.ddk.goods.detail";//多多客获取爆款排行商品接口
  public static final String   PDD_PID_GENERATE= "pdd.ddk.goods.pid.generate";//创建多多进宝推广位
  public static final String   PDD_RESOURCE_URL_GEN= "pdd.ddk.resource.url.gen";//生成多多进宝频道推广

  //单品推广
  public static final String   PDD_URL_GENERATE= "pdd.ddk.goods.promotion.url.generate";//多多进宝推广链接生成
  public static final String   PDD_UNIT_URL_GEN= "pdd.ddk.goods.zs.unit.url.gen";//多多进宝转链接口

  public static final String   PDD_RP_PROM_URL_GENERATE= "pdd.ddk.rp.prom.url.generate";//生成营销工具推广链接
  public static final String   PDD_GOODS_SEARCH= "pdd.ddk.goods.search"; //多多进宝商品查询,根据opt查询
  public static final String   PDD_ORDERLIST_RANGE= "pdd.ddk.order.list.range.get"; //多多进宝商品查询,根据opt查询
  public static final String   PDD_TIME_GET= "pdd.time.get"; //（获取拼多多系统时间）
  public static final String   PDD_BASIC_INFO_GET= "pdd.ddk.goods.basic.info.get"; //（获取商品基本信息接口）

  public static final String   PDD_GOODS_RECOMMEND_GET= "pdd.ddk.goods.recommend.get"; //运营频道商品查询API



  //每日分享
  public static final String DAILY_SHARE_URL=BASE_URL+"share/everyday/info.html?id=";
  public static final String DOWN_SHARE_URL=BASE_URL;
  public static final String INCOME_SHOW=BASE_URL+"share/index.html?id=";
  public static final String QQ_SHARE_LOGO=BASE_URL+"images/logo.png";

  public static final String PAGE_SIZE="10";
  public static final int SUCCESS_CODE=0;
  public static final String SUCCESS="SUCCESS";
  public static final String FAIL="FAIL";

  public static final String  INIT_DATA = "InitData";
  public static final String  MORE_DATA  ="MoreData";
  public static final String  REFRESH_DATA ="RefreshData";


  public static final String CONFIRMCODE=BASE_URL+"/api/user/sendRegisterCode";
  public static final String REGISTER="api/user/register";
  public static final String LOGIN="api/user/login";
  public static final String VIDEO_LIST="api/videos/getComment";


  public  static final String UNKOWN_CODE="0001";//未知
  public  static final String USER_ALREADY_EXISTS_CODE="0003";//已存在
  public  static final String USER_UNFINDABLE_CODE="0002";//用户不存在
  public  static final String MESSAGE_UNFINDABLE_CODE="0004";//验证码不存在
  public  static final String MESSAGE_PAST_DUE_CODE="0005";//验证码过期
  public  static final String MESSAGE_MISTAKE_CODE="0006";//验证码过去
  public  static final String READ_CODE_FAIL = "0007";//阅读错误

  //新闻详情域名地址
  public static final String NEWS_URL="http://www.cqlianbei.com/getHtml?id=";
  //新闻分享链接域名地址
  public static final String NEWS_SHARE_URL="http://www.cqlianbei.com/shareHtml?id=";
  //忘记密码发送验证码
  public static final String FORGETPWD_CONFIRM_CODE= "api/user/sendForgetCode";
  //修改密码
  public static final String UPDATE_PWD= "api/user/updatePasswd";
  //获取新闻title
  public static final String NEWS_TITLES ="api/news/types";
  //获取新闻条目
  public static final String NEWS_CONTENTS = "api/news/home";
  //邀请
  public static final String INVITE_BANNER = "api/news/getBanner";
  //召回徒弟列表
  public static final String CALL_BACK_FRIEND_LIST ="api/news/recallList";
  //签到
  public static final String SIGN ="api/sign/do";
  //获取金币
  public static final String  GOLD_COIN="api/account/detail/page";
  //提现
  public static final String  WITHDRAWAWAL="api/account/withdraw/page";
  //视频详情
  public static final String  VIDEO_DETAIL= "api/videos/getWonderfulVideo";

  //邀请详情
  public static final String  INVITE_DETAIL= "api/news/getInviteInfo";
  //新闻热门评论
  public static final String  NEWS_COMMENT= "api/news/getComment";
  //发表热门评论
  public static final String  PUBLISH_NEWS_COMMENT="api/news/setComment";
  //评论点赞
  public static final String  COMMENT_LIKE="api/news/likeComment";
  //获取相关推荐
  public static final String  NEWS_WONDERFUL_REMMOND= BASE_URL+"/api/news/getWonderfulNews";
  //获取相一条新闻的详情
  public static final String  NEWS_ONE= BASE_URL+"/api/news/getCommentDetail";
  //新闻内容简介
  public static final String  NEWS_CONTENT= "api/news/getHTMLContent";
  //获取新闻奖励数量
  public static final String  NEWS_REWARD= BASE_URL+"/api/news/getRewardsCnt";
  //阅读新闻
  public static final String  READ_NEWS="api/news/readNews";
  //阅读新闻后获得奖励
  public static final String  GET_REWARD_AFTER_READ_NEWS="/api/news/getReward";
  //活动专区
  public static final String  ACTIVITY_ZONE="/api/newer/mission";
  //新手活动提交答案
  public static final String  GREEN_HAND_QUESTION= "api/answer/mission";
  //签到状态查询
  public static final String  SIGN_STATE= "api/sign/init";
  //修改密码
  public static final String  MODIFY_PWD= "api/user/updatePasswd2";
  //徒弟列表
  public static final String  TUDI_LIST= "api/news/tudiList";
  //每日分享前调用
  public static final String  SHARE_PRE= BASE_URL+"/api/share/init";
  public static final String  SHARE_DAILYSHARE_PRE="api/dayshare/init";
  //每日分享后调用
  public static final String  SHARE_AFTER= BASE_URL+"/api/share/success";
  public static final String  SHARE_DAILYSHARE_AFTER= "api/dayshare/success";
  //晒收入
  public static final String  ISHARE_INCOME="api/show/income";
  //高额返利列表
  public static final String  FANLI_LIST= BASE_URL+"/api/makeMoney/getList";
  //高额返利任务列表
  public static final String  FANLI_TASK_LIST= BASE_URL+"/api/makeMoney/makeMoneyTask";
  //轻松赚钱
  public static final String  MAKE_MONEY= BASE_URL+"/api/makeMoney/easyMoneyList";
  //轻松赚钱任务列表
  public static final String  MAKE_MONEY_LIST= BASE_URL+"/api/makeMoney/easyMoneyTask";
  //热词
  public static final String  HOT_WORD= "api/news/keywords";
  //更换热词
  public static final String  CHANGE_HOT_WORD= BASE_URL+"/api/news/nextkeywords";
  //绑定微信
  public static final String  WX_BIND= "api/wx/bind";
  //新手任务热词搜索
  public static final String  MISSSION_READ= BASE_URL+"/api/newer/search";
  //找回徒弟
  public static final String  RECALL= BASE_URL+"/api/news/recall";
  //增加播放量
  public static final String  ADD_WATCH_NUM="api/videos/addAmountOfPlay";
  //查询可提现金额
  public static final String  WITHDRAWAL_MONEY= "api/withdraw/info";
  //确认提现
  public static final String  DO_WITHDRAWAL= "api/withdraw/do";
  //提现记录
  public static final String  WITHDRAWAL_RECORD= BASE_URL+"/api/withdraw/detail/page";
  //Webview
  public static final String  WEBVIEW= "/api/user/getOfficial";
  //日常任务状态
  public static final String  DAILY_MISSION= "/api/daymission/state";
  //日常任务分享
  public static final String  DAILY_MISSION_SHARE= "/api/daymission/show";
  //
  public static final String  BIND_PHONE= "/api/wx/bindphone";
  //消息分页
  public static final String  MESSAGE_LIST= "api/message/page";
  //消息红点
  public static final String  MESSAGE_DOT= "/api/message/redot";
  //微信发送验证码
  public static final String  WX_CONFIRM_CODE= "/api/wx/sendCaptcha";
    /*//邀请规则
    public static final String  INVITE_WEBVIEW= BASE_URL+"/api/user/getOfficial?type=INVITE";
    //签到规则
    public static final String  SIGN_IN= BASE_URL+"/api/user/getOfficial?type=SIGN_IN";
    //赚钱攻略
    public static final String  MAKEMONEY= BASE_URL+"/api/user/getOfficial?type=MAKEMONEY";
    //帮助与反馈
    public static final String  HELP= BASE_URL+"/api/user/getOfficial?type=HELP";*/
  //==========辛振宇==========================================



  //阅读新闻后获得奖励g
  public static final String  WRITE_CODE= "api/news/setInviteCode";
  //更新个人信息
  public static final String  GET_USER_INFO= "api/user/getUserInfo";
  //高额返利
  public static final String  HIGH_BACK_MONEY= BASE_URL+"/api/makeMoney/getList";
  //高额返利详情
  public static final String  HIGH_BACK_MONEY_DETAILS= BASE_URL+"/api/makeMoney/detail";


  //上传图片（file）
  public static final String  UPLOAD_PICS= BASE_URL+"/api/makeMoney/uploadImage";
  //上传图片2
  public static final String  SAVE_PICS= BASE_URL+"/api/makeMoney/saveImgs";
  //截图事例
  public static final String  getMakeMoneyExample= BASE_URL+"/api/makeMoney/getMakeMoneyExample";

  //分享之前
  public static final String  easyMoneyShare= BASE_URL+"/api/makeMoney/easyMoneyShare";
  //分享拼接
  public static final String  ShareUrl= "http://47.104.73.127:8080/share/esmy.html?token=";
  //分享拼接
  public static final String  takePartInUrl= BASE_URL+"api/makeMoney/takePartIn";
  //删除新闻
  public static final String  deleteNews= BASE_URL+"api/news/removeNews";
  //增加阅读次数
  public static final String  addViewCount= BASE_URL+"api/news/addViewCount";
  //检查版本更新
  public static final String  GET_VERSION= "sys/getVersion";
  //获取广告类型
  public static final String  GET_ADVCONFIG= BASE_URL+"sys/getConfigByType";
  //获取所有提現金額列表
  public static final String  GET_WITHDRAWALL= BASE_URL+"api/user/getWithdrawAll";
  //获取所有提現金額信息
  public static final String  GET_WITHDRAWNEED="api/user/getWithdrawNeed";
  //获取個人提現信息
  public static final String  GET_USERINFOWITHDRAW="api/user/getUserInfoWithdraw";
  //获取活动列表
  public static final String  GET_ACTIVITYS= "api/activity/getActivitys";
  //用户活动操作
  public static final String  SAVE_ACTIVITYUSER= "api/activity/saveActivityUser";
  //广告奖励 阅读广告奖励
  public static final String  SAVE_ADVREWARD= BASE_URL+"api/reward/advReward";
  //获取励
  public static final String  GET_ADVINFO="api/adv/getAdvInfo";

  //保存广告阅读任务奖励
  public static final String  SAVE_ADVINFO= BASE_URL+"api/adv/saveAdvInfo";

  //保存用户设备信息
  public static final String  SAVE_DEVICEINFO= BASE_URL+"api/user/saveDeviceInfo";
  public static final String  SEND_DEVICEINFO= BASE_URL+"api/user/sendDeviceInfo";
  public static final String  HTTP_SEND_DEVICEINFO="api/user/sendDeviceInfo";


}
