package com.shuangduan.zcy.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils
 * @ClassName: TimeUtils
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/15 11:14
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/15 11:14
 * @UpdateRemark: 更新说明
 * @Version: 1.1.0
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    //元宵节日期
    public static final int THE_LANTERN_FESTIVAL = 20200208;

    //获取当天
    public static String getDayTime() {
        long time_day = System.currentTimeMillis();
        Date date_day = new Date(time_day);
        SimpleDateFormat format_day = new SimpleDateFormat("dd");
        return format_day.format(date_day);
    }

    //获取星期
    public static String getWeekTime() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("E");
        return format.format(date);
    }

    //获取年月
    public static String getYearMonthTime() {
        long time_month = System.currentTimeMillis();
        Date date_month = new Date(time_month);
        SimpleDateFormat format_month = new SimpleDateFormat("MM/yyyy");
        return format_month.format(date_month);
    }

    //获取年月日
    public static String getYearMonthDayTime() {
        long time_day = System.currentTimeMillis();
        Date date_day = new Date(time_day);
        SimpleDateFormat format_day = new SimpleDateFormat("yyyyMMdd");
        return format_day.format(date_day);
    }
}
