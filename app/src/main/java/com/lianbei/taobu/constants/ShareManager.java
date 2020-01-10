package com.lianbei.taobu.constants;

import android.content.Context;

import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.api.APIs;
import java.util.HashMap;
import java.util.Map;

public class ShareManager {
    public Context context;

    public ShareManager(Context context){
        this.context  = context;
    }

    /**
     * 每日分享接口：分享成功后调用
     */
   public void  shareDailyShareAfter(){
       Map<String,String> params=new HashMap<> ();
       params.put("fo","Y");
       new HttpUtil.Builder ( APIs.SHARE_DAILYSHARE_AFTER )
               .Tag ( context )
               .Params ( params )
               .Success ( new Success ( ) {
                   @Override
                   public void Success(String model) {

                   }
               } ).Error ( new Error ( ) {
           @Override
           public void Error(Object... values) {

           }
       } ).post ();
   }
}
