package com.lianbei.taobu.mine.viewmanager;

import android.content.Context;
import android.content.Intent;

import com.lianbei.taobu.mine.login.WXLoginActivity;
import com.lianbei.taobu.mine.model.UserInfo;

public class UserUtils {
    public static boolean  isNotLogin(Context context){
       if(UserInfo.UserInfoIsEmpty (context )) {
           Intent intent  = new Intent ( context, WXLoginActivity.class );
           context.startActivity ( intent );
           return true;
       }
           return false;
       }

}
