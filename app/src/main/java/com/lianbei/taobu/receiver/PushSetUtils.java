package com.lianbei.taobu.receiver;

import android.app.Notification;
import android.content.Context;
import android.widget.Toast;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
 import  com.lianbei.taobu.R;
import com.lianbei.taobu.utils.Validator;

import cn.jpush.android.api.MultiActionsNotificationBuilder;

import static com.lianbei.taobu.receiver.TagAliasOperatorHelper.ACTION_ADD;
import static com.lianbei.taobu.receiver.TagAliasOperatorHelper.ACTION_CHECK;
import static com.lianbei.taobu.receiver.TagAliasOperatorHelper.ACTION_CLEAN;
import static com.lianbei.taobu.receiver.TagAliasOperatorHelper.ACTION_DELETE;
import static com.lianbei.taobu.receiver.TagAliasOperatorHelper.ACTION_GET;
import static com.lianbei.taobu.receiver.TagAliasOperatorHelper.ACTION_SET;
import static com.lianbei.taobu.receiver.TagAliasOperatorHelper.sequence;


public class PushSetUtils {
    private static final String TAG = "JIGUANG-Example";
    private Context context;
    Set<String> tags = null;
    String alias = null;
    int action = -1;
    boolean isAliasAction = false;
    private PushSetUtils(Context context){
        this.context = context;
    }
    private static PushSetUtils mInstance;

    public static PushSetUtils getInstance(Context context){
        if(mInstance == null){
            synchronized (TagAliasOperatorHelper.class){
                if(mInstance == null){
                    mInstance = new PushSetUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置通知提示方式 - 基础属性
     */
    public void setStyleBasic() {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        builder.statusBarDrawable = R.mipmap.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
        Toast.makeText(context, "Basic Builder - 1", Toast.LENGTH_SHORT).show();
    }


    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    public void setStyleCustom() {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(context, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = R.mipmap.ic_launcher;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setPushNotificationBuilder(2, builder);
        Toast.makeText(context, "Custom Builder - 2", Toast.LENGTH_SHORT).show();
    }


    public void setAddActionsStyle() {
        MultiActionsNotificationBuilder builder = new MultiActionsNotificationBuilder(context);
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(10, builder);
    }

    //增加tag
    public void handleAddTag(String tag){
        tags =  getInPutTags(tag);
        if(tags == null){
            return;
        }
        action = ACTION_ADD;
        handleSetTagAlias();
    }
    //设置tag
    public void handleSetTag(String tag){
        tags =  getInPutTags(tag);
        if(tags == null){
            return;
        }
        action = ACTION_SET;
        handleSetTagAlias();
    }
    //删除tag
    public void handleDeleteTag(String tag){
        tags =  getInPutTags(tag);
        if(tags == null){
            return;
        }
        action = ACTION_DELETE;
        handleSetTagAlias();
    }
    //获取所有tag
    public void handleGetalTtag(){
        action = ACTION_GET;
        handleSetTagAlias();
    }
    //清除所有tag
    public void handleCleanTag(String tag){
        action = ACTION_CLEAN;
        handleSetTagAlias();
    }
    //檢查有tag
    public void handleCheckTag(String tag){
        tags =  getInPutTags(tag);
        if(tags == null){
            return;
        }
        action = ACTION_CHECK;
        handleSetTagAlias();
    }

    //设置alias
    public void handleSetAlias(String alias){
        alias =  getInPutAlias(alias);
        if(alias == null){
            return;
        }
        isAliasAction = true;
        action = ACTION_SET;
        handleSetTagAlias();
    }


    //获取alias
    public void handleGetAlias(){
        isAliasAction = true;
        action = ACTION_GET;
        handleSetTagAlias();
    }
    //获取alias
    public void handleDeleteAlias(){
        isAliasAction = true;
        action = ACTION_DELETE;
        handleSetTagAlias();
    }
    public void handleSetTagAlias(){
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        if(isAliasAction){
            tagAliasBean.alias = alias;
        }else{
            tagAliasBean.tags = tags;
        }
        tagAliasBean.isAliasAction = isAliasAction;
        TagAliasOperatorHelper.getInstance().handleAction(context,sequence,tagAliasBean);
    }

    public void handleSetMobileNumber(String mobileNumber){
        if (!Validator.isStrNotEmpty(mobileNumber)) {
            return;
        }
        if (!ExampleUtil.isValidMobileNumber(mobileNumber)) {
            return;
        }
        sequence++;
        TagAliasOperatorHelper.getInstance().handleAction(context,sequence,mobileNumber);
    }
    /**
     * 获取输入的alias
     * */
    public String getInPutAlias(String alias){
        if (Validator.isStrNotEmpty(alias)) {
            return null;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            return null;
        }
        return alias;
    }
    /**
     * 获取输入的tags
     * */
    public Set<String> getInPutTags(String tag){
        // 检查 tag 的有效性
        if (!Validator.isStrNotEmpty(tag)) {
            return null;
        }
        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
                return null;
            }
            tagSet.add(sTagItme);
        }
        if(tagSet.isEmpty()){
            return null;
        }
        return tagSet;
    }
}