package com.lianbei.taobu.circle.model;

import java.io.Serializable;
import java.util.List;

public class ItmeInfo implements Serializable {
    private boolean inTeam; //当前用户是否加入到该组队中，自己队伍除外判断
    private userTeam userTeam;//组队信息
    private ItemInfoMsg msg; //组队留言信息信息
    private List<ItemDymlist> dList; //今日榜单
    private List<ItemDymlist> yList; //今日榜单
    private List<ItemDymlist> mList; //今日榜单
    public class userTeam implements  Serializable{
        private int id;
        private int user_id;
        private int ispass;
        private String title;
        private String icon;
        private String address;
        private String remark;
        private String start_time;
        private String end_time;
        private String create_time;
        private String nickname;
        private boolean inTeam;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getIspass() {
            return ispass;
        }

        public void setIspass(int ispass) {
            this.ispass = ispass;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isInTeam() {
            return inTeam;
        }

        public void setInTeam(boolean inTeam) {
            this.inTeam = inTeam;
        }
    }

    public boolean isInTeam() {
        return inTeam;
    }

    public void setInTeam(boolean inTeam) {
        this.inTeam = inTeam;
    }

    public userTeam getUserTeam() {
        return userTeam;
    }

    public void setUserTeam(userTeam userTeam) {
        this.userTeam = userTeam;
    }

    public ItemInfoMsg getMsg() {
        return msg;
    }

    public void setMsg(ItemInfoMsg msg) {
        this.msg = msg;
    }

    public List <ItemDymlist> getdList() {
        return dList;
    }

    public void setdList(List <ItemDymlist> dList) {
        this.dList = dList;
    }

    public List <ItemDymlist> getyList() {
        return yList;
    }

    public void setyList(List <ItemDymlist> yList) {
        this.yList = yList;
    }

    public List <ItemDymlist> getmList() {
        return mList;
    }

    public void setmList(List <ItemDymlist> mList) {
        this.mList = mList;
    }
}
