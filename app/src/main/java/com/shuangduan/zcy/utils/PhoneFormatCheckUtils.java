package com.shuangduan.zcy.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils
 * @ClassName: AnimationUtils
 * @Description: 手机号工具类
 * @Author: 徐玉
 * @CreateDate: 2019/10/10 16:57
 * @UpdateUser: 徐玉
 * @UpdateDate: 2019/10/10 16:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PhoneFormatCheckUtils {

    /**
     * 判断手机号
     */
    public static boolean isPhoneLegal(CharSequence str) throws PatternSyntaxException {
        return checkPhone(str);
    }

    private static boolean checkPhone(CharSequence str) throws PatternSyntaxException {
        Pattern p = Pattern.compile(Regular.TEL_PHONE_REG);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //拨打电话
    public static void getCallPhone(Context context, String phone) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:" + phone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
