package com.lianbei.taobu.utils.risenumber;


import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;


import com.lianbei.taobu.R;

import java.text.DecimalFormat;

public class NumberTextView extends TextView implements IRiseNumber{
    private static final int STOPPED = 0;

    private static final int RUNNING = 1;

    private int mPlayingState = STOPPED;

    private float number;

    private float fromNumber;

    private int textColor;           // 数字的颜色
    private int textSize;            // 数字的字号
    private int scale;//小数点后保留几位

    /**
     * 默认时长
     */
    private long duration = 1000;
    /**
     * 1.int 2.float
     */
    private int numberType = 2;

    private DecimalFormat fnum;

    private EndListener mEndListener = null;

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    /**
     * 构造方法
     *
     * @param context
     */
    public NumberTextView(Context context) {
        super(context);
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attr
     */
    public NumberTextView(Context context, AttributeSet attr) {
        super(context, attr);
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.NumberTextView);
        if(array != null){
            textColor = array.getColor( R.styleable.NumberTextView_numberColor, Color.DKGRAY);
            textSize = array.getInt( R.styleable.NumberTextView_numberSize, 40);
        }
        setTextColor(textColor);
        setTextSize(textSize);
    }

    public NumberTextView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.NumberTextView);
        if(array != null){
            textColor = array.getColor( R.styleable.NumberTextView_numberColor, Color.DKGRAY);
            textSize = array.getInt( R.styleable.NumberTextView_numberSize, 40);
        }
        setTextColor(textColor);
        setTextSize(textSize);
    }

    /**
     * 动画是否正在执行
     *
     * @return
     */
    public boolean isRunning() {
        return (mPlayingState == RUNNING);
    }

    /**
     * 浮点型数字变动
     */
    private void runFloat() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(fromNumber, number);
        valueAnimator.setDuration(duration);

        valueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        String temp = "###,###,###,###,###,###,###,##0" ;
                        if ( scale > 0 )
                            temp += ".";
                        for ( int i = 0 ; i < scale ; i++)
                            temp += "0";
                        FloatEvaluator evalutor = new FloatEvaluator();
                        DecimalFormat format = new DecimalFormat(temp);
                        float fraction = valueAnimator.getAnimatedFraction() ;
                        float currentValue = evalutor.evaluate(fraction , fromNumber, number);

                       // setText(format.format(currentValue)) ;
                        setText(format.format(Float.parseFloat(valueAnimator
                                .getAnimatedValue().toString())));

                        if (valueAnimator.getAnimatedFraction() >= 1) {
                            mPlayingState = STOPPED;
                            if (mEndListener != null)
                                mEndListener.onEndFinish();
                        }
                    }

                });

        valueAnimator.start();
    }

    /**
     * 整型数字变动
     */
    private void runInt() {

        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) fromNumber,
                (int) number);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setText(valueAnimator.getAnimatedValue().toString());
                if (valueAnimator.getAnimatedFraction() >= 1) {
                    mPlayingState = STOPPED;
                    if (mEndListener != null)
                        mEndListener.onEndFinish();
                }
            }
        });
        valueAnimator.start();
    }

    static int sizeOfInt(int x) {
        for (int i = 0; ; i=i+10) {
            if (x <= sizeTable[i])
                return i + 10;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fnum = new DecimalFormat ("##0");
    }

    /**
     * 开始动画
     */
    @Override
    public void start() {

        if (!isRunning()) {
            mPlayingState = RUNNING;
            if (numberType == 1)
                runInt();
            else
                runFloat();
        }
    }


    /**
     * 设置数字（整数型）
     * @param number
     */
    @Override
    public void withNumber(int number) {
        this.number = number;
        numberType = 1;
        if (number > 1000) {
            fromNumber = number
                    - (float) Math.pow(10, sizeOfInt((int) number) - 2);
        } else {
            fromNumber = number / 2;
        }
    }

    /**
     * 设置数字（浮点型）
     * @param number
     */
    @Override
    public void withNumber(float number) {

        this.number = number;
        numberType = 2;
        if (number > 1000) {
            fromNumber = number
                    - (float) Math.pow(10, sizeOfInt((int) number) - 1);
        } else {
            fromNumber = number / 2;
        }

    }

    /**
     * 设置 开始 和 结束 数字（整数型）
     * @param fromNumber
     * @param endNumber
     */
    @Override
    public void setFromAndEndNumber(int fromNumber, int endNumber) {
        this.fromNumber = fromNumber;
        this.number = endNumber;
        numberType = 1;
    }

    /**
     * 设置 开始 和 结束 数字（浮点型）
     * @param fromNumber
     * @param endNumber
     */
    @Override
    public void setFromAndEndNumber(float fromNumber, float endNumber) {
        this.fromNumber = fromNumber;
        this.number = endNumber;
        numberType = 2;
    }

    /**
     * 设置动画时长
     */
    @Override
    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public void setScale(int time) {
        this.scale = time;
    }

    @Override
    public void setOnEndListener(EndListener listener) {
        mEndListener = listener;
    }


    /**
     * 动画结束接口
     */
    public interface EndListener {
        /**
         * 动画结束
         */
        public void onEndFinish();
    }
}
