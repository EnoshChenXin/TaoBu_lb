package com.lianbei.taobu.taobu.model;

public class WalkBean {
    private int gamestate; //0：未参加比赛状态 1：已报名明日比赛状态  2：比赛进行中......
    private int stepnumtarget;//参赛步数目标
    private int nowstepnum;//当前步数
    private int period;//比赛期 如：15期
    private int group;//比赛组
    private String gameStartTime;//距离比赛开始还有 08:26:47
    private String gameEndTime;//距离比赛结束还有   08:32:34
    private String  startTime;//开赛时间
    private String sugarbeanNum;//奖金池总糖豆数量
    private int registrationNumber;//报名总人数


    public int getGamestate() {
        return gamestate;
    }

    public void setGamestate(int gamestate) {
        this.gamestate = gamestate;
    }

    public int getStepnumtarget() {
        return stepnumtarget;
    }

    public void setStepnumtarget(int stepnumtarget) {
        this.stepnumtarget = stepnumtarget;
    }

    public int getNowstepnum() {
        return nowstepnum;
    }

    public void setNowstepnum(int nowstepnum) {
        this.nowstepnum = nowstepnum;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(String gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public String getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(String gameEndTime) {
        this.gameEndTime = gameEndTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSugarbeanNum() {
        return sugarbeanNum;
    }

    public void setSugarbeanNum(String sugarbeanNum) {
        this.sugarbeanNum = sugarbeanNum;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
