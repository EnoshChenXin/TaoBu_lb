package com.lianbei.taobu.listener;

import android.content.Context;

import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Success;

import java.util.HashMap;
import java.util.Map;

public class RetroftManager {
    private Context context;
    private String url;

  public  RetroftManager(Context context,RequestCompletion requestCompletion){
      this.context =context;
      Map<String,String> params=new HashMap<> ();
      new HttpUtil.Builder (url )
          .Params ( params )
              .Tag ( context )
              .Success ( new Success ( ) {
                  @Override
                  public void Success(String model) {
                      if(requestCompletion != null){
                          requestCompletion.Success ( model,"" );
                      }
                  }
              } ).Error ( new Error ( ) {
          @Override
          public void Error(Object... values) {

          }
      } );
  }

}
