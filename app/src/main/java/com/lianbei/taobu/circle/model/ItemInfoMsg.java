package com.lianbei.taobu.circle.model;

import java.io.Serializable;
import java.util.List;

public class ItemInfoMsg implements Serializable {

        private int pages;
        private int count;
        private List <msglist> list;
        public class msglist implements Serializable{
            private int id;
            private int match_id;
            private int user_id;
            private String content;
            private String create_time;
            private String nickname;
            private String headimgurl;
            private List<childMsgList> list;

            public class childMsgList implements Serializable{
                private int id;
                private int match_id;
                private int msg_id;
                private int user_id;
                private String content;
                private String create_time;
                private String nickname;
                private String headimgurl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMatch_id() {
                    return match_id;
                }

                public void setMatch_id(int match_id) {
                    this.match_id = match_id;
                }

                public int getMsg_id() {
                    return msg_id;
                }

                public void setMsg_id(int msg_id) {
                    this.msg_id = msg_id;
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


            public List <childMsgList> getList() {
                return list;
            }

            public void setList(List <childMsgList> list) {
                this.list = list;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

        public List <msglist> getList() {
            return list;
        }

        public void setList(List <msglist> list) {
            this.list = list;
        }


}
