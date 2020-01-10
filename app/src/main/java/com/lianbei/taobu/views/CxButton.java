package com.lianbei.taobu.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

import com.lianbei.taobu.R;
import com.lianbei.taobu.utils.CustomValidator;
import com.lianbei.taobu.utils.Validator;


/**
 * Created by HASEE on 2017/5/9.
 */
public class CxButton extends Button {
    private mMaterialEditText[] editTexts;
    private boolean isEmpty1 = false; //false:不为空   true  为空 默认为false
    private boolean isEmpty2 = false; //false:不为空   true  为空 默认为false
    private boolean isEmpty3 = false; //false:不为空   true  为空 默认为false
    private Context mContext;

    public CxButton(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public CxButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public CxButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public CxButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
    }

    public void initView() {
        setBackgroundResource( R.drawable.background_button_press);
        EditText d = new EditText (this.getContext());
    }


    public interface setButtonClick {
        void buttonClick();
    }

    public void setButtonClick(setButtonClick buttonClick) {
        this.buttonClick = buttonClick;
    }

    /**
     * 单击事件监听器
     */
    public setButtonClick buttonClick = null;

    /**
     * @param editTexts 传入见EditText 监听列表
     */
    public void setButtonEditTextType(mMaterialEditText[] editTexts) {
        this.editTexts = editTexts;
        setClickable(false);
        for (mMaterialEditText edit : editTexts) {
            edit.addTextChangedListener(TextChange);
        }
    }

    public void setButtonType1(boolean MisEmpty1) {
        this.isEmpty1 = MisEmpty1;
        for (EditText edit : editTexts) {
            if (edit.getText().length() <= 0 || isEmpty1 || isEmpty2 || isEmpty3) {
                setBackgroundResource(R.drawable.background_button_press);
                setClickable(false);
                return;
            } else {
                setBackgroundResource(R.drawable.button_selector);
                setClickable(true);
            }
        }
    }


    public void setButtonType2(boolean MisEmpty2) {
        this.isEmpty2 = MisEmpty2;
        for (EditText edit : editTexts) {
            if (edit.getText().length() <= 0 || isEmpty1 || isEmpty2 || isEmpty3) {
                setBackgroundResource(R.drawable.background_button_press);
                setClickable(false);
                return;
            } else {
                setBackgroundResource(R.drawable.button_selector);
                setClickable(true);
            }
        }
    }

    public void setButtonType3(boolean MisEmpty3) {
        this.isEmpty3 = MisEmpty3;
        for (EditText edit : editTexts) {
            if (edit.getText().length() <= 0 || isEmpty1 || isEmpty2 || isEmpty3) {
                setBackgroundResource(R.drawable.background_button_press);
                setClickable(false);
                return;
            } else {
                setBackgroundResource(R.drawable.button_selector);
                setClickable(true);
            }
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isNotEmpty() {
        boolean valid = true;
        for (mMaterialEditText EditText : editTexts) {
            boolean singvalid = true;
            singvalid = Validator.isStrNotEmpty (EditText.getText ().toString ().trim ());
            if (!singvalid && valid) {
                valid = false;
            }
        }
        return valid;
    }

    public boolean checkInputValid() {
        boolean valid = true;
        for (mMaterialEditText EditText : editTexts) {
            boolean singvalid = true;
            singvalid = validationEt(EditText);
            if (!singvalid && valid) {
                valid = false;

            }
        }
        return valid;
    }

    /**
     * 设置密码
     *
     * @param editText
     */
    private boolean validationEt(mMaterialEditText editText) {
        if (editText.getHint().equals(getResources().getString(R.string.HINT_INPUT_PHONT))) {//手机号
            if (editText.validateWith( CustomValidator.CustomValidatorPhone(mContext))) {
                // Log.e("CustomValidatorPhone", editText.validateWith(CustomValidator.CustomValidatorPhone(mContext)) + "");
                //return true;
                return true;
            } else {
                return false;
            }
        } else if (editText.getHint().equals(getResources().getString(R.string.HINT_INPUT_PASSWORD))) {//密码
            if (editText.validateWith( CustomValidator.CustomValidatorIsEmpty(mContext))) {
                // Log.e("CustomValidatorIsEmpty", editText.validateWith(CustomValidator.CustomValidatorIsEmpty(mContext)) + "");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }



    TextWatcher TextChange = new TextWatcher () {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            for (mMaterialEditText edit : editTexts) {
                if (edit.getText().length() <= 0 || isEmpty1 || isEmpty2 || isEmpty3) {
                    setBackgroundResource(R.drawable.background_button_press);
                    setClickable(false);
                    return;
                } else {
                    setBackgroundResource(R.drawable.button_selector);
                    setClickable(true);
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}

