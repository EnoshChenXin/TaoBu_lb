package com.lianbei.taobu.taobu.model;

/**
 * 报名基本数据
 */
public class WalkApplyBean {
    private int SugarBean; //糖豆
    private int Stepnumber;//步数
    private int teamtype;//报名类型 0： 正常类型：非0：新用户报名队伍类型；1；类型1  2：类型2  3.类型3   4.......
    private int invitation;//报名需邀请好友数量

    public int getTeamtype() {
        return teamtype;
    }

    public void setTeamtype(int teamtype) {
        this.teamtype = teamtype;
    }

    public int getInvitation() {
        return invitation;
    }

    public void setInvitation(int invitation) {
        this.invitation = invitation;
    }

    public int getSugarBean() {
        return SugarBean;
    }

    public void setSugarBean(int sugarBean) {
        SugarBean = sugarBean;
    }

    public int getStepnumber() {
        return Stepnumber;
    }

    public void setStepnumber(int stepnumber) {
        Stepnumber = stepnumber;
    }
}


