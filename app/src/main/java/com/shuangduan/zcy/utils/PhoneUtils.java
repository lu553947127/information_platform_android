package com.shuangduan.zcy.utils;

import android.os.Build;

import com.blankj.utilcode.util.LogUtils;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils
 * @ClassName: PhoneUtils
 * @Description: 判断各种机型信息
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/5 17:21
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/5 17:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PhoneUtils {

    public static boolean isPhone(){
        String name = Build.MANUFACTURER;
        LogUtils.e(name);
        switch (name) {
            case "HUAWEI":
                return false;
            case "vivo":
                return false;
            case "OPPO":
                return false;
            case "Coolpad":
                return false;
            case "Meizu":
                return true;
            case "Xiaomi":
                return true;
            case "samsung":
                return false;
            case "Sony":
                return false;
            case "LG":
                return false;
            case"OnePlus":
                return false;
            case"Google":
                return false;
            default:
                return false;
        }
    }
}
