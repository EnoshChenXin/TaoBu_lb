package com.lianbei.taobu.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.api.PddParam;

import retrofit2.http.OPTIONS;

/**
 * Created by wangjiangong on 2017/3/15.
 */

public class NavigationView extends LinearLayout {

    private Context context;
    private RelativeLayout navigation_layout;
    private ImageButton btn_left;
    private Button btn_right,auth_btn;
    private TextView nav_title;
    private RelativeLayout message_layout;
    private ImageView message_imageview;
    private TextView message_content;

    private String titleText;
    private int titleTextColor;
    private int titleTextSize;

    private int left_drawable;
    private boolean leftBtnHidden;
    private int right_drawable;
    private String strBtnRight;
    private int rightBtnTextColor;
    private boolean rightBtnHidden;
    private NavigationEmptyView navigationEmptyView;

    private onNavigationBarClickListener navigationBarClickListener;

    //添加按钮点击事件监听
    public static interface onNavigationBarClickListener {

        /**
         * 点击左侧按钮回调
         */
        @OPTIONS
        void onLeftClick();

        /**
         * 点击右侧按钮回调
         */
        @OPTIONS
        void onRightClick();

        /**
         *
         */
        @OPTIONS
        void onRefreshClick();
    }

    public NavigationView(Context context) {
        super(context);
        initDefaultParams(context);
        initView(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultParams(context);
        initAttributes(context, attrs);
        initView(context);
    }

    public void setNaigationLayoutView(int index){
        navigationEmptyView.setVisibility(VISIBLE);
        navigationEmptyView.setContentView(index);
    }

    public void setNavigationBarClickListener(onNavigationBarClickListener clickListener) {
        this.navigationBarClickListener = clickListener;
    }

    public void setTitleText(String titleText) {
        if (null != titleText) {
            nav_title.setText(titleText);
            //方便起见，这里牵扯到业务逻辑
            PddParam.setCustom_parameters(this.getContext(),titleText,"");
        }
    }

    public String getTitleText() {
       return nav_title.getText().toString().trim();
    }

    public void setTitleTextColor(int titleTextColor) {
        this.nav_title.setTextColor(titleTextColor);
    }

    public void setTitleTextSize(int titleTextSize) {
        this.nav_title.setTextSize(titleTextSize);
    }

    public void setLeft_drawable(int left_drawable) {
        if (left_drawable != 0) {
            btn_left.setImageResource(left_drawable);
        } else {
            btn_left.setImageResource( R.mipmap.back);
        }
    }

    public void setLeftBtnHidden(boolean hidden) {
        if (hidden) {
            this.btn_left.setVisibility(INVISIBLE);
        } else {
            this.btn_left.setVisibility(VISIBLE);
        }
    }

    public void setRight_drawable(int right_drawable) {
        if (right_drawable != 0) {
            btn_right.setBackgroundResource(right_drawable);
        }
    }



    public void setStrBtnRight(String strBtnRight) {
        if (null != strBtnRight) {
            btn_right.setText(strBtnRight);
        }
    }

    public void setBtnRightTextColor(int color) {
        btn_right.setTextColor(color);
    }

    public void setRightBtnHidden(boolean hidden) {
        if (hidden) {
            this.btn_right.setVisibility(INVISIBLE);
        } else {
            this.btn_right.setVisibility(VISIBLE);
        }
    }

    public void setMessageViewVisibility(boolean visibility) {
        if (visibility) {
            this.message_layout.setVisibility(VISIBLE);
        } else {
            this.message_layout.setVisibility(GONE);
        }
    }

    public void setMessageViewText(String errorMessage) {
        if (null != errorMessage) {
            message_content.setText(errorMessage);
        }
    }
    //设置页面内容
    public void setCenter_drawable(int center_drawable) {
        if (center_drawable != 0) {
            //   message_imageview.setBackgroundResource(center_drawable);
            message_imageview.setImageDrawable(getResources().getDrawable(center_drawable));
        }
    }
    //设置重新加载uth_btn按钮
    public void isErrorButtonVisibility(boolean visibility){
        if (visibility) {
            this.auth_btn.setVisibility(VISIBLE);
        } else {
            this.auth_btn.setVisibility(GONE);
        }
    }


    public void setCustomViewVisibility(boolean visibility) {
        setMessageViewVisibility(false);
        if (visibility) {
            this.navigationEmptyView.setVisibility(VISIBLE);
        } else {
            this.navigationEmptyView.setVisibility(GONE);
        }
    }

    public void setMessageViewTextColor(int color) {
        if (color != 0) {
            this.message_content.setTextColor(color);
        }
    }

    public void setMessageTextSize(int size) {
        if (size != 0) {
            this.message_content.setTextSize(size);
        }
    }

    private void initDefaultParams(Context context) {
        this.context = context;
        titleText = "";
        //默认黑色字体
        titleTextColor = getResources().getColor( R.color.navigation_text_color);
        titleTextSize = 17;
        left_drawable = 0;
        leftBtnHidden = false;
        right_drawable = 0;
        //默认隐藏右边按钮
        rightBtnHidden = true;
        rightBtnTextColor = getResources().getColor(R.color.text_color_999999);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NavigationView);
            int count = array.getIndexCount();
            for (int index = 0; index < count; index++) {
                int attr = array.getIndex(index);
                switch (attr) {
                    case R.styleable.NavigationView_titleText:
                        titleText = array.getString(R.styleable.NavigationView_titleText);
                        break;
                    case R.styleable.NavigationView_titleColor:
                        //默认黑色字体
                        titleTextColor = array.getColor(R.styleable.NavigationView_titleColor, getResources().getColor(R.color.navigation_text_color));
                        break;
                    case R.styleable.NavigationView_titleSize:
                        //默认设置为17sp
                        titleTextSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_SP, 17, getResources().getDisplayMetrics()));
                        break;
                    case R.styleable.NavigationView_left_drawable:
                        left_drawable = array.getResourceId(R.styleable.NavigationView_left_drawable, 0);
                        break;
                    case R.styleable.NavigationView_left_button_hidden:
                        //默认隐藏左边按钮
                        leftBtnHidden = array.getBoolean(R.styleable.NavigationView_left_button_hidden, false);
                        break;
                    case R.styleable.NavigationView_right_drawable:
                        right_drawable = array.getResourceId(R.styleable.NavigationView_right_drawable, 0);
                        break;
                    case R.styleable.NavigationView_right_text:
                        strBtnRight = array.getString(R.styleable.NavigationView_right_text);
                        break;
                    case R.styleable.NavigationView_right_text_color:
                        rightBtnTextColor = array.getColor(R.styleable.NavigationView_right_text_color, getResources().getColor(R.color.text_color_999999));
                        break;
                    case R.styleable.NavigationView_right_button_hidden:
                        //默认隐藏右边按钮
                        rightBtnHidden = array.getBoolean(R.styleable.NavigationView_right_button_hidden, true);
                        break;
                }
            }
            //释放
            array.recycle();
        }
    }

    private void initView(final Context context) {
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, true);
        navigation_layout = (RelativeLayout) layout.findViewById(R.id.titleLayout);
        btn_left = (ImageButton) layout.findViewById(R.id.linear_back_title_bar);
        btn_right = (Button) layout.findViewById(R.id.btn_title_bar_right);
        auth_btn = (Button) layout.findViewById(R.id.auth_btn);
        nav_title = (TextView) layout.findViewById(R.id.tv_title_bar);
        message_layout = (RelativeLayout) layout.findViewById(R.id.rel_view_error);
        message_imageview = (ImageView) layout.findViewById(R.id.img_pic);
        message_content = (TextView) layout.findViewById(R.id.txt_errormsg);
        navigationEmptyView=(NavigationEmptyView) layout.findViewById(R.id.view_NavigationEmptyView);

        btn_left.setOnClickListener(new OnClickListener () {
            @Override
            public void onClick(View v) {
                navigationBarClickListener.onLeftClick();
            }
        });
        btn_right.setOnClickListener(new OnClickListener () {
            @Override
            public void onClick(View v) {
                navigationBarClickListener.onRightClick();
            }
        });
        message_layout.setOnClickListener(new OnClickListener () {
            @Override
            public void onClick(View v) {
                navigationBarClickListener.onRefreshClick();
            }
        });
        auth_btn.setOnClickListener(new OnClickListener () {
            @Override
            public void onClick(View v) {
                navigationBarClickListener.onRefreshClick();
            }
        });

        //左侧按钮
        setLeftBtnHidden(leftBtnHidden);
        setLeft_drawable(left_drawable);

        //右侧按钮
        setRightBtnHidden(rightBtnHidden);
        setRight_drawable(right_drawable);
        setStrBtnRight(strBtnRight);
        setBtnRightTextColor(rightBtnTextColor);

        //导航栏标题
        setTitleText(titleText);
        setTitleTextColor(titleTextColor);
        setTitleTextSize(titleTextSize);
    }
}
