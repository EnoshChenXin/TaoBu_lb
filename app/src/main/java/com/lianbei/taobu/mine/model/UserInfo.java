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

/**
 * Created by chenyuanxin on 2019/9/20.
 */

public class UserInfo implements Serializable {

    private int user_id;
    private boolean invite; //是否被邀请
    private String phone;
    private String head_portrait;
    private String gold;
    private String my_invite_code;
    private String balance;
    private String earnings;
    private String invite_code;
    private String newer_mission;
    private boolean bindwx;
    private String oneyuan;
    private String name;
    private String waitingWithdraw;
    private String readCountDay;
    private String loginState;
    private String loginErrorMsg;
    private int children;
    private int readDay;

    /**
     * 保存会员最新数据
     */
    public void updateUserInfo(Context context){
        try {
            ShareUtils.saveUserInfo(context,serialize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearUserInfo(Context context){
        try {
            ShareUtils.clearUserInfo(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserInfo getUserInfo(Context context){
        String strUserInfo = ShareUtils.getUserInfo(context);
        if (Validator.isStrNotEmpty(strUserInfo)){
            try {
                //已经保存过，进行反序列化过程
                return UserInfo.deSerialization(strUserInfo);
            }catch (Exception e){
                e.printStackTrace();
                //解析异常，清楚本地数据
                UserInfo.clearUserInfo(context);
                Log.d("serial", e.getMessage());
                return new UserInfo (context);
            }
        }else {
            return new UserInfo (context);
        }
    }

    /**
     * 判断用户信息是否为空，是否登录 :false 已登录过  true  空
     * @return
     */
    public static boolean UserInfoIsEmpty(Context context) {
        UserInfo userInfo = getUserInfo(context);
        if (0 != userInfo.getUser_id () ) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 将UserInfo实例序列化为字符串以便后续保存在SharedPreferences
     *
     * @param userInfo 某个登录会员实例
     * @return
     * @throws Exception
     */
    private static String serialize(UserInfo userInfo) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream (
                byteArrayOutputStream);
        objectOutputStream.writeObject(userInfo);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        return serStr;
    }

    /**
     * 反序列化草
     *
     * @param str 用户信息数据字符串
     * @return
     * @throws Exception
     * @throws ClassNotFoundException
     */
    private static UserInfo deSerialization(String str) throws Exception,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream (
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream (
                byteArrayInputStream);
        UserInfo person = (UserInfo) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return person;
    }
    public UserInfo(Context context){

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isInvite() {
        return invite;
    }

    public void setInvite(boolean invite) {
        this.invite = invite;
    }

    public boolean isBindwx() {
        return bindwx;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHead_portrait() {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait) {
        this.head_portrait = head_portrait;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getMy_invite_code() {
        return my_invite_code;
    }

    public void setMy_invite_code(String my_invite_code) {
        this.my_invite_code = my_invite_code;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getEarnings() {
        return earnings;
    }

    public void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getNewer_mission() {
        return newer_mission;
    }

    public void setNewer_mission(String newer_mission) {
        this.newer_mission = newer_mission;
    }

    public boolean getBindwx() {
        return bindwx;
    }

    public void setBindwx(boolean bindwx) {
        this.bindwx = bindwx;
    }

    public String getOneyuan() {
        return oneyuan;
    }

    public void setOneyuan(String oneyuan) {
        this.oneyuan = oneyuan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWaitingWithdraw() {
        return waitingWithdraw;
    }

    public void setWaitingWithdraw(String waitingWithdraw) {
        this.waitingWithdraw = waitingWithdraw;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getReadDay() {
        return readDay;
    }

    public void setReadDay(int readDay) {
        this.readDay = readDay;
    }

    public String getReadCountDay() {
        return readCountDay;
    }

    public void setReadCountDay(String readCountDay) {
        this.readCountDay = readCountDay;
    }

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public String getLoginErrorMsg() {
        return loginErrorMsg;
    }

    public void setLoginErrorMsg(String loginErrorMsg) {
        this.loginErrorMsg = loginErrorMsg;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "user_id='" + user_id + '\'' +
                ", phone='" + phone + '\'' +
                ", head_portrait='" + head_portrait + '\'' +
                ", gold='" + gold + '\'' +
                ", my_invite_code='" + my_invite_code + '\'' +
                ", balance='" + balance + '\'' +
                ", earnings='" + earnings + '\'' +
                ", invite_code='" + invite_code + '\'' +
                ", newer_mission='" + newer_mission + '\'' +
                ", bindwx='" + bindwx + '\'' +
                ", oneyuan='" + oneyuan + '\'' +
                ", name='" + name + '\'' +
                ", waitingWithdraw='" + waitingWithdraw + '\'' +
                ", children='" + children + '\'' +
                ", readDay='" + readDay + '\'' +
                ", readCountDay='" + readCountDay + '\'' +
                ", loginState='" + loginState + '\'' +
                ", loginErrorMsg='" + loginErrorMsg + '\'' +
                '}';
    }
}
