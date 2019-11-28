package com.shuangduan.zcy.utils;

import java.text.DecimalFormat;

public class DigitUtils {
    /**
     * double转String,保留小数点后两位
     *
     * @return
     */
    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
