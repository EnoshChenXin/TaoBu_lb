package com.lianbei.taobu.circle.view.commondialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.lianbei.commomview.dialog.Effectstype;
import com.lianbei.commomview.dialog.NiftyDialogBuilder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.utils.JXConstants;
import com.lianbei.taobu.utils.Validator;


public class GlobalDialogModel {
    private DialogCallBack dialogCallback;
    private DialogCallBack dialogCallBack;

    private String messageTitles = "";
    private String messageContents = "";
    private String cancelBtns = "";
    private String okBtns = "";
    private String dialogType = "";//弹窗类型 版本升级 0 强制升级  1,稍后再说   2，不再提醒
    private DialogCallBackInterface dialogCallBackInterface;
    NiftyDialogBuilder dialogBuilder = null;
    private static GlobalDialogModel instance = null;

    public static GlobalDialogModel getInstance() {
        if (instance == null)
            instance = new GlobalDialogModel ( );
        return instance;
    }

    public String getMessageTitle() {
        return messageTitles;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitles = messageTitle;
    }

    public String getMessageContent() {
        return messageContents;
    }

    public void setMessageContent(String messageContent) {
        this.messageContents = messageContent;
    }

    public String getCancelBtn() {
        return cancelBtns;
    }

    public void setCancelBtn(String cancelBtn) {
        this.cancelBtns = cancelBtn;
    }

    public String getOkBtn() {
        return okBtns;
    }

    public void setOkBtn(String okBtn) {
        this.okBtns = okBtn;
    }

    public DialogCallBack getDialogCallback() {
        return dialogCallback;
    }

    public void setDialogCallback(DialogCallBack dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    private void paramList(String messagetitle, String msgContent,
                           String cancleBtn, String okBtn) {
        // TODO Auto-generated method stub
        if (Validator.isStrNotEmpty ( messagetitle )) {

            this.messageTitles = messagetitle;
        } else {
            messageTitles = "提示";
        }
        if (Validator.isStrNotEmpty ( msgContent ))
            this.messageContents = msgContent;
        else
            messageContents = "notmessage";
        if (Validator.isStrNotEmpty ( cancleBtn ))
            this.cancelBtns = cancleBtn;
        else
            cancelBtns = "取消";
        if (Validator.isStrNotEmpty ( okBtn ))
            this.okBtns = okBtn;
        else
            okBtns = "确认";
    }

    private void paramList(String messagetitle, String msgContent,
                           String cancleBtn, String okBtn, String type) {
        // TODO Auto-generated method stub
        if (Validator.isStrNotEmpty ( messagetitle )) {

            this.messageTitles = messagetitle;
        } else {
            messageTitles = "提示";
        }
        if (Validator.isStrNotEmpty ( msgContent ))
            this.messageContents = msgContent;
        else
            messageContents = "notmessage";
        if (Validator.isStrNotEmpty ( cancleBtn ))
            this.cancelBtns = cancleBtn;
        else
            cancelBtns = "取消";
        if (Validator.isStrNotEmpty ( okBtn ))
            this.okBtns = okBtn;
        else
            okBtns = "确认";
        if (Validator.isStrNotEmpty ( type ))
            this.dialogType = type;
        else
            dialogType = "0";
    }

    /**
     * @param context        上下文
     * @param msgContent     消息内容
     * @param dialogcallback 回调方法
     *                       <p>
     *                       other 取消 确定
     */
    public void commonDialog(Context context, String msgContent,
                             DialogCallBack dialogcallback) {
        paramList ( "", msgContent, "", "" );
        dialogMolder ( context, dialogcallback );
    }

    /**
     * @param context        上下文
     * @param messagetitle   自定义消息title
     * @param msgContent     自定义消息内容
     * @param dialogcallback 回调 other 取消 确定
     */
    public void commonDialog(Context context, String messagetitle,
                             String msgContent, DialogCallBack dialogcallback) {
        paramList ( messagetitle, msgContent, "", "" );
        dialogMolder ( context, dialogcallback );
    }

    /**
     * @param context        上下文
     * @param messagetitle   消息title
     * @param msgContent     消息内容
     * @param cancleBtn      取消按钮
     * @param okBtn          确认按钮
     * @param dialogcallback 回调
     */
    public void commonDialog(Context context, String messagetitle,
                             String msgContent, String cancleBtn, String okBtn,
                             DialogCallBack dialogcallback) {
        paramList ( messagetitle, msgContent, cancleBtn, okBtn );
        dialogMolder ( context, dialogcallback );
    }

    /**
     * @param context        上下文
     * @param msgContent     消息内容
     * @param cancleBtn      取消按钮
     * @param okBtn          确认按钮
     * @param dialogcallback 回调
     */
    public void commonDialog(Context context, String msgContent,
                             String cancleBtn, String okBtn, DialogCallBack dialogcallback) {
        paramList ( "", msgContent, cancleBtn, okBtn );
        dialogMolder ( context, dialogcallback );
    }

    /**
     * @param context    上下文
     * @param msgContent 消息内容
     */
    public void fileDialog(Context context, String msgContent) {
        Log.e ( "JXConstants.fileDialog", JXConstants.fileDialog + "" );

        if (JXConstants.fileDialog)
            return;
        JXConstants.fileDialog = true;
        paramList ( "", msgContent, "", "" );
        fileDialog ( context );
    }

    /**
     * 一个弹出按钮提示框，自定义确定内容
     *
     * @param context
     * @param msgContent
     * @param dialogcallback
     */

    public void singleDialog(Context context, String msgContent,
                             DialogCallBack dialogcallback) {
        paramList ( "", msgContent, "", "" );
        singleDialog ( context, dialogcallback );
    }

    /**
     *
     * @param context
     * @param title
     * @param msgContent
     * @param dialogcallback
     */
    public void singleDialog(Context context, String title,String msgContent,
                             DialogCallBack dialogcallback) {
        paramList ( title, msgContent, "", "" );
        singleDialog ( context, dialogcallback );
    }
    /**
     * 消息提示框，只做提示，没有业务
     *
     * @param context
     * @param msgContent
     */

    public void commonPromptDialog(Context context, String msgContent) {
        paramList ( "", msgContent, "", "" );
        promptDialog ( context );
    }

    /**
     * 应用界面需要提醒用户弹出框，普通弹出框可调用下面的方式进行实现，针对不同的业务提供相应的构造方法
     *
     * @param context        上下文
     * @param dialogcallback 点击确定按钮回调方法
     */
    public void dialogMolder(Context context,
                             final DialogCallBack dialogcallback) {
        if (dialogBuilder == null)
            dialogBuilder = new NiftyDialogBuilder ( context, R.style.dialog_untran );
        dialogBuilder
                .withTitle ( messageTitles )
                .withTitleColor (
                        context.getResources ( ).getColor (
                                R.color.mon_dialog_mesg_color ) )
                .withMessage ( messageContents )
                .withMessageColor(
                        context.getResources ( ).getColor (
                                R.color.dialog_btn_textcolor ) )
                .withmButton2Color ( context.getResources ( ).getColor (
                        R.color.button_red )  )
                // 内容字体颜色
                .isCancelableOnTouchOutside ( false )
                // 是否点击取消
                .withDuration ( 200 )
                //
                .isCancelable ( false )
                .withEffect ( Effectstype.Fadein )
                // def Effectstype.Slidetop
                // .setCustomView(R.layout.custom_view, context)
                // 自定义diaglog内容.setCustomView(View
                // or
                // ResId,context)
                .withButton1Text ( cancelBtns )
                .setButton1Click ( new OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        if (dialogBuilder != null) {
                            dialogBuilder.dismiss ( );
                            dialogBuilder = null;

                        }
                    }
                } ).withButton2Text ( okBtns )// 好的
                .setButton2Click ( new OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        if (dialogBuilder != null) {
                            dialogBuilder.dismiss ( );
                            dialogBuilder = null;
                            dialogcallback.oklBtn ( );

                        }
                    }
                } );
        if (!dialogBuilder.isShowing ( )) {
            dialogBuilder.show ( );
        }

    }

    /**
     * 只有一个按钮消息框 ，一般退出到登录界面，重新登录
     *
     * @param context 上下文
     */

    private void fileDialog(final Context context) {
        if (dialogBuilder == null)
            dialogBuilder = new NiftyDialogBuilder ( context, R.style.load_dialog );
        dialogBuilder
                .withTitle ( messageTitles )
                .withTitleColor (
                        context.getResources ( ).getColor (
                                R.color.mon_dialog_mesg_color ) )
                .withMessage ( messageContents )
                .withMessageColor (
                        context.getResources ( ).getColor (
                                R.color.dialog_btn_textcolor ) )
                // 内容字体颜色
                .isCancelableOnTouchOutside ( false ).isCancelable ( false )
                // 是否点击取消
                .withDuration ( 200 )
                //
                .withEffect ( Effectstype.Fadein )
                // def Effectstype.Slidetop
                .setCustomView ( R.layout.custom_view, context )
                // 自定义diaglog内容.setCustomView(View
                // or
                // ResId,context)
                .withButton2Text ( okBtns ).setButton2Click ( new OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dialogBuilder != null) {
                    dialogBuilder.dismiss ( );
                    //EnterMgr.logout(context);
                    JXConstants.fileDialog = false;
                    dialogBuilder = null;
                }
            }
        } );
        if (!dialogBuilder.isShowing ( )) {
            dialogBuilder.show ( );
        }
    }

    /**
     * 只有一个按钮消息框 ，只做提醒确认
     *
     * @param context 上下文
     */

    private void promptDialog(final Context context) {
        if (dialogBuilder == null)
            dialogBuilder = new NiftyDialogBuilder ( context, R.style.load_dialog );
        dialogBuilder
                .withTitle ( messageTitles )
                .withTitleColor (
                        context.getResources ( ).getColor (
                                R.color.mon_dialog_mesg_color ) )
                .withMessage ( messageContents )
                .withMessageColor (
                        context.getResources ( ).getColor (
                                R.color.dialog_btn_textcolor ) )
                // 内容字体颜色
                .isCancelableOnTouchOutside ( false ).isCancelable ( false )
                // 是否点击取消
                .withDuration ( 200 )
                //
                .withEffect ( Effectstype.Fadein )
                // def Effectstype.Slidetop
                .setCustomView ( R.layout.custom_view, context )
                // 自定义diaglog内容.setCustomView(View
                // or
                // ResId,context)
                .withButton2Text ( okBtns ).setButton2Click ( new OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dialogBuilder != null) {
                    dialogBuilder.dismiss ( );
                    dialogBuilder = null;
                }
            }
        } );
        if (!dialogBuilder.isShowing ( )) {
            dialogBuilder.show ( );
        }
    }

    public interface DialogCallBack {
        // void cancelBtn();
        void oklBtn();
    }


    /**
     * 拨打电话通用方法
     *
     * @param context 上下文
     * @param phone   拨打电话号码
     */
    public void commonCallPhone(final Context context, final String phone) {
        paramList ( "联系客服", phone, "取消", "呼叫" );
        dialogMolder ( context, new DialogCallBack ( ) {

            @Override
            public void oklBtn() {
                callPhone ( context, phone );
            }
        } );
//		instance.dialogphoneMolder(context,new DialogCallBack() {
//			
//			@Override
//			public void oklBtn() {
//				callPhone(context, phone);
//			}
//		});
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phone   电话号码
     */

    private void callPhone(Context context, String phone) {
        Intent intent = new Intent ( );
        intent.setAction ( Intent.ACTION_CALL );// 指定意图动作
        intent.setData ( Uri.parse ( "tel:" + phone ) );
        context.startActivity ( intent );
    }

    /*
     *
     */

    // public abstract class DialogCallBackInterface implements userCarModle{
    //
    // @Override
    // public void oklBtn() {
    // // TODO Auto-generated method stub
    //
    // }
    //
    // @Override
    // public void cancelBtn() {
    // // TODO Auto-generated method stub
    //
    // }
    // }

    public interface userCarModle {
        void cancelBtn();

        void oklBtn();
    }

    public void specialDialogMolder(Context context, String msgContent,
                                    String cancleBtn, String okBtn,
                                    DialogCallBackInterface dialogcallback) {
        paramList ( "", msgContent, cancleBtn, okBtn );
        specialDialogMolder ( context, dialogcallback );
    }

    private void specialDialogMolder(Context context,
                                     final DialogCallBackInterface dialogCallBackInterface) {
        if (dialogBuilder == null)
            dialogBuilder = new NiftyDialogBuilder ( context, R.style.load_dialog );
        dialogBuilder
                .withTitle ( messageTitles )
                .withTitleColor (
                        context.getResources ( ).getColor (
                                R.color.mon_dialog_mesg_color ) )
                .withMessage ( messageContents )
                .withMessageColor (
                        context.getResources ( ).getColor (
                                R.color.dialog_btn_textcolor ) )
                // 内容字体颜色
                .isCancelableOnTouchOutside ( false )
                // 是否点击取消
                .withDuration ( 200 )
                //
                .isCancelable ( false )
                .withEffect ( Effectstype.Fadein )
                // def Effectstype.Slidetop
                .setCustomView ( R.layout.custom_view, context )
                // 自定义diaglog内容.setCustomView(View
                // or
                // ResId,context)
                .withButton1Text ( cancelBtns )
                .setButton1Click ( new OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (dialogBuilder != null) {
                            dialogBuilder.dismiss ( );
                            dialogBuilder = null;
                            dialogCallBackInterface.cancelBtn ( );

                        }
                    }
                } ).withButton2Text ( okBtns )// 好的
                //
                .setButton2Click ( new OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (dialogBuilder != null) {
                            dialogBuilder.dismiss ( );
                            dialogBuilder = null;
                            dialogCallBackInterface.oklBtn ( );

                        }
                    }
                } );
        if (!dialogBuilder.isShowing ( )) {
            dialogBuilder.show ( );
        }

    }

    /**
     * 只有一个按钮消息框 ， 一个按钮，自定义确定内容
     *
     * @param context 上下文
     */

    private void singleDialog(final Context context,
                              final DialogCallBack dialogcallback) {
        if (dialogBuilder == null)
            dialogBuilder = new NiftyDialogBuilder ( context, R.style.load_dialog );
        dialogBuilder
                .withTitle ( messageTitles )
                .withTitleColor (
                        context.getResources ( ).getColor (
                                R.color.mon_dialog_mesg_color ) )
                .withMessage ( messageContents )
                .withMessageColor (
                        context.getResources ( ).getColor (
                                R.color.dialog_btn_textcolor ) )
                // 内容字体颜色
                .isCancelableOnTouchOutside ( false )
                .isCancelable ( false )
                // 是否点击取消
                .withDuration ( 200 )
                //
                .withEffect ( Effectstype.Fadein )
                // def Effectstype.Slidetop
                .setCustomView ( R.layout.custom_view, context )
                .withButton2Text ( okBtns ).setButton2Click ( new OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if (dialogBuilder != null) {
                    dialogBuilder.dismiss ( );
                    dialogBuilder = null;
                    dialogcallback.oklBtn ( );

                }
            }
        } );
        if (!dialogBuilder.isShowing ( )) {
            dialogBuilder.show ( );
        }
    }

    /**
     * 拨打电话
     *
     * @param context        上下文
     * @param dialogcallback 点击确定按钮回调方法
     */
    public void dialogphoneMolder(Context context,
                                  final DialogCallBack dialogcallback) {
        if (dialogBuilder == null)
            dialogBuilder = new NiftyDialogBuilder ( context, R.style.dialog_untran );
        dialogBuilder
                .withTitle ( messageTitles )
                .withTitleColor (
                        context.getResources ( ).getColor (
                                R.color.mon_dialog_mesg_color ) )
                .withMessage ( messageContents )
                .withMessageColor (
                        context.getResources ( ).getColor (
                                R.color.dialog_btn_textcolor ) )
                // 内容字体颜色
                .isCancelableOnTouchOutside ( false )
                // 是否点击取消
                .withDuration ( 200 )
                //
                .isCancelable ( true )
                .withEffect ( Effectstype.Fadein )
                // def Effectstype.Slidetop
                // .setCustomView(R.layout.custom_view, context)
                // 自定义diaglog内容.setCustomView(View
                // or
                // ResId,context)
                .withButton1Text ( cancelBtns )
                .setButton1Click ( new OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        if (dialogBuilder != null) {
                            dialogBuilder.dismiss ( );
                            dialogBuilder = null;

                        }
                    }
                } ).withButton2Text ( okBtns )// 好的
                .setButton2Click ( new OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        if (dialogBuilder != null) {
                            dialogBuilder.dismiss ( );
                            dialogBuilder = null;
                            dialogcallback.oklBtn ( );

                        }
                    }
                } );
        if (!dialogBuilder.isShowing ( )) {
            dialogBuilder.show ( );
        }

    }


    /**
     * @param context                 上下文
     * @param messagetitle            自定义消息title
     * @param msgContent              自定义消息内容
     * @param dialogCallBackInterface 回调 other 取消 确定
     */
    public void updataVersionDialog(Context context, String messagetitle,
                                    String msgContent, String dialogType, DialogCallBackInterface dialogCallBackInterface) {
        String cancle = "";
        if(!Validator.isStrNotEmpty (dialogType ))
            return;
        if (dialogType.equals ( "0" )) {
            cancle = "立即升级";
        }
        if (dialogType.equals ( "1" )) {
            cancle = "稍后再说";
        }
        if (dialogType.equals ( "2" )) {
            cancle = "不再提醒";
        }
        paramList ( "发现新版本啦！", msgContent, cancle, "立即升级", dialogType );
        dialogUpdata ( context, dialogCallBackInterface );
    }

    /**
     * 版本更新弹框提醒
     *
     * @param context
     * @param dialogCallBackInterface
     */
    public void dialogUpdata(Context context,
                             final DialogCallBackInterface dialogCallBackInterface) {
        if (dialogBuilder == null)
            dialogBuilder = new NiftyDialogBuilder ( context, R.style.dialog_untran, "UPDATA" );
           dialogBuilder.withEffect ( Effectstype.Fadein);
           dialogBuilder.withTitle (messageTitles )
                .withTitleColor (
                        context.getResources ( ).getColor (
                                R.color.button_red ) )
                .withMessage ( messageContents )
                .withMessageColor (
                        context.getResources ( ).getColor (
                                R.color.mon_dialog_mesg_color ) )
                 .isCancelableOnTouchOutside ( false ).isCancelable ( false );

         if (dialogType.equals ( "1" ) || dialogType.equals ( "2" )) {
                        dialogBuilder
                    .withButton1Text ( cancelBtns )
                    .setButton1Click ( new OnClickListener ( ) {
                        @Override
                        public void onClick(View v) {
                            if (dialogBuilder != null) {
                                dialogBuilder.dismiss ( );
                                dialogBuilder = null;
                                dialogCallBackInterface.cancelBtn ( );

                            }
                        }
                    } );
        }
        dialogBuilder.withButton2Text ( okBtns )// 好的
                .setButton2Click ( new OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        if (dialogBuilder != null) {
                            dialogBuilder.dismiss ( );
                            dialogBuilder = null;
                            dialogCallBackInterface.oklBtn ( );
                        }
                    }
                } );
        if (!dialogBuilder.isShowing ( )) {
            dialogBuilder.show ( );
        }
    }
}
