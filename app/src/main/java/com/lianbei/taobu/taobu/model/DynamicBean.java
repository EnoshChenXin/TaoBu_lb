package com.lianbei.taobu.taobu.model;

import java.io.Serializable;
import java.util.List;

/**
 * 动态内容
 */
public class DynamicBean{
    private int pages;  //
    private int count;  //
    private String match_id; //自定义传入队伍ID ，后面用到
    private List <DynamicList> list;
    public class DynamicList  implements Serializable{
        private int id;
        private int user_id;
        private String content;
        private String list; //说说内容图片
        private List<String> contentImagelist; //说说内容图片
        private String like;//是否喜欢数量
        private String num;  //评论数量
        private int cate;
        private String create_time;
        private String nickname;
        private String headimgurl;


        public List <String> getContentImagelist() {
            return contentImagelist;
        }

        public void setContentImagelist(List <String> contentImagelist) {
            this.contentImagelist = contentImagelist;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getList() {
            return list;
        }

        public void setList(String list) {
            this.list = list;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getCate() {
            return cate;
        }

        public void setCate(int cate) {
            this.cate = cate;
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

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
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

    public List <DynamicList> getList() {
        return list;
    }

    public void setList(List <DynamicList> list) {
        this.list = list;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }
}
