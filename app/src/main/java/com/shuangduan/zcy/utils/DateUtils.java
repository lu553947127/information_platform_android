package com.shuangduan.zcy.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.utils
 * @class describe
 * @time 2019/8/19 17:35
 * @change
 * @chang time
 * @class describe
 */
public class DateUtils {

    public static String formatTime(int year, int month, int day) {
        String monthString;
        String dayString;
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = month + "";
        }
        if (day < 10) {
            dayString = "0" + day;
        } else {
            dayString = "" + day;
        }
        return year + "-" + monthString + "-" + dayString;
    }

    /**
     * 获取两个日期之间的间隔天数
     * `* @return 
     */
    @SuppressLint("SimpleDateFormat")
    public static int getGapCount(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            GregorianCalendar cal1 = new GregorianCalendar();
            GregorianCalendar cal2 = new GregorianCalendar();
            cal1.setTime(Objects.requireNonNull(sdf.parse(startDate)));
            cal2.setTime(Objects.requireNonNull(sdf.parse(endDate)));
            return (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24));// 从间隔毫秒变成间隔天数
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getTodayDate(Calendar calendar) {
        //设置时间为今天
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return DateUtils.formatTime(year, month, day);
    }

    public static String getTodayDate() {
        //设置时间为今天
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return DateUtils.formatTime(year, month, day);
    }
}
