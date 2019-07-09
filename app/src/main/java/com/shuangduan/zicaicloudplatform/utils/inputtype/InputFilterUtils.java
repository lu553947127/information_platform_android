package com.shuangduan.zicaicloudplatform.utils.inputtype;

import android.text.InputFilter;
import android.widget.EditText;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/11/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class InputFilterUtils {

    /**
     * 只输入中文
     * @param edt
     */
    public static void chineseFilter(EditText edt){
        edt.setFilters(new InputFilter[]{new ChineseFilter(), new InputFilter.LengthFilter(12)});
    }

//    /**
//     * 只输入英文和数字
//     * @param edt
//     */
//    public static void enFilter(EditText edt, int length){
//        edt.setFilters(new InputFilter[]{new ChineseFilter(), new InputFilter.LengthFilter(length)});
//    }

}
