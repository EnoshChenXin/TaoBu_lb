package com.lianbei.taobu.taobu.model;

import java.util.List;

public class SignReplyBean {
    private int pages;
    private int count;
    private List <ReplyList> list;

    public class ReplyList {
        private String  contentImagelist; //说说内容图片 前端自定义
        private int id;
        private int moment_id;
        private int user_id;
        private int reply_id;
        private int reply_user_id;
        private String content;
        private String nickname;
        private String create_time;

        public String getContentImagelist() {
            return contentImagelist;
        }

        public void setContentImagelist(String contentImagelist) {
            this.contentImagelist = contentImagelist;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMoment_id() {
            return moment_id;
        }

        public void setMoment_id(int moment_id) {
            this.moment_id = moment_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
        }

        public int getReply_user_id() {
            return reply_user_id;
        }

        public void setReply_user_id(int reply_user_id) {
            this.reply_user_id = reply_user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

    public List <ReplyList> getList() {
        return list;
    }

    public void setList(List <ReplyList> list) {
        this.list = list;
    }
}
