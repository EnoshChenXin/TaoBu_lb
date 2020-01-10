package com.lianbei.taobu.circle.model;

import java.util.List;

/**
 *群组管理列表
 */
public class lineListBean {
    private int pages;
    private int count;
    private List <ListBean> list;
    public class ListBean {
        private int match_id;
        private int user_id;
        private int ipass;
        private String update_time;
        private String create_time;
        private String nickname;

        public int getMatch_id() {
            return match_id;
        }

        public void setMatch_id(int match_id) {
            this.match_id = match_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getIpass() {
            return ipass;
        }

        public void setIpass(int ipass) {
            this.ipass = ipass;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List <ListBean> getList() {
        return list;
    }

    public void setList(List <ListBean> list) {
        this.list = list;
    }
}
