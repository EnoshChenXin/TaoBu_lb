package com.lianbei.commomview.loadprogress;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianbei.commomview.R;


public class CustomProgress extends Dialog {
	private Context mContext;
	static TextView txt;
	private static CustomProgress dialog = null;
	private ImageView spinnerImageView, successImageView, FailImageView, WarnImageView;
	private LinearLayout ly_loadingView,ly_tipView;

	public CustomProgress(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public CustomProgress(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
		initView();
	}

	private void initView() {

	}

	/**
	 * 当窗口焦点改变时调用
	 */
	public void onWindowFocusChanged(boolean hasFocus) {
		successImageView = (ImageView) dialog.findViewById( R.id.successImageView);
		spinnerImageView = (ImageView) dialog.findViewById(R.id.spinnerImageView);
		FailImageView = (ImageView) dialog.findViewById(R.id.FailImageView);
		WarnImageView = (ImageView) dialog.findViewById(R.id.WarnImageView);

		// 获取ImageView上的动画背景
		if (LoadProgress.status == LoadProgress.DialogStatus.lOAD) {
			AnimationDrawable spinner = (AnimationDrawable) spinnerImageView
					.getBackground();
			// 开始动画
			spinner.start();
		}

	}

	/**
	 * 给Dialog设置提示信息
	 *
	 * @param message
	 */
	public void setMessage(CharSequence message, LoadProgress.DialogStatus status) {
		LoadProgress.status = status;
		successImageView = (ImageView) dialog.findViewById(R.id.successImageView);
		spinnerImageView = (ImageView) dialog.findViewById(R.id.spinnerImageView);
		FailImageView = (ImageView) dialog.findViewById(R.id.FailImageView);
		WarnImageView = (ImageView) dialog.findViewById(R.id.WarnImageView);
		ly_loadingView = (LinearLayout) dialog.findViewById(R.id.ly_loadingView);
		ly_tipView = (LinearLayout) dialog.findViewById(R.id.ly_tipView);
		txt = (TextView) dialog.findViewById(R.id.message);
		if (message != null && message.length() > 0) {
			txt.setText(message);
			txt.invalidate();
		}
		ly_loadingView.setVisibility(View.GONE);
		ly_tipView.setVisibility(View.GONE);
		if (status == LoadProgress.DialogStatus.lOAD) {
			ly_loadingView.setVisibility(View.VISIBLE);
			setDialogWindowColor(0.5f);
			//spinnerImageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.anim_circle_128));
		} else if (status == LoadProgress.DialogStatus.SUCCESS) {
			ly_loadingView.setVisibility(View.GONE);
			ly_tipView.setVisibility(View.VISIBLE);
			successImageView.setVisibility(View.VISIBLE);
			// spinnerImageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.mipmap.tanchukuang_chenggong));
			setDialogWindowColor(0.1f);
		} else if (status == LoadProgress.DialogStatus.FAIL) {
			ly_loadingView.setVisibility(View.GONE);
			ly_tipView.setVisibility(View.VISIBLE);
			FailImageView.setVisibility(View.VISIBLE);
			//spinnerImageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.mipmap.tanchukuang_shibai));
			setDialogWindowColor(0.1f);
		} else if (status == LoadProgress.DialogStatus.WARN) {
			ly_loadingView.setVisibility(View.GONE);
			ly_tipView.setVisibility(View.VISIBLE);
			WarnImageView.setVisibility(View.VISIBLE);
		}
		spinnerImageView.invalidate();
	}

	/**
	 * 弹出自定义ProgressDialog
	 *
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 * @return
	 */
//	 public static CustomProgress show(Context context, CharSequence message,
//	 boolean cancelable, OnCancelListener cancelListener) {
//	 dialog = new CustomProgress(context,
//	 R.style.Custom_Progress);
//	 dialog.setTitle("");
//	 dialog.setContentView(R.layout.progress_custom);
//	 if (message == null || message.length() == 0) {
//	 dialog.findViewById(R.id.message).setVisibility(View.GONE);
//	 } else {
//	 TextView txt = (TextView) dialog.findViewById(R.id.message);
//	 txt.setText(message);
//	 }
//	 // 按返回键是否取消
//	 dialog.setCancelable(cancelable);
//	 // 监听返回键处理
//	 dialog.setOnCancelListener(cancelListener);
//	 // 设置居中
//	 dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//	 WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//	 // 设置背景层透明度
//	 lp.dimAmount = 0.2f;
//	 dialog.getWindow().setAttributes(lp);
//	 //
//	 dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//	 dialog.show();
//	 return dialog;
//	 }
	public static CustomProgress createDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		dialog = new CustomProgress(context, R.style.Custom_Progress);
		dialog.setTitle("");
		txt = (TextView) dialog.findViewById(R.id.message);
//		 txt.setText(message);
		dialog.setContentView(R.layout.progress_custom);
		// 按返回键是否取消
		dialog.setCancelable(cancelable);
		//点击外部不消失
		dialog.setCanceledOnTouchOutside(false);
		// 监听返回键处理
		dialog.setOnCancelListener(cancelListener);
		// 设置居中ia
		dialog.getWindow().getAttributes().gravity = Gravity.TOP;
		dialog.getWindow().setBackgroundDrawableResource(R.color.alph_zero);
		//设置背景透明度
		setDialogWindowColor(0.5f);
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		//设置dialog和软键盘共存
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		return dialog;
	}


	private static void setDialogWindowColor(float dimAmount) {
		//由于手机兼容性，这里不能使用这种方式
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		// 设置背景层透明度
		lp.dimAmount = dimAmount;
		lp.y = 500;
		dialog.getWindow().setAttributes(lp);
	}


//	public void show(String msg) {
//		if (dialog.isShowing()) {
//			dialog.cancel();
//			return;
//		}
//		try {
//			if (Validator.isStrNotEmpty(msg)) {
//				txt.setVisibility(View.VISIBLE);
//				txt.setText("");
//			} else {
//				txt.setVisibility(View.GONE);
//			}
//			dialog.show();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
}
