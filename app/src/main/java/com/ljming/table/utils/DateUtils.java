package com.ljming.table.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Title:DateUtils
 * <p>
 * Description:日期工具类
 * </p>
 * Author Jming.L
 * Date 2017/8/31 10:12
 */
@SuppressWarnings( "deprecation" )
public class DateUtils {

    // 用来全局控制 上一周，本周，下一周的周数变化
    private static int weeks = 0;

    /**
     * 根据日期获取星期
     */
    public static String getWeek(Date date) {
        String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 根据日期获取年
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 根据日期获取月0-11
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 根据日期获取日
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据日期获取小时
     */
    public static int getHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 根据日期获取分钟
     */
    public static int getMinute(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    /**
     * 获取当前日期与本周一相差的天数
     */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 获得上周星期一的日期
     */
    public static String getPreviousMonday() {
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 获得本周星期一的日期
     */
    public static String getCurrentMonday() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 获得本周星期一的日期
     */
    public static int getCurrentMondayTime() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        return monday.getDate();
    }

    /**
     * 获得下周星期一的日期
     */
    public static String getNextMonday() {
        weeks++;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 获得相应周的周日的日期
     */
    public static String getSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 字符串日期格式化为指定格式
     *
     * @param str     日期字符串
     * @param format1 原始格式
     * @param format2 目标格式
     */
    public static String time2Date(String str, String format1, String format2) {
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat(format1);
            SimpleDateFormat dateFormat2 = new SimpleDateFormat(format2);
            return dateFormat2.format(dateFormat1.parse(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串日期转为Date类型
     *
     * @param str    字符串日期
     * @param format 日期格式
     */
    public static Date strToDate(String str, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转换为指定格式字符串
     *
     * @param time   长整形日期
     * @param format 日期格式
     */
    public static String getDate(Long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 日期转换为指定格式字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return 格式化后日期字符串
     */
    public static String getDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 日期字符串转为日期
     *
     * @param str       日期字符串
     * @param formatStr 日期格式
     */
    public static int getDayForFormat(String str, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        // 字符串转为日期
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date != null)
            return date.getDate();
        else
            return -1;
    }

    /**
     * 日期字符串转为星期
     *
     * @param time      日期字符串
     * @param formatStr 日期格式
     */
    public static int getDayForWeek(String time, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        // 字符串转为日期
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date != null)
            return date.getDay();
        else
            return -1;
    }

    /**
     * 日期字符串格式化为Long类型
     *
     * @param str       日期字符串
     * @param formatStr 日期格式
     */
    public static long getDateForFormat(String str, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        // 字符串转为日期
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date != null)
            return date.getTime();
        else
            return -1;
    }

    /**
     * 日期字符串转为小时
     *
     * @param str       日期字符串
     * @param formatStr 日期格式
     */
    public static int getHourForFormat(String str, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        // 字符串转为日期
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date != null)
            return date.getHours();
        else
            return -1;
    }

    /**
     * 日期字符串转月份
     *
     * @param time      日期字符串
     * @param formatStr 日期格式
     */
    public static int getMonthForFormat(String time, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        // 字符串转为日期
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date != null)
            return date.getMonth() + 1;
        else
            return -1;
    }

    /**
     * 获取当前年份
     */
    public static int getYearForSystem() {
        Calendar a = Calendar.getInstance();
        return a.get(Calendar.YEAR);
    }

    /**
     * 相应月份第一天
     */
    public static Date getBeginDayOfMonth(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, offset);
        // 月份第一天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 相应月份最后一天
     */
    public static Date getEndDayOfMonth(int offset) {
        Calendar cal = Calendar.getInstance();
        // 月份最后一天
        cal.add(Calendar.MONTH, offset);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获取去年日期
     */
    public static Date getLastYearDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }

    /**
     * 比较两个日期的先后，第1个日期在第2个日期前返回1，第1个日期在第2个日期后返回-1
     *
     * @param date1 第1个日期
     * @param date2 第2个日期
     */
    public static int compareDate(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;    //"dt1在dt2前"
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;//"dt1在dt2后"
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 比较两个日期的先后，第1个日期在第2个日期前返回1，第1个日期在第2个日期后返回-1
     *
     * @param date1 第1个日期
     * @param date2 第2个日期
     */
    public static int compareDate(Date date1, Date date2) {
        try {
            Date dt1 = date1;
            Date dt2 = date2;
            if (dt1.getTime() > dt2.getTime()) {
                return 1;    //"dt1在dt2前"
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;//"dt1在dt2后"
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 比较两个日期的先后，第1个日期在第2个日期前返回1，第1个日期在第2个日期后返回-1
     *
     * @param date1 第1个日期
     * @param date2 第2个日期
     */
    public static int compareDate(long date1, long date2) {
        try {
            if (date1 > date2) {
                return 1;    //"dt1在dt2前"
            } else if (date1 < date2) {
                return -1;//"dt1在dt2后"
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取指定日期的前N天
     *
     * @param offset 日期偏移量
     * @param before 指定日期
     */
    public static Date getDayBefore(int offset, Date before) {
        Calendar c = Calendar.getInstance();
        c.setTime(before);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - offset);
        return c.getTime();
    }

    /**
     * 获取指定日期的后N天
     *
     * @param offset 日期偏移量
     * @param after  指定日期
     */
    public static Date getDayAfter(int offset, Date after) {
        Calendar c = Calendar.getInstance();
        c.setTime(after);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + offset);
        return c.getTime();
    }

    /**
     * 获取当前日期的前后日期
     *
     * @param offset 日期偏移量，单位为日
     */
    public static Date getDayOffset(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, offset);
        return cal.getTime();
    }

    /**
     * 获取当前日期的前后日期
     *
     * @param offset 日期偏移量，单位为月
     */
    public static Date getYearOffset(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, offset);
        return cal.getTime();
    }

    /**
     * 获取当前日期的前后日期
     *
     * @param offset 日期偏移量，单位为年
     */
    public static Date getMonthOffset(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, offset);
        return cal.getTime();
    }


}
