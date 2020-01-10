package com.lianbei.taobu.listener;

public class RequestOnClick {
    public onClickShare onClickShare = null;


    public void setOnClickShare(onClickShare onClickShare){
        this.onClickShare =onClickShare;
    }

    public interface  onClickShare{
        void wechatClick(ResultOK resultOK);
        void friendCircleClick(ResultOK resultOK);
    }



    public ResultOK resultOK = null;

      public interface  ResultOK{
        void resultok();

    }

}
