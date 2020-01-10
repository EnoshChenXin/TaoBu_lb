package com.lianbei.taobu.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hank
 * @create_date 2014-9-25
 * @version:1.0
 * @comment:字符串验证工具类
 * @Copyright: Visionet
 */
public class Validator {

    /**
     * 验证身份证号格式
     *
     * @param id
     * @return
     */
    public static boolean isIdCard(String id) {
        boolean result = false;
        if (isStrNotEmpty ( id )) {
            Pattern pattern = Pattern.compile ( "(^[1-9]((\\d{14})|(\\d{16}[0-9xX]))$)" );
            Matcher macher = pattern.matcher ( id );
            if (macher.find ( )) {
                result = true;
            } else {
                result = false;
            }
        }

//		if (isStrNotEmpty(id)) {
//			if (id.length() == 15) {
//				Pattern pattern = Pattern.compile("[0-9]*");
//				Matcher macher = pattern.matcher(id);
//				if (macher.find()) {
//					result = true;
//				} else {
//					result = false;
//				}
//			} else if (id.length() == 18) {
//				result = true;
//			} else {
//				return false;
//			}
//		}
        return result;
    }

    /**
     * 是否邮箱
     *
     * @param address
     * @return
     */
    public static boolean isEmail(String address) {
        if (!isStrNotEmpty ( address )) {
            return false;
        }

        String[] tokens = address.split ( "@" );

        if (tokens.length != 2) {
            return false;
        }

        for (int i = 0; i < tokens.length; i++) {
            char[] c = tokens[i].toCharArray ( );

            for (int j = 0; j < c.length; j++) {
                if (Character.isWhitespace ( c[j] )) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 字符串非空
     *
     * @param str
     * @return
     */

    public static boolean isStrNotEmpty(String str) {
        if (str == null) {
            return false;
        } else {
            if (TextUtils.isEmpty ( str )) {
                return false;
            }
            if (str.trim ( ).equals ( "" )) {
                return false;
            }
            if (str.trim ( ).equals ( "null" )) {
                return false;
            }
            if(str.trim ( ).length () == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 是否电话号码
     *
     * @param num
     * @return
     */
    public static boolean isPhoneNum(String num) {
        boolean result = false;
        if (isStrNotEmpty ( num )) {
            Pattern p = Pattern.compile ( "^(([1-9][0-9]))\\d{9}$" );
            Matcher m = p.matcher ( num );
            if (m.matches ( )) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 验证码输入长度校验
     *
     * @param num
     * @return
     */
    public static boolean isCheckNum(String num) {
        boolean result = false;
        if (isStrNotEmpty ( num )) {
            Pattern p = Pattern.compile ( "^\\d{6}$" );//：^\d{n}$
            Matcher m = p.matcher ( num );
            if (m.matches ( )) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 验证码最小长度
     *
     * @param min 最小长度
     * @param num 字符串
     * @return
     */
    public static boolean checkMinLength(int min, String num) {
        boolean result = false;
        if (isStrNotEmpty ( num )) {
            Pattern p = Pattern.compile ( "^\\d{" + min + ",}$" );
            Matcher m = p.matcher ( num );
            if (m.matches ( )) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 验证输入内容长度规则 ：长度大于6位的数字或字母
     *
     * @param min 最小长度
     * @param num 字符串
     * @return
     */

    public static boolean checkLoginPassword(int min, String num) {
        boolean result = false;
        Pattern p = Pattern.compile ( "^[0-9A-Za-z]{" + min + ",}$" );
        Matcher m = p.matcher ( num );
        if (m.matches ( )) {
            result = true;
        }
        return result;
    }

    /**
     * 验证输入内容长度规则 ：长度必须是在min和max之类，包括min和max本身
     *
     * @param min 最小长度
     * @param max 最大长度
     * @param num 被检测的字符串
     * @return
     */
    public static boolean checkLenghtInRange(int min, int max, String num) {
        boolean result = false;
        Pattern p = Pattern.compile ( "^[0-9A-Za-z]{" + min + "," + max + "}$" );
        Matcher m = p.matcher ( num );
        if (m.matches ( )) {
            result = true;
        }
        return result;
    }

    public static String MD5(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = str.getBytes ( );
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance ( "MD5" );
            // 使用指定的字节更新摘要
            mdInst.update ( btInput );
            // 获得密文
            byte[] md = mdInst.digest ( );
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char chars[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
                chars[k++] = hexDigits[byte0 & 0xf];
            }
            return new String ( chars );
        } catch (Exception e) {
            e.printStackTrace ( );
            return "";
        }
    }

    /**
     * 判断内容是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {
        if (isStrNotEmpty ( str )) {
            for (char c : str.toCharArray ( )) {
                Pattern p = Pattern.compile ( "[\u4e00-\u9fa5]" );
                Matcher m = p.matcher ( String.valueOf ( c ) );
                if (!m.find ( )) {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * 获取取车时间的星期
     *
     * @param pTime
     * @return
     * @throws Exception
     */
    public static String dayForWeek(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd" );
        Calendar c = Calendar.getInstance ( );
        try {
            c.setTime ( format.parse ( pTime ) );
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ( );
        }
        String daywek = "";
        int dayForWeek = 0;
        if (c.get ( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get ( Calendar.DAY_OF_WEEK ) - 1;
        }
        switch (dayForWeek) {
            case 1:
                daywek = "周一";
                break;
            case 2:
                daywek = "周二";
                break;
            case 3:
                daywek = "周三";
                break;
            case 4:
                daywek = "周四";
                break;
            case 5:
                daywek = "周五";
                break;
            case 6:
                daywek = "周六";
                break;
            case 7:
                daywek = "周日";
                break;
        }
        return daywek;
    }

    public static boolean isNotNull(Object obj) {
        if (obj == null || obj.equals ( "" )) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置内容换行
     * @param strs
     * @return
     */

    public static String getDataContent(String strs) {
        Scanner in = new Scanner ( strs );
        StringBuffer sb = new StringBuffer ( );
        //将处理后的文件output.txt创建到该地址下
        boolean next = true;
        while (in.hasNext ( )) {
            String str = in.nextLine ( );
            //按行读取，遇到换行符停止。将读取到的内容赋值到str中
            str.replace ( " ", "" );
            String Utilstr = "";
            Utilstr = getSubUtilSimple ( str, "1(.*?)2" );
            if (Utilstr.length ( ) > 0 && next) {
                sb.append ( "1" + Utilstr + "\n" );
            } else {
                if (next)
                    sb.append (str.substring ( str.indexOf ( "1" ) ) );
                next = false;
            }
            Utilstr = getSubUtilSimple ( strs, "2(.*?)3" );
            if (Utilstr.length ( ) > 0 && next) {
                sb.append ( "2" + Utilstr + "\n" );
            } else {
                if (next)
                    sb.append ( str.substring ( str.indexOf ( "2" ) ) );
                next = false;
            }
            Utilstr = getSubUtilSimple ( str, "3(.*?)4" );
            if (Utilstr.length ( ) > 0 && next) {
                sb.append ( "3" + Utilstr + "\n" );
            } else {
                if (next)
                    sb.append ( str.substring ( str.indexOf ( "3" ) ) );
                next = false;
            }
            Utilstr = getSubUtilSimple ( str, "4(.*?)5" );
            if (Utilstr.length ( ) > 0 && next) {
                sb.append ( Utilstr + "\n" );
            } else {
                if (next)
                    sb.append ( str.substring ( str.indexOf ( "4" ) ) );
                next = false;
            }
        }
        return sb.toString ( );
    }

    /**
     * 竖杠换行  |
     * @param
     * @param
     * @return
     */
   public static String  getDataContentvertical(String str){
       String[] strs=str.split("\\|");
       StringBuffer sb = new StringBuffer ( );
       for(int i=0,len=strs.length;i<len;i++){
           sb.append ( strs[i].toString()+"\n");
       }
       return sb.toString ();
    }


    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile ( rgex );// 匹配的模式
        Matcher m = pattern.matcher ( soap );
        while (m.find ( )) {
            return m.group ( 1 );
        }
        return "";
    }
    public static boolean isListNotEmpty(List list) {
        return list != null && !list.isEmpty();
    }
}
