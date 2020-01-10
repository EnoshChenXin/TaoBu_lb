package com.lianbei.taobu.mine.model;

import android.content.Context;
import android.util.Log;

import com.lianbei.taobu.utils.ShareUtils;
import com.lianbei.taobu.utils.Validator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class WXUserInfo implements Serializable {
    private String openid;//普通用户的标识，对当前开发者帐号唯一
    private String nickname;//普通用户昵称
    private String sex;//普通用户性别，1为男性，2为女性
    private String province;//普通用户个人资料填写的省份
    private String city;//普通用户个人资料填写的城市
    private String country;//国家，如中国为CN户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空国家，如中国为CN
    private String headimgurl;	//用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
    private String unionid;	//用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
    private String language;
    /**
     * 保存用户微信最新数据
     */
    public void updateUserInfo(Context context){
        try {
            ShareUtils.saveWXUserInfo(context,serialize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void clearWXUserInfo(Context context){
        try {
            ShareUtils.clearWXUserInfo(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WXUserInfo getWXUserInfo(Context context){
        String strUserInfo = ShareUtils.getWXUserInfo (context);
        if (Validator.isStrNotEmpty(strUserInfo)){
            try {
                //已经保存过，进行反序列化过程
                return WXUserInfo.deSerialization(strUserInfo);
            }catch (Exception e){
                e.printStackTrace();
                //解析异常，清楚本地数据
                WXUserInfo.clearWXUserInfo(context);
                Log.d("serial", e.getMessage());
                return new WXUserInfo (context);
            }
        }else {
            return new WXUserInfo (context);
        }
    }
    /**
     * 判断用户信息是否为空，是否登录 :false 已登录过  true  空
     * @return
     */
    public static boolean WxUserInfoIsEmpty(Context context) {
        WXUserInfo wxUserInfo = getWXUserInfo (context);
        if (Validator.isStrNotEmpty ( wxUserInfo.getOpenid () )) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 反序列化草
     *
     * @param str 用户信息数据字符串
     * @return
     * @throws Exception
     * @throws ClassNotFoundException
     */
    private static WXUserInfo deSerialization(String str) throws Exception,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream (
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream (
                byteArrayInputStream);
        WXUserInfo person = (WXUserInfo) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return person;
    }
    /**
     * 将UserInfo实例序列化为字符串以便后续保存在SharedPreferences
     *
     * @param wxUserInfo
     * @return
     * @throws Exception
     */
    private static String serialize(WXUserInfo wxUserInfo) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream (
                byteArrayOutputStream);
        objectOutputStream.writeObject(wxUserInfo);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        return serStr;
    }

    public WXUserInfo(Context context){

    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
