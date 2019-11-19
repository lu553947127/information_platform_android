package com.shuangduan.zcy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

}
