package com.lianbei.taobu.taobu.viewmanager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Fail;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.api.TBParam;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.model.Clockinfo;
import com.lianbei.taobu.mine.model.MemberInfo;
import com.lianbei.taobu.mine.model.MemberUserInfo;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.taobu.model.CommodityBean;
import com.lianbei.taobu.taobu.model.DynamicBean;
import com.lianbei.taobu.taobu.model.ServerImageUrl;
import com.lianbei.taobu.utils.GsonTypeAdapter;
import com.lianbei.taobu.utils.MGson;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.Validator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

public class TaoBuManager {

    private Activity mContext;

    public TaoBuManager( Activity mContext){
        this.mContext = mContext;
    }
    private static volatile TaoBuManager instance = null;

    private TaoBuManager(){
    }

    public static TaoBuManager getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (TaoBuManager.class) {
                if (instance == null) {
                    instance = new TaoBuManager();
                }
            }
        }

        return instance;
    }

    /**
     * 签到
     * @param completion
     */
    public void SignIn(RequestCompletion completion,String tag){
        Map<String,Object> map = new HashMap <> (  );
        new HttpUtil.Builder ( APIs.CLOCK_CLOCK )
                .Params ( TBParam.MapParam ( map ) )
                .isShowLoadProgess ( true,false )
                .Tag (mContext )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ();
                        MemberInfo memberInfo = gson.fromJson(model, MemberInfo.class);
                        if(memberInfo != null){
                            completion.Success (memberInfo,tag);
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                completion.Error ( values[0] ,"");
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {
                completion.Fail (model+"");
            }
        } ).post ();
    }


    /**
     * 获取打卡记录
     * @param completion
     */
    public void GetCLOCKContent(RequestCompletion completion,String tag) {
        this.requestCompletion = completion;
        Map<String,Object> map = new HashMap <> (  );
        new HttpUtil.Builder ( APIs.USER_CLOCK )
                .Params (TBParam.MapParam ( map) )
                .isShowLoadProgess (true,false )
                .Tag (mContext )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson =new MGson (  ).Gson ();
                        MemberInfo memberInfo =gson.fromJson (model,MemberInfo.class );
                        if(Validator.isNotNull ( memberInfo.getReturnData () ) && memberInfo.getCode () == 0){
                            Clockinfo clockinfo =gson.fromJson (gson.toJson (memberInfo.getReturnData ()),Clockinfo.class);
                            if(clockinfo != null &&  clockinfo.getClock ()!= null){
                                if(clockinfo != null){
                                    completion.Success (clockinfo,tag);
                                }
                            }
                        }
                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                completion.Error ( values[0] ,"");
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {
                completion.Fail (model+"");
            }
        } ).post ();

    }

    /**
     * 获取动态内容
     * @param completion
     * @param cate cate字段1.跑圈 2.打开内容
     */
    public void getDynamicContent( Context context,RequestCompletion completion,String cate){
        this.requestCompletion = completion;
        Map<String,Object> map = new HashMap <> (  );
        map.put ( "cate",cate );
        map.put ( "page","" );
        map.put ( "limit","" );
        new HttpUtil.Builder ( APIs.MOMENTS )
                .Params ( TBParam.MapParam ( map ) )
                .Tag (context )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson =new MGson (  ).Gson ();
                        MemberInfo memberInfo =gson.fromJson (model, MemberInfo.class );
                        if(memberInfo != null){
                            if(memberInfo.getCode () == 0){
                                DynamicBean dynamicBeanList =gson.fromJson (gson.toJson (memberInfo.getReturnData ()  ) ,DynamicBean.class );
                                if(requestCompletion != null){
                                    requestCompletion.Success ( dynamicBeanList,"" );
                                }
                            }
                        }
                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {

            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();

       /**
        ArrayList<String> list = new ArrayList <> (  );
        list.add ( "http://nx.lbeizxw.com/image/wap/jieti8.jpg" );
        List <DynamicBean> dynamicBeanList = new ArrayList <> ( );
        DynamicBean dynamicBean = new DynamicBean ();
        dynamicBean.setType ( "0" );
        dynamicBean.setNickName ( "新" );
        dynamicBean.setClocknum ( "第9次打卡" );
        dynamicBean.setHeadimageurl ( "http://nx.lbeizxw.com/image/wap/jieti8.jpg" );
        dynamicBean.setCriteTime ( "2019/08/2" );
        dynamicBean.setImageurl (list  );
        dynamicBean.setLikenum ( 29 );
        dynamicBean.setIslike (true);
        dynamicBean.setMessagenum ( 34 );
        dynamicBeanList.add ( dynamicBean );

        DynamicBean dynamicBean1 = new DynamicBean ();
        ArrayList<String> list1 = new ArrayList <> (  );
        list1.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list1.add ( "http://nx.lbeizxw.com/image/shop.png" );
        dynamicBean1.setType ( "1" );
        dynamicBean1.setNickName ( "yuan幕刃" );
        dynamicBean1.setClocknum ( "第3次打卡" );
        dynamicBean1.setContent ( "今天天气很不错，我要早点起来锻炼，可惜器，啦啦啦啦啦啦啦啦啦啦" );
        dynamicBean1.setHeadimageurl ( "http://nx.lbeizxw.com/image/wap/jieti8.jpg" );
        dynamicBean1.setCriteTime ( "2019/08/12");
        dynamicBean1.setImageurl (list1);
        dynamicBean1.setLikenum ( 23 );
        dynamicBean1.setIslike (false);
        dynamicBean1.setMessagenum ( 3 );
        dynamicBeanList.add ( dynamicBean1 );

        DynamicBean dynamicBean2 = new DynamicBean ();
        ArrayList<String> list2 = new ArrayList <> (  );
        list2.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list2.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list2.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list2.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list2.add ( "http://lbstorage.oss-cn-hangzhou.aliyuncs.com/img/dynamic/f31057faa5754bb9a872570da6e91106.png" );
        list2.add ( "http://nx.lbeizxw.com/image/shop.png" );
        dynamicBean2.setType ( "1" );
        dynamicBean2.setNickName ( "大大" );
        dynamicBean2.setClocknum ( "第5次打卡" );
        dynamicBean2.setContent ( "今天吃肉肉今天吃肉肉今天吃肉肉今天吃肉肉今天吃肉肉今天吃肉肉今天吃肉肉" );
        dynamicBean2.setHeadimageurl ( "http://nx.lbeizxw.com/image/wap/jieti8.jpg" );
        dynamicBean2.setCriteTime ( "2019/08/2");
        dynamicBean2.setImageurl (list2);
        dynamicBean2.setLikenum ( 23 );
        dynamicBean2.setIslike (true);
        dynamicBean2.setMessagenum ( 3 );
        dynamicBeanList.add ( dynamicBean2 );

        DynamicBean dynamicBean3 = new DynamicBean ();
        ArrayList<String> list3 = new ArrayList <> (  );
        list3.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list3.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list3.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list3.add ( "http://nx.lbeizxw.com/image/shop.png" );
        dynamicBean3.setType ( "0" );
        dynamicBean3.setNickName ( "李杰" );
        dynamicBean3.setClocknum ( "第35次打卡" );
        dynamicBean3.setHeadimageurl ( "http://nx.lbeizxw.com/image/wap/jieti8.jpg" );
        dynamicBean3.setCriteTime ( "2019/09/12");
        dynamicBean3.setImageurl (list3);
        dynamicBean3.setLikenum ( 3 );
        dynamicBean3.setIslike (true);
        dynamicBean3.setMessagenum ( 36 );
        dynamicBeanList.add ( dynamicBean3 );

        DynamicBean dynamicBean4 = new DynamicBean ();
        ArrayList<String> list4 = new ArrayList <> (  );
        list4.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list4.add ( "http://lbstorage.oss-cn-hangzhou.aliyuncs.com/img/dynamic/f31057faa5754bb9a872570da6e91106.png" );
        list4.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list4.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list4.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list4.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list4.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list4.add ( "http://nx.lbeizxw.com/image/shop.png" );
        list4.add ( "http://nx.lbeizxw.com/image/shop.png" );
        dynamicBean4.setType ( "0" );
        dynamicBean4.setNickName ( "李杰000001" );
        dynamicBean4.setClocknum ( "第35次打卡" );
        dynamicBean4.setHeadimageurl ( "http://nx.lbeizxw.com/image/wap/jieti8.jpg" );
        dynamicBean4.setCriteTime ( "2019/09/12");
        dynamicBean4.setImageurl (list4);
        dynamicBean4.setLikenum ( 36 );
        dynamicBean4.setIslike (true);
        dynamicBean4.setMessagenum ( 6 );
        dynamicBeanList.add ( dynamicBean4 );

        if(requestCompletion != null){
            requestCompletion.Success ( dynamicBeanList,"" );
        }
        **/

    }


    /**
     * 我的热门发内容
     * @param completion
     * @param ser
     */
    public void GetCommodityContent(RequestCompletion completion,String ser) {
            this.requestCompletion = completion;
            List <CommodityBean> commodityBeans = new ArrayList <> ( );
            CommodityBean commodityBean = new CommodityBean ();
            commodityBean.setTitle ( "嗨吃家酸辣粉红薯粉6桶*113g整箱网红速食方便面粉丝重庆风味夜宵" );
            commodityBean.setDescribe ( "兑换人数：556" );
            commodityBean.setPrice ( "869" );
            commodityBeans.add (commodityBean );


            CommodityBean commodityBean1 = new CommodityBean ();
            commodityBean1.setTitle ( "30包/8包丝飘天然竹浆本色纸巾抽纸批发整箱家用卫生纸面巾纸抽【预售：8月4日发完】" );
            commodityBean1.setDescribe ( "兑换人数：34" );
            commodityBean1.setPrice ( "189" );
            commodityBeans.add (commodityBean1 );

            CommodityBean commodityBean2 = new CommodityBean ();
            commodityBean2.setTitle ( "千丝乳酸菌小口袋面包早餐手撕蛋糕吐司好吃的网红零食品休闲小吃" );
            commodityBean2.setDescribe ( "兑换人数：456" );
            commodityBean2.setPrice ( "859" );
            commodityBeans.add (commodityBean2 );

            CommodityBean commodityBean3 = new CommodityBean ();
            commodityBean3.setTitle ( "居家凉拖鞋女夏季室内防滑男家居软底浴室洗澡家用外穿拖鞋情侣" );
            commodityBean3.setDescribe ( "兑换人数：74" );
            commodityBean3.setPrice ( "53" );
            commodityBeans.add (commodityBean3 );

            CommodityBean commodityBean4 = new CommodityBean ();
            commodityBean4.setTitle ( "正宗陕西大荔大荔冬枣新鲜应季水果冬枣脆甜冬枣鲜枣红枣1/4斤装" );
            commodityBean4.setDescribe ( "兑换人数：456" );
            commodityBean4.setPrice ( "785" );
            commodityBeans.add (commodityBean4 );

            CommodityBean commodityBean5 = new CommodityBean ();
            commodityBean5.setTitle ( "恒澍洗碗海绵擦百洁布不沾油金刚砂魔力清洁洗锅用品厨房刷碗神器" );
            commodityBean5.setDescribe ( "兑换人数：356" );
            commodityBean5.setPrice ( "435" );
            commodityBeans.add (commodityBean5 );
        if(requestCompletion != null){
            requestCompletion.Success ( commodityBeans,"" );
        }

    }

    List<File> mFileList = new ArrayList <> (  );//上传图片的文件
    List<String> imgpath = new ArrayList <> (  );//获取到的服务器图片路径
    Map<String ,String> updaynameParam  = new HashMap <> (  );;
    StringBuffer sb = null ;
    String contentStr ="";
    File file = null;

    /**
     * 更新动态内容
     * @param fileList
     *    cate字段1.跑圈内容 2.打卡内容
     * @param completion
     */
    public void uploadDynameImage(Activity context,List<File> fileList,String content,Map<String ,String> param, RequestCompletion completion){
        this.mContext =context;
        this.requestCompletion =completion;
        contentStr = content;
        updaynameParam.clear ();
        updaynameParam.putAll (param);
        sb =null;
        if(fileList != null && !fileList.isEmpty () && fileList.size ()>0){
            sb =new StringBuffer (  );
            mFileList.clear ();
            mFileList.addAll ( fileList );
            ChooseUploadFile();
        }else if(Validator.isStrNotEmpty ( content )){
            updataDynamic();
        }
    }

    private void ChooseUploadFile(){
        if(mFileList != null && !mFileList.isEmpty () && mFileList.size ()>0){
            file =mFileList.get ( 0 );
            if(file != null  && file.isFile ()){
                uploadFile(file);
            }
        }else{
            //图片上传完成
            ToastUtil.showShort (mContext,"图片上传成功"  );
            String[] sids = imgpath.toArray(new String[imgpath.size()]);
            updataDynamic();
        }
    }
    //上传图片
    private void uploadFile(File file){
        Map<String,Object> map =new HashMap <> (  );
        map.put ( "path", Constant.DYNAMIC_IMG );
        new HttpUtil.Builder ( APIs.UPLOAD_FILE )
                .Tag ( mContext )
                .Params (TBParam.MapParam ( map ))
                .File ( "file",file)
                .isShowLoadProgess ( true, false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        mFileList.remove ( 0 );
                        Gson gson =new MGson (  ).Gson ();
                        MemberInfo memberInfo =gson.fromJson ( model,MemberInfo.class );
                        if(memberInfo != null){
                            if(memberInfo.getCode () == 0){
                                ServerImageUrl serverImageUrl = gson.fromJson (gson.toJson ( memberInfo.getReturnData () ), ServerImageUrl.class );
                                imgpath.add (serverImageUrl.getUrl ()+"");
                                sb.append ( serverImageUrl.getUrl ()+"," );
                                ChooseUploadFile();
                            }
                        }
                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {

            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).upload ();
    }

    /**
     * 更新动态
     * */
    private void updataDynamic(){
        String charAt ="";
        if(sb != null && !sb.equals ( "" )){
            charAt= sb.deleteCharAt(sb.length() - 1).toString();
        }
        Map<String ,Object> map = new HashMap <> (  );
        map.put ( "content",contentStr );
        map.put ( "list" ,charAt);
        map.putAll ( updaynameParam );
        Log.e ( "moment/moments","content:--"+contentStr + "list:--"+charAt);
        new HttpUtil.Builder ( APIs.PUBLISH_MOMENTS )
                .Tag ( mContext )
                .isShowLoadProgess ( true, false )
                .Params (TBParam.MapParam (map))
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson =new MGson (  ).Gson ();
                        MemberInfo memberInfo = gson.fromJson ( model,MemberInfo.class);
                        if(memberInfo != null){
                            if(requestCompletion != null){
                                ToastUtil.showShort (mContext,memberInfo.getMsg ()+"");
                                requestCompletion.Success (memberInfo.getCode ()+"","");
                            }
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {

            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();
    }

    /**
     * 获取组队留言评论
     * @param content
     */
  public void  item_msgreply(Context context,String match_id, String msg_id, String content,RequestCompletion request){
      Map<String ,Object> map = new HashMap <> (  );
      map.put ( "match_id",match_id+"" );
      map.put ( "msg_id",msg_id+"" );
      map.put ( "content" ,content);
      new HttpUtil.Builder ( APIs.ITEM_MSGREPLY )
              .Tag ( context )
              .isShowLoadProgess ( false, false )
              .Params (TBParam.MapParam (map))
              .Success ( new Success ( ) {
                  @Override
                  public void Success(String model) {
                      Gson gson =new MGson (  ).Gson ();
                      MemberInfo memberInfo = gson.fromJson ( model,MemberInfo.class);
                      if(memberInfo != null){
                          if(request != null){
                              ToastUtil.showShort (context,memberInfo.getMsg ()+"");
                              request.Success (memberInfo.getCode ()+"","");
                          }
                      }

                  }
              } ).Error ( new Error ( ) {
          @Override
          public void Error(Object... values) {
              request.Error (  );
          }
      } ).Fail ( new Fail ( ) {
          @Override
          public void Fail(String model) {
              request.Fail (model);
          }
      } ).post ();
  }

    /**
     * 获取组队留言评论列表
     * @param content
     */
    public void  item_replylist(Context context,String match_id, String msg_id, String content,RequestCompletion request){
        Map<String ,Object> map = new HashMap <> (  );
        map.put ( "match_id",match_id+"" );
        map.put ( "msg_id",msg_id+"" );
        map.put ( "content" ,content);
        new HttpUtil.Builder ( APIs.ITEM_REPLYLIST )
                .Tag ( context )
                .isShowLoadProgess ( false, false )
                .Params (TBParam.MapParam (map))
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson =new MGson (  ).Gson ();
                        MemberInfo memberInfo = gson.fromJson ( model,MemberInfo.class);
                        if(memberInfo != null){
                            if(request != null){
                                ToastUtil.showShort (context,memberInfo.getMsg ()+"");
                                request.Success (memberInfo.getCode ()+"","");
                            }
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                request.Error (  );
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {
                request.Fail (model);
            }
        } ).post ();
    }
    /**
     * 回复/留言
     * @param content
     */
    public void  item_userreply(Context context,String id,String reply_id,String reply_user_id, String nickname, String content,RequestCompletion request){
        Map<String ,Object> map = new HashMap <> (  );
        map.put ( "moment_id",id+"" );
        map.put ( "reply_id" ,reply_id);
        map.put ( "reply_user_id" ,reply_user_id);
        map.put ( "nickname" ,nickname);
        map.put ( "content" ,content);
        new HttpUtil.Builder ( APIs.ITEM_USERREPLY )
                .Tag ( context )
                .isShowLoadProgess ( false, false )
                .Params (TBParam.MapParam (map))
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson =new MGson (  ).Gson ();
                        MemberInfo memberInfo = gson.fromJson ( model,MemberInfo.class);
                        if(memberInfo != null){
                            if(request != null){
                                ToastUtil.showShort (context,memberInfo.getMsg ()+"");
                                request.Success (memberInfo.getCode ()+"","");
                            }
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                request.Error (  );
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {
                request.Fail (model);
            }
        } ).post ();
    }
    private RequestCompletion requestCompletion = null;

    public interface RequestCompletion{
        void Success(Object value,String tag);
        void Fail(Object value);//业务上的400
        void Error(Object... values);//网络问题、超时、连不上服务器 404等
    }
}
