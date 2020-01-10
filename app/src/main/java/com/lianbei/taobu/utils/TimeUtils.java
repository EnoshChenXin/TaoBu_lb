package com.lianbei.taobu.utils;

import android.app.Activity;
import android.util.Log;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    private static String TAG = "TimeUtils";

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return nowTimeStamp
     */
    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis();
        String nowTimeStamp = String.valueOf(time / 1000);
        return nowTimeStamp;
    }

    /**
     * Java unix时间戳 移除秒毫秒部分
     * @return
     */
    public static Long getCurrentTime() {
        //毫秒时间转成分钟
        double doubleTime = (Math.floor(System.currentTimeMillis() / 60000L));
        //往下取整 1.9=> 1.0
        long floorValue = new Double(doubleTime).longValue();
        return floorValue * 60;
    }

    public static int compare_date(String DATE1, String DATE2) {
        SimpleDateFormat df = new SimpleDateFormat ( "yyyy-MM-dd hh:mm:ss" );
        try {
            Date dt1 = df.parse ( DATE1 );
            Date dt2 = df.parse ( DATE2 );
            if (dt1.getTime ( ) > dt2.getTime ( )) {
                return 1;
            } else if (dt1.getTime ( ) < dt2.getTime ( )) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace ( );
        }
        return -2;
    }

    public static int compare_Stringdate(String DATE1, String DATE2) {
        try {
            if (DATE1.compareTo ( DATE2 ) > 0) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return -2;
    }


    private void time() {
        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy年MM月dd日 HH:mm:ss" );
        Date curDate = new Date ( System.currentTimeMillis ( ) );
        //获取当前时间
        String str = formatter.format ( curDate );
    }

    //字符串转时间戳
    public static String getTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
        Date d;
        try {
            d = sdf.parse ( timeString );
            long l = d.getTime ( );
            timeStamp = String.valueOf ( l );
        } catch (ParseException e) {
            e.printStackTrace ( );
        }
        return timeStamp;
    }



    public static Calendar getCalendar(String timeString){
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        Date d;
        Calendar cal = Calendar.getInstance();
        try {
            d = format.parse ( timeString );
            cal.setTime (d);
        } catch (ParseException e) {
            e.printStackTrace();
            return  null;
        }
        return cal;
    }


    //
    public static String getTime() {
        Date date = new Date ( );
        //当天实时时间
        SimpleDateFormat dff = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
        String nowdata = dff.format ( date );
        if (Validator.isStrNotEmpty ( nowdata )) {
            return nowdata;
        }
        return "2018-11-01 00:00:00";
    }

    public static String getNetTime() {
        URL url = null;  //取得资源对象
        try {
            url = new URL ( "http://www.baidu.com" );
            // url = new URL("http://www.ntsc.ac.cn");
            //中国科学院国家授时中心
            //  url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection ( ); //生成连接对象
            uc.connect ( ); //发出连接
            long ld = uc.getDate ( );//取得网站日期时间
            // 取得网站日期时间
            DateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
            Calendar calendar = Calendar.getInstance ( );
            calendar.setTimeInMillis ( ld );
            final String format = formatter.format ( calendar.getTime ( ) );
            return format;
        } catch (Exception e) {
            e.printStackTrace ( );
            return "2008-01-01 23:59:59";
        }
    }

    private void getLocalTime() {
        URL url = null;
        //取得资源对象
        try {
            DateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
            Calendar calendar = Calendar.getInstance ( );
            calendar.setTimeInMillis ( System.currentTimeMillis ( ) );
            String format = formatter.format ( calendar.getTime ( ) );
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }


    /**
     * 用户操作数据
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    private String setOperationTime = "";


    /**
     * @return
     */
    static int index = 0;
    static int adTaskInt = 0;
    public static int ShowActionWindow(Activity context, String OperationTime) {
        String defaulendtimes = (String) SPUtils.get ( "adtaskendtime", "2008-01-01 23:59:59" );
        if (TimeUtils.compare_Stringdate ( OperationTime, defaulendtimes ) == -1) {//当前时间小于结束时间
            adTaskInt = Integer.valueOf ( SPUtils.get ("adTask", 0 ).toString ( ) );
            return adTaskInt;
        } else if (compare_Stringdate ( OperationTime, defaulendtimes ) == 1) {//当前时间大于结束时间 。新的一天
            String endtime1 = "";
            try {
                if (OperationTime.indexOf ( " " ) != -1) {
                    endtime1 = OperationTime.substring ( 0, OperationTime.indexOf ( " " ) );
                } else {
                    Date date = new Date ( );
                    SimpleDateFormat endtime = new SimpleDateFormat ( "yyyy-MM-dd" );
                    endtime1 = endtime.format ( date );
                }
                String endtimes = endtime1 + " 23:59:59";
                SPUtils.put ("adtaskendtime", endtimes + "" );
                SPUtils.put ("adTask", 3 );
                if (index < 3) {
                    index++;
                    ShowActionWindow ( context, OperationTime );
                }
            } catch (Exception e) {
                e.printStackTrace ( );
                SPUtils.put ("adTask", 3 );
            }
        } else {
            return adTaskInt;
        }
        return adTaskInt;
    }

private void adf(){
    // GregorianCalendar
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    // 设置日期为2011-07-24 09：59:50
    calendar.set(2011, 06, 24, 9, 59, 50);
    // 12小时制
    int temp12Hour = Calendar.HOUR;
    // 24小时制
    int temp24Hour = calendar.HOUR_OF_DAY;
    // 显示年份
    int yearIndex = Calendar.YEAR;
    int year = calendar.get(yearIndex);
    System.out.println("yearIndex=" + yearIndex);
    System.out.println("year=" + year);
    // 显示月份 (从0开始, 实际显示要加一)
    int monthIndex = Calendar.MONTH;
    int month = calendar.get(monthIndex) + 1;
    System.out.println("monthIndex=" + monthIndex);
    System.out.println("month=" + month);
    // 今年的第几天
    int dayOfYearIndex = Calendar.DAY_OF_YEAR;
    int dayOfYear = calendar.get(dayOfYearIndex);
    System.out.println("dayOfYearIndex=" + dayOfYearIndex);
    System.out.println("dayOfYear=" + dayOfYear);
    // 本月的第N天
    int dayOfMonthIndex = Calendar.DAY_OF_MONTH;
    int dayOfMonth = calendar.get(dayOfMonthIndex);
    System.out.println("dayOfMonthIndex=" + dayOfMonthIndex);
    System.out.println("dayOfMonth=" + dayOfMonth);
    // 本周第N天，从周日开始
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    if (dayOfWeek == 0) {
        dayOfWeek = 7;
    }
    System.out.println("dayOfWeek=" + dayOfWeek);
    // 三小时以后
    int hourOfDayIndex = Calendar.HOUR_OF_DAY;
    calendar.add(hourOfDayIndex, 3);
    int afterThreeHour = calendar.get(hourOfDayIndex);
    System.out.println("afterThreeHour=" + afterThreeHour);
    // 当前分钟数
    int minuteIndex = Calendar.MINUTE;
    int minute = calendar.get(minuteIndex);
    System.out.println("minuteIndex=" + minuteIndex);
    System.out.println("minute=" + minute);
    // 15分钟以后
    calendar.add(minuteIndex, 15);
    minute = calendar.get(minuteIndex);
    System.out.println("minute+15=" + minute);
    // 30分钟以前
    calendar.add(minuteIndex, -30);
    minute = calendar.get(minuteIndex);
    System.out.println("minute-30=" + minute);
    // 格式化显示
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
    String time = sdf.format(calendar.getTime());
    System.out.println(time);
    // 重置Calendar显示当前时间
    calendar.setTime(new Date());
    time = sdf.format(calendar.getTime());
    System.out.println(time);
    // 创建一个Calendar 用于比较时间
    Calendar calendarNew = Calendar.getInstance();
    // 设定为5小时以前，后者大，显示-1
    calendarNew.add(Calendar.HOUR, -5);
    System.out.println("时间比较：" + calendarNew.compareTo(calendar));
    // 设定7小时以后，前者大 ，显示1
    calendarNew.add(Calendar.HOUR, +7);
    System.out.println("时间比较：" + calendarNew.compareTo(calendar));
    // 退回2小时，时间相同，显示0
    calendarNew.add(Calendar.HOUR, -2);
    System.out.println("时间比较：" + calendarNew.compareTo(calendar));
    // 创建两个日历对象
    Calendar cal = Calendar.getInstance();
    Calendar future = Calendar.getInstance();
    // 打印当前日期
    System.out.println("Current date: " + cal.getTime());
    // 改变年份
    future.set(Calendar.YEAR, 2066);
    System.out.println("Year is " + future.get(Calendar.YEAR));
    // 检查日期是否在当前日期之后
    if (future.after(cal)) {
        System.out.println("Date " + future.getTime() + " is after current date.");
    }
}
    //时间戳转换成日期格式：方法二UNIX
    public static String getUnixTransferTime(long nowtime,String pattern){
        Date date=new Date(nowtime*1000);
        SimpleDateFormat format=new SimpleDateFormat(pattern);
        String nowDateString=format.format(date);
        return nowDateString;
    }
}