package com.lianbei.taobu.mine.model;

import java.util.List;

public class Clockinfo {
    private MColok clock; //当前打卡情况
    private List<Coloklist> list;  //当月打卡情况
    public class MColok{
        int user_id;
        int idate;  //当前日期
        int week;   //打卡次数
        String update_time;  //更新时间
        String create_time;   //创建时间
        boolean flag;   //是否打卡

       public int getIdate() {
           return idate;
       }

       public void setIdate(int idate) {
           this.idate = idate;
       }

       public int getWeek() {
           return week;
       }

       public void setWeek(int week) {
           this.week = week;
       }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
    public class  Coloklist{
        int idate;  //打卡日期
        boolean flag;   //是否打卡

        public int getIdate() {
            return idate;
        }

        public void setIdate(int idate) {
            this.idate = idate;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }

    public MColok getClock() {
        return clock;
    }

    public void setClock(MColok clock) {
        this.clock = clock;
    }

    public List <Coloklist> getList() {
        return list;
    }

    public void setList(List <Coloklist> list) {
        this.list = list;
    }

    public List <Coloklist> getColoklist() {
        return list;
    }

    public void setColoklist(List <Coloklist> coloklist) {
        this.list = coloklist;
    }
}
