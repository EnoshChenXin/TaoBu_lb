package com.lianbei.taobu.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.Stack;

public class ActivityUtils {
    private static Stack<Activity> activityStack;
    private static Stack<Activity> activityStack2;
    private static ActivityUtils instance;

    private ActivityUtils(){}
    /**
     * 单一实例
     */
    public static ActivityUtils getAppManager(){
        if(instance==null){
            instance=new ActivityUtils();
        }
        return instance;
    }
    /**
     * 添加Activity到堆栈 */
    public void addActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity(){
        Activity activity=activityStack.lastElement();
        return activity;
    }
    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        Activity activity=activityStack.lastElement();
        finishActivity(activity);
    }
    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity){
        if(activity!=null){
            activityStack.remove(activity);
            activity.finish();
            activity=null;
        }
    }
    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls){
        for (Activity activity : activityStack) {
            if(activity.getClass().equals(cls) ){
                finishActivity(activity);
            }
        }
    }
    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
           // finishAllActivity();
          //  ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
          //  activityMgr.restartPackage(context.getPackageName());
            //System.exit(0);
            System.currentTimeMillis();
        } catch (Exception e) { }
    }

    /**
     * 添加Activity
     * @param activity
     */
    public void addActivityNews(Activity activity){
        if(activityStack2==null){
            activityStack2=new Stack<Activity>();
        }
        activityStack2.add(activity);
        if(activityStack2.size ()>3){
            for (int i = 0; i <activityStack2.size ()-2 ; i++) {
                activityStack2.remove ( i);
                activityStack2.get ( i).finish ();
            }
    }
    }
    /**
     * 查询栈信息
     */

    public void checkAllActivity(){
        Log.e ( "---activityStack:","开始---");
        for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
                Log.e ( "activityStack:",activityStack.get (i).getLocalClassName ()+"");
            }
        }
        Log.e ( "---activityStack:","结束---");
    }
}
