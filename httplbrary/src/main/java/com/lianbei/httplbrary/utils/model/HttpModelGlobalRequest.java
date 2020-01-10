package com.lianbei.httplbrary.utils.model;

/**
 * Created by HASEE on 2017/3/17.
 */

/**
 * 公共异常数据模型
 */

public class HttpModelGlobalRequest {

    private String returnFlag;
    private mReturnData returnData;

    public mReturnData getReturnData() {
        return returnData;
    }

    public void setReturnData(mReturnData returnData) {
        this.returnData = returnData;
    }

    public String getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(String returnFlag) {
        this.returnFlag = returnFlag;
    }

    public class mReturnData {

        private String mesg;
        private String mesgCode;

        public String getMesg() {
            return mesg;
        }

        public void setMesg(String mesg) {
            this.mesg = mesg;
        }

        public String getMesgCode() {
            return mesgCode;
        }

        public void setMesgCode(String mesgCode) {
            this.mesgCode = mesgCode;
        }

        @Override
        public String toString() {
            return "mReturnData{" +
                    "mesg='" + mesg + '\'' +
                    ", mesgCode='" + mesgCode + '\'' +
                    '}';
        }
    }
}
