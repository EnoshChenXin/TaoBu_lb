package com.lianbei.taobu.mine.viewmanager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lianbei.commomview.loadprogress.LoadProgress;
import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Fail;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.api.TBParam;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.model.AddressBean;
import com.lianbei.taobu.mine.model.MemberInfo;
import com.lianbei.taobu.mine.model.MemberUserInfo;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.mine.model.WXUserInfo;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.utils.GsonTypeAdapter;
import com.lianbei.taobu.utils.MGson;
import com.lianbei.taobu.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoManager {
    private Completion mCompletionCallback;
    private static Context mContext;
    private String phoneNumber;
    String strObject = "";
    private static volatile UserInfoManager instance = null;

    public UserInfoManager(Context context) {
        mContext = context;
    }

    public static UserInfoManager getInstance(Context context) {
        // if already inited, no need to get lock everytime
        mContext = context;
        if (instance == null) {
            synchronized (UserInfoManager.class) {
                if (instance == null) {
                    instance = new UserInfoManager(context);
                }
            }
        }
        return instance;
    }

    public void setmCompletionCallback(Completion mCompletionCallback) {
        this.mCompletionCallback = mCompletionCallback;
    }

    public interface Completion {
        void Completion();

        void Fail();
    }


    /**
     * 微信登录成功请求接口
     *
     * @param wxUserInfo 用户微信信息
     * @param completion 回调接口
     */
    public void WechatLogin(WXUserInfo wxUserInfo, RequestCompletion completion) {
        Map<String, String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("openid", wxUserInfo.getOpenid());
            jsonObject.put("nickname", wxUserInfo.getNickname());
            jsonObject.put("sex", wxUserInfo.getSex());
            jsonObject.put("language", wxUserInfo.getLanguage());
            jsonObject.put("city", wxUserInfo.getCity());
            jsonObject.put("province", wxUserInfo.getProvince());
            jsonObject.put("country", wxUserInfo.getCountry());
            jsonObject.put("headimgurl", wxUserInfo.getHeadimgurl());
            jsonObject.put("unionid", wxUserInfo.getUnionid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("user", jsonObject.toString());
        new HttpUtil.Builder(APIs.WECHAT_LOGIN)
                .Tag(mContext)
                .Params(params)
                .Success(new Success() {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson().Gson();
                        MemberInfo memberInfo = gson.fromJson(model, MemberInfo.class);
                        if (memberInfo != null) {
                            UserInfo userInfo = gson.fromJson(memberInfo.getReturnData().toString(), UserInfo.class);
                            userInfo.updateUserInfo(mContext);
                            if (completion != null) {
                                completion.Success(model, "");
                            }
                        } else {
                            if (completion != null) {
                                completion.Fail("数据错误");
                            }
                        }
                    }
                }).Error(new Error() {
            @Override
            public void Error(Object... values) {
                Log.e("Error", values[1] + "");
                if (values[1] != null) {
                    LoadProgress.getInstance().dialogalert("登录失败Error:", mContext);
                    completion.Error("登录失败Error");
                }
            }
        }).Fail(new Fail() {
            @Override
            public void Fail(String model) {
                mCompletionCallback.Fail();
            }
        }).post();
    }

    /**
     * 首次登录奖励
     */
    public void getRegCoin(RequestCompletion completion) {
        new HttpUtil.Builder(APIs.USER_REG_COIN)
                .Tag(mContext)
                .Params(TBParam.MapParam(null))
                .Success(new Success() {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson().Gson();
                        java.lang.reflect.Type type = new TypeToken<MemberInfo>() {
                        }.getType();
                        MemberInfo memberInfo = gson.fromJson(model, type);
                        if (memberInfo != null) {
                            if (memberInfo.getCode() != -1) {
                                try {
                                    String num = memberInfo.getReturnData().toString();
                                    JSONObject jsonObject = new JSONObject(num);
                                    int coin = jsonObject.getInt("coin");
                                    completion.Success(coin, "");
                                } catch (Exception e) {

                                }
                            }
                        } else {
                            completion.Error("数据错误");
                        }

                    }
                }).Error(new Error() {
            @Override
            public void Error(Object... values) {
                completion.Error("数据错误");
            }
        }).Fail(new Fail() {
            @Override
            public void Fail(String model) {
                completion.Fail(model + "");
            }
        }).post();

    }


    /**
     * 忘记密码发送验证码
     *
     * @param phoneNumber
     */
    public void getForgetPwdConfirmCode(String phoneNumber) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phoneNumber);
        new HttpUtil.Builder(APIs.FORGETPWD_CONFIRM_CODE)
                .Tag(mContext)
                .Params(params)
                .Success(new Success() {
                    @Override
                    public void Success(String model) {
                        try {
                            JSONObject jsonObject = new JSONObject(model);
                            String return_msg = jsonObject.getString("return_msg");
                            ToastUtil.showShort(mContext, return_msg);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).Error(new Error() {
            @Override
            public void Error(Object... values) {

            }
        }).post();
    }

    /**
     * 更新密码/忘记密码
     *
     * @param username
     * @param pwd
     * @param code       验证码
     * @param completion
     */
    public void updatePwd(String username, String pwd, String code, RequestCompletion completion) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", username);
        params.put("newPasswd", pwd);
        params.put("code", code);
        new HttpUtil.Builder(APIs.UPDATE_PWD)
                .Tag(mContext)
                .Params(params)
                .Success(new Success() {
                    @Override
                    public void Success(String model) {

                        if (completion != null) {
                            completion.Success(model, "");
                        }

                    }
                }).Error(new Error() {
            @Override
            public void Error(Object... values) {
                if (completion != null) {
                    completion.Error(values[1], "");
                }
            }
        }).post();
    }

    public void setInviteCode(String code, RequestCompletion completion) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        new HttpUtil.Builder(APIs.SHIP_BINDUSER)
                .Params(params)
                .Tag(mContext)
                .isShowLoadProgess(true, false)
                .Success(new Success() {
                    @Override
                    public void Success(String model) {
                        Gson gson = new MGson().Gson();
                        MemberInfo memberInfo = gson.fromJson(model, MemberInfo.class);
                        if (memberInfo != null) {

                        }


                    }
                }).Error(new Error() {
            @Override
            public void Error(Object... values) {

            }
        }).Fail(new Fail() {
            @Override
            public void Fail(String model) {

            }
        }).post();
    }


    /**
     * 修改密码
     *
     * @param oldPasswd
     * @param newPasswd
     * @param completion
     */
    public void modifyPwd(String oldPasswd, String newPasswd, RequestCompletion completion) {
        Map<String, String> params = new HashMap<>();
        params.put("oldPasswd", oldPasswd);
        params.put("newPasswd", newPasswd);
        new HttpUtil.Builder(APIs.MODIFY_PWD)
                .Params(params)
                .Tag(mContext)
                .isShowLoadProgess(true, false)
                .Success(new Success() {
                    @Override
                    public void Success(String model) {
                        if (completion != null) {
                            completion.Success(model, "");
                        }

                    }
                }).Error(new Error() {
            @Override
            public void Error(Object... values) {
                if (completion != null) {
                    completion.Error(values[1], "");
                }

            }
        }).post();
    }

    /**
     * 添加地址
     *
     * @param completion
     */
    public void modifyAddress(RequestCompletion completion) {
        List<AddressBean> addressBeans = new ArrayList<>();
        AddressBean addressBean = new AddressBean();
        addressBean.setName("巴南老哥");
        addressBean.setPhone("19923018910");
        addressBean.setDistrict("重庆市 重庆市 巴南区");
        addressBean.setAddress("万达广场万达广场");
        addressBean.setIschoose(true);
        addressBeans.add(addressBean);
        AddressBean addressBean2 = new AddressBean();
        addressBean2.setName("陈圆心");
        addressBean2.setPhone("19923018910");
        addressBean2.setDistrict("重庆市 重庆市 巴南区");
        addressBean2.setAddress("万达广场万达广场10吨6-7");
        addressBeans.add(addressBean2);
        completion.Success(addressBeans, "");

//        Map<String,String> params=new HashMap<>();
//        params.put("oldPasswd",oldPasswd);
//        params.put("newPasswd",newPasswd);
//        new HttpUtil.Builder ( APIs.MODIFY_PWD )
//                .Params ( params )
//                .Tag ( context )
//                .isShowLoadProgess ( true,false )
//                .Success ( new Success ( ) {
//                    @Override
//                    public void Success(String model) {
//                        if(completion != null){
//                            completion.Success (model,""  );
//                        }
//
//                    }
//                } ).Error ( new Error ( ) {
//            @Override
//            public void Error(Object... values) {
//                if(completion != null){
//                    completion.Error (values[1],""  );
//                }
//
//            }
//        } ).post ();
    }
}
