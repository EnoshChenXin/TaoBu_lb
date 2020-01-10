package com.lianbei.taobu.circle.viewmanager;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Fail;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.api.TBParam;
import com.lianbei.taobu.circle.model.GameBean;
import com.lianbei.taobu.circle.model.GroupPoperBean;
import com.lianbei.taobu.circle.model.InformationBean;
import com.lianbei.taobu.circle.model.ItemInfoMsg;
import com.lianbei.taobu.circle.model.ItmeInfo;
import com.lianbei.taobu.circle.model.lineListBean;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.mine.model.MemberInfo;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.taobu.model.CommodityBean;
import com.lianbei.taobu.taobu.model.ServerImageUrl;
import com.lianbei.taobu.taobu.model.SignReplyBean;
import com.lianbei.taobu.taobu.viewmanager.TaoBuManager;
import com.lianbei.taobu.utils.MGson;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {
    private static Context mContext;

    private static volatile GameManager instance = null;

    private GameManager(Context context){
         mContext = context;
    }

    public static GameManager getInstance(Context context) {
        mContext =context;
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (GameManager.class) {
                if (instance == null) {
                    instance = new GameManager(context);
                }
            }
        }

        return instance;
    }

    public GameManager(Activity mContext) {
        this.mContext = mContext;
    }

    public void GetGroupGameContent(GameManager.RequestCompletion completion) {
        this.requestCompletion = completion;
        Map <String, String> map = new HashMap <> ( );
        new HttpUtil.Builder ( APIs.BASE_URL )
                .Params ( map )
                .Tag ( mContext )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {

            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ( );
    }

    /**
     * 获取组队列表
     *
     * @param completion
     * @param ser
     */
    public void GetGroupGameContent(RequestCompletion completion, String ser) {
        this.requestCompletion = completion;
        Map <String, Object> map = new HashMap <> ( );
        new HttpUtil.Builder ( APIs.TEAM_TEAMLIS )
                .Tag ( mContext )
                .Params ( TBParam.MapParam ( map ) )
                .isShowLoadProgess ( true, false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ( );
                        MemberInfo memberInfo = gson.fromJson ( model, MemberInfo.class );
                        if (memberInfo != null) {
                            String json = gson.toJson ( memberInfo.getReturnData ( ) );
                            GameBean gameBean = gson.fromJson ( json, GameBean.class );
                            if (requestCompletion != null) {
                                requestCompletion.Success ( gameBean, "" );
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
        } ).post ( );

//        List <GameBean> gameBeanList = new ArrayList <> ( );
//        GameBean gameBean = new GameBean ();
////        gameBean.setGame_title ( "巴南小组赛" );
//        gameBean.setDescribe ( "这里有你也有我，世界很美好" );
//        gameBeanList.add (gameBean );
//
//        GameBean gameBean2 = new GameBean ();
//        gameBean2.setGame_title ( "外国语大学校友跑圈" );
//        gameBean2.setDescribe ( "遇见美好的自己遇见美好的自己遇见美好的自己遇见美好的自己遇见美好的自己遇见美好的自己" );
//        gameBeanList.add (gameBean2 );
//
//        GameBean gameBean3 = new GameBean ();
//        gameBean3.setGame_title ( "每天4000步敢不敢来" );
//        gameBean3.setDescribe ( "来了就好，就怕你不敢来" );
//        gameBeanList.add (gameBean3 );
//
//        if(requestCompletion != null){
//            requestCompletion.Success ( gameBeanList,"" );
//        }
    }

    Map <String, Object> params = new HashMap <> ( );
    JSONObject jsonObject = new JSONObject ( );

    /**
     * 上传组队图片
     *
     * @param title      标题
     * @param remark     简介
     * @param ispass     是否允许直接加入xx
     * @param file
     * @param completion
     */
    public void uploadItemImage(File file, String title, String remark, String ispass, RequestCompletion completion) {
        requestCompletion = completion;
        try {
            jsonObject.put ( "title", title );
            jsonObject.put ( "remark", remark );
            jsonObject.put ( "ispass", ispass );
            jsonObject.put ( "user_id", UserInfo.getUserInfo ( mContext ).getUser_id ( ) + "" );
        } catch (JSONException e) {
            e.printStackTrace ( );
        }
        uploadFile ( file );
    }

    //上传图片
    private void uploadFile(File file) {
        Map <String, Object> map = new HashMap <> ( );
        map.put ( "path", Constant.ITEMICON_IMG );
        new HttpUtil.Builder ( APIs.UPLOAD_FILE )
                .Tag ( mContext )
                .Params ( TBParam.MapParam(map) )
                .File ( "file", file )
                .isShowLoadProgess ( true, false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ( );
                        MemberInfo memberInfo = gson.fromJson ( model, MemberInfo.class );
                        if (memberInfo != null) {
                            if (memberInfo.getCode ( ) == 0) {
                                ServerImageUrl serverImageUrl = gson.fromJson ( gson.toJson ( memberInfo.getReturnData ( ) ), ServerImageUrl.class );
                                try {
                                    jsonObject.put ( "icon", serverImageUrl.getUrl ( ) + "" );
                                    params.put ( "json", jsonObject.toString ( ) );
                                } catch (JSONException e) {
                                    e.printStackTrace ( );
                                }
                                creatGroup ( );
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
        } ).upload ( );
    }

    /**
     *创建队伍
     */
    public void creatGroup() {
        new HttpUtil.Builder ( APIs.TEAM_COMBOTEAM )
                .Tag ( mContext )
                .Params ( TBParam.MapParam(params) )
                .isShowLoadProgess ( true, false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ( );
                        MemberInfo memberInfo = gson.fromJson ( model, MemberInfo.class );
                        if (memberInfo != null) {
                            if (requestCompletion != null) {
                                requestCompletion.Success ( memberInfo, "" );
                            }
                        } else {
                            requestCompletion.Error ( memberInfo.getMsg ( ), "" );
                        }
                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                requestCompletion.Error ( values, "" );
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ( );
    }

    /**
     * 发送组队留言
     *
     * @param content 留言内容
     */
    public void ItemSendMessage(Context context,String content, String match_id, RequestCompletion completion) {
        Map<String,Object> map = new HashMap <> (  );
        map.put ( "content" ,content);
        map.put ( "match_id" ,match_id);
        new HttpUtil.Builder ( APIs.ITEM_SETMSG )
                .Tag ( context )
                .Params ( TBParam.MapParam ( map ) )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ( );
                        MemberInfo memberInfo = gson.fromJson ( model, MemberInfo.class );
                        if (memberInfo != null) {
                            if (completion != null) {
                                completion.Success ( memberInfo.getCode ()+"", "" );
                                ToastUtil.showShort ( context,memberInfo.getMsg ( ) );
                            }
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                completion.Error ( values, "" );
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {
                completion.Fail ( model );
            }
        } ).post ( );
    }


    /**
     *
     *群组讨论列表
     */
    public void ItemmsgList(int page, int limit,String match_id, RequestCompletion completion,String tag) {
        Map<String,Object> map = new HashMap <> (  );
        map.put ( "page" ,page+"");
        map.put ( "limit" ,limit+"");
        map.put ( "match_id" ,match_id);
        new HttpUtil.Builder ( APIs.ITEM_MSGLIST )
                .Tag ( mContext )
                .Params ( TBParam.MapParam ( map ) )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ( );
                        MemberInfo memberInfo = gson.fromJson ( model, MemberInfo.class );
                        if (memberInfo != null) {
                            if (completion != null) {
                                String data = gson.toJson(memberInfo.getReturnData());
                                ItemInfoMsg itemInfoMsg = gson.fromJson(data,ItemInfoMsg.class);
                                completion.Success ( itemInfoMsg, tag );
                            }
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                completion.Error ( values, "" );
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {
                completion.Fail ( model );
            }
        } ).post ( );
    }


    /**
     * 加入群组
     * @param match_id  队伍编号
     * @param completion
     */
    public void additem_apply(String match_id, RequestCompletion completion) {
        Map<String,Object> map = new HashMap <> (  );
        map.put ( "match_id" ,match_id);
        new HttpUtil.Builder ( APIs.ITEM_APPLY )
                .Tag ( mContext )
                .Params ( TBParam.MapParam ( map ) )
                .isShowLoadProgess ( true,false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ( );
                        MemberInfo memberInfo = gson.fromJson ( model, MemberInfo.class );
                        if (memberInfo != null) {
                            if (completion != null) {
                                    completion.Success ( memberInfo, "" );
                            }
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                completion.Error ( values, "" );
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ( );

    }

    private RequestCompletion requestCompletion = null;

    public interface RequestCompletion{
        void Success(Object value,String tag);
        void Fail(Object value);//业务上的400
        void Error(Object... values);//网络问题、超时、连不上服务器 404等
    }


    /**
     * 群组对详情，当前组队申请加入和添加信息
     */
    public void GetGroupManagerList(String match_id,String page,String limit ,RequestCompletion completion) {
        this.requestCompletion = completion;
        Map<String,String> map = new HashMap <> (  );
        map.put ( "match_id" ,match_id);
        map.put ( "page" ,page);
        map.put ( "limit" ,limit);
        new HttpUtil.Builder ( APIs.ITEM_LINELIST )
                .Tag ( mContext )
                .Params ( map )
                .isShowLoadProgess ( true,false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ( );
                        MemberInfo memberInfo = gson.fromJson ( model, MemberInfo.class );
                        if (memberInfo != null) {
                            if (completion != null) {
                                if (memberInfo.getCode ( ) == 0) {
                                    String data = gson.toJson (memberInfo.getReturnData ()  );
                                    lineListBean lineListBean = gson.fromJson (data, lineListBean.class);
                                    completion.Success ( lineListBean, "" );
                                }else {
                                    ToastUtil.showShort ( mContext,memberInfo.getMsg ( ) );
                                    completion.Fail ( memberInfo.getMsg ( ) );
                                }
                            }
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                completion.Error ( values, "" );
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ( );




//        List <GroupPoperBean> gameBeanList = new ArrayList <> ( );
//        GroupPoperBean gameBean = new GroupPoperBean ();
//        gameBean.setNickName ( "小学=生要拍" );
//        gameBean.setDate ( "2019-07-28" );
//        gameBean.setState ( "0" );
//        gameBean.setGroupType ( "0" );
//        gameBeanList.add (gameBean );
//
//        GroupPoperBean gameBean1 = new GroupPoperBean ();
//        gameBean1.setNickName ( "我是跑步小大人，哦了" );
//        gameBean1.setDate ( "2019-07-29" );
//        gameBean1.setState ( "1" );
//        gameBean.setGroupType ( "0" );
//        gameBeanList.add (gameBean1 );
//
//        GroupPoperBean gameBean2 = new GroupPoperBean ();
//        gameBean2.setNickName ( "跑步小大人" );
//        gameBean2.setDate ( "2019-08-04" );
//        gameBean2.setState ( "2" );
//        gameBean.setGroupType ( "0" );
//        gameBeanList.add (gameBean2 );
//
//        if(requestCompletion != null){
//            requestCompletion.Success ( gameBeanList,"" );
//        }
    }

    /**
     *
     * @param match_id
     * @param user_id
     * @param ipass   是否通過
     * @param completion
     */
    public void intHasPass(String match_id,String user_id,String ipass ,RequestCompletion completion){
        Map<String,String> map = new HashMap <> (  );
        map.put ( "match_id" ,match_id);
        map.put ( "user_id" ,user_id);
        map.put ( "ipass" ,ipass);
        new HttpUtil.Builder ( APIs.ITEM_HASPASS )
                .Tag ( mContext )
                .Params ( map )
                .isShowLoadProgess ( true,false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ( );
                        MemberInfo memberInfo = gson.fromJson ( model, MemberInfo.class );
                        if (memberInfo != null) {
                            if (completion != null) {
                                if (memberInfo.getCode ( ) == 0) {
                                    completion.Success ( memberInfo, "" );
                                }else {
                                    completion.Fail ( memberInfo.getMsg ( ) );
                                }
                                ToastUtil.showShort ( mContext,memberInfo.getMsg ( ) );
                            }
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                completion.Error ( values, "" );
            }
        } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ( );
    }




    public void GetInformationList(RequestCompletion completion, String ser){
        this.requestCompletion = completion;
        List <InformationBean> informationBeans = new ArrayList <> ( );
        InformationBean informationBean = new InformationBean ();
        informationBean.setTitle ( "话题  | 不知不就8月" );
        informationBean.setDescribe ( "每月flag你坚持的怎么样了呢？" );
        informationBeans.add (informationBean  );

        InformationBean informationBean1 = new InformationBean ();
        informationBean1.setTitle ( "话题  | 毕业季，来晒晒你的你的" );
        informationBean1.setDescribe ( "现在VS学生时代" );
        informationBeans.add (informationBean1  );

        InformationBean informationBean2 = new InformationBean ();
        informationBean2.setTitle ( "去年的你这会在干嘛" );
        informationBean2.setDescribe ( "上班-睡觉？我是在干这个" );
        informationBeans.add (informationBean2  );

        InformationBean informationBean3 = new InformationBean ();
        informationBean3.setTitle ( "这个知识不多见" );
        informationBean3.setDescribe ( "上班-睡觉？我是在干这个" );
        informationBeans.add (informationBean3  );
        if(requestCompletion != null){
            requestCompletion.Success ( informationBeans,"" );
        }
    }

    /**
     * 搜索队伍
     * @param title 标题
     * @param page  页码
     * @param limit  每页多少条
     * @param requestCompletion
     */
    public void searchgroup(String title,int page,int limit,RequestCompletion requestCompletion){
        Map<String,String> param  = new HashMap <> ();
        param.put ( "title" ,title);
        param.put ( "page" ,page+"");
        param.put ( "limit" ,limit+"");
        new HttpUtil.Builder ( APIs.ITEM_SEARCH )
                .Params ( param )
                .Tag (mContext  )
                .isShowLoadProgess ( true,false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson ( ).Gson ( );
                        MemberInfo memberInfo = gson.fromJson ( model, MemberInfo.class );
                        if (memberInfo != null) {
                            String json = gson.toJson ( memberInfo.getReturnData ( ) );
                            GameBean gameBean = gson.fromJson ( json, GameBean.class );
                            if (requestCompletion != null) {
                                requestCompletion.Success ( gameBean, "" );
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
     * 组队详情
     * @param id 获取组队编号
     * @param completion
     */
    public void getItemInfo(String id,RequestCompletion completion){
        Map<String,Object> param  = new HashMap <> ();
        param.put ( "id" ,id);
        new HttpUtil.Builder ( APIs.ITEM_TEAMINFO )
                .Params ( TBParam.MapParam (param ) )
                .Tag (mContext  )
                .isShowLoadProgess ( true,false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson (  ).Gson ();
                        MemberInfo memberInfo = gson.fromJson ( model,MemberInfo.class );
                        if(memberInfo != null){
                            if(memberInfo.getCode () == 0){
                                String strjson = gson.toJson ( memberInfo.getReturnData () );
                                ItmeInfo itmeInfo = gson.fromJson (strjson,ItmeInfo.class);
                                if(completion != null){
                                    completion.Success (itmeInfo,"");
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
    }

    /**
     * 动态id
     * 获取组队留言信息
     */
    public void getItem_Replylist(int id,int page,int limit, RequestCompletion completion,String tag){
        Map<String,Object> param  = new HashMap <> ();
        param.put ( "id" ,id+"");
        param.put ( "page" ,page+"");
        param.put ( "limit" ,limit+"");
        new HttpUtil.Builder ( APIs.REPLY_REPLYLIST )
                .Tag ( mContext )
                .Params( TBParam.MapParam (param ) )
                .isShowLoadProgess ( true,false )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson (  ).Gson (); //SignReplyBean
                        MemberInfo memberInfo =gson.fromJson ( model, MemberInfo.class);
                        if(memberInfo != null && memberInfo.getCode ()==0){
                            SignReplyBean signReplyBean =gson.fromJson ( gson.toJson ( memberInfo.getReturnData () ),SignReplyBean.class);
                            completion.Success (signReplyBean,tag );
                        }else{
                            completion.Fail ( "" );
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
}
