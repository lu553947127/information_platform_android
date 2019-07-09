package com.shuangduan.zicaicloudplatform.utils.inputtype;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/11/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class EnFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern p = Pattern.compile("[a-zA-Z0-9]");

        Matcher m = p.matcher(source.toString());

        if (!m.matches()) return "";
        return null;
    }
}
