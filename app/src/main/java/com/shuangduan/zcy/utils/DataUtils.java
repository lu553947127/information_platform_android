package com.shuangduan.zcy.utils;

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
public class DataUtils {
    public static String formatTime(int year, int month, int day){
        String monthString;
        String dayString;
        if (month < 10){
            monthString = "0" + month;
        }else {
            monthString = month + "";
        }
        if (day < 10){
            dayString = "0" + day;
        }else {
            dayString = "" + day;
        }
        return year + "-" + monthString + "-" + dayString;
    }
}
