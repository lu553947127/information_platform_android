package com.shuangduan.zcy.utils;

import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

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

    public static void isPhone(View view){
        String name = Build.MANUFACTURER;
        LogUtils.e(name);
        switch (name) {
            case "HUAWEI":
                getHeight(view,0);
                break;
            case "vivo":
                getHeight(view,65);
                break;
            case "OPPO":
                getHeight(view,65);
                break;
            case "Coolpad":
                getHeight(view,0);
                break;
            case "Meizu":
                getHeight(view,70);
                break;
            case "Xiaomi":
                getHeight(view,30);
                break;
            case "samsung":
                getHeight(view,25);
                break;
            case "Sony":
                getHeight(view,0);
                break;
            case "LG":
                getHeight(view,0);
                break;
            case"OnePlus":
                getHeight(view,0);
                break;
            case"Google":
                getHeight(view,0);
                break;
            default:
                break;
        }
    }

    //设置高度
    public static void getHeight(View view, int height){
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) view.getLayoutParams();
        linearParams.height = height;
        view.setLayoutParams(linearParams);
    }
}
