package com.lianbei.commomview.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbei.commomview.R;
import com.lianbei.commomview.dialog.effects.BaseEffects;


/**
 *
 * @author y_chen
 *
 */
public class NiftyDialogBuilder extends Dialog implements DialogInterface {

	private final String defTextColor = "#FFFFFFFF";

	private final String defDividerColor = "#11000000";

	private final String defMsgColor = "#FFFFFFFF";

	private final String defDialogColor = "#FFE74C3C";

	private Effectstype type = null;

	private LinearLayout mLinearLayoutView;

	private RelativeLayout mRelativeLayoutView;

	private LinearLayout mLinearLayoutMsgView;

	private LinearLayout mLinearLayoutTopView;

	private FrameLayout mFrameLayoutCustomView;

	private View mDialogView;

	private View mDivider;

	private TextView mTitle;

	private TextView mMessage;

	private ImageView mIcon;

	private Button mButton1;

	private Button mButton2;
	private RelativeLayout relativeLayout;

	private int mDuration = -1;

	private static int mOrientation = 1;

	private boolean isCancelable = true;
	private Context context;

	private LinearLayout lin_button;
	private  String TYPE = "";

	private volatile static NiftyDialogBuilder instance;

	public NiftyDialogBuilder(Context context) {
		super(context);
		init(context);

	}

	public NiftyDialogBuilder(Context context, int theme) {
		super(context, theme);
		this.context = context;
		TYPE ="";
		init(context);
	}
	public NiftyDialogBuilder(Context context, int theme,String type){
		super(context, theme);
		this.context = context;
		TYPE =type;
		initImage(context,type);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = ViewGroup.LayoutParams.MATCH_PARENT;
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(
				(WindowManager.LayoutParams) params);
//		getWindow().setBackgroundDrawableResource(R.drawable.img_dialog);
		getWindow().setBackgroundDrawableResource(R.color.dialog_background_pressed);
	}

	public static NiftyDialogBuilder getInstance(Context context) {

		int ort = context.getResources().getConfiguration().orientation;
		if (mOrientation != ort) {
			mOrientation = ort;
			instance = null;
		}

		if (instance == null || ((Activity) context).isFinishing()) {
			synchronized (NiftyDialogBuilder.class) {
				if (instance == null) {
					instance = new NiftyDialogBuilder(context,
							R.style.dialog_untran);
				}
			}
		}
		return instance;
	}


	private void init(Context context) {
		mDialogView = View.inflate(context, R.layout.dialog_layout, null);
		mLinearLayoutView = (LinearLayout) mDialogView
				.findViewById(R.id.parentPanel);
		mRelativeLayoutView = (RelativeLayout) mDialogView
				.findViewById(R.id.main);
		mLinearLayoutTopView = (LinearLayout) mDialogView
				.findViewById(R.id.topPanel);
		mLinearLayoutMsgView = (LinearLayout) mDialogView
				.findViewById(R.id.contentPanel);
		mFrameLayoutCustomView = (FrameLayout) mDialogView
				.findViewById(R.id.customPanel);
		lin_button = (LinearLayout) mDialogView.findViewById(R.id.lin_button);
		mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
		mMessage = (TextView) mDialogView.findViewById(R.id.message);
		mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
		mDivider = mDialogView.findViewById( R.id.titleDivider);
		mButton1 = (Button) mDialogView.findViewById(R.id.button1);
		mButton2 = (Button) mDialogView.findViewById(R.id.button2);
		if (mMessage.getLineCount() > 1) {
			mMessage.setGravity(Gravity.LEFT);
		}
		setContentView(mDialogView);

		this.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {

				mLinearLayoutView.setVisibility(View.VISIBLE);
				if (type == null) {
					type = Effectstype.Slidetop;
				}
				start(type);

			}
		});
		mRelativeLayoutView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isCancelable)
					dismiss();
			}
		});
	}
	private void initImage(Context contextm,String types) {
		if(types.equals("UPDATA")){
			mDialogView = View.inflate(context, R.layout.dialog_updata_layout, null);
		}

		mLinearLayoutView = (LinearLayout) mDialogView
				.findViewById(R.id.parentPanel);
		mRelativeLayoutView = (RelativeLayout) mDialogView
				.findViewById(R.id.main);
		mLinearLayoutTopView = (LinearLayout) mDialogView
				.findViewById(R.id.topPanel);
		mLinearLayoutMsgView = (LinearLayout) mDialogView
				.findViewById(R.id.contentPanel);
		mFrameLayoutCustomView = (FrameLayout) mDialogView
				.findViewById(R.id.customPanel);
		lin_button = (LinearLayout) mDialogView.findViewById(R.id.lin_button);
		mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
		mMessage = (TextView) mDialogView.findViewById(R.id.message);
		mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
		mDivider = mDialogView.findViewById( R.id.titleDivider);
		mButton1 = (Button) mDialogView.findViewById(R.id.button1);
		mButton2 = (Button) mDialogView.findViewById(R.id.button2);
		mMessage.setGravity(Gravity.LEFT);
		setContentView(mDialogView);
		this.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {

				mLinearLayoutView.setVisibility(View.VISIBLE);
				if (type == null) {
					type = Effectstype.Slidetop;
				}
				start(type);

			}
		});
		mRelativeLayoutView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isCancelable)
					dismiss();
			}
		});
	}
	public void toDefault() {
		mTitle.setTextColor(Color.parseColor(defTextColor));
		mDivider.setBackgroundColor(Color.parseColor(defDividerColor));
		mMessage.setTextColor(Color.parseColor(defMsgColor));
		mLinearLayoutView.setBackgroundColor(Color.parseColor(defDialogColor));
	}

	@SuppressLint("ResourceAsColor")
	public NiftyDialogBuilder withDividerColor(int colorString) {
		mDivider.setBackgroundColor(context.getResources().getColor(
				R.color.font_color_white));
		return this;
	}

	public NiftyDialogBuilder withTitle(CharSequence title) {
		toggleView(mLinearLayoutTopView, title);
		mTitle.setText(title);
		return this;
	}

	public NiftyDialogBuilder withTitleColor(int colorString) {
		mTitle.setTextColor(colorString);
		return this;
	}

	public NiftyDialogBuilder withMessage(int textResId) {
		toggleView(mLinearLayoutMsgView, textResId);
		mMessage.setText(textResId);
		return this;
	}

	public NiftyDialogBuilder withMessage(CharSequence msg) {
		toggleView(mLinearLayoutMsgView, msg);
		mMessage.setText(msg);

		mMessage.post(new Runnable() {

			@Override
			public void run() {
				int lineCount = mMessage.getLineCount ( );
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT );

				if (getScreenWidth ( context ) > 800) {
//					lp.setMargins(50, 130, 50, 130);
					lp.setMargins ( 50, 25, 50, 25 );
				} else {
//					lp.setMargins(30, 90, 30, 90);
					lp.setMargins ( 30, 15, 30, 15 );
				}
				mMessage.setLayoutParams ( lp );
				if (lineCount > 1) {

					mMessage.setGravity (Gravity.CENTER );
				}
				if (TYPE.equals ("UPDATA")) {
					mMessage.setGravity ( Gravity.LEFT );
					lp.setMargins ( 0, 0, 0, 0 );

					// mMessage.setHeight(300);
				}
			}
		});

		return this;
	}

	public NiftyDialogBuilder withMessageColor(int colorString) {
		mMessage.setTextColor(colorString);
		return this;
	}
	public NiftyDialogBuilder withmButton2Color(int colorString){
		mButton2.setTextColor(colorString);
		return this;
	}


	/**
	 * �����Ļ�߶�
	 *
	 * @param context
	 * @return
	 */
	public int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	public NiftyDialogBuilder withIcon(int drawableResId) {
		mIcon.setImageResource(drawableResId);
		return this;
	}

	public NiftyDialogBuilder withIcon(Drawable icon) {
		mIcon.setImageDrawable(icon);
		return this;
	}

	public NiftyDialogBuilder withDuration(int duration) {
		this.mDuration = duration;
		return this;
	}

	public NiftyDialogBuilder withEffect(Effectstype type) {
		this.type = type;
		return this;
	}

	public NiftyDialogBuilder withButtonDrawable(int resid) {
		mButton1.setBackgroundResource(resid);
		mButton2.setBackgroundResource(resid);
		return this;
	}

	public NiftyDialogBuilder withButton1Text(CharSequence text) {
		lin_button.setVisibility(View.VISIBLE);
		mButton1.setVisibility(View.VISIBLE);
		mButton1.setText(text);

		return this;
	}

	public NiftyDialogBuilder withButton2Text(CharSequence text) {
			lin_button.setVisibility(View.VISIBLE);
		mButton2.setVisibility(View.VISIBLE);
		mButton2.setText(text);
		return this;
	}

	public NiftyDialogBuilder setButton1Click(View.OnClickListener click) {
		mButton1.setOnClickListener(click);
		return this;
	}

	public NiftyDialogBuilder setButton2Click(View.OnClickListener click) {
		mButton2.setOnClickListener(click);
		return this;
	}

	public NiftyDialogBuilder setCustomView(int resId, Context context) {
		View customView = View.inflate(context, resId, null);
		if (mFrameLayoutCustomView.getChildCount() > 0) {
			mFrameLayoutCustomView.removeAllViews();
		}
		mFrameLayoutCustomView.addView(customView);
		return this;
	}

	public NiftyDialogBuilder setCustomView(View view, Context context) {
		if (mFrameLayoutCustomView.getChildCount() > 0) {
			mFrameLayoutCustomView.removeAllViews();
		}
		mFrameLayoutCustomView.addView(view);

		return this;
	}

	public NiftyDialogBuilder isCancelableOnTouchOutside(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCanceledOnTouchOutside(cancelable);
		return this;
	}

	public NiftyDialogBuilder isCancelable(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCancelable(cancelable);
		return this;
	}

	private void toggleView(View view, Object obj) {
		if (obj == null) {
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void show() {
		if (!((Activity) context).isFinishing()) {
			super.show();
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm.isActive()) {
				if (((Activity) context).getCurrentFocus() != null) {
					imm.hideSoftInputFromWindow(((Activity) context)
									.getCurrentFocus().getApplicationWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
	}

	private void start(Effectstype type) {
		BaseEffects animator = type.getAnimator();
		if (mDuration != -1) {
			animator.setDuration(Math.abs(mDuration));
		}
		animator.start(mRelativeLayoutView);
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
				lin_button.setVisibility(View.GONE);
			mButton1.setVisibility(View.GONE);
			mButton2.setVisibility(View.GONE);
		}
	}
}
