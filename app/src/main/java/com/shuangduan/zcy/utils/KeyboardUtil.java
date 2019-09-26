package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class KeyboardUtil {

    private Activity mActivity;
    /**
     * 隐藏键盘的方法
     *
     * @param context
     */
    public static void hideKeyboard(Activity context) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        // 隐藏软键盘
//        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 关闭activity中打开的键盘
     *
     * @param activity
     */
    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 设置输入框 只能输入数字和小数点
     *
     * @param editText
     */
    public static void RemoveDecimalPoints(EditText editText) {
        //设置输入框 只能输入数字和小数点
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //设置字符过滤（设置小数点只能输入到后两位）
        editText.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            if(source.equals(".") && dest.toString().length() == 0){
                return "0.";
            }
            if(dest.toString().contains(".")){
                int index = dest.toString().indexOf(".");
                int length = dest.toString().substring(index).length();
                if(length == 3){
                    return "";
                }
            }
            return null;
        }});
    }

    /**
     * list转字符串
     *
     * @param list
     */
    public static String getListForString(List list) {
        StringBuilder stringBuffer=new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            if (i==list.size()-1){
                stringBuffer.append(list.get(i));
            }else {
                stringBuffer.append(list.get(i)).append(",");
            }
        }
        return stringBuffer.toString();
    }
}
