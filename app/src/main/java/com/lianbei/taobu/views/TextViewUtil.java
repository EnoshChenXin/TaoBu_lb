package com.lianbei.taobu.views;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;

import com.lianbei.taobu.R;
import com.lianbei.taobu.utils.DisplayUtil;

import androidx.core.content.ContextCompat;


/**
 * Created by HASEE on 2017/4/10.
 */

public class TextViewUtil {
    /**
     * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)、
     * Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)、
     * Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)、
     * Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
     */

    public TextViewUtil() {
    }

    /**
     * 设置字体大小，用px
     *
     * @param context
     *
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param pxSize
     *            像素大小
     * @return
     */
    public static  SpannableString getSizeSpanUsePx(Context context, String str, int start, int end, int pxSize) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new AbsoluteSizeSpan(pxSize), 4, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置字体大小，用px
     *
     * @param context
     *
     * @param str
     *            目标字符串
     *            开始位置
     * @param pxSize
     *            像素大小
     * @return
     */
    public static  SpannableString getSizeSpanUsePx(Context context, String str, int pxSize,int color) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new AbsoluteSizeSpan(pxSize), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体前景色
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,color)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置字体大小，用dip
     *
     * @param context
     *
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param dipSize
     *            像素大小
     * @return
     */
    public SpannableString getSizeSpanUseDip(Context context, String str, int start, int end, int dipSize) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new AbsoluteSizeSpan(dipSize, true), 4, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置字体大小，用sp
     *
     * @param context
     *
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param spSize
     *            sp大小
     * @return
     */

    public static SpannableString getSizeSpanSpToPx(Context context, String str, int start, int end, int spSize,int color) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new AbsoluteSizeSpan( DisplayUtil.sp2px(context, spSize)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体前景色
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public SpannableString getSizeSpanSpToPx(Context context, String str,String tagStr, int start, int end, int spSize) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, spSize)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_color666666)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, spSize)), end, tagStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.subject_color)), end, tagStr.length()+end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, spSize)), tagStr.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.text_color333333)), tagStr.length()+end, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;
    }

    /**
     *  订单记录  电量 里程
     * @param context
     * @param str
     * @param tagStr
     * @return
     */
    public SpannableString orderSizeSpanSpToPx(Context context, String str,String tagStr,String end) {
        SpannableString ss = new SpannableString(str);

        ss.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 13)), 0, tagStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_color666666)), 0, tagStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 15)), tagStr.length(), str.length()-end.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.subject_color)), tagStr.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 13)), str.length()-end.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.subject_color)), tagStr.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;

    }
    /**
     * 设置字体相对大小
     *
     * @param context
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param relativeSize
     *            相对大小 如：0.5f，2.0f
     * @return
     */
    public SpannableString getRelativeSizeSpan(Context context, String str, int start, int end, float relativeSize) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new RelativeSizeSpan(relativeSize), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置字体
     *
     * @param context
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param typeface
     *            字体类型 如：default，efault-bold,monospace,serif,sans-serif
     * @return
     */
    public SpannableString getTypeFaceSpan(Context context, String str, int start, int end, String typeface) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new TypefaceSpan(typeface), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置字体形体
     *
     * @param context
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param style
     *            字体类型 如： Typeface.NORMAL正常 Typeface.BOLD粗体 Typeface.ITALIC斜体
     *            Typeface.BOLD_ITALIC粗斜体
     * @return
     */
    public SpannableString getStyleSpan(Context context, String str, int start, int end, int style) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new StyleSpan(style), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置字体下划线
     *
     * @param context
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @return
     */
    public SpannableString getUnderLineSpan(Context context, String str, int start, int end) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置字体删除线
     *
     * @param context
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @return
     */
    public SpannableString getDeleteLineSpan(Context context, String str, int start, int end) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置上标
     *
     * @param context
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @return
     */
    public SpannableString getSuperscriptSpan(Context context, String str, int start, int end) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置放大系数
     *
     * @param context
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param scale
     *            放大多少倍，x轴方向，y轴不变 如：0.5f， 2.0f
     * @return
     */
    public SpannableString getScaleSpan(Context context, String str, int start, int end, float scale) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new ScaleXSpan(scale), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置下标
     *
     * @param context
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @return
     */
    public SpannableString getSubscriptSpan(Context context, String str, int start, int end) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new SubscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置背景色
     *
     * @param context
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param color
     *            颜色值 如Color.BLACK
     * @return
     */
    public SpannableString getBackGroundColorSpan(Context context, String str, int start, int end, int color) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new BackgroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置背景色
     *
     * @param context
     *
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param color
     *            颜色值 如：#CCCCCC
     * @return
     */
    public SpannableString getBackGroundColorSpan(Context context, String str, int start, int end, String color) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new BackgroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置前景色
     *
     * @param context
     *
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param color
     *            颜色值 如Color.BLACK
     * @return
     */
    public SpannableString getForegroundColorSpan(Context context, String str, int start, int end, int color) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }



    /**
     * 设置前景色
     *
     * @param context
     *
     * @param str
     *            目标字符串
     * @param start
     *            开始位置
     * @param end
     *            结束位置
     * @param color
     *            颜色值 如：#CCCCCC
     * @return
     */
    public SpannableString getForegroundColorSpan(Context context, String str, int start, int end, String color) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置多个
     * @param context
     * @param str

     * @param spSize
     * @param color
     * @return
     */
    public static SpannableString getSizeSpanSpToPx2(Context context, String str, int start1, int end1,int start2, int end2, int spSize,int color) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new AbsoluteSizeSpan( DisplayUtil.sp2px(context, spSize)), start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan( DisplayUtil.sp2px(context, spSize)), start2, end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体前景色
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,color)), start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,color)), start2, end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
