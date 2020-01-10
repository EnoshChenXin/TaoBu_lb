package com.lianbei.commomview.loadprogress;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by HASEE on 2017/3/24.
 */

public class LoadProgress {
    public static DialogStatus status = DialogStatus.lOAD;
    private static CustomProgress progressDialog = null;


    public interface dialogDismiss {
        void dialogDismiss();

    }

    /**
     * 单击事件监听器
     */
    private dialogDismiss mListener = null;

    public void setOnTouchClickListener(dialogDismiss listener) {
        mListener = listener;
    }

    private static class SingletonHolder {
        private static final LoadProgress INSTANCE = new LoadProgress();
    }

    private LoadProgress() {

    }

    public static final LoadProgress getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     *
     */
    public enum DialogStatus {
        lOAD,
        SUCCESS,
        FAIL,
        WARN
    }

    /*
     * 开始加载进度条
     */
    public void startProgressDialog(LoadProgress.DialogStatus status, String message, final Context context) {
        if (progressDialog == null) {
            progressDialog = CustomProgress.createDialog(context, true, null);
            if (message == null || message.equals("null") || message.equals("")) {
                progressDialog.setMessage("", status);
            } else {
                progressDialog.setMessage(message, status);
            }
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == event.KEYCODE_BACK) {

                        return true;
                    } else if (keyCode == event.KEYCODE_SEARCH) {
                        return true;
                    }
                    return false;
                }
            });
            progressDialog.show();
        } else {
            progressDialog.show();
        }

    }

    /**
     * 刷新加载框内容
     */
    public void refreshProgressContent(LoadProgress.DialogStatus status, String msg) {
        if (progressDialog != null) {
            progressDialog.setMessage(msg, status);
        }

    }

    /*
     * 停止加载进度条
     */
    public void stopProgressDialog(int type) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
            if (type == 1) {//1 ,通知回调完成提醒
                if (mListener != null) {
                    mListener.dialogDismiss();
                }
            }


        }
    }

    /**
     * 页面输入错误提醒
     */
    public void dialogalert(String message, final Context context) {
        startProgressDialog(DialogStatus.FAIL, message, context);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               LoadProgress.getInstance().stopProgressDialog(0);
                           }

                       },
                500);
    }

    /**
     * 页面输入警告提醒
     */
    public void dialogalert(final Context context, String message, DialogStatus status) {
        startProgressDialog(status, message, context);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               LoadProgress.getInstance().stopProgressDialog(0);
                           }

                       },
                500);
    }
}
