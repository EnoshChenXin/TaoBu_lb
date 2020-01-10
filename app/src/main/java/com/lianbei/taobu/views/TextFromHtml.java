package com.lianbei.taobu.views;

import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;


/***
 * 设置文字内容格式等，
 */
public class TextFromHtml {
    /**
     * 设置字体内容
     *
     * @param str
     * @return
     */
    public SpannableString spannableStr(String str) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan(50), 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * @param before 前面的文字内容
     * @param middle 中间的文字内容
     * @param later  后面的文字内容
     * @return
     */
    public Spanned fromHtml(String before, String middle, String later) {
        Log.e("fromHtml", "<font color=\"#FF0000\" 'size ='" + beforeTextSize + "'>" + before + "</font>"
                + "<font color='" + middleTextColor + " size='" + middleTextSize + "'>" + middle + "</font>"
                + "<font color= ' " + laterTextColor + "' size ='" + laterTextSize + "'>" + later + "</font>");

        return Html.fromHtml("<big><font color=#ff6600 size=50sp >积分</font></big>"
                + "<font color='" + middleTextColor + " size='" + middleTextSize + "'>" + middle + "</font>"
                + "<font color= ' " + laterTextColor + "' size ='" + laterTextSize + "'>" + later + "</font>");
    }

    /**
     * @param before 前面的文字内容
     * @param later  后面的文字内容
     * @return
     */
    public Spanned fromHtml(String before,  String later) {
        Log.e("fromHtml", "<font color=\"#FF0000\" 'size ='" + beforeTextSize + "'>" + before + "</font>"
                + "<font color= ' " + laterTextColor + "' size ='" + laterTextSize + "'>" + later + "</font>");

        return Html.fromHtml("<big><font color=#ff6600 size=50sp >积分</font></big>"
                + "<font color= ' " + laterTextColor + "' size ='" + laterTextSize + "'>" + later + "</font>");
    }


    /**
     * 前面的字体样式
     */

    String beforeTextColor = "#00FF00";
    String beforeTextSize = "30";
    /**
     * 中间的字体样式
     */
    String middleTextColor = "#00FF00";
    ;
    String middleTextSize = "15";

    /**
     *
     */
    /**
     * 中间的字体样式
     */
    static String laterTextColor = "#00FF00";
    ;
    static String laterTextSize = "15";

    public String getBeforeTextColor() {
        return beforeTextColor;
    }

    public void setBeforeTextColor(String beforeTextColor) {
        this.beforeTextColor = beforeTextColor;
    }

    public String getBeforeTextSize() {
        return beforeTextSize;
    }

    public void setBeforeTextSize(String beforeTextSize) {
        this.beforeTextColor = beforeTextSize;
    }

    public String getMiddleTextColor() {
        return middleTextColor;
    }

    public void setMiddleTextColor(String middleTextColor) {
        this.beforeTextColor = middleTextColor;
    }

    public String getMiddleTextSize() {
        return middleTextSize;
    }

    public void setMiddleTextSize(String middleTextSize) {
        this.beforeTextColor= middleTextSize;
    }

    public static String getLaterTextColor() {
        return laterTextColor;
    }

    public static void setLaterTextColor(String laterTextColor) {
        TextFromHtml.laterTextColor = laterTextColor;
    }

    public static String getLaterTextSize() {
        return laterTextSize;
    }

    public static void setLaterTextSize(String laterTextSize) {
        TextFromHtml.laterTextSize = laterTextSize;
    }
}



























