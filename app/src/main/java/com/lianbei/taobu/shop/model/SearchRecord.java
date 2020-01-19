package com.lianbei.taobu.shop.model;


import java.io.Serializable;

import androidx.annotation.NonNull;

public class SearchRecord implements Serializable {
   private  String keywordRecord;
   private int hotNum;

    public int getHotNum() {
        return hotNum;
    }

    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    public String getKeywordRecord() {
        return keywordRecord;
    }

    public void setKeywordRecord(String keywordRecord) {
        this.keywordRecord = keywordRecord;
    }

    @Override//重写hashCode方法，因为包括了年龄和姓名，其中姓名是字符串，所以有其对应的哈希值，将其乘五的意思是，降低发生碰撞的几率。
    public int hashCode() {
        return keywordRecord.hashCode();
    }
    @Override
    public boolean equals( Object o) {
        if(o==null){
            throw new RuntimeException("不能为空值");
        }//先判断该值是否为null，若是的话，就抛出一个异常
        if(!(o instanceof SearchRecord)){
            throw new RuntimeException("这不是person的对象");

        }//在判断其是不是Person类的对象，如果不是的话，就抛出一个异常
        SearchRecord s = (SearchRecord) o;
        return (s.getKeywordRecord().equals(this.keywordRecord));//最后判断姓名和年龄是不是相等的
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
