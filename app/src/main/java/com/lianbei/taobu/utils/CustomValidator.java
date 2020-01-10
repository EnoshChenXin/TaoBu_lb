package com.lianbei.taobu.utils;

import android.content.Context;

import com.lianbei.taobu.R;
import com.lianbei.taobu.views.materialedittext.validation.RegexpValidator;

/**
 * Created by HASEE on 2017/5/25.
 */

public class CustomValidator {
    private Context context;

    public static RegexpValidator CustomValidatorPhone(Context context) {
        return new RegexpValidator(context.getResources().getString( R.string.ERROR_MESSAGE_PHONE_INVALID), "^(([1-9][0-9]))\\d{9}$");
    }

    public static RegexpValidator CustomValidatorIsEmpty(Context context) {
        return new RegexpValidator(context.getResources().getString(R.string.STR_NOTEMPTY), "[^\\s]{1,}");
    }
}
