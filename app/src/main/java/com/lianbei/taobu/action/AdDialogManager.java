package com.lianbei.taobu.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.lianbei.commomview.adlibrary.bean.ADActionBean;
import com.lianbei.commomview.adlibrary.bean.ADctionInfo;
import com.lianbei.commomview.adlibrary.bean.AdInfo;
import com.lianbei.taobu.action.model.AdProgressModel;
import com.lianbei.taobu.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class AdDialogManager {
    private static List <AdInfo> advList = null;
    private ADActionBean adActionBean = new ADActionBean ( );
    private Activity context;
    private String windowPage ;
    private static List<ADctionInfo> actionInfoList = new ArrayList <> (  );

    private static AdDialogManager instance = null;

    public static AdDialogManager getInstance() {
        if (instance == null)
            instance = new AdDialogManager ( );
        return instance;
    }

    public void AdDialogManager(Activity context, String windowPage) {
        this.context = context;
        this.windowPage = windowPage;
        initData ( );
    }

    public AdDialogManager(Activity context) {
        this.context = context;
        initData ( );
    }
    public AdDialogManager() {

    }
    public AdDialogManager(Activity context,String windowPage) {
        this.context = context;
        this.windowPage = windowPage;
        initData ( );
    }

    public void setAdParam(Activity context, AdProgressModel adModel) {
        this.context = context;
        initData ( );
    }

    public void initData() {
        if(actionInfoList != null && actionInfoList.size ()>0){
            AdRulesManager.ShowActionWindow (context,actionInfoList,windowPage);
        }else{
            getActivityslist();
        }
    }
    private void getActivityslist() {
        ADctionInfo aDctionInfo = new ADctionInfo ();
        aDctionInfo.setId ( "001" );
        aDctionInfo.setType ( "6" );
        aDctionInfo.setPagetype ( "3" );
        aDctionInfo.setWindowpage ( "4" );
        aDctionInfo.setWindowUrl ( "http://nx.lbeizxw.com/image/tb/hot.png" );
        aDctionInfo.setUrl ( "http://nx.lbeizxw.com/bdjy2/1011.html" );
        actionInfoList.add ( aDctionInfo );
        AdRulesManager.ShowActionWindow (context,actionInfoList,windowPage);

    }


    private void BuildIntent(Context context,Intent intent, String url, String title){
        intent.putExtra ("url" ,url+"" );
        intent.putExtra ("title" ,title+"" );
        context.startActivity ( intent );
    }
    private static boolean isEmptyUrl(String Url){
        if(Url != null && !Url.equals ( "null" ) && !Url.equals ( "" )){
            return  false;
        }else{
            return  true;
        }
    }
}
