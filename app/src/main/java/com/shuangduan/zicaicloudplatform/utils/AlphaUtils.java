package com.shuangduan.zicaicloudplatform.utils;

import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/08/31
 *     desc   : 获取十六进制的透明度
 *     version: 1.0
 * </pre>
 */

public class AlphaUtils {

    public static void getAlpha(){
        for (int i = 0; i <= 100; i++) {
            float temp = 255 * i * 1.0f / 100f;
            int round = Math.round(temp);
            //四舍五入
            String hexString = Integer.toHexString(round);
            if (hexString.length() < 2) {
                hexString += "0";
            }
            LogUtils.i(" 百分比:" + i + "%" + " HEX: " + hexString.toUpperCase());
        }
    }

}
