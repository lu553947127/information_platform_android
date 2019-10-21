package com.shuangduan.zcy.utils.inputtype;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/11/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ChineseFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern p = Pattern.compile("[a-zA-Z0-9|\u4e00-\u9fa5]+");

        Matcher m = p.matcher(source.toString());

        if (!m.matches()) return "";
        return null;
    }
}
