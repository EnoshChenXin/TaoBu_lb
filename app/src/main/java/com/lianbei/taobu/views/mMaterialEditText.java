package com.lianbei.taobu.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.lianbei.taobu.R;
import com.lianbei.taobu.views.materialedittext.MaterialEditText;

/**
 * Created by HASEE on 2017/3/30.
 */

public class mMaterialEditText extends MaterialEditText {
    public mMaterialEditText(Context context) {
        super(context);
        initView();
    }

    public mMaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public mMaterialEditText(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        initView();
    }

    /**
     * ContextCompat.getColor(Context context, int id)
     */
    private void  initView(){
        /**
        app:met_baseColor="#007688"
        app:met_primaryColor="#2196F3"
        app:met_textColor="@color/text_color"
        app:met_textColorHint="#33ff0000"
        app:met_errorColor="#ddaa00"
        app:met_helperTextColor="#795548"
        app:met_underlineColor="#003587"
                **/
        this.setHintTextColor(this.getResources().getColor( R.color.input_prompt_lineolor_CCCCCC));//text_size_15
        this.setTextColor(this.getResources().getColor(R.color.text_color333333));
        this.setTextSize( TypedValue.COMPLEX_UNIT_SP,15);
        this.setPrimaryColor( ContextCompat.getColor(this.getContext(),R.color.main_red_no));
        this.setUnderlineColor( ContextCompat.getColor(this.getContext(),R.color.light_gray2));
       // this.setBaseColor(this.getResources().getColor(R.color.white));
    }



}
